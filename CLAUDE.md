# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Project overview

fast-retire is a personal-finance / portfolio-tracking app with a Spring Boot (Java 25) backend and an
Angular 20 frontend. The backend fetches stock, crypto, and FX-rate prices from external APIs, persists
them, and computes end-of-day portfolio valuations. The Angular app is built and copied into the Spring
Boot jar's static resources so the whole thing ships as a single container/jar.

## Build & run commands

Backend is a composite Gradle build: the root `settings.gradle.kts` does `includeBuild("backend")`, so
always target the `backend` build directly.

```bash
# Build/compile everything
./gradlew -p backend build

# Run the standalone app (serves API + SPA on :8080)
./gradlew -p backend :fast-retire-standalone:bootRun

# Build just the runnable jar
./gradlew -p backend :fast-retire-standalone:bootJar

# Run all tests (currently no test sources exist in any module)
./gradlew -p backend test

# Run tests for a single module
./gradlew -p backend :fast-retire-application:test
```

Local Postgres (required before running the backend):
```bash
docker compose -f postgres_db/docker-compose.yaml up -d
```
This starts Postgres on `localhost:5432` with db `app_db`, user/pass `app_user`/`app_pass` — matching the
values hardcoded in `application-devlocal.yaml` / `application.yaml`. There is no Flyway migration
(`spring.flyway.enabled: false`); the `devlocal` profile uses `ddl-auto: create-drop` so Hibernate creates
the schema from entities on each run, while `default`/`kubernetes` profiles use `ddl-auto: validate`
against schema `ft_dev`.

Run the backend against local Postgres with:
```bash
./gradlew -p backend :fast-retire-standalone:bootRun --args='--spring.profiles.active=devlocal'
```

Frontend (in `frontend/`):
```bash
npm install
npm start          # ng serve with proxy.conf.json → proxies /api to localhost:8080
npm run build       # production build to dist/
npm test            # Karma/Jasmine — no spec files exist yet
```

Full container build (builds Angular, then bundles into the Spring Boot jar as static resources):
```bash
docker build -t kamilkazmierczak/fast-retire:latest .
docker run --network=host -e SPRING_PROFILES_ACTIVE=devlocal kamilkazmierczak/fast-retire:latest
```

## Backend architecture

The backend is split into Gradle modules following a strict api/application/infrastructure layering, and
this pattern is duplicated for the "integration" subsystem:

- **`fast-retire-api`** — pure interfaces/contracts only, no dependencies on other modules.
- **`fast-retire-application`** — JPA entities and repository interfaces for the core domain
  (`trade`, `history`, `currencyrates`, `user`, `portfolioeod`). Depends only on `fast-retire-api`.
- **`fast-retire-integration/fast-retire-integration-api`** — interfaces for external data fetchers/savers
  (`StockFetcher`, `CurrencyRateFetcher`, `CryptocurrencyFetcher`, and their `*Saver` counterparts) plus
  request/response DTOs.
- **`fast-retire-integration/fast-retire-integration-application`** — implementations of the fetcher/saver
  interfaces (each hits a specific external HTTP API), plus `*Controller` REST endpoints and `*Scheduler`
  cron jobs for stocks, crypto, and FX rates.
- **`fast-retire-integration/fast-retire-integration-infrastructure`** — Jackson response deserializers for
  each external API's payload shape, plus the user-portfolio REST endpoint
  (`PortfolioController`) and working-day adjustment logic (`LastWorkingDay*Adjuster`).
- **`fast-retire-infrastructure`** — the composition root. `BeanConfiguration` wires every bean by hand
  (no component scanning/`@Service`/`@Repository` annotations are used anywhere — everything is an
  explicit `@Bean` method), and `@Import`s the other `*Configuration` classes (`JpaConfiguration`,
  `InfrastructureConfiguration`, `EndpointsConfiguration`, `FiltersConfiguration`).
- **`fast-retire-standalone`** — the only module with a `@SpringBootApplication`. It imports
  `BeanConfiguration` and adds `SpaForwardingController`, which forwards any non-file path to
  `index.html` so Angular's client-side router works.

Because everything is manually wired through `BeanConfiguration`, adding a new bean/service means adding
both the class and an explicit `@Bean` method there — it will not be picked up automatically.

Schedulers (`StockScheduler`, `CryptocurrencyScheduler`, `CurrencyRateScheduler`, `PortfolioScheduler`) are
only registered when the `timer.enabled=true` property is set (see the `@ConditionalOnProperty` guards in
`BeanConfiguration`).

Jackson: this codebase uses the `tools.jackson` package (Jackson 3 / "jackson-databind3"), not the classic
`com.fasterxml.jackson`. Watch for this when adding imports.

Lombok is used throughout entities/impls (`@Builder`, `@RequiredArgsConstructor`, `@Getter`, `@Log4j2`,
etc.) — follow existing conventions rather than writing manual boilerplate.

## Frontend architecture

Angular 20 standalone-component app (no `NgModule`s). Structure under `frontend/src/app/`:
- `layouts/main-layout` — the app shell.
- `views/overview` — the main dashboard view, with an `overview-service.ts` for API calls.
- Styling uses Bulma (`bulma.scss` compiled via `npm run build-bulma`) plus SCSS per component.
- Charts use `ng-apexcharts`.

In dev, `npm start` proxies `/api/*` to `localhost:8080` (see `proxy.conf.json`) — always run the backend
locally alongside `ng serve`.

## Known issue to flag, not silently "fix"

`StockFetcherImpl`, `CryptocurrencyFetcherImpl`, and `CurrencyRateFetcherImpl` (in
`fast-retire-integration-application`) currently have external API keys hardcoded as string literals
rather than sourced from configuration/secrets. If you touch these files, flag this to the user rather
than assuming it's intentional.

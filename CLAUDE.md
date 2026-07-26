# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Project overview

fast-retire is a personal portfolio-tracking app: a Spring Boot 4.0.1 backend (Java 25) plus an Angular 20
frontend. The backend pulls stock/crypto prices from AlphaVantage and FX rates from fxratesapi, stores them
as daily `History` / `CurrencyRate` rows, and derives per-asset end-of-day valuations (`PortfolioEod`) from a
user's `Trade` list. The Angular app is built and copied into the Spring Boot jar's static resources, so the
whole product ships as one jar/container.

`AGENTS.md` is a symlink to this file.

## Build & run commands

Root is a Gradle composite build (`includeBuild("backend")`) and the root `build.gradle.kts` is intentionally
empty — **always pass `-p backend`**; task paths like `:fast-retire-standalone:bootRun` do not exist at the root.

```bash
./gradlew -p backend build                                # compile + test everything
./gradlew -p backend :fast-retire-standalone:bootRun      # run API + SPA on :8080
./gradlew -p backend :fast-retire-standalone:bootJar      # runnable jar
./gradlew -p backend test                                 # all tests
./gradlew -p backend :fast-retire-application:test        # one module
./gradlew -p backend :fast-retire-application:test --tests 'fast.retire.application.portfolioeod.*Test'
```

There are currently **no test sources in any backend module and no `*.spec.ts` in the frontend**, so every
`test` task is a no-op — a green `test` run proves nothing.

Local Postgres (required before starting the backend):

```bash
docker compose -f postgres_db/docker-compose.yaml up -d
```

Starts Postgres 16 on `localhost:5432`, db `app_db`, user/pass `app_user`/`app_pass` — matching the values
hardcoded in every `application*.yaml`. Run the backend against it with the `devlocal` profile:

```bash
./gradlew -p backend :fast-retire-standalone:bootRun --args='--spring.profiles.active=devlocal'
```

Frontend (`cd frontend`):

```bash
npm install
npm start                       # ng serve, proxies /api → localhost:8080 (proxy.conf.json)
npm run build                   # → dist/fast-retire-frontend/browser/
npm test                        # Karma/Jasmine
npm test -- --include='**/overview.spec.ts'   # single spec
npm run build-bulma             # recompile bulma.scss → bulma.css (manual, not part of build)
```

Full container build (stage 1 builds Angular, stage 2 copies `dist/` into
`fast-retire-standalone/src/main/resources/static/` then builds the jar):

```bash
docker build -t kamilkazmierczak/fast-retire:latest .
docker run --network=host -e SPRING_PROFILES_ACTIVE=devlocal kamilkazmierczak/fast-retire:latest
```

## Backend architecture

Gradle modules enforce an api/application/infrastructure layering, duplicated for the "integration"
subsystem. Package roots mirror module names (`fast.retire.application`, `fast.retire.integration.api`, …).

- **`fast-retire-api`** — currently **empty** (no sources, empty build file), though other modules depend on
  it. It's the intended home for shared contracts.
- **`fast-retire-application`** — the core domain: JPA entities + Spring Data repositories under `trade`,
  `history`, `currencyrates`, `user`, `portfolioeod`, plus `PortfolioEodService`, `PortfolioEodGenerator`
  (walks each asset's trades day-by-day from first trade to today, prices them via `History` and converts
  with `CurrencyRate`), `PortfolioChangeCalculator`, and `PortfolioScheduler`.
- **`fast-retire-integration/…-api`** — fetcher/saver interfaces (`StockFetcher`, `CurrencyRateFetcher`,
  `CryptocurrencyFetcher`, matching `*Saver`s) and their request/response DTOs.
- **`fast-retire-integration/…-application`** — fetcher/saver impls (each calls one external HTTP API via
  `RestTemplate`), the `/integration/**` controllers, and the price-sync schedulers.
- **`fast-retire-integration/…-infrastructure`** — Jackson deserializers for each external payload shape, the
  `/api/portfolio/**` controller + its response DTOs, and the `LastWorkingDay*Adjuster` `TemporalAdjuster`s
  used to pick day/week/month-ago comparison dates.
- **`fast-retire-infrastructure`** — the composition root: `BeanConfiguration` plus the `@Import`ed
  `JpaConfiguration` (`@EnableJpaRepositories`/`@EntityScan` on `fast.retire.application`),
  `InfrastructureConfiguration` (`JsonMapper`, `RestTemplate`), `EndpointsConfiguration` (controllers as
  beans), `FiltersConfiguration` (`TraceIdResponseFilter`).
- **`fast-retire-standalone`** — the only `@SpringBootApplication` (also `@EnableScheduling`). Holds all
  `application*.yaml` and the Flyway migrations, and adds `SpaForwardingController`, which forwards any
  extension-less path to `index.html` for Angular's router.

### Conventions that will bite you

- **No component scanning.** There are no `@Service`/`@Repository`/`@Component` annotations anywhere;
  controllers aren't scanned either. Every bean is an explicit `@Bean` method in `BeanConfiguration` or one of
  the imported `*Configuration` classes. A new class is invisible until you wire it there — and new
  controllers belong in `EndpointsConfiguration`.
- **Jackson 3.** Imports are `tools.jackson.*`, not `com.fasterxml.jackson.*`.
- **Lombok everywhere** (`@Builder`, `@RequiredArgsConstructor`, `@Getter`, `@Log4j2`). Follow suit rather than
  hand-writing boilerplate. `-parameters` is on for all subprojects.
- **Schedulers are opt-in**: `StockScheduler`, `CryptocurrencyScheduler`, `CurrencyRateScheduler`,
  `PortfolioScheduler` are guarded by `@ConditionalOnProperty(name = "timer.enabled", havingValue = "true")`.
  Their `@Scheduled(cron = "${timer.*.cron}")` placeholders are **not defined in any yaml**, so enabling
  `timer.enabled=true` also requires supplying `timer.synchronize-stock-price.cron`,
  `timer.synchronize-crypto-price.cron`, `timer.synchronize-currency-rate.cron` and
  `timer.regenerate-portfolio.cron` or startup fails.
- **URL prefixes matter for dev.** Only `/api/**` is proxied by `ng serve`; the integration endpoints live at
  `/integration/{stocks,cryptocurrencies,currency-rates}` and must be called against `localhost:8080` directly.

### Database / schema

`spring.flyway.enabled: false` in **all three** profiles, yet `V1__..`–`V6__..` migrations exist under
`fast-retire-standalone/src/main/resources/db/migration/` — they are never applied automatically and must be
kept in sync with the entities by hand.

- `devlocal` — `ddl-auto: create-drop`, no `currentSchema` (uses `public`); Hibernate rebuilds the schema on
  every run, so data does not survive a restart.
- default / `kubernetes` — `ddl-auto: validate` against `?currentSchema=ft_dev`; the schema and tables must
  already exist (run the migrations manually), or startup fails validation.

## Frontend architecture

Angular 20, standalone components only (no `NgModule`s), **zoneless** change detection
(`provideZonelessChangeDetection()` in `app.config.ts`) — avoid patterns that rely on zone-triggered refresh;
prefer signals. Layout: `layouts/main-layout` is the shell, `views/overview` is the single route (`''`), with
`overview-service.ts` holding the HTTP calls and the response interfaces. `environment.apiUrl` is `'/api'` in
both dev and prod. Styling is Bulma + per-component SCSS; charts via `ng-apexcharts` (apexcharts is also
injected as a global script in `angular.json`).

## Issues to flag rather than silently work around

- `StockFetcherImpl`, `CryptocurrencyFetcherImpl`, `CurrencyRateFetcherImpl` have live external API keys as
  `private static final String API_KEY` literals; DB credentials are hardcoded in the yaml files. If you touch
  these files, raise it with the user instead of assuming it's intentional.
- `InfrastructureConfiguration.jsonMapper()` builds a `SimpleModule` with the three custom `*ResponseDeserializer`s
  but never registers it on the returned `JsonMapper`, so those deserializers are effectively unused.

## Agent skills

### Issue tracker

Issues and specs live as markdown files under `.scratch/<feature-slug>/` in this repo — no external tracker.
See `docs/agents/issue-tracker.md`.

### Triage labels

The five canonical triage roles used as-is (`needs-triage`, `needs-info`, `ready-for-agent`,
`ready-for-human`, `wontfix`), written to a `Status:` line in each issue file.
See `docs/agents/triage-labels.md`.

### Domain docs

Single-context: `CONTEXT.md` + `docs/adr/` at the repo root (neither exists yet; created lazily).
See `docs/agents/domain.md`.

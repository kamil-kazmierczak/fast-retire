package fast.retire.infrastructure.filters;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.MDC;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

public class TraceIdResponseFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {


        String traceId = MDC.get("traceId");
        String spanId = MDC.get("spanId");

        if (traceId != null) {
            response.addHeader("X-Trace-Id", traceId);
        }

        if (spanId != null) {
            response.addHeader("X-Span-Id", spanId);
        }


        filterChain.doFilter(request, response);

    }
}

package com.urlshortener.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;
import java.util.Map;

/**
 * HealthController — lightweight liveness check.
 *
 * WHY this exists separately from /actuator/health:
 * - /actuator/health checks DB, Redis, Kafka connectivity (heavy)
 * - /health just checks "is the JVM alive?" (ultra-lightweight)
 * - Load balancers hit this every 10 seconds — keep it instant
 *
 * In production: Kubernetes liveness probe points to this endpoint.
 * If it returns non-200, Kubernetes restarts the pod.
 */
@RestController
@RequestMapping("/health")
public class HealthController {

    // Always use SLF4J interface (Logger), never a concrete implementation
    // This lets you swap Logback → Log4j2 without touching this code
    private static final Logger log = LoggerFactory.getLogger(HealthController.class);

    /**
     * GET /api/v1/health
     *
     * ResponseEntity<T> gives full HTTP control:
     * - ResponseEntity.ok()        → 200 OK
     * - ResponseEntity.notFound()  → 404
     * - ResponseEntity.status(429) → custom status
     *
     * Interview: Why ResponseEntity instead of returning Map directly?
     * Direct return always gives 200. ResponseEntity lets you set
     * the correct status code explicitly — important for error scenarios.
     */
    @GetMapping
    public ResponseEntity<Map<String, Object>> health() {
        log.info("Health check endpoint called");

        // Map.of() — immutable map, Java 9+
        // Immutable = thread safe, no accidental mutation after creation
        // Instant.now() — always UTC, never LocalDateTime for APIs
        Map<String, Object> response = Map.of(
                "status", "UP",
                "service", "url-shortener-platform",
                "timestamp", Instant.now().toString(),
                "version", "1.0.0"
        );

        return ResponseEntity.ok(response);
    }
}

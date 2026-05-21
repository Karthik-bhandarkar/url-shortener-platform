package com.urlshortener.service;

import com.urlshortener.dto.request.ShortenUrlRequest;
import com.urlshortener.dto.response.UrlResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.Instant;

/**
 * UrlService — core business logic for URL shortening operations.
 *
 * @Service tells Spring to:
 *   1. Create one instance of this class (singleton by default)
 *   2. Make it available for @Autowired / constructor injection
 *   3. Apply AOP (transactions, logging) when annotated
 *
 * WHY singleton? One instance shared across all requests.
 * This is safe because services should be STATELESS —
 * no instance variables that change per request.
 *
 * CURRENT STATE: Returns stub data (no DB yet).
 * Phase 2 will replace stubs with real PostgreSQL operations.
 * The controller will NOT change — only this service changes.
 * That's the power of layered architecture.
 */
@Service
public class UrlService {

    private static final Logger log = LoggerFactory.getLogger(UrlService.class);

    // Base URL for generating short links
    // In Phase 5, this moves to application.yml (externalised config)
    private static final String BASE_URL = "https://short.ly/";

    /**
     * Shorten a long URL into a short code.
     *
     * Current implementation: returns stub data.
     * Phase 2 implementation will:
     *   1. Check if URL already shortened (idempotency)
     *   2. Generate Base62 short code
     *   3. Save to PostgreSQL
     *   4. Cache in Redis
     *   5. Return real UrlResponse
     *
     * @param request validated DTO from controller
     * @return UrlResponse with short code and metadata
     */
    public UrlResponse shortenUrl(ShortenUrlRequest request) {
        log.debug("Shortening URL: {}", request.originalUrl());

        // STUB: hardcoded for now — Phase 2 replaces this
        // Using Java record's auto-generated accessor: request.originalUrl()
        // Records use method-style accessors, not getXxx() style
        String shortCode = "abc123";
        String shortUrl = BASE_URL + shortCode;

        log.info("URL shortened successfully. shortCode={}", shortCode);

        // Return immutable record — all fields set in constructor
        return new UrlResponse(
                shortCode,
                shortUrl,
                request.originalUrl(),
                Instant.now(),     // createdAt — current UTC time
                null,              // expiresAt — null means never expires
                0L                 // clickCount — zero on creation
        );
    }

    /**
     * Retrieve URL details by short code.
     *
     * Current: stub returning fake data.
     * Phase 2: DB lookup + cache check.
     *
     * @param shortCode the short identifier (e.g. "abc123")
     * @return UrlResponse with full details
     */
    public UrlResponse getUrlByShortCode(String shortCode) {
        log.debug("Fetching URL for shortCode: {}", shortCode);

        // STUB: Phase 2 replaces with real DB lookup
        return new UrlResponse(
                shortCode,
                BASE_URL + shortCode,
                "https://example.com/very-long-original-url",
                Instant.now(),
                null,
                42L
        );
    }
}

package com.urlshortener.controller;

import com.urlshortener.dto.request.ShortenUrlRequest;
import com.urlshortener.dto.response.UrlResponse;
import com.urlshortener.service.UrlService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * UrlController — HTTP layer for URL shortening operations.
 *
 * Responsibilities (ONLY these three):
 *   1. Map HTTP endpoints to service methods
 *   2. Enforce input validation via @Valid
 *   3. Return correct HTTP status codes
 *
 * What controllers must NEVER do:
 *   - Business logic (belongs in Service)
 *   - Database queries (belongs in Repository)
 *   - Data transformation logic (belongs in Service or Mapper)
 *
 * @RestController = @Controller + @ResponseBody
 * Every method return value is automatically serialised to JSON.
 *
 * @RequestMapping("/urls") — all endpoints in this class start with /api/v1/urls
 * (the /api/v1 prefix comes from application.yml context-path)
 */
@RestController
@RequestMapping("/urls")
public class UrlController {

    private static final Logger log = LoggerFactory.getLogger(UrlController.class);

    // Constructor injection — the ONLY correct way to inject dependencies
    // WHY not @Autowired on field?
    //   1. Can't be made final (immutability)
    //   2. Hidden dependencies (hard to test)
    //   3. Spring itself recommends constructor injection
    // With one constructor, Spring auto-injects without @Autowired annotation
    private final UrlService urlService;

    public UrlController(UrlService urlService) {
        this.urlService = urlService;
    }

    /**
     * POST /api/v1/urls
     * Shorten a new URL.
     *
     * @Valid triggers Hibernate Validator on the request body.
     * If validation fails, Spring throws MethodArgumentNotValidException
     * BEFORE this method is called — the controller never even runs.
     * Phase 3 will add a @ControllerAdvice to handle that exception properly.
     *
     * Returns 201 CREATED (not 200 OK) because we're creating a new resource.
     * This is the correct REST semantics — most juniors return 200 for everything.
     *
     * @param request validated request body
     * @return 201 with UrlResponse body
     */
    @PostMapping
    public ResponseEntity<UrlResponse> shortenUrl(@Valid @RequestBody ShortenUrlRequest request) {
        log.info("POST /urls request received for: {}", request.originalUrl());

        UrlResponse response = urlService.shortenUrl(request);

        // 201 Created — correct status for resource creation
        // ResponseEntity.status(HttpStatus.CREATED).body(response) does the same
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    /**
     * GET /api/v1/urls/{shortCode}
     * Retrieve URL details by short code.
     *
     * @PathVariable extracts {shortCode} from the URL path.
     * Example: GET /api/v1/urls/abc123 → shortCode = "abc123"
     *
     * Note: This endpoint returns URL DETAILS (JSON).
     * The actual REDIRECT (HTTP 302) will be a separate endpoint
     * at GET /{shortCode} (no /api/v1 prefix) added in Phase 2.
     * Why separate? The redirect is public, ultra-fast, and cached.
     * The details endpoint requires authentication (Phase 3).
     *
     * @param shortCode the short identifier from URL path
     * @return 200 with UrlResponse body
     */
    @GetMapping("/{shortCode}")
    public ResponseEntity<UrlResponse> getUrl(@PathVariable String shortCode) {
        log.info("GET /urls/{} request received", shortCode);

        UrlResponse response = urlService.getUrlByShortCode(shortCode);

        return ResponseEntity.ok(response);
    }

    /**
     * DELETE /api/v1/urls/{shortCode}
     * Delete a shortened URL.
     *
     * Returns 204 No Content — correct REST semantics for deletion.
     * 204 means "success, but I have nothing to return."
     *
     * Phase 3 will add: only the URL owner can delete their URL (JWT auth).
     *
     * @param shortCode the short identifier to delete
     * @return 204 No Content
     */
    @DeleteMapping("/{shortCode}")
    public ResponseEntity<Void> deleteUrl(@PathVariable String shortCode) {
        log.info("DELETE /urls/{} request received", shortCode);

        // STUB: Phase 2 will call urlService.deleteUrl(shortCode)
        // Returns Void because 204 has no response body
        return ResponseEntity.noContent().build();
    }
}

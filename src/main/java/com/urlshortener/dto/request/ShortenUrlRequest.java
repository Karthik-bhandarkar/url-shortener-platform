package com.urlshortener.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

/**
 * ShortenUrlRequest — the body of POST /api/v1/urls
 *
 * WHY a Java Record (not a class)?
 * Records (Java 16+) are immutable data carriers.
 * They auto-generate: constructor, getters, equals(), hashCode(), toString()
 * Perfect for DTOs because request data should NEVER be mutated after parsing.
 *
 * WHY validation annotations here (not in the controller)?
 * Validation is part of the CONTRACT for this DTO.
 * Keeping it here means any controller using this DTO gets validation for free.
 *
 * Interview: "Where do you put validation logic?"
 * Answer: On the DTO with JSR-380 annotations, enforced by @Valid in the controller.
 * Hibernate Validator is the JSR-380 implementation Spring Boot auto-configures.
 */
public record ShortenUrlRequest(

        // @NotBlank: fails on null, "", and "   " (blank string)
        // @NotNull only fails on null — use @NotBlank for strings
        @NotBlank(message = "URL must not be blank")

        // Must start with http:// or https:// — prevents javascript: injection
        // This is an OWASP security requirement (open redirect vulnerability)
        @Pattern(
                regexp = "^https?://.*",
                message = "URL must start with http:// or https://"
        )

        // Reasonable URL length limit
        @Size(max = 2048, message = "URL must not exceed 2048 characters")
        String originalUrl,

        // Optional: user can provide a custom alias like "my-link"
        // null means we auto-generate a short code
        @Size(min = 3, max = 20, message = "Custom alias must be 3-20 characters")
        String customAlias

) {}

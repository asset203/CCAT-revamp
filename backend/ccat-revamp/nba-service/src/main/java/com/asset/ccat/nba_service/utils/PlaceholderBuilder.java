package com.asset.ccat.nba_service.utils;


import java.util.HashMap;
import java.util.Map;

/**
 * @author Mayar Ezz el-Din
 */
public class PlaceholderBuilder {
    private final Map<String, String> placeholders = new HashMap<>();

    /**
     * Adds a placeholder and its value.
     *
     * @param placeholder The placeholder key to replace.
     * @param value       The value to replace the placeholder with.
     * @return The current UrlPlaceholderBuilder object (for chaining).
     */
    public PlaceholderBuilder addPlaceholder(String placeholder, String value) {
        placeholders.put(placeholder, value);
        return this;
    }

    /**
     * Adds a placeholder conditionally if the value is not null or blank.
     *
     * @param placeholder The placeholder key to replace.
     * @param value       The value to replace the placeholder with.
     * @return The current UrlPlaceholderBuilder object (for chaining).
     */
    public PlaceholderBuilder addOptionalPlaceholder(String placeholder, String value) {
        if (value != null && !value.isBlank())
            placeholders.put(placeholder, value);
        return this;
    }

    /**
     * Replaces all placeholders in the base URL.
     *
     * @param baseUrl The base URL containing placeholders.
     * @return The URL with all placeholders replaced by their values.
     */
    public String buildUrl(String baseUrl) {
        for (Map.Entry<String, String> entry : placeholders.entrySet())
            baseUrl = baseUrl.replace(entry.getKey(), entry.getValue());
        return baseUrl;
    }
}

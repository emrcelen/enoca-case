package com.enoca.emrecelen.utilities;

import org.springframework.util.AntPathMatcher;

import java.util.Set;

public class FilterUtils {
    private static Set<String> antPatterns = Set.of(
            "/login",
            "/register",
            "/v3/api-docs/**",
            "/v3/api-docs.yaml",
            "/swagger-ui/**",
            "/swagger-ui.html",
            "/v2/api-docs/**",
            "/swagger-resources",
            "/swagger-resources/**",
            "/swagge-ui/**",
            "/configuration/ui",
            "/configuration/security",
            "/webjars/**"
    );

    public static boolean isExcludePath(String path) {
        var matcher = new AntPathMatcher();
        return antPatterns.stream().anyMatch(pattern -> matcher.match(pattern, path));
    }

    public static Set<String> getAntPatterns() {
        return antPatterns;
    }
}

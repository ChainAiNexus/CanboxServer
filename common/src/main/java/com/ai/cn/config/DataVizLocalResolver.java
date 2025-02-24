package com.ai.cn.config;

import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.AcceptHeaderLocaleResolver;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

/**
 *          http    language
 */
public class DataVizLocalResolver extends AcceptHeaderLocaleResolver implements WebMvcConfigurer {
    List<Locale> LOCALES = Arrays.asList(
            new Locale("en"),
            new Locale("zh","CN"),
            new Locale("zh","TW"),
            new Locale("ja"),
            new Locale("ko"),
            new Locale("vi"),
            new Locale("ru"));

    @Override
    public Locale resolveLocale(HttpServletRequest request) {
        String headerLang = request.getHeader("lang");

        return headerLang == null || headerLang.isEmpty()
                ? Locale.CANADA
                : Locale.lookup(Locale.LanguageRange.parse(headerLang), LOCALES);
    }
}

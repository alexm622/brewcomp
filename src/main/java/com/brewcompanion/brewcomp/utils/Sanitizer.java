package com.brewcompanion.brewcomp.utils;

import org.jsoup.Jsoup;
import org.jsoup.safety.Safelist;

import com.brewcompanion.brewcomp.Main;

/**
 * Sanitizer
 */
public class Sanitizer {

    private static Safelist basic = Safelist.basic();
    private static Safelist relaxed = Safelist.relaxed();

    private Sanitizer() {
        // private constructor to hide the implicit public one
    }

    /**
     * Sanitizes HTML input using JSoup
     * @param input
     * @return
     */
    public static String sanitizeHTML(String input){
        String cleaned = Jsoup.clean(input, basic);
        Main.getLogger().debug("Sanitized HTML: " + cleaned);
        return cleaned;
    }

    /**
     * Sanitizes HTML input using JSoup
     * @param input
     * @return
     */
    public static String sanitizeHTMLRelaxed(String input){
        String cleaned = Jsoup.clean(input, relaxed);
        Main.getLogger().debug("Sanitized HTML: " + cleaned);
        return cleaned;
    }
}
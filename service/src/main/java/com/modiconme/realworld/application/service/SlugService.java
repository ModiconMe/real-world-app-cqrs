package com.modiconme.realworld.application.service;

import org.springframework.stereotype.Service;

import java.text.Normalizer;
import java.util.Locale;
import java.util.regex.Pattern;

@Service
public class SlugService {

    private static final Pattern NONLATIN = Pattern.compile("[^\\w_-]"); // not letter or _ or -

    public String createSlug(String title) {
        String normalized = Normalizer.normalize(title, Normalizer.Form.NFD);
        String slug = NONLATIN.matcher(normalized).replaceAll("-");
        return slug.toLowerCase(Locale.ENGLISH)
                .replaceAll("-{2,}|_","-") // -- OR _
                .replaceAll("^-|-$",""); // -start(match) end-(match) start-end(not match)
    }

}

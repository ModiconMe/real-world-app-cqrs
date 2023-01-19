package com.modiconme.realworld.application.service;

import org.springframework.stereotype.Service;

@Service
public class SlugService {

    public String createSlug(String title) {
        return title.toLowerCase().replace(" ", "-");
    }

}

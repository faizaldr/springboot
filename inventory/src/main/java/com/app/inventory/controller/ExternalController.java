package com.app.inventory.controller;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/bean")
public class ExternalController {

    // gunakan bean
    private final String pesan;

    // bean diambil berdasarkan tipe data/objek
    public ExternalController(@Qualifier("appPesan") String pesan){
        this.pesan = pesan;
    }

    @GetMapping
    public String getPesan(){
        return pesan;
    }
}
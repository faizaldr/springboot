package id.eduparx.social.controller;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
public class Hallo {
    @GetMapping("/api/hallo")
    public String hallo() {
        return "Selamat Malam";
    }
    
}

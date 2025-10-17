package id.eduparx.social.controller;

import org.springframework.beans.factory.annotation.Autowired;

import id.eduparx.social.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import id.eduparx.social.repository.UserRepository;

@RestController
@RequestMapping("/api/users")
public class UserController {
    // DI
    @Autowired
    // mengurangi deklarasi dengan new
    // mengurangi pembuatan objek baru di memory
    private UserRepository userRepository;

    
}

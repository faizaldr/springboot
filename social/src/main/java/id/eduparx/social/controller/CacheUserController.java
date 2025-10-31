package id.eduparx.social.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import id.eduparx.social.dto.UserDto;
import id.eduparx.social.service.CacheUserService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
@RequestMapping("/api/cache")
@CrossOrigin(origins = "*")
public class CacheUserController {
    private final CacheUserService cacheUserService;
    private final RedisTemplate<String, Object> redisTemplate;

    public CacheUserController(CacheUserService cacheUserService, RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
        this.cacheUserService = cacheUserService;
    }

    @GetMapping("/users")
    public ResponseEntity<List<UserDto>> getAllUsers() {
        long startTime = System.currentTimeMillis();
        List<UserDto> users = cacheUserService.getAllUsers();
        long duration = System.currentTimeMillis() - startTime;
        System.out.println("Response Time = " + duration + "ms");
        return ResponseEntity.ok(users);
    }

    @GetMapping("/clear-cache")
    public ResponseEntity<Map<String,String>> clearCache(){
        cacheUserService.clearAllCache();
        Map<String, String> response = new HashMap<>();
        response.put("Message", "Semua cache terhapus");
        return ResponseEntity.ok(response);
    }
    



}

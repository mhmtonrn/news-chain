package com.softengine.newschain.api;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
@RequestMapping("api/info")
public class Info {

    @GetMapping
    public String info(){
        return "news-chain v1.1 -> " + LocalDateTime.now();
    }
}

package com.ip.info.controller;

import com.ip.info.model.entity.IpDetailsEntity;
import com.ip.info.service.IpDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;


@RestController
@RequestMapping("/ipdetails/v1")
public class IpDetailsController {

    @Autowired
    private IpDetailsService ipDetailsService;

    @GetMapping("/fetch")
    public Mono<IpDetailsEntity> fetchDetails(@RequestParam(required = false) String ip) {
        return ipDetailsService.fetchDetails(ip);
    }
}

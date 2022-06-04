package com.example.server.controller;

import com.example.server.model.Response;
import com.example.server.service.ServerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.Map;

@RestController
@RequestMapping("/server")
public class ServerController {

    private final ServerService serverService;

    public ServerController(ServerService serverService) {
        this.serverService = serverService;
    }

    @GetMapping("/list")
    public ResponseEntity<Response> getServers() {
        return ResponseEntity.ok(
                new Response(
                        LocalDateTime.now(),
                        HttpStatus.OK.value(),
                        HttpStatus.OK,
                        "Server retrieved",
                        Map.of("servers", serverService.list(30)))
        );
    }
}

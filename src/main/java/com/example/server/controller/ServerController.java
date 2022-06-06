package com.example.server.controller;

import com.example.server.enumiration.Status;
import com.example.server.model.Response;
import com.example.server.model.Server;
import com.example.server.service.ServerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.IOException;
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
                        Map.of("servers", serverService.list(30))
                )
        );
    }

    @GetMapping("/ping/{ipAddress}")
    public ResponseEntity<Response> pingServer(
            @PathVariable("ipAddress")
            String ipAddress
    ) throws IOException {
        Server server = serverService.ping(ipAddress);
        return ResponseEntity.ok(
                new Response(
                        LocalDateTime.now(),
                        HttpStatus.OK.value(),
                        HttpStatus.OK,
                        server.getStatus() == Status.SERVER_UP ? "Ping success" : "Ping failed",
                        Map.of("server", server)
                )
        );
    }

    @PostMapping("/save")
    public ResponseEntity<Response> saveServer(
            @RequestBody
            @Valid
            Server server
    ) {
        return ResponseEntity.ok(
                new Response(
                        LocalDateTime.now(),
                        HttpStatus.CREATED.value(),
                        HttpStatus.CREATED,
                        "Server created",
                        Map.of("server", serverService.create(server))
                )
        );
    }
}

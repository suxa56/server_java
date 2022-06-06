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
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.Map;

import static org.springframework.http.MediaType.IMAGE_PNG_VALUE;

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
                        "Servers retrieved",
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

    @GetMapping("/get/{id}")
    public ResponseEntity<Response> getServer(
            @PathVariable("id")
            Long id
    ) {
        return ResponseEntity.ok(
                new Response(
                        LocalDateTime.now(),
                        HttpStatus.OK.value(),
                        HttpStatus.OK,
                        "Server retrieved",
                        Map.of("server", serverService.get(id))
                )
        );
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Response> deleteServer(
            @PathVariable("id")
            Long id
    ) {
        return ResponseEntity.ok(
                new Response(
                        LocalDateTime.now(),
                        HttpStatus.OK.value(),
                        HttpStatus.OK,
                        "Server deleted",
                        Map.of("deleted", serverService.delete(id))
                )
        );
    }

    @GetMapping(path = "/image/{fileName}", produces = IMAGE_PNG_VALUE)
    public byte[] getServerImage(
            @PathVariable("fileName")
            String fileName
    ) throws IOException {
        return Files.readAllBytes(Paths.get(System.getProperty("user.home") + "Downloads/images/" + fileName));
    }
}

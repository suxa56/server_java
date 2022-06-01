package com.example.server.service.implementation;

import com.example.server.enumiration.Status;
import com.example.server.model.Server;
import com.example.server.repo.ServerRepo;
import com.example.server.service.ServerService;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.transaction.Transactional;
import java.io.IOException;
import java.net.InetAddress;
import java.util.Collection;
import java.util.Random;
import java.util.logging.Logger;

@Service
@Transactional
public class ServerServiceImpl implements ServerService {

    private final ServerRepo serverRepo;

    public ServerServiceImpl(ServerRepo serverRepo) {
        this.serverRepo = serverRepo;
    }

    @Override
    public Server create(Server server) {
        Logger.getLogger("Saving new server: " + server.getName());
        server.setImageUrl(setServerImageUrl());
        return serverRepo.save(server);
    }

    @Override
    public Server ping(String ipAddress) throws IOException {
        Logger.getLogger("Pinging server ip: " + ipAddress);
        Server server = serverRepo.findByIpAddress(ipAddress);
        InetAddress address = InetAddress.getByName(ipAddress);
        server.setStatus(address.isReachable(10000) ? Status.SERVER_UP : Status.SERVER_DOWN);
        serverRepo.save(server);
        return server;
    }

    @Override
    public Collection<Server> list(int limit) {
        Logger.getLogger("Fetching all servers");
        return serverRepo.findAll(PageRequest.of(0, limit)).toList();
    }

    @Override
    public Server get(Long id) {
        Logger.getLogger("Fetching server by Id: " + id);
        return serverRepo.findById(id).get();
    }

    @Override
    public Server update(Server server) {
        Logger.getLogger("Updating server: " + server.getName());
        return serverRepo.save(server);
    }

    @Override
    public Boolean delete(Long id) {
        Logger.getLogger("Deleting server: " + id);
        serverRepo.deleteById(id);
        return true;
    }

    private String setServerImageUrl() {
//        String[] imageNames = {"server1.png","server2.png","server3.png","server4.png"};
//        return ServletUriComponentsBuilder.fromCurrentContextPath().path("/server/image/" + imageNames[new Random().nextInt(4)]).toUriString();
        return null;
    }

}

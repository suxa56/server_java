package com.example.server.service;

import com.example.server.model.Server;
import org.springframework.stereotype.Service;

import java.util.Collection;

public interface ServerService {
    Server create(Server server);
    Collection<Server> list(int limit);
    Server get(Long id);
    Server update(Server server);
    Boolean delete(Long id);
}

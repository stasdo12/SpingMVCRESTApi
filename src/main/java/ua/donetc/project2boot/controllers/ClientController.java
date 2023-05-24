package ua.donetc.project2boot.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ua.donetc.project2boot.entity.Client;
import ua.donetc.project2boot.repositories.ClientRepo;
import ua.donetc.project2boot.services.ClientService;

import java.util.List;

@RestController
@RequestMapping("/app/v1/client")
public class ClientController {

    private final ClientService clientService;
    private final ClientRepo clientRepository;
    @Autowired
    public ClientController(ClientService clientService, ClientRepo clientRepository) {
        this.clientService = clientService;
        this.clientRepository = clientRepository;
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/fillDB")
    public String fillDataBase() {
        clientService.generateDB();
        return "Amount clients: " + clientRepository.count();
    }


    @GetMapping()
    public List<Client> findByNameContaining(@RequestParam String clientName) {
        return clientService.findByNameContaining(clientName);

    }

}
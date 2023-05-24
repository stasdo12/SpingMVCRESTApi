package ua.donetc.project2boot.services;

import com.github.javafaker.Faker;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.transaction.annotation.Transactional;
import ua.donetc.project2boot.entity.Client;
import ua.donetc.project2boot.repositories.ClientRepo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringJUnitConfig
@SpringBootTest
@Transactional
class ClientServiceTest {

    @Mock
    private ClientRepo clientRepo;

    @InjectMocks
    private ClientService clientService;

    @Test
    void findByNameContaining() {

        List<Client> clients = Arrays.asList(
                new Client(),
                new Client(),
                new Client()
        );
        String name = clients.get(1).getFullName();
        when(clientRepo.findByFullNameContaining(name)).thenReturn(clients);
    }

    @Test
    void create2000Clients() {

        List<Client> clientList = clientService.create2000Clients();

        assertEquals(2000, clientList.size());
        for(Client client: clientList){
            assertNotNull(client);
            assertNotNull(client.getFullName());
            assertNotNull(client.getMobileNumber());
            assertNotNull(client.getEmailAddresses());
        }
    }
}
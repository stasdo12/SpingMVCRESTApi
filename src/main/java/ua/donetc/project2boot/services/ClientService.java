package ua.donetc.project2boot.services;

import com.github.javafaker.Faker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.donetc.project2boot.entity.Client;
import ua.donetc.project2boot.entity.EmailAddress;
import ua.donetc.project2boot.repositories.ClientRepo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class ClientService {

    private final ClientRepo clientRepo;

    @Autowired
    public ClientService(ClientRepo clientRepo) {
        this.clientRepo = clientRepo;
    }

    public List<Client> findByNameContaining(String userName){
        return clientRepo.findByFullNameContaining(userName);
    }

    public void generateDB(){
        List<Client> clients = create2000Clients();
        clientRepo.saveAll(clients);
    }

    public List<Client> create2000Clients() {
        List<Client> clients = new ArrayList<>();
        Faker faker = new Faker();
        for (int i = 0; i < 2_000; i++) {
            String firstName = faker.name().firstName();
            String lastName = faker.name().lastName();
            String sufixTel = String.valueOf(i);
            String telephone = "+375290000000";

            List<EmailAddress>emailAddresses= Arrays.asList(
                    new EmailAddress((firstName + lastName).toLowerCase() + "1" + i + "@gmail.com"),
                    new EmailAddress((firstName + lastName).toLowerCase() + "2" + i + "@gmail.com"));

            telephone = telephone.substring(0, telephone.length()-sufixTel.length()) + sufixTel;
            Client client = new Client(
                    firstName + " " + lastName,
                    telephone,
                    emailAddresses
            );

            for (EmailAddress emailAddress:emailAddresses) {
                emailAddress.setClient(client);
            }

            clients.add(client);
        }
        return clients;
    }
}

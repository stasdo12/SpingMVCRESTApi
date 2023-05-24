package ua.donetc.project2boot.repositories;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import ua.donetc.project2boot.entity.Client;

import java.util.List;

public interface ClientRepo extends JpaRepository<Client, Long> {


    @EntityGraph(type = EntityGraph.EntityGraphType.FETCH, value = "client_entity-graph")
    List<Client> findByFullNameContaining(String name);

}

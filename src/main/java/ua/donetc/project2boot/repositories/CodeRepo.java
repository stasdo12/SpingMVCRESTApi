package ua.donetc.project2boot.repositories;

import org.jetbrains.annotations.NotNull;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import ua.donetc.project2boot.entity.Code;

import java.util.List;
import java.util.Optional;

public interface CodeRepo extends JpaRepository<Code, Long> {


    @NotNull
    @Override
    @EntityGraph(type = EntityGraph.EntityGraphType.FETCH, value = "code_entity-graph")
    List<Code> findAll();

    Optional<Code>getByName(String name);

    void deleteByName(String name);


    @EntityGraph(type = EntityGraph.EntityGraphType.FETCH, value = "code_entity-graph")
    List<Code> getCodeByNameStartingWith(String startLetter);
}

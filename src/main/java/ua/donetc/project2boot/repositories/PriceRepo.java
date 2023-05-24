package ua.donetc.project2boot.repositories;

import org.checkerframework.checker.nullness.Opt;
import org.springframework.data.jpa.repository.JpaRepository;
import ua.donetc.project2boot.entity.Code;
import ua.donetc.project2boot.entity.Price;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface PriceRepo extends JpaRepository<Price, Long> {

    List<Price> findAllByTimeLessThan(LocalDateTime time);

    void deleteAllByTimeLessThan(LocalDateTime time);

    Optional<Price>findFirstByCodeOrderByTimeDesc(Code code);


}

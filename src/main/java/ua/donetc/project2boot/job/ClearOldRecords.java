package ua.donetc.project2boot.job;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.Before;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import ua.donetc.project2boot.services.PriceService;

import java.time.LocalDateTime;

@Component
@AllArgsConstructor
@Slf4j
@EnableScheduling
public class ClearOldRecords {



    private final PriceService priceService;
//@Scheduled(fixedRate = 60000)
    private void clear24After(){
        priceService.clear(LocalDateTime.now().minusMinutes(5));
    }
}

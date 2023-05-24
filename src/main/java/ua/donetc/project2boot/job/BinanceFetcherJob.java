package ua.donetc.project2boot.job;

import com.binance.api.client.BinanceApiRestClient;
import com.binance.api.client.domain.market.TickerPrice;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import ua.donetc.project2boot.entity.Code;
import ua.donetc.project2boot.providers.BinanceProvider;
import ua.donetc.project2boot.services.CodeService;
import ua.donetc.project2boot.services.PriceService;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Component
@AllArgsConstructor
@Slf4j
@EnableScheduling
public class BinanceFetcherJob {
    private final CodeService codeService;
    private final PriceService priceService;
    private final BinanceApiRestClient binanceApiRestClient;
    private final BinanceProvider binanceProvider;



//@Scheduled(fixedRate = 60000)
    private void call(){
    List<TickerPrice> tickerPrices = binanceApiRestClient.getAllPrices();
    BigDecimal multiply = binanceProvider.getMultiply();
    tickerPrices.forEach(tickerPrice -> {
        String codeName =null;
        BigDecimal price =BigDecimal.ZERO;
        try {
            codeName = tickerPrice.getSymbol();
            Code code = codeService.get(codeName);
            price = new BigDecimal(tickerPrice.getPrice());

            priceService.save(code, price.multiply(multiply).toBigInteger(), LocalDateTime.now());
        } catch (Exception e) {
            log.error("Code:{}, Price:{} , Exception{}", codeName, price, e.getMessage());
        }
    });
}
}

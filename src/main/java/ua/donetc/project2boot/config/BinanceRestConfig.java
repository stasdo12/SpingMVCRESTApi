package ua.donetc.project2boot.config;

import com.binance.api.client.BinanceApiClientFactory;
import com.binance.api.client.BinanceApiRestClient;
import com.querydsl.core.QueryFactory;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import ua.donetc.project2boot.providers.BinanceProvider;
import ua.donetc.project2boot.util.UserValidator;

@Configuration
@AllArgsConstructor
public class BinanceRestConfig {

private final BinanceProvider binanceProvider;
private final UserValidator userValidator;

    @Bean
    public BinanceApiRestClient binanceApiRestClient() {
        return BinanceApiClientFactory.newInstance(binanceProvider.getApiKey(), binanceProvider.getSecretKey()).newRestClient();

    }





}

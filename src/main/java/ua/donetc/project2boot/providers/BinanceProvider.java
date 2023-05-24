package ua.donetc.project2boot.providers;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
@Getter
public class BinanceProvider {

    @Value("${APIKEY}")
    private String apiKey;
    @Value("${SECRET}")
    private String secretKey;
    @Value("${multiply}")
    private BigDecimal multiply;

    public String getApiKey() {
        return apiKey;
    }

    public String getSecretKey() {
        return secretKey;
    }

    public BigDecimal getMultiply() {
        return multiply;
    }
}

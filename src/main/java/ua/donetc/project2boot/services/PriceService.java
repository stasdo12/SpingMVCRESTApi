package ua.donetc.project2boot.services;

import lombok.AllArgsConstructor;
import org.checkerframework.checker.nullness.Opt;
import org.springframework.stereotype.Service;
import ua.donetc.project2boot.entity.Code;
import ua.donetc.project2boot.entity.Price;
import ua.donetc.project2boot.repositories.PriceRepo;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class PriceService {




    private final PriceRepo priceRepo;


    public Optional<Price> getLastPrice(Code code){
        return priceRepo.findFirstByCodeOrderByTimeDesc(code);
    }


    public void clear(LocalDateTime upTo){
        this.priceRepo.deleteAllByTimeLessThan(upTo);

    }



    public Price save(Code code, BigInteger priceVal, LocalDateTime time){
        Price price = new Price();
        price.setCode(code);
        price.setVal(priceVal);
        price.setTime(time);
        return priceRepo.save(price);
    }






}

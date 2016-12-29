package com.example.web.api;

import com.example.domain.ProductPrice;
import com.example.service.PriceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@RestController
@RequestMapping("/api/prices")
public class PriceController {

    @Autowired
    private PriceService priceService;

    @RequestMapping(value = "/{date}", method = RequestMethod.GET)
    public List<ProductPrice> listProductPricesByDate(@PathVariable String date) {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss Z");

        //TODO validate date

        return priceService.getProductPricesByDate(OffsetDateTime.from(formatter.parse(date)));
    }

}

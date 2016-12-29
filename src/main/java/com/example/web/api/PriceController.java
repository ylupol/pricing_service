package com.example.web.api;

import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.domain.ProductPrice;
import com.example.service.PriceService;

@RestController
@RequestMapping("/api/prices")
public class PriceController {

    @Autowired
    private PriceService priceService;

    @RequestMapping(value = "/{date}", method = RequestMethod.GET)
    public ResponseEntity<?> listProductPricesByDate(@PathVariable String date) {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss Z");

        OffsetDateTime dateTime;
        try {
            dateTime = OffsetDateTime.from(formatter.parse(date));
        } catch (DateTimeParseException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

        List<ProductPrice> productPricesByDate = priceService.getProductPricesByDate(dateTime);
        return ResponseEntity.ok(productPricesByDate);
    }

}

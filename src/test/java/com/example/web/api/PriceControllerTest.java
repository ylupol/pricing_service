package com.example.web.api;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDateTime;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.example.domain.Price;
import com.example.domain.Product;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class PriceControllerTest
{
    private static final String PRICES_ENDPOINT = "/api/prices/";

    @Autowired
    private MockMvc mvc;

//    @Test
//    public void addPrice() throws Exception
//    {
//        Product product = new Product("chair");
//        Price price = new Price(product, 13.2, LocalDateTime.now());
//        mvc.perform(
//                MockMvcRequestBuilders.post(PRICES_ENDPOINT).accept(
//                        MediaType.APPLICATION_JSON)).andExpect(status().isOk());
//    }
}

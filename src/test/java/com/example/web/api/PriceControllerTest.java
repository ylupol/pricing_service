package com.example.web.api;

import com.example.domain.Price;
import com.example.domain.Product;
import com.example.repository.PriceRepository;
import com.example.repository.ProductRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.nio.charset.Charset;
import java.time.Clock;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertNotNull;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class PriceControllerTest {
    private static final String PRICES_ENDPOINT = "/api/prices";

    @Autowired
    private PriceRepository priceRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private MockMvc mvc;

    private HttpMessageConverter mappingJackson2HttpMessageConverter;

    private MediaType contentType = new MediaType(
            MediaType.APPLICATION_JSON.getType(),
            MediaType.APPLICATION_JSON.getSubtype(), Charset.forName("utf8"));

    @Autowired
    public void setConverters(HttpMessageConverter<?>[] converters) {
        mappingJackson2HttpMessageConverter = Arrays
                .stream(converters)
                .filter(hmc -> hmc instanceof MappingJackson2HttpMessageConverter)
                .findAny().orElse(null);
        assertNotNull(mappingJackson2HttpMessageConverter);
    }

    @Before
    public void setUp() {
        priceRepository.deleteAllInBatch();
        productRepository.deleteAllInBatch();
    }

    @Test
    public void listPricesForTimestamp() throws Exception {
        Product chair = productRepository.save(new Product("Chair"));
        Price chairPrice = priceRepository.save(new Price(chair, 11.3, OffsetDateTime.now(Clock.systemUTC()).minusMonths(1)));

        Product table = productRepository.save(new Product("Table"));
        Price tablePrice = priceRepository.save(new Price(table, 4.2, OffsetDateTime.now(Clock.systemUTC()).minusMonths(3)));

        Product sofa = productRepository.save(new Product("Sofa"));
        Price sofaPrice = priceRepository.save(new Price(sofa, 20.8, OffsetDateTime.now(Clock.systemUTC()).minusMonths(6)));

        OffsetDateTime queryDate = OffsetDateTime.now(Clock.systemUTC()).minusMonths(2);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss Z");
        String uri = PRICES_ENDPOINT + "/" + formatter.format(queryDate);

        mvc.perform(
                MockMvcRequestBuilders.get(uri))
                .andExpect(content().contentType(contentType))
                .andExpect(jsonPath("$[0].productName", is(table.getName())))
                .andExpect(jsonPath("$[0].price", is(tablePrice.getPrice())))
                .andExpect(jsonPath("$[1].productName", is(sofa.getName())))
                .andExpect(jsonPath("$[1].price", is(sofaPrice.getPrice())));
    }

}

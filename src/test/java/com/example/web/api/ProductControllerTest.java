package com.example.web.api;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.IOException;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.mock.http.MockHttpOutputMessage;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.example.domain.Price;
import com.example.domain.Product;
import com.example.repository.PriceRepository;
import com.example.repository.ProductRepository;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class ProductControllerTest
{
    public static final String PRODUCT_ENDPOINT = "/api/products";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private PriceRepository priceRepository;

    private HttpMessageConverter mappingJackson2HttpMessageConverter;

    private MediaType contentType = new MediaType(
            MediaType.APPLICATION_JSON.getType(),
            MediaType.APPLICATION_JSON.getSubtype(), Charset.forName("utf8"));

    @Autowired
    public void setConverters(HttpMessageConverter<?>[] converters)
    {
        mappingJackson2HttpMessageConverter = Arrays
                .stream(converters)
                .filter(hmc -> hmc instanceof MappingJackson2HttpMessageConverter)
                .findAny().orElse(null);
        assertNotNull(mappingJackson2HttpMessageConverter);
    }

    @Before
    public void setUp()
    {
        priceRepository.deleteAllInBatch();
        productRepository.deleteAllInBatch();
    }

    @Test
    public void readProducts() throws Exception
    {
        Product chair = productRepository.save(new Product("Chair"));
        Product table = productRepository.save(new Product("Table"));

        mockMvc.perform(get(PRODUCT_ENDPOINT).contentType(contentType))
                .andExpect(status().isOk())
                .andExpect(content().contentType(contentType))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id", is(chair.getId().intValue())))
                .andExpect(jsonPath("$[0].name", is(chair.getName())))
                .andExpect(jsonPath("$[1].id", is(table.getId().intValue())))
                .andExpect(jsonPath("$[1].name", is(table.getName())));
    }

    @Test
    public void readSingleProduct() throws Exception
    {
        Product product = productRepository.save(new Product("Chair"));

        mockMvc.perform(get(PRODUCT_ENDPOINT + "/" + product.getId()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(contentType))
                .andExpect(jsonPath("$.id", is(product.getId().intValue())))
                .andExpect(jsonPath("$.name", is(product.getName())));
    }

    @Test
    public void deleteProduct() throws Exception
    {
        Product product = productRepository.save(new Product("Chair"));

        mockMvc.perform(
                delete(PRODUCT_ENDPOINT + "/" + product.getId()).contentType(
                        contentType)).andExpect(status().isOk());
    }

    @Test
    public void createProduct() throws Exception
    {
        Product product = new Product("Chair");

        mockMvc.perform(
                post(PRODUCT_ENDPOINT).contentType(contentType).content(
                        json(product))).andExpect(status().isCreated());
    }

    @Test
    public void createPriceForProduct() throws Exception
    {
        Product product = productRepository.save(new Product("Chair"));
        Price price = new Price(product, 9.99, new Date());

        mockMvc.perform(
                post(PRODUCT_ENDPOINT + "/" + product.getId() + "/prices/")
                        .contentType(contentType).content(json(price)))
                .andExpect(status().isCreated());
    }

    @Test
    public void readPricesForProduct() throws Exception
    {
        Product product = productRepository.save(new Product("Chair"));
        Price price1 = priceRepository
                .save(new Price(product, 9.99, new GregorianCalendar(2015,
                        Calendar.MARCH, 22).getTime()));
        Price price2 = priceRepository.save(new Price(product, 5.99,
                new GregorianCalendar(2016, Calendar.JULY, 4).getTime()));
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        String price1ExpectedDateString = dateFormat.format(price1.getDate());
        String price2ExpectedDateString = dateFormat.format(price2.getDate());


        mockMvc.perform(
                get(PRODUCT_ENDPOINT + "/" + product.getId() + "/prices/")
                        .contentType(contentType)).andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id", is(price1.getId().intValue())))
                .andExpect(jsonPath("$[0].price", is(price1.getPrice())))
//                .andExpect(jsonPath("$[0].date", is(price1ExpectedDateString))) //TODO fix date deserialization
                .andExpect(jsonPath("$[1].id", is(price2.getId().intValue())))
                .andExpect(jsonPath("$[1].price", is(price2.getPrice())));
//                .andExpect(jsonPath("$[1].date", is(price2ExpectedDateString))); //TODO fix date deserialization
    }

    private String json(Object o) throws IOException
    {
        MockHttpOutputMessage mockHttpOutputMessage = new MockHttpOutputMessage();
        mappingJackson2HttpMessageConverter.write(o, contentType,
                mockHttpOutputMessage);
        return mockHttpOutputMessage.getBodyAsString();
    }

}

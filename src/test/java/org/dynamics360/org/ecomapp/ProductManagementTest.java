package org.dynamics360.org.ecomapp;

import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;
import net.minidev.json.JSONArray;
import org.dynamics360.org.ecomapp.dtos.ProductDto;
import org.dynamics360.org.ecomapp.persistence.entities.Product;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;

import java.net.URI;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ProductManagementTest {
    @Autowired
    TestRestTemplate restTemplate;

    @Test
    @DisplayName("Should return a product when data is saved")
    void testReadProduct_Found() {
        ResponseEntity<String> response = restTemplate.getForEntity("/products/123", String.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

        DocumentContext documentContext = JsonPath.parse(response.getBody());
        Number id = documentContext.read("$.id");
        assertThat(id).isEqualTo(123);

        Double price = documentContext.read("$.price");
        assertThat(price).isEqualTo(123);
    }

    @Test
    @DisplayName("Should return a 404 when product is not found")
    void testReadProduct_NotFound() {
        ResponseEntity<String> response = restTemplate.getForEntity("/products/123456", String.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    @DirtiesContext
    @DisplayName("Should create a product")
    void testCreateProduct() {
        ProductDto productDto = new ProductDto(null, "911", "An Emergency phone", 300.12);
        ResponseEntity<Void> response = restTemplate.postForEntity("/products", productDto, Void.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);

        URI locationOfNewCashCard = response.getHeaders().getLocation();
        ResponseEntity<String> getResponse = restTemplate
                .getForEntity(locationOfNewCashCard, String.class);

        assertThat(getResponse.getStatusCode()).isEqualTo(HttpStatus.OK);

        DocumentContext documentContext = JsonPath.parse(getResponse.getBody());
        Number id = documentContext.read("$.id");
        Double amount = documentContext.read("$.price");

        assertThat(id).isNotNull();
        assertThat(amount).isEqualTo(300.12);

    }

    @Test
    @DisplayName("Should show all products")
    void testFindAllProducts() {
        ResponseEntity<String> response = restTemplate
                .getForEntity("/products?number=0&size=10", String.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

        DocumentContext documentContext = JsonPath.parse(response.getBody());
        JSONArray ids = documentContext.read("$..id");
        assertThat(ids).contains(123, 1234);
    }

}

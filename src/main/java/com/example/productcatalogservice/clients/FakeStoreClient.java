package com.example.productcatalogservice.clients;

import com.example.productcatalogservice.dtos.FakeStoreProductDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RequestCallback;
import org.springframework.web.client.ResponseExtractor;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

@Component
public class FakeStoreClient {

    @Autowired
    private RestTemplateBuilder restTemplateBuilder = new RestTemplateBuilder();

    public FakeStoreProductDto getProduct(Long productId) {
        return requestForEntity(HttpMethod.GET, "https://fakestoreapi.com/products/{id}", null, FakeStoreProductDto.class, productId).getBody();
    }

    public FakeStoreProductDto[] getAllProducts() {
        return requestForEntity(HttpMethod.GET, "https://fakestoreapi.com/products", null, FakeStoreProductDto[].class).getBody();
    }

    public FakeStoreProductDto createProduct(FakeStoreProductDto fakeStoreProductDto) {
        return requestForEntity(HttpMethod.POST, "https://fakestoreapi.com/products", fakeStoreProductDto, FakeStoreProductDto.class).getBody();
    }

    public FakeStoreProductDto updateProduct(Long productId, FakeStoreProductDto fakeStoreProductDto) {
        return requestForEntity(HttpMethod.PUT, "https://fakestoreapi.com/products/{id}", fakeStoreProductDto, FakeStoreProductDto.class, productId).getBody();
    }

    public FakeStoreProductDto deleteProduct(Long productId) {
        return requestForEntity(HttpMethod.DELETE, "https://fakestoreapi.com/products/{id}", null, FakeStoreProductDto.class, productId).getBody();
    }

    public <T> ResponseEntity<T> requestForEntity(HttpMethod httpMethod, String url, @Nullable Object request, Class<T> responseType, Object... uriVariables) throws RestClientException {
        RestTemplate restTemplate = restTemplateBuilder.build();
        RequestCallback requestCallback = restTemplate.httpEntityCallback(request, responseType);
        ResponseExtractor<ResponseEntity<T>> responseExtractor = restTemplate.responseEntityExtractor(responseType);
        return restTemplate.execute(url, httpMethod, requestCallback, responseExtractor, uriVariables);
    }

}

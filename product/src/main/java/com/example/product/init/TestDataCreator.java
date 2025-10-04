package com.example.product.init;

import com.example.product.domain.Product;
import com.example.product.infrastructure.ProductRepository;
import jakarta.annotation.PostConstruct;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class TestDataCreator {


  private final ProductRepository productRepository;

  public TestDataCreator(ProductRepository productRepository) {
    this.productRepository = productRepository;
  }

  @PostConstruct
  public void createTestData() {

    Product product1 = new Product(100L, 100L);
    Product product2 = new Product(100L, 200L);

    productRepository.saveAll(List.of(product1, product2));
  }
}

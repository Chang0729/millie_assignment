package com.millie.assignment.application.port.out;

import com.millie.assignment.domain.Product;
import java.util.List;
import java.util.Optional;

public interface LoadProductPort {
    List<Product> loadAllProducts();
    Optional<Product> loadProduct(Long id);
}

package com.millie.assignment.adapter.out.persistence;

import com.millie.assignment.application.port.out.LoadProductPort;
import com.millie.assignment.domain.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class ProductPersistenceAdapter implements LoadProductPort {

    private final ProductRepository productRepository;
    private final ProductPersistenceMapper mapper; // ✅ 매퍼 주입

    @Override
    public List<Product> loadAllProducts() {
        // 1. repository.findAll()로 DB에서 Entity 리스트를 가져옵니다.
        // 2. stream().map()을 사용해 Entity를 도메인 객체(Product)로 변환합니다.
        return productRepository.findAll().stream()
                .map(mapper::mapToDomain) // 아래에 정의된 변환 메서드 사용
                .collect(Collectors.toList());
    }

    @Override
    public Optional<Product> loadProduct(Long id) {
        // findById는 결과가 없을 수 있으므로 Optional을 반환합니다.
        // 결과가 있다면 map을 통해 Entity를 도메인 객체로 변환합니다.
        return productRepository.findById(id)
                .map(mapper::mapToDomain);
    }
}


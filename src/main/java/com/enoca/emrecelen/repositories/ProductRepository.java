package com.enoca.emrecelen.repositories;

import com.enoca.emrecelen.model.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Pageable;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface ProductRepository extends JpaRepository<Product, UUID>, JpaSpecificationExecutor<Product> {
    Optional<Product> findByName(String productName);
    Page<Product> findAllByOrderByStockQuantityAsc(Pageable pageable);
    Page<Product> findAllByOrderByStockQuantityDesc(Pageable pageable);
    boolean existsByName(String productName);
}

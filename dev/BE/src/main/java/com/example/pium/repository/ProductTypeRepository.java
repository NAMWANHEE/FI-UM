package com.example.pium.repository;

import com.example.pium.entity.ProductTypeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductTypeRepository extends JpaRepository<ProductTypeEntity, Integer> {
    ProductTypeEntity findByType(String type);
}

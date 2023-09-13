package com.example.pium.repository;

import com.example.pium.entity.ItemListEntity
        ;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface ItemListRepository extends JpaRepository<ItemListEntity, Integer> {
    List<ItemListEntity> findAll();
    ItemListEntity findByItemNo(Integer itemNo);
}

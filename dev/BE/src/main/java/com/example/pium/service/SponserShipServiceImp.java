package com.example.pium.service;

import com.example.pium.dto.SponserShipDto;
import com.example.pium.entity.ItemListEntity;
import com.example.pium.repository.SponserShipRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class SponserShipServiceImp {

    private final SponserShipRepository sponserShipRepository;

    public void saveProduct(ItemListEntity itemListEntity) {
        ItemListEntity newItem = ItemListEntity.builder()
                        .itemName(itemListEntity.getItemName())
                                .itemUnitPrice(itemListEntity.getItemUnitPrice())
                                        .itemCount(itemListEntity.getItemCount())
                                                .itemDescription(itemListEntity.getItemDescription())
                                                        .itemImagePath(itemListEntity.getItemImagePath())
                                                                .build();

        sponserShipRepository.save(newItem);
    }


//    후원 물품 전체 조회
    public List<ItemListEntity> findAllProducts() {
        return sponserShipRepository.findAll();
    }


//    후원 물품 상세 조회
public ItemListEntity findProductById(Integer id) {
    return sponserShipRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Item with id " + id + " not found"));
}


}
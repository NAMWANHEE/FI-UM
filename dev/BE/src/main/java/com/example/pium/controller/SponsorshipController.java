package com.example.pium.controller;

import com.example.pium.entity.ItemListEntity;
import com.example.pium.service.SponserShipServiceImp;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@CrossOrigin("*")
@RequiredArgsConstructor
@RequestMapping("/sponsorship")
@RestController
@Slf4j
public class SponsorshipController {

    private final SponserShipServiceImp sponserShipServiceImp;

    @PostMapping
    public Map<String,String> registItem(@RequestBody ItemListEntity itemListEntity){
        sponserShipServiceImp.saveProduct(itemListEntity);
        Map<String, String> map = new HashMap<>();
        map.put("msg","성공");
        return map;
    }

    @GetMapping
    public ResponseEntity<List<ItemListEntity>> getAllItems() {
        List<ItemListEntity> entities = sponserShipServiceImp.findAllProducts();
        System.out.println(entities);

        if (!entities.isEmpty()) {
            return ResponseEntity.ok(entities);
        } else {
            return ResponseEntity.noContent().build(); // or return an empty list with a 200 OK status.

        }

    }

//    @GetMapping("/{id}")
//    public ResponseEntity<ItemListEntity> getDetailItemById(@PathVariable Integer id) {
//        ItemListEntity itemOpt = sponserShipServiceImp.findProductById(id);
//        if (itemOpt.isPresent()) {
//            return ResponseEntity.ok(itemOpt.get());
//        } else {
//            return ResponseEntity.notFound().build();
//        }
//    }






}

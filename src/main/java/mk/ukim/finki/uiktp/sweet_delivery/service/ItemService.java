package mk.ukim.finki.uiktp.sweet_delivery.service;


import mk.ukim.finki.uiktp.sweet_delivery.model.enums.ItemCategory;
import mk.ukim.finki.uiktp.sweet_delivery.model.metamodel.Item;
import mk.ukim.finki.uiktp.sweet_delivery.model.metamodel.dto.ItemDTO;

import java.util.List;
import java.util.Optional;

public interface ItemService {
   Item createItem(ItemDTO itemDTO);

    void deleteById(Long itemId);

    List<Item> findAll();
}

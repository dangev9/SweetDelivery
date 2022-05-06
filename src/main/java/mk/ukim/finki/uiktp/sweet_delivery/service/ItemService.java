package mk.ukim.finki.uiktp.sweet_delivery.service;


import mk.ukim.finki.uiktp.sweet_delivery.model.enums.ItemCategory;
import mk.ukim.finki.uiktp.sweet_delivery.model.metamodel.Item;

import java.util.Optional;

public interface ItemService {
    Optional<Item> createItem(String name, Integer items_in_stock, ItemCategory itemCategory, Integer price,
                              String img_url);
    Optional<Item> deleteItem(Long itemId);
}

package mk.ukim.finki.uiktp.sweet_delivery.service.impl;

import mk.ukim.finki.uiktp.sweet_delivery.model.enums.ItemCategory;
import mk.ukim.finki.uiktp.sweet_delivery.model.exceptions.ItemAlreadyExistsException;
import mk.ukim.finki.uiktp.sweet_delivery.model.exceptions.ItemNotFoundException;
import mk.ukim.finki.uiktp.sweet_delivery.model.metamodel.Item;
import mk.ukim.finki.uiktp.sweet_delivery.repository.ItemRepository;
import mk.ukim.finki.uiktp.sweet_delivery.service.ItemService;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ItemServiceImpl implements ItemService {
    private final ItemRepository itemRepository;

    public ItemServiceImpl(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    @Override
    public Optional<Item> createItem(String name, Integer items_in_stock, ItemCategory itemCategory, Integer price,
                                     String img_url) {

        if(this.itemRepository.findByName(name).isPresent()) {
            throw new ItemAlreadyExistsException();
        }

        return Optional.of(this.itemRepository.save(new Item(items_in_stock, name, itemCategory, price, img_url)));
    }

    @Override
    public Optional<Item> deleteItem(Long itemId) {
        Item item = this.itemRepository.findById(itemId).orElseThrow(ItemNotFoundException::new);
        this.itemRepository.deleteById(itemId);
        return Optional.of(item);
    }
}

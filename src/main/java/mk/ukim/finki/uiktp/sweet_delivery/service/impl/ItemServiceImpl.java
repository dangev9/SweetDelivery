package mk.ukim.finki.uiktp.sweet_delivery.service.impl;

import mk.ukim.finki.uiktp.sweet_delivery.model.exceptions.ItemAlreadyExistsException;
import mk.ukim.finki.uiktp.sweet_delivery.model.metamodel.Item;
import mk.ukim.finki.uiktp.sweet_delivery.model.metamodel.dto.ItemDTO;
import mk.ukim.finki.uiktp.sweet_delivery.repository.ItemRepository;
import mk.ukim.finki.uiktp.sweet_delivery.service.ItemService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ItemServiceImpl implements ItemService {
    private final ItemRepository itemRepository;

    public ItemServiceImpl(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    @Override
    public Item createItem(ItemDTO itemDTO) {

        if(this.itemRepository.findByName(itemDTO.getName()).isPresent()) {
            throw new ItemAlreadyExistsException();
        }

        Item item  = new Item();

        if(itemDTO.getName()!=null){
            item.setName(itemDTO.getName());
        }
        if(itemDTO.getImg_url()!=null){
            item.setImg_url(itemDTO.getImg_url());
        }
        item.setItems_in_stock(itemDTO.getItems_in_stock());
        item.setPrice(itemDTO.getPrice());
        return this.itemRepository.save(item);
    }

    @Override
    public void deleteById(Long itemId) {
        this.itemRepository.deleteById(itemId);
    }

    @Override
    public List<Item> findAll() {
        return this.itemRepository.findAll();
    }
}

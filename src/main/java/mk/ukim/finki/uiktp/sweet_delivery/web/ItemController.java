package mk.ukim.finki.uiktp.sweet_delivery.web;


import mk.ukim.finki.uiktp.sweet_delivery.model.metamodel.Item;
import mk.ukim.finki.uiktp.sweet_delivery.model.metamodel.dto.ItemDTO;
import mk.ukim.finki.uiktp.sweet_delivery.service.ItemService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/rest/item")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class ItemController {

    private final ItemService itemService;


    public ItemController(ItemService itemService) {
        this.itemService = itemService;
    }


    @PostMapping("/create")
    public Item createItem(@RequestBody ItemDTO itemDTO){
        return this.itemService.createItem(itemDTO);
    }

    @DeleteMapping("delete/{itemId}")
    public void deleteById(@PathVariable Long itemId){
        this.itemService.deleteById(itemId);
    }

    @GetMapping("/all")
    public List<Item> findAll(){
        return this.itemService.findAll();
    }



}

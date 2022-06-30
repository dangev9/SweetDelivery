package mk.ukim.finki.uiktp.sweet_delivery;

import mk.ukim.finki.uiktp.sweet_delivery.model.enums.ItemCategory;
import mk.ukim.finki.uiktp.sweet_delivery.model.metamodel.Item;
import mk.ukim.finki.uiktp.sweet_delivery.model.metamodel.dto.ItemDTO;
import mk.ukim.finki.uiktp.sweet_delivery.repository.ItemRepository;
import mk.ukim.finki.uiktp.sweet_delivery.service.ItemService;
import mk.ukim.finki.uiktp.sweet_delivery.service.impl.ItemServiceImpl;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;


@RunWith(MockitoJUnitRunner.class)
public class ItemTest {

    @Mock
    private ItemRepository itemRepository;

    private ItemService service;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
        Item item= new Item(5, "Item Name", ItemCategory.FRUITS, 100, "url");
        List<Item> items=new ArrayList<>();
        items.add(item);

        Mockito.when(this.itemRepository.save(Mockito.any(Item.class))).thenReturn(item);

        this.service = Mockito.spy(new ItemServiceImpl(this.itemRepository));
    }

    @Test
    public void testCreateItemSuccess(){

        ItemDTO itemDTO = new ItemDTO("Item Name",5, 100, "url");
        Item item = this.service.createItem(itemDTO);

        Mockito.verify(this.service).createItem(itemDTO);

        Assert.assertNotNull("Item is null", itemDTO);
        Assert.assertEquals("name do not match", "Item Name", item.getName());
        Assert.assertEquals("price do not match", Integer.valueOf(100), item.getPrice());

        Assert.assertEquals("img_url do not match", "url", item.getImg_url());
        Assert.assertEquals("item_in_stock do not match", Integer.valueOf(5), item.getItems_in_stock());

    }

    @Test
    public void createItemNullOrEmptyCredentials(){

        ItemDTO itemDTO = new ItemDTO(null,5, 100, "");

        Assert.assertThrows("IllegalArgumentException expected",
                IllegalArgumentException.class,
                () -> this.service.createItem(itemDTO));
        Mockito.verify(this.service).createItem(itemDTO);

    }

    @Test
    public void deleteItemById() {
        ItemDTO itemDTO = new ItemDTO("Item Name",5, 100, "url");
        Item item = this.service.createItem(itemDTO);
        item.setId(2L);

        Mockito.verify(this.service).createItem(itemDTO);

        this.service.deleteById(item.getId());
        Mockito.verify(this.service).deleteById(item.getId());
    }

}

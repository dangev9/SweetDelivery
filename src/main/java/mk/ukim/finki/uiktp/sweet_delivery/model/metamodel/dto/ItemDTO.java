package mk.ukim.finki.uiktp.sweet_delivery.model.metamodel.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ItemDTO {


    String name;
    Integer items_in_stock;
    Integer price;
    String img_url;

}

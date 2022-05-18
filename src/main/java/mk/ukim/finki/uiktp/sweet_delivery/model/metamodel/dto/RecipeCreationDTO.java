package mk.ukim.finki.uiktp.sweet_delivery.model.metamodel.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RecipeCreationDTO {
    private Integer price;
    private String description;
    private String name;
    private String img_url;
    private List<Long> itemList;
}

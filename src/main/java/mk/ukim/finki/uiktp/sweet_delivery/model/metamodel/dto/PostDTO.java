package mk.ukim.finki.uiktp.sweet_delivery.model.metamodel.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PostDTO {
    private Long recipeId;
    private String userName;
}

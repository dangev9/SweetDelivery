package mk.ukim.finki.uiktp.sweet_delivery.model.metamodel.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import mk.ukim.finki.uiktp.sweet_delivery.model.metamodel.Recipe;
import mk.ukim.finki.uiktp.sweet_delivery.model.userroles.User;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class OrderDTO {

    Integer amount;
    String deliveryAddress;
    List<Recipe> recipeList;
    User user;

}

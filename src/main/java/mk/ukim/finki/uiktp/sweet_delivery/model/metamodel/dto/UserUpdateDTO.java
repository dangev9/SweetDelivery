package mk.ukim.finki.uiktp.sweet_delivery.model.metamodel.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserUpdateDTO {
    private Long userId;
    private String username;
    private Boolean changePassword;
    private String oldPassword;
    private String newPassword;
    private String firstName;
    private String lastName;
    private String email;
    private String address;

}

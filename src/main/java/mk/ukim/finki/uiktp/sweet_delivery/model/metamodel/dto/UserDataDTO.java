package mk.ukim.finki.uiktp.sweet_delivery.model.metamodel.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import mk.ukim.finki.uiktp.sweet_delivery.model.userroles.AppUserRole;

import java.util.List;

@Data
@NoArgsConstructor
public class UserDataDTO {

    @ApiModelProperty(position = 0)
    private String username;
    @ApiModelProperty(position = 1)
    private String email;
    @ApiModelProperty(position = 2)
    private String password;
    @ApiModelProperty(position = 3)
    List<AppUserRole> appUserRoles;

    @ApiModelProperty
    private String address;

    @ApiModelProperty
    private String firstName;

    @ApiModelProperty
    private String lastName;

}

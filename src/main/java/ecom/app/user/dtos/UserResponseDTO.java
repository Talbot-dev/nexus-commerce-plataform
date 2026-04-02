package ecom.app.userModule.dtos;

import lombok.Data;

@Data
public class UserResponseDTO {
    private String id;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private AddressResponseDTO address;
}

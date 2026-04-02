package ecom.app.userModule.dtos;

import lombok.Data;

@Data
public class AddressResponseDTO {
    private String street;
    private String city;
    private String state;
    private String zipcode;
    private String country;
}

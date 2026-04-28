package ecom.app.mapper;

import ecom.app.user.dtos.AddressResponseDTO;
import ecom.app.user.dtos.UserRequestDTO;
import ecom.app.user.entities.Address;
import ecom.app.user.entities.User;

public class DtoToUser {

    public static User mapToObject(UserRequestDTO dto) {
        User user = new User();
        user.setFirstName(dto.getFirstName());
        user.setLastName(dto.getLastName());
        user.setEmail(dto.getEmail());
        user.setPhone(dto.getPhone());

        if (dto.getAddress() != null) {
            user.setAddress(mapDtoToAddress(dto.getAddress()));
        }
        return user;
    }

    private static Address mapDtoToAddress(AddressResponseDTO addressDto) {
        Address address = new Address();
        address.setStreet(addressDto.getStreet());
        address.setZipcode(addressDto.getZipcode());
        address.setCity(addressDto.getCity());
        address.setState(addressDto.getState());
        address.setCountry(addressDto.getCountry());
        return address;
    }
}


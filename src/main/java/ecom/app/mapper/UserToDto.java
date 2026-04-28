package ecom.app.mapper;

import ecom.app.user.dtos.AddressResponseDTO;
import ecom.app.user.dtos.UserResponseDTO;
import ecom.app.user.entities.Address;
import ecom.app.user.entities.User;

public class UserToDto {

    public static UserResponseDTO mapToDto(User user) {
        UserResponseDTO outputData = new UserResponseDTO();
        outputData.setId(String.valueOf(user.getId()));
        outputData.setFirstName(String.valueOf(user.getFirstName()));
        outputData.setLastName(String.valueOf(user.getLastName()));
        outputData.setEmail(String.valueOf(user.getEmail()));
        outputData.setPhone(String.valueOf(user.getPhone()));

        if (user.getAddress() != null) {
            outputData.setAddress(mapAddressToDto(user.getAddress()));
        }

        return outputData;
    }

    private static AddressResponseDTO mapAddressToDto(Address address) {
        AddressResponseDTO addressResponse = new AddressResponseDTO();
        addressResponse.setStreet(address.getStreet());
        addressResponse.setZipcode(address.getZipcode());
        addressResponse.setCity(address.getCity());
        addressResponse.setState(address.getState());
        addressResponse.setCountry(address.getCountry());
        return addressResponse;
    }
}




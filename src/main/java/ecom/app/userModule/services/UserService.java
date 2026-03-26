package ecom.app.userModule.services;

import ecom.app.userModule.dtos.AddressResponseDTO;
import ecom.app.userModule.dtos.UserRequestDTO;
import ecom.app.userModule.dtos.UserResponseDTO;
import ecom.app.userModule.entities.User;
import ecom.app.userModule.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository accessToDatabase;

    public List<UserResponseDTO> getAllUsers() {
        return accessToDatabase.findAll().stream()
                .map(this::mapToUserResponseDTO)
                .collect(Collectors.toList());
    }

    public Optional<UserResponseDTO> getSingleUser(Long id) {
        return accessToDatabase.findById(id).map(this::mapToUserResponseDTO);
    }

    public String registerUser(UserRequestDTO user){
        accessToDatabase.save(user);
        //Missing the converting data method
        return "Usuario Exitosamente registrado";
    }

    public boolean modifyUserInfo(UserRequestDTO userInfoAdded, Long id) {
        return accessToDatabase.findById(id)
                .map(ExistingUser -> {
                    ExistingUser.setFirstName(userInfoAdded.getFirstName());
                    ExistingUser.setLastName(userInfoAdded.getLastName());
                    accessToDatabase.save(ExistingUser);
                    return true;
                }).orElse(false);
        }

    private UserResponseDTO mapToUserResponseDTO (User user) {
        UserResponseDTO outputData = new UserResponseDTO();
        outputData.setId(String.valueOf(user.getId()));
        outputData.setFirstName(String.valueOf(user.getFirstName()));
        outputData.setLastName(String.valueOf(user.getLastName()));
        outputData.setEmail(String.valueOf(user.getEmail()));
        outputData.setPhone(String.valueOf(user.getPhone()));

        if (user.getAddress() != null){
            AddressResponseDTO  addressResponse = new AddressResponseDTO();
            addressResponse.setStreet(user.getAddress().getStreet());
            addressResponse.setZipcode(user.getAddress().getZipcode());
            addressResponse.setCity(user.getAddress().getCity());
            addressResponse.setState(user.getAddress().getState());
            addressResponse.setCountry(user.getAddress().getCountry());
        }
        return outputData;
    }
}
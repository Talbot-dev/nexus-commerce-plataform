package ecom.app.user.services;

import ecom.app.user.dtos.AddressResponseDTO;
import ecom.app.user.dtos.UserRequestDTO;
import ecom.app.user.dtos.UserResponseDTO;
import ecom.app.user.entities.Address;
import ecom.app.user.entities.User;
import ecom.app.user.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service layer responsible for handling business logic related to users.
 *
 * Provides operations for retrieving, creating and updating users,
 * as well as mapping between entities and DTOs.
 */
@Service
public class UserService {

    private final UserRepository accessToDatabase;

    /**
     * Constructs the service with the required repository dependency.
     *
     * @param accessToDatabase repository used for database operations
     */
    public UserService(UserRepository accessToDatabase){
        this.accessToDatabase = accessToDatabase;
    }

    /**
     * Retrieves all users from the database and maps them to response DTOs.
     *
     * @return list of users as {@link UserResponseDTO}
     */
    public List<UserResponseDTO> getAllUsers() {
        return accessToDatabase.findAll().stream()
                .map(this::mapToUserResponseDTO)
                .collect(Collectors.toList());
    }

    /**
     * Retrieves a single user by its ID.
     *
     * @param id unique identifier of the user
     * @return an {@link Optional} containing the user if found, otherwise empty
     */
    public Optional<UserResponseDTO> getSingleUser(Long id) {
        return accessToDatabase.findById(id).map(this::mapToUserResponseDTO);
    }

    /**
     * Registers a new user in the application
     *
     * @param userRequestDTO data required to create the user
     * @return confirmation message indicating successful creation
     * */
    public String registerUser(UserRequestDTO userRequestDTO) {
        User user = new User();
        accessToDatabase.save(createUserFromRequestDTO(userRequestDTO ,user));
        return "User successfully added";
    }

    /**
     * Updates basic user information (first name and last name).
     *
     * @param userInfoAdded DTO containing updated user information
     * @param id identifier of the user to update
     * @return true if the user exists and was updated, false otherwise
     */
    public boolean modifyUserInfo(UserRequestDTO userInfoAdded, Long id) {
        return accessToDatabase.findById(id)
                .map(ExistingUser -> {
                    ExistingUser.setFirstName(userInfoAdded.getFirstName());
                    ExistingUser.setLastName(userInfoAdded.getLastName());
                    accessToDatabase.save(ExistingUser);
                    return true;
                }).orElse(false);
        }

    /**
     * Populates a {@link User} entity using data from a request DTO.
     * Also handles optional address creation.
     *
     * @param dto source data from client
     * @param user target entity to populate
     * @return populated {@link User} entity ready for persistence
     */

    private User createUserFromRequestDTO(UserRequestDTO dto, User user) {
        user.setFirstName(dto.getFirstName());
        user.setLastName(dto.getLastName());
        user.setEmail(dto.getEmail());
        user.setPhone(dto.getPhone());
        if(dto.getAddress() != null){
            Address address = new Address();
            address.setStreet(dto.getAddress().getStreet());
            address.setZipcode(dto.getAddress().getZipcode());
            address.setCity(dto.getAddress().getCity());
            address.setState(dto.getAddress().getState());
            address.setCountry(dto.getAddress().getCountry());
            user.setAddress(address);
        }
        return user;
    }

    /**
     * Maps a {@link User} entity into a response DTO.
     *
     * @param user entity retrieved from database
     * @return mapped {@link UserResponseDTO}
     */
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
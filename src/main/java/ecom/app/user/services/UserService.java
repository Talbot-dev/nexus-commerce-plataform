package ecom.app.user.services;

import ecom.app.mapper.DtoToUser;
import ecom.app.user.dtos.UserRequestDTO;
import ecom.app.user.dtos.UserResponseDTO;
import ecom.app.user.repository.UserRepository;
import org.springframework.stereotype.Service;
import ecom.app.mapper.UserToDto;

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
    public UserService(UserRepository accessToDatabase) {
        this.accessToDatabase = accessToDatabase;
    }

    /**
     * Retrieves all users from the database and maps them to response DTOs.
     *
     * @return list of users as {@link UserResponseDTO}
     */
    public List<UserResponseDTO> getAllUsers() {
        return accessToDatabase.findAll().stream()
                .map(UserToDto::mapToDto)
                .collect(Collectors.toList());
    }

    /**
     * Retrieves a single user by its ID.
     *
     * @param id unique identifier of the user
     * @return an {@link Optional} containing the user if found, otherwise empty
     */
    public Optional<UserResponseDTO> getSingleUser(Long id) {
        return accessToDatabase.findById(id).map(UserToDto::mapToDto);
    }
    /**
     * Registers a new user in the application
     *
     * @param userRequestDTO data required to create the user
     * @return confirmation message indicating successful creation
     *
     */
    public String registerUser(UserRequestDTO userRequestDTO) {
        accessToDatabase.save(DtoToUser.mapToObject(userRequestDTO));
        return "User successfully added";
    }

    /**
     * Updates basic user information (first name and last name).
     *
     * @param userInfoAdded DTO containing updated user information
     * @param id            identifier of the user to update
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

}
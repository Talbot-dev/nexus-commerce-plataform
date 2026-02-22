package ecom.app.services;

import ecom.app.models.User;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    private Long registeredIDs = 0L;
    private final List<User> listOfRegisteredUsers = new ArrayList<>();

    public List<User> getAllUsers() {
        return listOfRegisteredUsers.isEmpty()
                ? Collections.emptyList()
                : listOfRegisteredUsers;
    }

    public Optional<User> getSingleUser(Long id) {
        return listOfRegisteredUsers.stream()
                .filter(user -> user.getId().equals(id))
                .findFirst();
    }

    public String registerUser(User user){
        user.setId(registeredIDs += 1);
        listOfRegisteredUsers.add(user);
        return "Usuario Exitosamente registrado";
    }

    public boolean modifyUserInfo(User userInfoAdded, Long id) {
        return listOfRegisteredUsers.stream()
                .filter(user -> user.getId().equals(id))
                .findFirst()
                .map(ExistingUser -> {
                    ExistingUser.setFirstName(userInfoAdded.getFirstName());
                    ExistingUser.setLastName(userInfoAdded.getLastName());
                    return true;
                }).orElse(false);
    }

    /*public boolean removeExistingUser(Long id) {
        return listOfRegisteredUsers.stream()
                .filter(user -> user.getId().equals(id))
                .findFirst()
                .m

    }*/
}
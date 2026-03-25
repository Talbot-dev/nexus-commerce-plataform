package ecom.app.services;

import ecom.app.models.User;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
/*
* Dejaré las pruebas con un arrayList hasta que logre conectar directamente con postgreSQL
* */
@Service
public class UserService {
    //private final UserRepository accessToDatabase;
    private final List<User> registeredUsers = new ArrayList<>();
    private Long controlledIds = 0L;

    public List<User> getAllUsers() {
        return registeredUsers;
        //return accessToDatabase.findAll();
    }

    public Optional<User> getSingleUser(Long id) {
        return registeredUsers.stream().filter(
                user -> user.getId().equals(id)).findFirst();
        //return accessToDatabase.findById(id);
    }

    public String registerUser(User user){
        user.setId(controlledIds += 1);
        registeredUsers.add(user);
        //accessToDatabase.save(user);
        return "Usuario Exitosamente registrado";
    }

    public boolean modifyUserInfo(User userInfoAdded, Long id) {
        return registeredUsers.stream().filter(user -> user.getId().equals(id)).findFirst()
                .map(ExistingUser -> {
                    ExistingUser.setFirstName(userInfoAdded.getFirstName());
                    ExistingUser.setLastName(userInfoAdded.getLastName());
                    return true;
                }).orElse(false);
        }

        /*return accessToDatabase.findById(id)
                .map(ExistingUser -> {
                    ExistingUser.setFirstName(userInfoAdded.getFirstName());
                    ExistingUser.setLastName(userInfoAdded.getLastName());
                    accessToDatabase.save(ExistingUser);
                    return true;
                }).orElse(false);
                }*/
    }
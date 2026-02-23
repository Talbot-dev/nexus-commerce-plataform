package ecom.app.services;

import ecom.app.models.User;
import ecom.app.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/*
* Dejaré las pruebas con un arrayList hasta que logre conectar directamente con postgreSQL
* */

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository accessToDatabase;

    public List<User> getAllUsers() {
        return accessToDatabase.findAll();
    }

    public Optional<User> getSingleUser(Long id) {
        return accessToDatabase.findById(id);
    }

    public String registerUser(User user){
        accessToDatabase.save(user);
        return "Usuario Exitosamente registrado";
    }

    public boolean modifyUserInfo(User userInfoAdded, Long id) {
        return accessToDatabase.findById(id)
                .map(ExistingUser -> {
                    ExistingUser.setFirstName(userInfoAdded.getFirstName());
                    ExistingUser.setLastName(userInfoAdded.getLastName());
                    accessToDatabase.save(ExistingUser);
                    return true;
                }).orElse(false);
    }
}
package ecom.app.services;

import ecom.app.models.User;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {
    private Long registeredIDs = 0L;
    private final List<User> listOfRegisteredUsers = new ArrayList<>();

    public List<User> getAllUsers (){
        return listOfRegisteredUsers;
    }

    public User getSingleUser(Long id){
        for (User user : listOfRegisteredUsers){
            if(user.getId().equals(id)){
                return user;
            }
        }
        return null;
    }

    public List<User> registerUser(User user){
        user.setId(registeredIDs += 1);
        listOfRegisteredUsers.add(user);
        return listOfRegisteredUsers;
    }
}
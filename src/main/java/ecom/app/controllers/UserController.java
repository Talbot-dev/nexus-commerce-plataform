package ecom.app.controllers;

import ecom.app.models.User;
import ecom.app.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/users")
    public List<User> fetchAllUsers(){
        return userService.getAllUsers();
    }

    @GetMapping("/users/{id}")
    public User findUser(@PathVariable Long id){
        return userService.getSingleUser(id);
    }

    @PostMapping("/users")
    public List<User> createUserResource(@RequestBody User user){
        return userService.registerUser(user);
    }
}

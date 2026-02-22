package ecom.app.controllers;

import ecom.app.models.User;
import ecom.app.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class  UserController {

    private final UserService userService;


    @RequestMapping(value = "/users", method = RequestMethod.POST)
    public ResponseEntity<String> createUserResource(@RequestBody User user){
        return new ResponseEntity<>(userService.registerUser(user), HttpStatus.OK);
    }


    @GetMapping("/users")
    public ResponseEntity<List<User>> fetchAllUsers(){
        if (userService.getAllUsers().isEmpty()) {
            return new ResponseEntity<>(userService.getAllUsers(), HttpStatus.NOT_FOUND);
        }
       return new ResponseEntity<>(userService.getAllUsers(), HttpStatus.OK);
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<User> findUser(@PathVariable Long id){
        return userService.getSingleUser(id)
                .map(ResponseEntity :: ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/users/{id}")
    public ResponseEntity<String> updateUser(@RequestBody User user, @PathVariable Long id){
        return userService.modifyUserInfo(user, id) ? ResponseEntity.ok().build() : ResponseEntity.notFound().build();
    }

    /*@DeleteMapping("/user/{id}C")
    public ResponseEntity<String> deleteUser(@PathVariable Long id){
        return userService.removeExistingUser(id) ? ResponseEntity.ok().build() : ResponseEntity.notFound().build();

    }*/
}

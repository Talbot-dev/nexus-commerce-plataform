package ecom.app.userModule.controllers;

import ecom.app.userModule.dtos.UserRequestDTO;
import ecom.app.userModule.dtos.UserResponseDTO;
import ecom.app.userModule.services.UserService;
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
    public ResponseEntity<String> createUserResource(@RequestBody UserRequestDTO user){
        return new ResponseEntity<>(userService.registerUser(user), HttpStatus.OK);
    }

    @RequestMapping(value="/users", method = RequestMethod.GET)
    public ResponseEntity<List<UserResponseDTO>> fetchAllUsers(){
        if (userService.getAllUsers().isEmpty()) {
            return new ResponseEntity<>(userService.getAllUsers(), HttpStatus.NOT_FOUND);
        }
       return new ResponseEntity<>(userService.getAllUsers(), HttpStatus.OK);
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<UserResponseDTO> findUser(@PathVariable Long id){
        return userService.getSingleUser(id)
                .map(ResponseEntity :: ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/users/{id}")
    public ResponseEntity<String> updateUser(@RequestBody UserRequestDTO updatedUser, @PathVariable Long id){
        return userService.modifyUserInfo(updatedUser, id) ? ResponseEntity.ok().build() : ResponseEntity.notFound().build();
    }
}

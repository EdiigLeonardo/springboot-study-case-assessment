package com.acme.payments.controller;
import com.acme.payments.domain.User;
import com.acme.payments.service.UserService;
import org.springframework.web.bind.annotation.*;
import java.util.*;
@RestController @RequestMapping("/api/users")
public class UserController {
 private final UserService service; public UserController(UserService service){this.service=service;}
 @GetMapping("/age/30") public List<User> age30(){ return service.usersExactly30(); }
 @GetMapping("/unique") public Set<User> unique(){ return service.uniqueUsersByNameWrong(); }
}

package com.bachir.userservice.controller;

import com.bachir.userservice.entity.User;
import com.bachir.userservice.service.UserService;
import com.bachir.userservice.vo.ResponseTemplateVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
@Slf4j
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("/")
    public User saveUser(@RequestBody  User user){
    log.info("inside saveUser in user controller");
        return userService.saveUser(user);
    }

    @GetMapping("/{id}")

   public ResponseTemplateVo getUserWithDepartment(@PathVariable("id") Long userId){
        log.info("inside getUserWithDepartment in user controller");
        return userService.getUserWithDepartment(userId);
   }

}

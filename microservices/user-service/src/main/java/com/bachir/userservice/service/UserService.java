package com.bachir.userservice.service;

import com.bachir.userservice.entity.User;
import com.bachir.userservice.repository.UserRepository;
import com.bachir.userservice.vo.Department;
import com.bachir.userservice.vo.ResponseTemplateVo;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.client.RestTemplate;

@Service
@Slf4j
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RestTemplate restTemplate;


    public User saveUser(@RequestBody  User user) {
        log.info("inside saveUser of user service");
        return userRepository.save(user);
    }

  @CircuitBreaker(name = "breaker" , fallbackMethod="fallback")
    public ResponseTemplateVo getUserWithDepartment(Long userId) {
        log.info("inside getUserWithDepartment of user service");
        User user=userRepository.findByUserId(userId);
        Long departmentId=user.getDepartmentId();
        Department department=restTemplate.
                getForObject("http://DEPARTMENT-SERVICE/departments/" + departmentId,Department.class);
        ResponseTemplateVo vo=new ResponseTemplateVo();
        vo.setDepartment(department);
        vo.setUser(user);
      return vo;
    }


    public ResponseTemplateVo fallback(Exception e){
        log.info("message from fallback method");
        ResponseTemplateVo vo=new ResponseTemplateVo();
        return vo;
    }
}

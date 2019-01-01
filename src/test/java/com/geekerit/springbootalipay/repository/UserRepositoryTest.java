package com.geekerit.springbootalipay.repository;

import com.geekerit.springbootalipay.SpringbootAlipayApplicationTests;
import com.geekerit.springbootalipay.domain.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

class UserRepositoryTest extends SpringbootAlipayApplicationTests {

    @Autowired
    UserRepository userRepository;

    @Test
    void testSave(){
        User user = new User();
        user.setUsername("arwin");
        user.setAge(23);
        User save = userRepository.save(user);
    }

}
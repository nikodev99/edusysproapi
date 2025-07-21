package com.edusyspro.api.repository;

import com.edusyspro.api.auth.user.User;
import com.edusyspro.api.auth.user.UserRepository;
import com.edusyspro.api.auth.user.UserSchoolRole;
import com.edusyspro.api.auth.user.UserType;
import com.edusyspro.api.model.School;
import com.edusyspro.api.model.enums.Role;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.UUID;

@SpringBootTest
public class UserRepositoryTest {
    @Autowired
    private UserRepository userRepository;

    @Test
    public void createUserTest() {
        var userSchoolRole = UserSchoolRole.builder()
                .schoolId(UUID.fromString("81148a1b-bdb9-4be1-9efd-fdf4106341d6"))
                .roles(List.of(Role.ADMIN))
                .build();

        var user = User.builder()
                .username("johndoe")
                .password("@John1234")
                .phoneNumber("012345678")
                .email("john@gmail.com")
                .personalInfoId(419L)
                .schoolAffiliations(List.of(userSchoolRole))
                .userType(UserType.EMPLOYEE)
                .build();

        userSchoolRole.setUser(user);

        userRepository.save(user);
    }

    @Test
    @Transactional
    public void fetchUserTest() {
        var user = userRepository.findByUsername("johndoe");
        System.out.println(user);
    }
}

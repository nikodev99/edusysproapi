package com.edusyspro.api.repository;

import com.edusyspro.api.auth.request.AssignToUserRequest;
import com.edusyspro.api.auth.response.UserInfoResponse;
import com.edusyspro.api.auth.user.*;
import com.edusyspro.api.data.ConstantUtils;
import com.edusyspro.api.dto.custom.UpdateField;
import com.edusyspro.api.model.enums.Role;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class UserRepositoryTest {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserSchoolRoleService userSchoolRoleService;

    @Test
    public void createUserTest() {
        var userSchoolRole = UserSchoolRole.builder()
                .schoolId(UUID.fromString(ConstantUtils.CS2_SCHOOL))
                .roles(List.of(Role.ADMIN, Role.TOP_ADMIN))
                .build();

        var user = User.builder()
                .username("janedoe")
                .password("@Jane1234")
                .phoneNumber("012345678")
                .email("jane@gmail.com")
                .personalInfoId(23L)
                .schoolAffiliations(List.of(userSchoolRole))
                .userType(UserType.EMPLOYEE)
                .build();

        userSchoolRole.setUser(user);

        userRepository.save(user);
    }

    @Test
    public void updateRolesTest() {
        var user = userRepository.findByUsername("johndoe").orElseThrow();
        var roles = List.of(Role.ADMIN, Role.TOP_ADMIN);

        boolean updated = userSchoolRoleService.updateAccount(user.getId(), new UpdateField("roles", roles));

        assertTrue(updated);
    }

    @Test
    public void updateUserRolesTest() {
        boolean updated = userSchoolRoleService.updateAccount(
                4L,
                new UpdateField("roles", List.of(Role.SECRETARY))
        );
        assertTrue(updated);
    }

    @Test
    public void fetchAccountTest() {
        Optional<UserInfoResponse> user = userRepository.findUserById(3L);
        user.ifPresent(System.out::println);
    }

    @Test
    public void userSchoolRoleExistsTest() {
        AssignToUserRequest assign = new AssignToUserRequest(
                1L, "621286d1-01a7-4f00-9e57-9e135c1fe94f", List.of(Role.ADMIN, Role.TOP_ADMIN));

        boolean exists = userSchoolRoleService.isUserSchoolRoleExist(assign.userId(), assign.schoolId());

        assertTrue(exists);
    }

    @Test
    @Transactional
    public void fetchUserTest() {
        var user = userRepository.findByUsername("johndoe");
        System.out.println(user);
    }
}

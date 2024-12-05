package com.edusyspro.api.repository;

import com.edusyspro.api.dto.GuardianDTO;
import com.edusyspro.api.service.interfaces.GuardianService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class GuardianDTORepositoryTest {

    @Autowired
    private GuardianService guardianService;

    @Test
    public void findGuardianById() {
        GuardianDTO guardianDTO = guardianService.findGuardianById("b18335d3-b493-427f-9fae-1633b620ea7f");
        System.out.println(guardianDTO);
    }

}

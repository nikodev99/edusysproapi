package com.edusyspro.api.repository;

import com.edusyspro.api.dto.custom.StudentBossEssential;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class ClasseStudentBossRepositoryTest {

    @Autowired
    private ClasseStudentBossRepository classeStudentBossRepository;

    @Test
    public void testStudentEnrollmentEntitiesIsTakenIntoAccount() {
        StudentBossEssential boss = classeStudentBossRepository.findCurrentStudentBoss(15).orElseThrow();
        System.out.println(boss);
    }

}

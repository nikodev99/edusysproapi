package com.edusyspro.api.utils;

import com.arakelian.faker.model.Gender;
import com.arakelian.faker.model.Person;
import com.arakelian.faker.service.RandomAddress;
import com.arakelian.faker.service.RandomPerson;
import com.edusyspro.api.model.*;
import com.edusyspro.api.model.enums.Blood;
import com.edusyspro.api.model.enums.Status;

import java.time.LocalDate;
import java.time.Year;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.IntStream;

public class Fake {

    private static final Random random = new Random();

    public static List<Teacher> getMultipleTeachers(
            int numberOfTeacher,
            School school,
            ClasseEntity[][] classes,
            Course[][] courses
    ) {
        List<Teacher> teachers = new ArrayList<>();
        for (int i = 0; i < numberOfTeacher; i++) {
            Course[] course = null;
            if (courses != null && i < courses.length && courses[i] != null) {
                course = courses[i];
            }
            teachers.add(getTeacher(school, course, classes[i]));
        }
        return teachers;
    }

    public static Teacher getTeacher(School school, Course[] courses, ClasseEntity[] classes) {
        Person p = RandomPerson.get().next();
        com.arakelian.faker.model.Address a = RandomAddress.get().next();

        String[] streetParts = a.getStreet().split(" ");
        int number = Integer.parseInt(streetParts[0]);
        String street = streetParts[1];

        List<Course> courseList = courses != null ? Arrays.stream(courses).filter(c -> c.getId() != 0).toList() : new ArrayList<>();

        return Teacher.builder()
                .personalInfo(Individual.builder()
                        .birthDate(LocalDate.from(p.getBirthdate()))
                        .emailId((oneWord(randomWord()) + "@" + oneWord(randomWord(3)) + "." + oneWord(randomWord(3))).toLowerCase())
                        .firstName(p.getFirstName())
                        .lastName(p.getLastName())
                        .gender(p.getGender() == Gender.MALE ? com.edusyspro.api.model.enums.Gender.HOMME : com.edusyspro.api.model.enums.Gender.FEMME)
                        .status(randomStatus())
                        .telephone(randomNumber())
                        .birthCity(a.getCity())
                        .address(Address.builder()
                                .borough(randomWord())
                                .city(a.getCity())
                                .country(a.getState())
                                .neighborhood(randomWord())
                                .number(number)
                                .street(street)
                                .zipCode(a.getPostalCode())
                                .build()
                        )
                        .build()
                )
                .hireDate(randomDate())
                .courses(courseList.isEmpty() ? null : courseList)
                .aClasses(List.of(classes))
                .school(school)
                .salaryByHour(1500.00)
                .build();
    }

    public static List<StudentEntity> getStudents(int numberOfStudents, School school) {
        return IntStream.range(0, numberOfStudents)
                .mapToObj(i -> getStudent(school))
                .toList();
    }

    public static StudentEntity getStudent(School school) {
        Person p = RandomPerson.get().next();
        Person g = RandomPerson.get().next();
        com.arakelian.faker.model.Address a = RandomAddress.get().next();

        String emailId = (oneWord(randomWord()) + "@" + oneWord(randomWord(3)) + "." + oneWord(randomWord(3))).toLowerCase();

        String[] streetParts = a.getStreet().split(" ");
        int number = Integer.parseInt(streetParts[0]);
        String street = streetParts[1];

        Address address = Address.builder()
                .borough(randomWord())
                .city(a.getCity())
                .country(a.getState())
                .neighborhood(randomWord())
                .number(number)
                .street(street)
                .zipCode(a.getPostalCode())
                .build();

        HealthCondition healthCondition = HealthCondition.builder()
                .bloodType(randomBlood())
                .height(20 + random.nextInt(81))
                .weight(100 + random.nextInt(101))
                .build();

        return StudentEntity.builder()
                .personalInfo(Individual.builder()
                        .firstName(p.getFirstName())
                        .lastName(p.getLastName())
                        .gender(p.getGender() == Gender.MALE ? com.edusyspro.api.model.enums.Gender.HOMME : com.edusyspro.api.model.enums.Gender.FEMME)
                        .birthDate(randomDate(1990))
                        .birthCity("Brazzaville")
                        .nationality("COG")
                        .telephone(randomNumber())
                        .address(address)
                        .build()
                )
                .dadName(oneWord(randomWord()) + " " + oneWord(randomWord()))
                .momName(oneWord(randomWord()) + " " + oneWord(randomWord()))
                .reference(school.getAbbr() + randomNumber().substring(0, 6))
                .healthCondition(healthCondition)
                .guardian(GuardianEntity.builder()
                        .personalInfo(Individual.builder()
                                .firstName(g.getFirstName())
                                .lastName(g.getLastName())
                                .status(randomStatus())
                                .gender(g.getGender() == Gender.MALE ? com.edusyspro.api.model.enums.Gender.HOMME : com.edusyspro.api.model.enums.Gender.FEMME)
                                .emailId(emailId)
                                .telephone(randomNumber())
                                .address(address)
                                .build()
                        )
                        .jobTitle(g.getTitle())
                        .company(oneWord(randomWord()))
                        .build()
                )
                .build();
    }

    public static List<EnrollmentEntity> studentToEnroll(int numberOfStudents, AcademicYear academicYear, School school, ClasseEntity classeEntity) {
        return IntStream.range(0, numberOfStudents)
                .mapToObj(i -> setEnrollment(academicYear, school, classeEntity))
                .toList();
    }

    private static EnrollmentEntity setEnrollment(AcademicYear academicYear, School school, ClasseEntity classeEntity) {
        return EnrollmentEntity.builder()
                .academicYear(academicYear)
                .student(getStudent(school))
                .classe(classeEntity)
                .enrollmentDate(Datetime.brazzavilleDatetime())
                .isArchived(false)
                .build();
    }

    private static String randomWord() {
        return randomWord(randomDigit());
    }

    private static String randomWord(int length) {
        Person p = RandomPerson.get().next();
        return p.getComments().substring(0, length);
    }

    private static String oneWord(String sentence) {
        return sentence.replaceAll("\\s", "");
    }

    private static LocalDate randomDate() {
        return randomDate(2010, 2020);
    }

    private static LocalDate randomDate(int startYear) {
        return randomDate(startYear, Year.now().getValue());
    }

    private static LocalDate randomDate(int startYear, int endYear) {
        LocalDate startDate = LocalDate.of(startYear, 1, 1);
        long daysBetween = ChronoUnit.DAYS.between(startDate, LocalDate.of(endYear, 12, 31));
        long randomDays = ThreadLocalRandom.current().nextLong(daysBetween + 1);
        return startDate.plusDays(randomDays);
    }

    private static int randomDigit() {
        return random.nextInt(11) + 5;
    }

    private static String randomNumber() {
        int lowerBound = 100_000_000;
        int upperBound = 999_999_999;
        return Long.toString(lowerBound + random.nextInt(upperBound - lowerBound + 1));
    }

    private static Status randomStatus() {
        Status[] statuses = Status.values();
        return statuses[random.nextInt(statuses.length)];
    }

    private static Blood randomBlood() {
        Blood[] bloods = Blood.values();
        return bloods[random.nextInt(bloods.length)];
    }

}

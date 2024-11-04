package be.pxl.ja.exercise1;


import java.time.LocalDate;
import java.util.*;

import static java.util.Comparator.comparing;
import static java.util.Comparator.comparingInt;
import static java.util.stream.Collectors.*;

public class StartFile {
    static List<Student> students = StudentList.createStudents();

    public static void main(String[] args) {
        if (students.isEmpty())
            return;
        partOne();
        partTwo();
        partThree();
        partFour();
        partFive();
        partSix();
        partSeven();
        partEight();
        partNine();
    }

    public static void partOne() {
        //Get the birthday
        students.stream()
                .filter(s -> s.getDateOfBirth().getDayOfYear() == LocalDate.now().getDayOfYear())
                .forEach(student -> System.out.println(student.getName()));
    }

    public static void partTwo() {
        //Find the student named Carol.
        students.stream()
                .filter(s -> "Carol".equals(s.getName()))
                .findFirst()//Find the student named Carol. Not Students, so you don't need to find them all.
                .ifPresent(System.out::println);
    }

    private static void partThree() {
        //How many ppl will graduate in 2017?
        System.out.println(students.stream()
                .filter(s -> s.getGraduationYear() == 2017)
                .count());
    }

    private static void partFour() {
        //Highest score (print score + name)
        students.stream()
                .max(comparingInt(Student::getScore))
                .ifPresent(student -> System.out.printf("Highest Score: %s; Achieved by: %s\n", student.getScore(), student.getName()));
    }

    private static void partFive() {
        //oldest student (print I dunno, just print the whole student)
        students.stream().min(comparing(Student::getDateOfBirth)).ifPresent(System.out::println);
    }

    private static void partSix() {
        //Get all names from all students with ',' inbetween
        System.out.println("-----BEGIN All names Joined by \",\"-----");
        StringJoiner stringJoiner = new StringJoiner(",");
        students.forEach(student -> stringJoiner.add(student.getName()));
        System.out.println(stringJoiner);
        //or
        students.forEach(student -> System.out.printf("%s,", student.getName()));
        System.out.println();
        System.out.println("-----END All names Joined by \",\"-----");
    }

    private static void partSeven() {
        //youngest student graduating in 2018
        students.stream()
                .filter(s -> s.getGraduationYear() == 2018)
                .max(comparing(Student::getDateOfBirth))
                .ifPresent(student -> System.out.printf("Youngest student graduating in 2018: %s\n\tBorn: %s\n", student.getName(), student.getDateOfBirth()));
    }

    private static void partEight() {
        //Just going to put this in a handy dandy variable.
        //Map avarages in score for each graduation year
        Map<Integer, Double> studentMap = students.stream()
                .collect(
                        groupingBy(Student::getGraduationYear,
                                mapping(Student::getScore,
                                        averagingDouble(Integer::doubleValue)
                                )
                        )
                );
        System.out.println(studentMap);
    }

    private static void partNine() {
        //sort on graduation year(most recent first, i think they mean 2017 by that, if not, just replace
        // "Student::getGraduationYear" with "student -> -student.getGraduationYear()", then on score (highest first)
        students.stream()
                .sorted(comparing(student -> -student.getScore()))
                .sorted(comparing(Student::getGraduationYear))
                .forEach(s ->
                        System.out.printf("Name: %s \n\tGraduation: %s \n\tScore: %s \n", s.getName(), s.getGraduationYear(), s.getScore())
                );
    }
}
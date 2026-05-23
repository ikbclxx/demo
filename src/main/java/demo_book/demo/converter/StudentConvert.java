package demo_book.demo.converter;

import demo_book.demo.dao.Student;
import demo_book.demo.dto.StudentDTO;

public class StudentConvert {
    public static StudentDTO converStudent(Student student){
        StudentDTO studentDTO = new StudentDTO();
        studentDTO.setId(student.getId());
        studentDTO.setName(student.getName());
        studentDTO.setEmail(student.getEmail());
        studentDTO.setAge(student.getAge());
        return studentDTO;
    }

    public static Student converStudent(StudentDTO studentDTO){
        Student student = new Student();
        student.setId(student.getId());
        student.setName(studentDTO.getName());
        student.setEmail(studentDTO.getEmail());
        student.setAge(studentDTO.getAge());
        return student;
    }
}

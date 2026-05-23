package demo_book.demo.service;

import demo_book.demo.dto.StudentDTO;

public interface StudentServices {

    StudentDTO getStudentById(long id);

    long addNewStudent(StudentDTO studentDTO);

    void deleteStudentById(Long id);


    StudentDTO updateStudentById(Long id, String name, String email, String age);

}

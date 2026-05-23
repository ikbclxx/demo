package demo_book.demo.controller;


import demo_book.demo.Response;
import demo_book.demo.dao.Student;
import demo_book.demo.dto.StudentDTO;
import demo_book.demo.service.StudentServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
public class StudentController {



    @Autowired
    private StudentServices studentServices;

    @GetMapping("/student/{id}")
    public Response<StudentDTO> getStudentById(@PathVariable Long id){
        return Response.newSuccess(studentServices.getStudentById(id));
    }

    @PostMapping("/student")
    public Response<Long> addNewStudent(@RequestBody StudentDTO studentDTO){
        return Response.newSuccess(studentServices.addNewStudent(studentDTO));
    }


    @DeleteMapping("/student/{id}")
    public void deleteStudentById(@PathVariable Long id){
    studentServices.deleteStudentById(id);
    }

    @PutMapping("/student/{id}")
    public Response<StudentDTO> updateStudentById(@PathVariable Long id, @RequestParam (required = false)String name,
                                                  @RequestParam (required = false)String email,  @RequestParam (required = false)String age){


        return Response.newSuccess(studentServices.updateStudentById(id, name, email, age));


    }

}

package demo_book.demo.service;

import demo_book.demo.converter.StudentConvert;
import demo_book.demo.dao.Student;
import demo_book.demo.dao.StudentRepository;
import demo_book.demo.dto.StudentDTO;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.List;

@Service
public class  StudentServicesImpl implements StudentServices {

    @Autowired
    private StudentRepository studentRepository;


    @Override
    public StudentDTO getStudentById(long id){
        Student student = studentRepository.findById(id).orElseThrow(RuntimeException::new);
        return StudentConvert.converStudent(student);


    }

    @Override
    public long addNewStudent(StudentDTO studentDTO) {
        List<Student> studentList= studentRepository.findByEmail(studentDTO.getEmail());
        if (!CollectionUtils.isEmpty(studentList)){
            throw new IllegalStateException("email:" + studentDTO.getEmail() + "已被占用");
        }
        Student student = studentRepository.save(StudentConvert.converStudent(studentDTO));
        return student.getId();
    }

    @Override
    public void deleteStudentById(Long id){
        studentRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("id:" + id + "doesn't exist!"));
        studentRepository.deleteById(id);
    }

    @Override
    @Transactional
    public StudentDTO updateStudentById(Long id, String name, String email, String age) {
        Student studentInDB = studentRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("id:" + id + "doesn't exist!"));

        if (StringUtils.hasLength(name) && !studentInDB.getName().equals(name)) {
            studentInDB.setName(name);
        }
        if (StringUtils.hasLength(email) && !studentInDB.getEmail().equals(email)) {
            studentInDB.setEmail(email);
        }
        if (StringUtils.hasLength(age) && !studentInDB.getAge().equals(age)) {
            studentInDB.setAge(age);
        }
        Student student = studentRepository.save(studentInDB);
        return StudentConvert.converStudent(student);

    }
}

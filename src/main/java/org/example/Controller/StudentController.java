package org.example.Controller;

import lombok.AllArgsConstructor;
import org.example.Service.StudentService;
import org.example.model.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Slice;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Objects;


@RestController
@RequestMapping("/api/students")
@AllArgsConstructor
public class StudentController {
    @Autowired
    private StudentService studentService;

    @GetMapping
    public List<Student> getAllStudents(){
        return studentService.getAllStudents();
    }

    @GetMapping
    public Slice<Student> getStudentsWithPagination(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size,
            @RequestParam(defaultValue = "fullName") String sortBy){
        return studentService.getStudentsWithPagination(page, size, sortBy);
    }


    @GetMapping("/{id}")
    public Student getStudent (@PathVariable Long id){
        return studentService.getStudentById(id);
    }

    @PostMapping
    public Student addStudent(@RequestBody Student student){
        return studentService.addStudent(student);
    }

    @PutMapping("/{id}")
    public Student updateStudent(@PathVariable Long id, @RequestBody Student updatedStudent){
        return studentService.updateStudent(id, updatedStudent);
    }

    @PatchMapping("/{id}")
    public Student patchStudent(@PathVariable Long id, @RequestBody Map<String, Object> updates){
        return studentService.patchStudent(id, updates);
    }

    @DeleteMapping("/{id}")
    public void deleteStudent(@PathVariable Long id){studentService.deleteStudentById(id);}

}

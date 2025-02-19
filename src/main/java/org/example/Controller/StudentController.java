package org.example.Controller;

import lombok.AllArgsConstructor;
import org.example.DTO.StudentDTO;
import org.example.Service.StudentService;
import org.example.model.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Slice;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/api/students")
@AllArgsConstructor
public class StudentController {
    @Autowired
    private StudentService studentService;

    @GetMapping("/all")
    public List<StudentDTO> getAllStudents(){
        return studentService.getAllStudents();
    }

    @GetMapping
    public Slice<StudentDTO> getStudentsWithPagination(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size,
            @RequestParam(defaultValue = "fullName") String sortBy){
        return studentService.getStudentsWithPagination(page, size, sortBy);
    }


    @GetMapping("/{id}")
    public StudentDTO getStudent (@PathVariable Long id){
        return studentService.getStudentById(id);
    }

    @PostMapping
    public StudentDTO addStudent(@RequestBody StudentDTO studentDTO) throws IOException {
        return studentService.addStudent(studentDTO);
    }

    @PutMapping("/{id}")
    public StudentDTO updateStudent(@PathVariable Long id, @RequestBody StudentDTO studentDTO) throws IOException {
        return studentService.updateStudent(id, studentDTO);
    }

    @PatchMapping("/{id}")
    public StudentDTO patchStudent(@PathVariable Long id, @RequestBody Map<String, Object> updates) throws IOException {
        return studentService.patchStudent(id, updates);
    }

    @DeleteMapping("/{id}")
    public void deleteStudent(@PathVariable Long id) throws IOException {studentService.deleteStudentById(id);}

}

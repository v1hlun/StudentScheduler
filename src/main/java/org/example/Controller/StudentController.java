package org.example.Controller;

import lombok.AllArgsConstructor;
import org.example.DTO.StudentDTO;
import org.example.Service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Slice;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<List<StudentDTO>> getAllStudents(){
        return ResponseEntity.ok(studentService.getAllStudents());
    }

    @GetMapping
    public ResponseEntity<Slice<StudentDTO>> getStudentsWithPagination(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size,
            @RequestParam(defaultValue = "fullName") String sortBy){
        return ResponseEntity.status(200).body(studentService.getStudentsWithPagination(page, size, sortBy)) ;
    }


    @GetMapping("/{id}")
    public ResponseEntity<StudentDTO> getStudent (@PathVariable Long id){
        return ResponseEntity.ok().body(studentService.getStudentById(id));
    }

    @PostMapping
    public ResponseEntity<StudentDTO> addStudent(@RequestBody StudentDTO studentDTO) throws IOException {
        return ResponseEntity.status(201).body(studentService.addStudent(studentDTO));
    }

    @PutMapping("/{id}")
    public ResponseEntity<StudentDTO> updateStudent(@PathVariable Long id, @RequestBody StudentDTO studentDTO) throws IOException {
        try {
            return ResponseEntity.ok().body(studentService.updateStudent(id, studentDTO));
        }
        catch (RuntimeException e){
            return ResponseEntity.status(404).body(null);
        }
    }

    @PatchMapping("/{id}")
    public ResponseEntity<StudentDTO> patchStudent(@PathVariable Long id, @RequestBody Map<String, Object> updates) throws IOException {
        try {
            return ResponseEntity.ok().body(studentService.patchStudent(id, updates));
        }
        catch (RuntimeException e){
            return ResponseEntity.status(404).body(null);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteStudent(@PathVariable Long id) throws IOException {
        studentService.deleteStudentById(id);
        return ResponseEntity.noContent().build();
    }

}

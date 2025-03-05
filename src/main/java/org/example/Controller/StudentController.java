package org.example.Controller;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
public class StudentController {
    @Autowired
    private StudentService studentService;

    @GetMapping("/all")
    public ResponseEntity<List<StudentDTO>> getAllStudents() {
        log.info("Получение всех студентов");
        List<StudentDTO> students = studentService.getAllStudents();
        return ResponseEntity.ok(students);
    }

    @GetMapping
    public ResponseEntity<Slice<StudentDTO>> getStudentsWithPagination(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size,
            @RequestParam(defaultValue = "fullName") String sortBy) {
        log.info("Получение студентов с пагинацией: page={}, size={}, sortBy={}", page, size, sortBy);
        return ResponseEntity.ok(studentService.getStudentsWithPagination(page, size, sortBy));
    }

    @GetMapping("/{id}")
    public ResponseEntity<StudentDTO> getStudent(@PathVariable Long id) {
        log.info("Получение студента по ID: {}", id);
        return ResponseEntity.ok(studentService.getStudentById(id));
    }

    @PostMapping
    public ResponseEntity<StudentDTO> addStudent(@RequestBody StudentDTO studentDTO) throws IOException {
        log.info("Добавление студента: {}", studentDTO.getFullName());
        return ResponseEntity.status(201).body(studentService.addStudent(studentDTO));
    }

    @PutMapping("/{id}")
    public ResponseEntity<StudentDTO> updateStudent(@PathVariable Long id, @RequestBody StudentDTO studentDTO) throws IOException {
        log.info("Обновление студента ID: {}", id);
        try {
            return ResponseEntity.ok(studentService.updateStudent(id, studentDTO));
        } catch (RuntimeException e) {
            log.error("Ошибка обновления студента ID: {}. Причина: {}", id, e.getMessage());
            return ResponseEntity.status(404).body(null);
        }
    }

    @PatchMapping("/{id}")
    public ResponseEntity<StudentDTO> patchStudent(@PathVariable Long id, @RequestBody Map<String, Object> updates) throws IOException {
        log.info("Частичное обновление студента ID: {}", id);
        try {
            return ResponseEntity.ok(studentService.patchStudent(id, updates));
        } catch (RuntimeException e) {
            log.error("Ошибка частичного обновления студента ID: {}. Причина: {}", id, e.getMessage());
            return ResponseEntity.status(404).body(null);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteStudent(@PathVariable Long id) throws IOException {
        log.warn("Удаление студента ID: {}", id);
        studentService.deleteStudentById(id);
        return ResponseEntity.noContent().build();
    }
}

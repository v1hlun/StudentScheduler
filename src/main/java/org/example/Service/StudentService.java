package org.example.Service;

import lombok.AllArgsConstructor;
import org.example.Repository.StudentRepository;
import org.example.model.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@Service
@AllArgsConstructor
public class StudentService {

    @Autowired
    private StudentRepository studentRepository;
    private WebSocketHandler webSocketHandler;

    public Student addStudent(Student student) throws IOException {
        Student savedStudent = studentRepository.save(student);
        webSocketHandler.sendUpdate("student", savedStudent);
        return savedStudent;
    }

    public Student getStudentById(Long id){
        return studentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Student not found"))  ;
    }

    public Slice<Student> getStudentsWithPagination(int page, int size, String sortBy){
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy).ascending());
        return studentRepository.findBy(pageable);
    }

    public List<Student> getAllStudents(){
        return studentRepository.findAll();
    }

    public Student updateStudent(Long id,Student updatedStudent) throws IOException {
        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Student not found"));

        /*// Обновляем поля
        student.setFullName(updatedStudent.getFullName());
        student.setAddress(updatedStudent.getAddress());
        student.setTelNumber(updatedStudent.getTelNumber());
        student.setSpeciality(updatedStudent.getSpeciality());
        student.setCode(updatedStudent.getCode());
        student.setFaculty(updatedStudent.getFaculty());
        student.setFormEducation(updatedStudent.getFormEducation());
        student.setPaidEducation(updatedStudent.getPaidEducation());
        student.setStepEducation(updatedStudent.getStepEducation());
        student.setYearOfGraduation(updatedStudent.getYearOfGraduation());
        student.setConsolidation(updatedStudent.getConsolidation());*/

        updatedStudent.setId(id);
        Student savedStudent = studentRepository.save(updatedStudent);
        webSocketHandler.sendUpdate("student", savedStudent);

        return savedStudent;
    }


    public Student patchStudent(Long id, Map<String,Object> updates) throws IOException {
        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Student not found"));

        updates.forEach((key, value) -> {
            switch (key) {
                case "fullName" -> student.setFullName((String) value);
                case "address" -> student.setAddress((String) value);
                case "telNumber" -> student.setTelNumber((String) value);
                case "speciality" -> student.setSpeciality((String) value);
                case "code" -> student.setCode((String) value);
                case "faculty" -> student.setFaculty((String) value);
                case "formEducation" -> student.setFormEducation((String) value);
                case "paidEducation" -> student.setPaidEducation((String) value);
                case "stepEducation" -> student.setStepEducation((String) value);
                case "yearOfGraduation" -> student.setYearOfGraduation((String) value);
                case "consolidation" -> student.setConsolidation((String) value);
                default -> throw new RuntimeException("Invalid field: " + key);
            }
        });
        Student updatedStudent = studentRepository.save(student);
        webSocketHandler.sendUpdate("student", updatedStudent);

        return updatedStudent;
    }

    public void deleteStudentById(Long studentId) throws IOException {
        studentRepository.deleteById(studentId);
        webSocketHandler.sendUpdate("student_deleted", studentId);
    }
}

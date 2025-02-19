package org.example.Service;

import lombok.AllArgsConstructor;
import org.example.DTO.StudentDTO;
import org.example.Mapper.StudentMapper;
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
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class StudentService {

    @Autowired
    private StudentRepository studentRepository;
    private WebSocketHandler webSocketHandler;

    public StudentDTO addStudent(StudentDTO studentDTO) throws IOException {
        Student student = StudentMapper.INSTANCE.toEntity(studentDTO);
        Student savedStudent = studentRepository.save(student);
        webSocketHandler.sendUpdate("student", savedStudent);
        return StudentMapper.INSTANCE.toDto(savedStudent);
    }

    public StudentDTO getStudentById(Long id){
        Student student =  studentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Student not found"))  ;
        return StudentMapper.INSTANCE.toDto(student);
    }

    public Slice<StudentDTO> getStudentsWithPagination(int page, int size, String sortBy){
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy).ascending());
        return studentRepository.findBy(pageable).map(StudentMapper.INSTANCE::toDto);
    }

    public List<StudentDTO> getAllStudents(){
        return studentRepository.findAll()
                .stream()
                .map(StudentMapper.INSTANCE::toDto)
                .collect(Collectors.toList());
    }


    public StudentDTO updateStudent(Long id, StudentDTO studentDTO) throws IOException {
        Student existingStudent = studentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Student not found"));

        Student updatedStudent = StudentMapper.INSTANCE.toEntity(studentDTO);
        updatedStudent.setId(id);

        Student savedStudent = studentRepository.save(updatedStudent);
        webSocketHandler.sendUpdate("student", savedStudent);

        return StudentMapper.INSTANCE.toDto(savedStudent);
    }


    public StudentDTO patchStudent(Long id, Map<String,Object> updates) throws IOException {
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

        return StudentMapper.INSTANCE.toDto(updatedStudent);
    }

    public void deleteStudentById(Long studentId) throws IOException {
        studentRepository.deleteById(studentId);
        webSocketHandler.sendUpdate("student_deleted", studentId);
    }
}

package org.example.Service;

import lombok.AllArgsConstructor;
import org.example.Repository.StudentRepository;
import org.example.model.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PatchMapping;

import java.util.List;
import java.util.Map;

@Service
@AllArgsConstructor
public class StudentService {

    @Autowired
    private StudentRepository studentRepository;

    public Student addStudent(Student student){
        return studentRepository.save(student);
    }

    public Student getStudentById(Long id){
        return studentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Student not found"))  ;
    }

    /*public Student getStudentByName(String name){
        return studentRepository.findByFullName(name);
    }*/

    public List<Student> getAllStudents(){
        return studentRepository.findAll();
    }

    public Student updateStudent(Long id,Student updatedStudent){
        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Student not found"));

        // Обновляем поля
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
        student.setConsolidation(updatedStudent.getConsolidation());


        return studentRepository.save(student);
    }


    public Student patchStudent(Long id, Map<String,Object> updates){
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
        return studentRepository.save(student);
    }

    public void deleteStudentById(Long studentId){studentRepository.deleteById(studentId);}
}

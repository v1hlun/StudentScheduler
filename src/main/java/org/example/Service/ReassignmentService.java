package org.example.Service;

import lombok.AllArgsConstructor;
import org.example.Repository.ReassignmentRepository;
import org.example.Repository.StudentRepository;
import org.example.model.Reassignment;
import org.example.model.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@AllArgsConstructor
public class ReassignmentService {
    @Autowired
    private final ReassignmentRepository reassignmentRepository;
    @Autowired
    private final StudentRepository studentRepository;

    public Reassignment addReassignment(Long studentId, Reassignment reassignment){
        Student student = studentRepository.findById(studentId).
                orElseThrow(() -> new RuntimeException("Student not fount"));
        reassignment.setStudent(student);
        return reassignmentRepository.save(reassignment);
    }

    public Reassignment getReassignmentById(Long id){
        return reassignmentRepository.findByStudentId(id).
                orElseThrow(() -> new RuntimeException("Student not found"));
    }

    public List<Reassignment> getAllReassignment(){return reassignmentRepository.findAll();}

    public Slice<Reassignment> getReassignmentsWithPagination(int page, int size){
        Pageable pageable = PageRequest.of(page, size) ;
        return reassignmentRepository.findBy(pageable);
    }

    public Reassignment updateReassignment(Long id, Map<String, Object> updates){
        Reassignment reassignment = reassignmentRepository.findByStudentId(id)
                .orElseThrow(() -> new RuntimeException("No reassignment with such id"));

        updates.forEach((key, value) -> {
            switch (key) {
                case "profiling" -> reassignment.setProfiling((String) value);
                case "nameOldCompany" -> reassignment.setNameOldCompany((String) value);
                case "nameNewCompany" -> reassignment.setNameNewCompany((String) value);
                case "jobTitle" -> reassignment.setJobTitle((String) value);
                case "notes" -> reassignment.setNotes((String) value);
                case "consolidation" -> reassignment.setConsolidation((String) value);
            }
        });

        return reassignmentRepository.save(reassignment);
    }

    public void deleteReassignment(Long id){
        if (!reassignmentRepository.existsById(id)) {
            throw new RuntimeException("No reassignment with such id");
        }
        reassignmentRepository.deleteById(id);};

}

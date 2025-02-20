package org.example.Service;

import lombok.AllArgsConstructor;
import org.example.DTO.ReassignmentDTO;
import org.example.Mapper.ReassignmentMapper;
import org.example.Repository.ReassignmentRepository;
import org.example.Repository.StudentRepository;
import org.example.model.Reassignment;
import org.example.model.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ReassignmentService {
    @Autowired
    private final ReassignmentRepository reassignmentRepository;
    @Autowired
    private final StudentRepository studentRepository;
    private WebSocketHandler webSocketHandler;

    // Добавление нового Reassignment
    public ReassignmentDTO addReassignment(Long studentId, ReassignmentDTO reassignmentDTO) throws IOException {
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new RuntimeException("Student not found"));

        Reassignment reassignment = ReassignmentMapper.INSTANCE.toEntity(reassignmentDTO);
        reassignment.setStudent(student);

        Reassignment saved = reassignmentRepository.save(reassignment);
        webSocketHandler.sendUpdate("reassignment", toDTOWithStudent(saved));
        return toDTOWithStudent(saved);
    }

    // Получение Reassignment по ID
    public ReassignmentDTO getReassignmentById(Long id) {
        Reassignment reassignment = reassignmentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Reassignment not found"));
        return toDTOWithStudent(reassignment);
    }

    // Получение всех Reassignment
    public List<ReassignmentDTO> getAllReassignment() {
        return reassignmentRepository.findAll().stream()
                .map(this::toDTOWithStudent)
                .collect(Collectors.toList());
    }

    // Получение Reassignment с пагинацией
    public Slice<ReassignmentDTO> getReassignmentsWithPagination(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return reassignmentRepository.findBy(pageable).map(this::toDTOWithStudent);
    }

    // Обновление Reassignment
    public ReassignmentDTO updateReassignment(Long id, Map<String, Object> updates) throws IOException {
        Reassignment reassignment = reassignmentRepository.findById(id)
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

        Reassignment updated = reassignmentRepository.save(reassignment);
        webSocketHandler.sendUpdate("reassignment", toDTOWithStudent(updated));
        return toDTOWithStudent(updated);
    }

    // Удаление Reassignment
    public void deleteReassignment(Long id) throws IOException {
        if (!reassignmentRepository.existsById(id)) {
            throw new RuntimeException("No reassignment with such id");
        }
        reassignmentRepository.deleteById(id);
        webSocketHandler.sendUpdate("reassignment", "Reassignment deleted: " + id);
    }


    private ReassignmentDTO toDTOWithStudent(Reassignment reassignment) {
        ReassignmentDTO dto = ReassignmentMapper.INSTANCE.toDto(reassignment);
        Student student = reassignment.getStudent();
        if (student != null) {
            dto.setStudentId(student.getId());
            dto.setFullName(student.getFullName());
            dto.setAddress(student.getAddress());
        }
        return dto;
    }
}
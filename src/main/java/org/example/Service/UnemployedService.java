package org.example.Service;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.example.DTO.UnemployedDTO;
import org.example.Mapper.UnemployedMapper;
import org.example.Repository.StudentRepository;
import org.example.Repository.UnemployedRepository;
import org.example.model.Distribution;
import org.example.model.Student;
import org.example.model.Unemployed;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class UnemployedService {
    @Autowired
    private final UnemployedRepository unemployedRepository;
    @Autowired
    private final StudentRepository studentRepository;
    private WebSocketHandler webSocketHandler;

    public UnemployedDTO addUnemployed(Long studentId, UnemployedDTO unemployedDTO) throws IOException {
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new RuntimeException("Student not found"));

        Unemployed unemployed = UnemployedMapper.INSTANCE.toEntity(unemployedDTO);
        unemployed.setStudent(student);

        Unemployed saved = unemployedRepository.save(unemployed);
        webSocketHandler.sendUpdate("unemployed", toDTOWithStudent(saved));
        return toDTOWithStudent(saved);
    }

    public UnemployedDTO getUnemployedById(Long id) {
        Unemployed unemployed = unemployedRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Unemployed not found"));
        return toDTOWithStudent(unemployed);
    }

    public List<UnemployedDTO> getAllUnemployed(){
        return unemployedRepository.findAll().stream()
                .map(this::toDTOWithStudent)
                .collect(Collectors.toList());
    }

    public Slice<UnemployedDTO> getUnemployedWithPagination(int page, int size){
        Pageable pageable = PageRequest.of(page, size);
        return unemployedRepository.findBy(pageable).map(this::toDTOWithStudent);    }

    public UnemployedDTO updateUnemployed(Long id, Map<String, Object> updates) throws IOException {
        Unemployed unemployed = unemployedRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("No unemployed with such id"));

        updates.forEach((key, value) -> {
            switch (key) {
                case "profiling" -> unemployed.setProfiling((String) value);
                case "nameCompany" -> unemployed.setNameCompany((String) value);
                case "directionReturned" -> unemployed.setDirectionReturned((String) value);
                case "admissionToHigherLevel" -> unemployed.setAdmissionToHigherLevel((String) value);
                case "companyNotified" -> unemployed.setCompanyNotified((String) value);
                case "dataNotification" -> unemployed.setDataNotification((String) value);
                case "conscriptionEmployment" -> unemployed.setConscriptionEmployment((String) value);
                case "servesArmy" -> unemployed.setServesArmy((String) value);
                case "paymentOfTuitionFees" -> unemployed.setPaymentOfTuitionFees((String) value);
                case "notes" -> unemployed.setNotes((String) value);
                case "consolidation" -> unemployed.setConsolidation((String) value);
            }
        });
        Unemployed updated = unemployedRepository.save(unemployed);
        webSocketHandler.sendUpdate("unemployed", toDTOWithStudent(updated));

        return toDTOWithStudent(updated);
    }

    @Transactional
    public void deleteUnemployed(Long id) throws IOException {
        Optional<Unemployed> unemployedOpt = unemployedRepository.findById(id);
        if (unemployedOpt.isEmpty()) {
            throw new RuntimeException("No unemployed with such id");
        }

        Unemployed unemployed = unemployedOpt.get();

        // Находим связанного Student и разрываем связь
        Student student = studentRepository.findByUnemployed(unemployed);
        if (student != null) {
            student.setUnemployed(null);
            studentRepository.save(student);
            webSocketHandler.sendUpdate("student", "Student updated: " + student.getId());
        }

        // Теперь удаляем Distribution
        unemployedRepository.delete(unemployed);
        webSocketHandler.sendUpdate("unemployed", "Unemployed deleted: " + id);
    }

    private UnemployedDTO toDTOWithStudent(Unemployed unemployed) {
        UnemployedDTO dto = UnemployedMapper.INSTANCE.toDto(unemployed);
        Student student = unemployed.getStudent();
        if (student != null) {
            dto.setStudentId(student.getId());
            dto.setFullName(student.getFullName());
            dto.setAddress(student.getAddress());
        }
        return dto;
    }

}

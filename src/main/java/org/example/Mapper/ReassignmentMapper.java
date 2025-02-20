package org.example.Mapper;


import org.example.DTO.DistributionDTO;
import org.example.DTO.ReassignmentDTO;
import org.example.DTO.StudentDTO;
import org.example.model.Distribution;
import org.example.model.Reassignment;
import org.example.model.Student;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ReassignmentMapper {
    ReassignmentMapper INSTANCE = Mappers.getMapper(ReassignmentMapper.class);

    @Mapping(source = "student.id", target = "studentId")
    ReassignmentDTO toDto(Reassignment entity);

    @Mapping(source = "studentId", target = "student.id")
    Reassignment toEntity(ReassignmentDTO dto);

    default StudentDTO studentToStudentDTO(Student student) {
        if (student == null) {
            return null;
        }
        StudentDTO dto = new StudentDTO();
        dto.setId(student.getId());
        dto.setFullName(student.getFullName());
        dto.setAddress(student.getAddress());
        return dto;
    }
}

package org.example.Mapper;

import org.example.DTO.StudentDTO;
import org.example.DTO.UnemployedDTO;
import org.example.model.Student;
import org.example.model.Unemployed;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface UnemployedMapper {
    UnemployedMapper INSTANCE = Mappers.getMapper(UnemployedMapper.class);

    @Mapping(source = "student.id", target = "studentId")
    UnemployedDTO toDto(Unemployed entity);

    @Mapping(source = "studentId", target = "student.id")
    Unemployed toEntity(UnemployedDTO dto);

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

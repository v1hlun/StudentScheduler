package org.example.Mapper;



import org.example.DTO.StudentDTO;
import org.example.model.Student;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface StudentMapper {
    StudentMapper INSTANCE = Mappers.getMapper(StudentMapper.class);

    Student toEntity(StudentDTO dto);
    StudentDTO toDto(Student entity);
}


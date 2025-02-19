package org.example.Mapper;


import org.example.DTO.DistributionDTO;
import org.example.model.Distribution;
import org.example.model.Student;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface DistributionMapper {
    DistributionMapper INSTANCE = Mappers.getMapper(DistributionMapper.class);

    @Mapping(source = "student.id", target = "studentId")
    DistributionDTO toDto(Distribution entity);

    @Mapping(source = "studentId", target = "student.id")
    Distribution toEntity(DistributionDTO dto);
}

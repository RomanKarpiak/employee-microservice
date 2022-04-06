package com.roman.mappers;

import com.roman.entity.DepartmentDtoSnapshot;
import dto.department.DepartmentDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface DepartmentDtoSnapshotMapper {

    @Mapping(target = "snapshotId",ignore = true)
    @Mapping(target = "departmentId",source = "departmentDto.id")
    DepartmentDtoSnapshot toDtoSnapshot(DepartmentDto departmentDto);
}

package com.roman.mappers;

import com.roman.entity.DepartmentDtoSnapshot;
import dto.department.DepartmentDto;
import javax.annotation.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2022-04-05T19:07:47+0300",
    comments = "version: 1.4.2.Final, compiler: javac, environment: Java 1.8.0_322 (Amazon.com Inc.)"
)
@Component
public class DepartmentDtoSnapshotMapperImpl implements DepartmentDtoSnapshotMapper {

    @Override
    public DepartmentDtoSnapshot toDtoSnapshot(DepartmentDto departmentDto) {
        if ( departmentDto == null ) {
            return null;
        }

        DepartmentDtoSnapshot departmentDtoSnapshot = new DepartmentDtoSnapshot();

        departmentDtoSnapshot.setDepartmentId( departmentDto.getId() );
        departmentDtoSnapshot.setDepartmentName( departmentDto.getDepartmentName() );
        departmentDtoSnapshot.setCreationDate( departmentDto.getCreationDate() );
        departmentDtoSnapshot.setMainDepartment( departmentDto.getMainDepartment() );

        return departmentDtoSnapshot;
    }
}

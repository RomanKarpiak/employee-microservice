package com.roman.mappers;

import com.roman.dto.DepartmentDto;
import com.roman.entity.DepartmentDtoSnapshot;
import javax.annotation.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2021-07-25T20:04:41+0400",
    comments = "version: 1.4.2.Final, compiler: javac, environment: Java 1.8.0_291 (Oracle Corporation)"
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

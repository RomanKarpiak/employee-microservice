package com.roman.repo;

import com.roman.entity.DepartmentDtoSnapshot;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface DepartmentDtoSnapshotRepo extends JpaRepository<DepartmentDtoSnapshot,Long> {

    DepartmentDtoSnapshot findByDepartmentId(Long departmentId);

    @Transactional
    @Modifying
    @Query("delete from DepartmentDtoSnapshot dds WHERE dds.departmentId = :departmentId")
    void deleteByDepartmentId(@Param( "departmentId") Long departmentId);
}

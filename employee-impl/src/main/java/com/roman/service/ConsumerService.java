package com.roman.service;

import com.roman.entity.config.kafka.ConsumerKafkaProperties;
import com.roman.entity.DepartmentDtoSnapshot;
import com.roman.mappers.DepartmentDtoSnapshotMapper;
import com.roman.repo.DepartmentDtoSnapshotRepo;
import dto.department.DepartmentDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class ConsumerService {

    private final DepartmentDtoSnapshotRepo dtoRepo;
    private final DepartmentDtoSnapshotMapper snapshotMapper;

    @KafkaListener(topics = {"created-department"}, groupId = ConsumerKafkaProperties.CONSUMER_GROUP_ID)
    public void consumerMessage1(DepartmentDto dto) {
        DepartmentDtoSnapshot snapshot = dtoRepo.save(snapshotMapper.toDtoSnapshot(dto));
        log.info("CONSUMER: We received and save a new DepartmentDto in departments_snapshot table!!!{}", dto);
        log.info("CONSUMER: Added SNAPSHOT:{}", snapshot);
    }

    @KafkaListener(topics = {"deleted-department"}, groupId = ConsumerKafkaProperties.CONSUMER_GROUP_ID)
    public void consumerMessage2(DepartmentDto dto) {
        dtoRepo.deleteByDepartmentId(dto.getId());
        log.info("CONSUMER: We received and deleted the DepartmentDto from departments_snapshot table!!!{}", dto);
    }
}

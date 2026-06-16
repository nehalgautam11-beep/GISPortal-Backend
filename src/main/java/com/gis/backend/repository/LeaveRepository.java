package com.gis.backend.repository;

import com.gis.backend.entity.LeaveRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LeaveRepository
        extends JpaRepository<LeaveRequest, Long> {

    List<LeaveRequest> findByUserRole(
            String userRole
    );

    List<LeaveRequest> findByStatus(
            String status
    );

    List<LeaveRequest> findByUserId(
            Long userId
    );
}
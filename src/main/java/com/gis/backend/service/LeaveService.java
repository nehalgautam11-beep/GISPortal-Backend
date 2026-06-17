package com.gis.backend.service;

import com.gis.backend.entity.LeaveRequest;
import com.gis.backend.repository.LeaveRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class LeaveService {

    private final LeaveRepository repository;

    public LeaveService(
            LeaveRepository repository
    ) {
        this.repository = repository;
    }

    public LeaveRequest applyLeave(
            LeaveRequest leaveRequest
    ) {

        leaveRequest.setAppliedAt(
                LocalDateTime.now()
        );

        leaveRequest.setStatus(
                "PENDING"
        );

        return repository.save(
                leaveRequest
        );
    }

    public List<LeaveRequest> getLeavesByRole(
            String role
    ) {

        return repository.findByUserRole(
                role
        );
    }

    public List<LeaveRequest> getPendingLeaves() {

        return repository.findByStatus(
                "PENDING"
        );
    }

    public void deleteLeave(Long leaveId) {

        repository.deleteById(leaveId);
    }


    public List<LeaveRequest> getAllLeaves() {

        return repository.findAll();
    }




    public List<LeaveRequest> getLeavesByUserId(
            Long userId
    ) {

        return repository.findByUserId(
                userId
        );
    }

    public LeaveRequest approveLeave(
            Long leaveId,
            String adminName
    ) {

        LeaveRequest leave =
                repository.findById(leaveId)
                        .orElseThrow();

        leave.setStatus(
                "APPROVED"
        );

        leave.setApprovedBy(
                adminName
        );

        leave.setApprovedAt(
                LocalDateTime.now()
        );

        return repository.save(
                leave
        );
    }

    public LeaveRequest rejectLeave(
            Long leaveId,
            String adminName
    ) {

        LeaveRequest leave =
                repository.findById(leaveId)
                        .orElseThrow();

        leave.setStatus(
                "REJECTED"
        );

        leave.setApprovedBy(
                adminName
        );

        leave.setApprovedAt(
                LocalDateTime.now()
        );

        return repository.save(
                leave
        );
    }
}
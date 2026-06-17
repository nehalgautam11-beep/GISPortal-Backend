package com.gis.backend.controller;

import com.gis.backend.entity.LeaveRequest;
import com.gis.backend.service.LeaveService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/leaves")
public class LeaveController {

    private final LeaveService service;

    public LeaveController(
            LeaveService service
    ) {
        this.service = service;
    }

    @PostMapping
    public LeaveRequest applyLeave(
            @RequestBody LeaveRequest leaveRequest
    ) {

        return service.applyLeave(
                leaveRequest
        );
    }


    @DeleteMapping("/{id}")
    public void deleteLeave(
            @PathVariable Long id
    ) {

        service.deleteLeave(id);
    }




    @GetMapping("/all")
    public List<LeaveRequest> getAllLeaves() {

        return service.getAllLeaves();
    }

    @GetMapping("/pending")
    public List<LeaveRequest> getPendingLeaves() {

        return service.getPendingLeaves();
    }

    @GetMapping("/my")
    public List<LeaveRequest> getMyLeaves(
            @RequestParam Long userId
    ) {

        return service.getLeavesByUserId(
                userId
        );
    }

    @PutMapping("/{id}/approve")
    public LeaveRequest approveLeave(

            @PathVariable Long id,

            @RequestParam String adminName
    ) {

        return service.approveLeave(
                id,
                adminName
        );
    }

    @PutMapping("/{id}/reject")
    public LeaveRequest rejectLeave(

            @PathVariable Long id,

            @RequestParam String adminName
    ) {

        return service.rejectLeave(
                id,
                adminName
        );
    }
}
package com.farhan.Socio.controller;

import com.farhan.Socio.entity.Report;
import com.farhan.Socio.entity.ReportActionRequest;
import com.farhan.Socio.service.ReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin/reports")
@RequiredArgsConstructor
public class AdminReportController {

    private final ReportService reportService;
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/act")
    public ResponseEntity<String> actOnReport(@RequestBody ReportActionRequest request) {
        Report updatedReport = reportService.actOnReport(request.getReportId(), request.getNewStatus());
        return ResponseEntity.ok("Report status updated to " + updatedReport.getStatus());
    }
}

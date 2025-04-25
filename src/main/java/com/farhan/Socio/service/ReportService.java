package com.farhan.Socio.service;

import com.farhan.Socio.entity.Report;
import com.farhan.Socio.entity.ReportStatus;
import com.farhan.Socio.repository.ReportRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReportService {

    private final ReportRepository reportRepository;

    public Report actOnReport(Long reportId, ReportStatus newStatus) {
        Report report = reportRepository.findById(reportId)
                .orElseThrow(() -> new RuntimeException("Report not found"));

        report.setStatus(newStatus);
        return reportRepository.save(report);
    }
}


package com.farhan.Socio.entity;

import lombok.Data;

@Data
public class ReportActionRequest {
    private Long reportId;
    private ReportStatus newStatus; // eg: CLOSED
}

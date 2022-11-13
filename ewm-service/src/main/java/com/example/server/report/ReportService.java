package com.example.server.report;

import org.springframework.web.bind.annotation.PathVariable;

public interface ReportService {

	ReportDto reportComment(Long userId, Long commentId, NewReportDto commentDto);

	ReportDto confirmReport(@PathVariable("reportId") Long reportId);

	ReportDto rejectReport(@PathVariable("reportId") Long reportId);
}

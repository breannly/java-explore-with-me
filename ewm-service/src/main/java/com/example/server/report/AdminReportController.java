package com.example.server.report;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin/reports")
public class AdminReportController {

	private final ReportService reportService;

	@PatchMapping("/{reportId}/confirmed")
	public ReportDto confirmReport(@PathVariable("reportId") Long reportId) {
		return reportService.confirmReport(reportId);
	}

	@PatchMapping("/{reportId}/rejected")
	public ReportDto rejectReport(@PathVariable("reportId") Long reportId) {
		return reportService.rejectReport(reportId);
	}
}

package com.example.server.report;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users/{userId}/reports")
public class AuthorizedReportController {

	private final ReportService reportService;

	@PostMapping("/{commentId}")
	public ReportDto reportComment(@PathVariable("userId") Long userId,
								   @PathVariable("commentId") Long commentId,
								   @Valid @RequestBody NewReportDto commentDto) {
		return reportService.reportComment(userId, commentId, commentDto);
	}
}

package com.example.server.report;

import com.example.server.comment.dto.CommentShortDto;
import com.example.server.user.dto.UserShortDto;

public class ReportMapper {

	public static Report mapToReport(NewReportDto newRequestCommentDto,
									 ReportReason reason) {
		Report requestComment = new Report();
		requestComment.setDescription(newRequestCommentDto.getDescription());
		requestComment.setReason(reason);

		return requestComment;
	}

	public static ReportDto mapToReportDto(Report report) {
		return new ReportDto(
				report.getId(),
				new UserShortDto(
						report.getPlaintiff().getId(),
						report.getPlaintiff().getName()
				),
				new CommentShortDto(
						report.getComment().getId(),
						new UserShortDto(
								report.getComment().getOwner().getId(),
								report.getComment().getOwner().getName()
						),
						report.getComment().getDescription(),
						report.getComment().getAvailable()
				),
				report.getReason(),
				report.getDescription()
		);
	}
}

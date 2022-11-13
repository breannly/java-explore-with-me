package com.example.server.report;

import com.example.server.comment.dto.CommentShortDto;
import com.example.server.user.dto.UserShortDto;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class ReportDto {

	private final Long id;
	private final UserShortDto plaintiff;
	private final CommentShortDto comment;
	private final ReportReason reason;
	private final String description;
	private final ReportStatus status;
}

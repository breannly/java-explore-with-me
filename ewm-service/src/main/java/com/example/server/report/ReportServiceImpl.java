package com.example.server.report;

import com.example.server.comment.model.Comment;
import com.example.server.comment.repo.CommentRepository;
import com.example.server.exception.ObjectNotFoundException;
import com.example.server.exception.ValidationException;
import com.example.server.user.User;
import com.example.server.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReportServiceImpl implements ReportService {

	private final UserRepository userRepository;
	private final ReportRepository reportRepository;
	private final CommentRepository commentRepository;

	private static final Boolean NOT_AVAILABLE = false;

	@Override
	public ReportDto reportComment(Long userId, Long commentId, NewReportDto commentDto) {
		User user = userRepository.findById(userId).orElseThrow(()
				-> new ObjectNotFoundException("User not found"));
		Comment comment = commentRepository.findById(commentId).orElseThrow(()
				-> new ObjectNotFoundException("Comment not found"));
		if (comment.getOwner().getId().equals(userId)) {
			throw new ValidationException("Validation failed");
		}
		ReportReason reason = ReportReason.from(commentDto.getReason());
		Report report = ReportMapper.mapToReport(commentDto, user, comment, reason);
		return ReportMapper.mapToReportDto(reportRepository.save(report));
	}

	@Override
	public ReportDto confirmReport(Long reportId) {
		Report report = reportRepository.findById(reportId).orElseThrow(()
				-> new ObjectNotFoundException("Report not found"));
		report.setStatus(ReportStatus.CONFIRMED);
		Comment comment = report.getComment();
		comment.setAvailable(NOT_AVAILABLE);
		commentRepository.save(comment);
		reportRepository.save(report);
		return ReportMapper.mapToReportDto(report);
	}

	@Override
	public ReportDto rejectReport(Long reportId) {
		Report report = reportRepository.findById(reportId).orElseThrow(()
				-> new ObjectNotFoundException("Report not found"));
		report.setStatus(ReportStatus.REJECTED);
		reportRepository.save(report);
		return ReportMapper.mapToReportDto(report);
	}
}

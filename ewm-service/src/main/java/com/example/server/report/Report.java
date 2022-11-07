package com.example.server.report;

import com.example.server.comment.model.Comment;
import com.example.server.user.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "reports")
public class Report {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "report_id")
	private Long id;

	@NotNull
	@ManyToOne
	@JoinColumn(name = "plaintiff_id")
	private User plaintiff;

	@NotNull
	@ManyToOne
	@JoinColumn(name = "comment_id")
	private Comment comment;

	@Enumerated(value = EnumType.STRING)
	private ReportReason reason;

	@NotBlank
	private String description;

	@Enumerated(value = EnumType.STRING)
	private ReportStatus status;
}

package com.example.server.report;

public enum ReportReason {
	SPAM,
	FRAUD,
	VIOLENCE,
	MISLEADING,
	NOT_SUPPORT;

	public static ReportReason from(String label) {
		for (ReportReason commentReason : ReportReason.values()) {
			if (commentReason.toString().equals(label)) {
				return commentReason;
			}
		}
		return NOT_SUPPORT;
	}
}

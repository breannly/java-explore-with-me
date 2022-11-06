package com.example.stats.view;

public class ViewMapper {

	public static View mapToView(EndpointHit endpointHit) {
		View view = new View();
		view.setApp(endpointHit.getApp());
		view.setIp(endpointHit.getIp());
		view.setUri(endpointHit.getUri());
		view.setTimestamp(endpointHit.getTimestamp());
		return view;
	}

	public static ViewStats mapToViewStats(View view, Long countView) {
		return new ViewStats(
				view.getApp(),
				view.getUri(),
				countView);
	}
}

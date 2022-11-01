package com.example.stats.view;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ViewServiceImpl implements ViewService {

	private final ViewRepository viewRepository;

	@Override
	public void saveStat(EndpointHit endpointHit) {
		View view = ViewMapper.mapToView(endpointHit);
		viewRepository.save(view);
	}

	@Override
	public List<ViewStats> getStats(StatsSearchParams searchParams) {
		List<View> stats = viewRepository.searchStatsByParams(searchParams);
		return stats
				.stream()
				.map(view -> {
					Long countViews;
					if (searchParams.getUnique()) {
						countViews = viewRepository.countUniqueView(view.getUri());
					} else {
						countViews = viewRepository.countViewByUri(view.getUri());
					}
					return ViewMapper.mapToViewStats(view, countViews);
				})
				.collect(Collectors.toList());
	}
}

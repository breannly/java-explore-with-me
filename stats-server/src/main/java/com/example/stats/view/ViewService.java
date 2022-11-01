package com.example.stats.view;

import java.util.List;

public interface ViewService {

	void saveStat(EndpointHit endpointHit);

	List<ViewStats> getStats(StatsSearchParams searchParams);
}

package com.example.stats.view;

import java.util.List;

public interface ViewService {

	void postStat(EndpointHit endpointHit);

	List<ViewStats> getStats(StatsSearchParams searchParams);
}

package com.example.server.compilations;

import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

public interface CompilationService {

	List<CompilationDto> getCompilations(Integer from, Integer size);

	CompilationDto getCompilation(Long compId);

	CompilationDto createCompilation(NewCompilationDto newCompilation);

	void deleteCompilation(Long compId);

	void deleteCompilationEvent(Long compId, Long eventId);

	void addCompilationEvent(Long compId, Long eventId);

	void unpinCompilation(Long compId);

	void pinCompilation(Long compId);

}

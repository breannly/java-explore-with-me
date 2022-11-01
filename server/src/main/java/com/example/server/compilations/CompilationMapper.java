package com.example.server.compilations;

import com.example.server.event.dto.EventShortDto;
import com.example.server.event.model.Event;

import java.util.List;

public class CompilationMapper {

	public static Compilation mapToCompilation(NewCompilationDto newCompilation, List<Event> events) {
		Compilation compilation = new Compilation();
		compilation.setEvents(events);
		compilation.setPinned(newCompilation.getPinned());
		compilation.setTitle(newCompilation.getTitle());
		return compilation;
	}

	public static CompilationDto mapToCompilationDto(Compilation compilation, List<EventShortDto> events) {
		return new CompilationDto(
				compilation.getId(),
				events,
				compilation.getPinned(),
				compilation.getTitle()
		);
	}
}

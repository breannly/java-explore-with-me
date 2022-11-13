package com.example.server.compilations;

import com.example.server.client.EventClient;
import com.example.server.event.dto.EventMapper;
import com.example.server.event.dto.EventShortDto;
import com.example.server.event.model.Event;
import com.example.server.event.repo.EventRepository;
import com.example.server.event.repo.RequestRepository;
import com.example.server.exception.ObjectNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CompilationServiceImpl implements CompilationService {

	private final CompilationRepository compilationRepository;
	private final RequestRepository requestRepository;
	private final EventRepository eventRepository;
	private final EventClient eventClient;

	@Override
	public List<CompilationDto> getCompilations(Integer from, Integer size) {
		Pageable pageable = PageRequest.of(from / size, size);
		List<Compilation> compilations = compilationRepository.findAll(pageable).toList();
		return compilations
				.stream()
				.map(compilation -> {
					List<EventShortDto> eventShortDtos = compilation.getEvents()
							.stream()
							.map(event -> {
								Long confirmedRequests = requestRepository.countConfirmedRequests(event.getId());
								Long views = eventClient.getEventViews(event.getCreatedOn(), event.getId());
								return EventMapper.mapToShortDto(event, confirmedRequests, views);
							})
							.collect(Collectors.toList());
					return CompilationMapper.mapToCompilationDto(compilation, eventShortDtos);
				})
				.collect(Collectors.toList());
	}

	@Override
	public CompilationDto getCompilation(Long compId) {
		Compilation compilation = compilationRepository.findById(compId).orElseThrow(()
				-> new ObjectNotFoundException("Compilation not found"));
		List<EventShortDto> eventShortDtos = compilation.getEvents()
				.stream()
				.map(event -> {
					Long confirmedRequests = requestRepository
							.countConfirmedRequests(event.getId());
					Long views = eventClient.getEventViews(event.getCreatedOn(), event.getId());
					return EventMapper.mapToShortDto(event, confirmedRequests, views);
				})
				.collect(Collectors.toList());
		return CompilationMapper.mapToCompilationDto(compilation, eventShortDtos);
	}

	@Override
	public CompilationDto createCompilation(NewCompilationDto newCompilation) {
		List<Event> events = eventRepository.findEventsByIds(newCompilation.getEvents());
		List<EventShortDto> eventShortDtos = events
				.stream()
				.map(event -> {
					Long confirmedRequests = requestRepository
							.countConfirmedRequests(event.getId());
					Long views = eventClient.getEventViews(event.getCreatedOn(), event.getId());
					return EventMapper.mapToShortDto(event, confirmedRequests, views);
				})
				.collect(Collectors.toList());
		Compilation compilation = CompilationMapper.mapToCompilation(newCompilation, events);
		return CompilationMapper.mapToCompilationDto(compilationRepository.save(compilation), eventShortDtos);
	}

	@Override
	public void deleteCompilation(Long compId) {
		compilationRepository.deleteById(compId);
	}

	@Override
	public void deleteCompilationEvent(Long compId, Long eventId) {
		Compilation compilation = compilationRepository.findById(compId).orElseThrow(()
				-> new ObjectNotFoundException("Compilation not found"));
		Event event = eventRepository.findById(eventId).orElseThrow(()
				-> new ObjectNotFoundException("Event not found"));
		compilation.getEvents().remove(event);
		compilationRepository.save(compilation);
	}

	@Override
	public void addCompilationEvent(Long compId, Long eventId) {
		Compilation compilation = compilationRepository.findById(compId).orElseThrow(()
				-> new ObjectNotFoundException("Compilation not found"));
		Event event = eventRepository.findById(eventId).orElseThrow(()
				-> new ObjectNotFoundException("Event not found"));
		compilation.getEvents().add(event);
		compilationRepository.save(compilation);
	}

	@Override
	public void unpinCompilation(Long compId) {
		Compilation compilation = compilationRepository.findById(compId).orElseThrow(()
				-> new ObjectNotFoundException("Compilation not found"));
		compilation.setPinned(false);
		compilationRepository.save(compilation);
	}

	@Override
	public void pinCompilation(Long compId) {
		Compilation compilation = compilationRepository.findById(compId).orElseThrow(()
				-> new ObjectNotFoundException("Compilation not found"));
		compilation.setPinned(true);
		compilationRepository.save(compilation);
	}
}

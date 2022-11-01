package com.example.server.compilations;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/compilations")
public class CompilationController {

	private final CompilationService compilationService;

	@GetMapping
	public List<CompilationDto> getCompilations(@RequestParam(value = "from", defaultValue = "0") Integer from,
												@RequestParam(value = "size", defaultValue = "10") Integer size) {
		return compilationService.getCompilations(from, size);
	}

	@GetMapping("/{compId}")
	public CompilationDto getCompilation(@PathVariable("compId") Long compId) {
		return compilationService.getCompilation(compId);
	}
}

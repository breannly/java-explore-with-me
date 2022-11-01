package com.example.server.complitation;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/compilations")
public class CompilationController {

    @GetMapping
    public Compilation getCompilations() {
        return null;
    }

    @GetMapping("/{compId}")
    public Compilation getCompilation(@PathVariable("compId") Long compId) {
        return null;
    }
}

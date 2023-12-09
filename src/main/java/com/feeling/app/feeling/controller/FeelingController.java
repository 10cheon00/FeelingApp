package com.feeling.app.feeling.controller;

import com.feeling.app.feeling.entity.Feeling;
import com.feeling.app.feeling.service.FeelingService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("${api.url}/feelings")
public class FeelingController {
    private final FeelingService feelingService;

    @Autowired
    public FeelingController(FeelingService feelingService) {
        this.feelingService = feelingService;
    }

    @GetMapping("")
    public ResponseEntity<List<Feeling>> get(
            HttpServletRequest request,
            @RequestParam(value = "year", required = false) Integer year,
            @RequestParam(value = "month", required = false) Integer month,
            @RequestParam(value = "day", required = false) Integer day) {
        List<Feeling> feelingList = feelingService.findAll(
                Optional.ofNullable(year),
                Optional.ofNullable(month),
                Optional.ofNullable(day));

        return ResponseEntity.ok().body(feelingList);
    }

    @PostMapping("")
    public ResponseEntity<Feeling> create(
            HttpServletRequest request,
            @RequestBody Feeling feeling)
            throws Exception {
        Feeling result = feelingService.save(feeling);

        return ResponseEntity
                .created(URI.create(request.getRequestURI()))
                .contentType(MediaType.APPLICATION_JSON)
                .body(result);
    }
}

package org.example.controller;

import org.example.dto.LordDto;
import org.example.entity.Lord;
import org.example.service.LordService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;


@RestController
@RequestMapping(value = "/api/lords")
public class LordController {
    private final LordService lordService;

    public LordController(LordService lordService) {
        this.lordService = lordService;
    }

    @PostMapping("/add")
    public ResponseEntity<String> addLord(@Valid @RequestBody LordDto lordDto) {
        if(lordService.addLord(lordDto) != null){
            return ResponseEntity.ok(lordDto.getName() + " was added");
        }
        return ResponseEntity.badRequest().body("Lord with name=" + lordDto.getName() + " is already exists");
    }

    @GetMapping("/free")
    public ResponseEntity<List<LordDto>> getFreeLords() {
        return ResponseEntity.ok(lordService.getFreeLords());
    }

    @GetMapping("/youngest")
    public ResponseEntity<List<LordDto>> getYoungestLords() {
        return ResponseEntity.ok(lordService.getYoungestLords());
    }
}

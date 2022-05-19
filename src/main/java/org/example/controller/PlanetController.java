package org.example.controller;

import org.example.dto.AssignmentDto;
import org.example.dto.PlanetDto;
import org.example.service.PlanetService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(value = "/api/planets")
public class PlanetController {
    private final PlanetService planetService;

    public PlanetController(PlanetService planetService) {
        this.planetService = planetService;
    }

    @PostMapping("/add")
    public ResponseEntity<String> addPlanet(@Valid @RequestBody PlanetDto planetDto) {
        if (planetService.addPlanet(planetDto) != null){
            return ResponseEntity.ok(planetDto.getName() + " was added");
        }
        return ResponseEntity.badRequest().body("Planet with name=" + planetDto.getName() + " is already exist");
    }

    @PostMapping("/assignment")
    public ResponseEntity<String> assignLordToPlanet(@RequestBody AssignmentDto assignmentDto) {
        if (planetService.assignLordToPlanet(assignmentDto) != null) {
            return ResponseEntity.ok("Planet with id=" + assignmentDto.getPlanetId() + " was assigned to lord with id=" + assignmentDto.getLordId());
        }
        return ResponseEntity.badRequest().body("Check if planet with id=" + assignmentDto.getPlanetId() + " and lord with id=" + assignmentDto.getLordId() + " are exist");
    }

    @DeleteMapping("/delete")
    public ResponseEntity<String> deletePlanet(@RequestParam(value = "id") Long id) {
        if (planetService.deletePlanet(id) != null) {
            return ResponseEntity.ok("Deleted planet with id=" + id);
        }
        return ResponseEntity.badRequest().body("There is no planet with id=" + id);
    }
}

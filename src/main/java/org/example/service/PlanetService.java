package org.example.service;

import org.example.dto.AssignmentDto;
import org.example.dto.PlanetDto;
import org.example.entity.Planet;

public interface PlanetService {
    Planet addPlanet(PlanetDto planetDto);

    Planet assignLordToPlanet(AssignmentDto assignmentDto);

    Long deletePlanet(Long id);

}

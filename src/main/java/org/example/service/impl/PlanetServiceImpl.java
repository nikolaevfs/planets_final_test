package org.example.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.example.dto.AssignmentDto;
import org.example.dto.PlanetDto;
import org.example.entity.Lord;
import org.example.entity.Planet;
import org.example.repository.LordRepository;
import org.example.repository.PlanetRepository;
import org.example.service.PlanetService;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class PlanetServiceImpl implements PlanetService {
    private final PlanetRepository planetRepository;
    private final LordRepository lordRepository;

    public PlanetServiceImpl(PlanetRepository planetRepository, LordRepository lordRepository) {
        this.planetRepository = planetRepository;
        this.lordRepository = lordRepository;
    }

    @Override
    public Planet addPlanet(PlanetDto planetDto) {
        log.info("Adding new planet");
        if(planetRepository.getPlanetByName(planetDto.getName()) != null){
            log.warn("Planet is already exists");
            return null;
        }
        return planetRepository.save(Planet.dtoToPlanet(planetDto));
    }

    @Override
    public Planet assignLordToPlanet(AssignmentDto assignmentDto) {
        log.info("Assigning planet to lord");
        Planet candidate = planetRepository.getPlanetById(assignmentDto.getPlanetId());
        try{
            Lord lord = lordRepository.getLordById(assignmentDto.getLordId());
            if(lord == null){
                throw new Exception();
            }
            candidate.setLord(lord);
        }catch (Exception e){
            log.warn("Wrong input on assigning");
            return null;
        }
        return planetRepository.save(candidate);
    }

    @Override
    public Long deletePlanet(Long id) {
        log.info("Deleting planet");
        try{
            planetRepository.delete(planetRepository.getPlanetById(id));
        }catch (Exception e){
            log.warn("Wrong input on deleting planet");
            return null;
        }
        return id;
    }
}

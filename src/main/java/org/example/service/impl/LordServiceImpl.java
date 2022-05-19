package org.example.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.example.dto.LordDto;
import org.example.entity.Lord;
import org.example.repository.LordRepository;
import org.example.service.LordService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class LordServiceImpl implements LordService {
    private final LordRepository lordRepository;

    public LordServiceImpl(LordRepository lordRepository) {
        this.lordRepository = lordRepository;
    }

    @Override
    public Lord addLord(LordDto lordDTO) {
        log.info("Adding new lord");
        if(lordRepository.getLordByName(lordDTO.getName()) != null){
            log.warn("Lord is already exists");
            return null;
        }
        return lordRepository.save(Lord.dtoToLord(lordDTO));
    }

    @Override
    public List<LordDto> getFreeLords() {
        log.info("Getting free lords");
        return lordRepository.findAll().stream()
                .filter(lord -> lord.getPlanets().size() == 0)
                .map(Lord::lordToDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<LordDto> getYoungestLords() {
        log.info("Getting youngest lords");
        return lordRepository.getYoungest().stream()
                .map(Lord::lordToDto)
                .collect(Collectors.toList());
    }
}

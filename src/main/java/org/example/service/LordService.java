package org.example.service;

import org.example.dto.LordDto;
import org.example.entity.Lord;

import java.util.List;

public interface LordService {
    Lord addLord(LordDto lordDTO);

    List<LordDto> getFreeLords();

    List<LordDto> getYoungestLords();
}

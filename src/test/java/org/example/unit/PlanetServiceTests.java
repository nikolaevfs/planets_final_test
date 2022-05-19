package org.example.unit;

import org.example.dto.AssignmentDto;
import org.example.entity.Lord;
import org.example.entity.Planet;
import org.example.repository.LordRepository;
import org.example.repository.PlanetRepository;
import org.example.service.PlanetService;
import org.example.service.impl.PlanetServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class PlanetServiceTests {
    private MockMvc mockMvc;

    @Mock
    private PlanetRepository planetRepository;

    @Mock
    private LordRepository lordRepository;


    PlanetService planetService;


    @BeforeEach
    public void setUp(){
        planetService = new PlanetServiceImpl(planetRepository, lordRepository);
        mockMvc = MockMvcBuilders
                .standaloneSetup(planetService)
                .build();
    }

    @Test
    public void assignPlanetToNotExistingLord(){
        //given
        AssignmentDto assignmentDto = new AssignmentDto(100L, 1L);
        Planet planet = new Planet(1L, "Earth", null);

        //when
        Mockito.when(planetRepository.getPlanetById(assignmentDto.getPlanetId())).thenReturn(planet);
        Mockito.when(lordRepository.getLordById(assignmentDto.getLordId())).thenReturn(null);

        //then
        Assertions.assertNull(planetService.assignLordToPlanet(assignmentDto));

        verify(planetRepository, never()).save(any());
    }

    @Test
    public void assignNotExistingPlanet(){
        //given
        AssignmentDto assignmentDto = new AssignmentDto(1L, 100L);
        Lord lord = new Lord(1L, "Lord", 30, null);


        //when
        Mockito.when(planetRepository.getPlanetById(assignmentDto.getPlanetId())).thenReturn(null);
        Mockito.when(lordRepository.getLordById(assignmentDto.getLordId())).thenReturn(lord);


        //then
        Assertions.assertNull(planetService.assignLordToPlanet(assignmentDto));

        verify(planetRepository, never()).save(any());
    }

    @Test
    public void assignPlanetExistingLord(){
        //given
        AssignmentDto assignmentDto = new AssignmentDto(1L, 1L);
        Planet planet = new Planet(1L, "Earth", null);
        Lord lord = new Lord(1L, "Lord", 30, null);

        //when
        Mockito.when(planetRepository.getPlanetById(assignmentDto.getPlanetId())).thenReturn(planet);
        Mockito.when(lordRepository.getLordById(assignmentDto.getLordId())).thenReturn(lord);
        planet.setLord(lord);
        Mockito.when(planetRepository.save(any())).thenReturn(planet);

        //then
        Assertions.assertEquals(planetService.assignLordToPlanet(assignmentDto), planet);

        verify(planetRepository).save(any());
    }
}

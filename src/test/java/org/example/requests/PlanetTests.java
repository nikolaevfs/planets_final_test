package org.example.requests;


import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.dto.AssignmentDto;
import org.example.dto.PlanetDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class PlanetTests {

    @Autowired
    MockMvc mockMvc;

    @Test
    public void addingPlanet() throws Exception {

        ObjectMapper mapper = new ObjectMapper();

        String jsonPlanetDto = mapper.writeValueAsString(new PlanetDto("NewPlanet"));

        this.mockMvc.perform(post("/api/planets/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonPlanetDto))
                .andExpect(status().isOk());
    }

    @Test
    public void addingExistingPlanet() throws Exception {

        ObjectMapper mapper = new ObjectMapper();

        String jsonPlanetDto = mapper.writeValueAsString(new PlanetDto("Earth"));

        this.mockMvc.perform(post("/api/planets/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonPlanetDto))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void assigningPlanet() throws Exception {

        ObjectMapper mapper = new ObjectMapper();

        String jsonAssignmentDto = mapper.writeValueAsString(new AssignmentDto(1L, 1L));

        this.mockMvc.perform(post("/api/planets/assignment")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonAssignmentDto))
                .andExpect(status().isOk());
    }

    @Test
    public void assigningPlanetLordDoesntExist() throws Exception {

        ObjectMapper mapper = new ObjectMapper();

        String jsonAssignmentDto = mapper.writeValueAsString(new AssignmentDto(20L, 1L));

        this.mockMvc.perform(post("/api/planets/assignment")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonAssignmentDto))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void assigningNotExistingPlanet() throws Exception {

        ObjectMapper mapper = new ObjectMapper();

        String jsonAssignmentDto = mapper.writeValueAsString(new AssignmentDto(1L, 20L));

        this.mockMvc.perform(post("/api/planets/assignment")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonAssignmentDto))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void deleteExistingPlanet() throws Exception {
        this.mockMvc.perform(delete("/api/planets/delete?id=1"))
                .andExpect(status().isOk());
    }

    @Test
    public void deleteNotExistingPlanet() throws Exception {
        this.mockMvc.perform(delete("/api/planets/delete?id=20"))
                .andExpect(status().isBadRequest());
    }
}

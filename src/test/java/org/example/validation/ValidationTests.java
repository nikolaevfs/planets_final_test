package org.example.validation;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.dto.LordDto;
import org.example.dto.PlanetDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class ValidationTests {
    @Autowired
    MockMvc mockMvc;

    @Test
    public void oneErrorWithNameAddingLord() throws Exception {

        ObjectMapper mapper = new ObjectMapper();

        String jsonLordDto = mapper.writeValueAsString(new LordDto("", 20));

        this.mockMvc.perform(post("/api/lords/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonLordDto))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errors", hasSize(1)));
    }

    @Test
    public void oneErrorWithAgeAddingLordLess() throws Exception {

        ObjectMapper mapper = new ObjectMapper();

        String jsonLordDto = mapper.writeValueAsString(new LordDto("CorrectName", -1));

        this.mockMvc.perform(post("/api/lords/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonLordDto))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errors", hasSize(1)));
    }

    @Test
    public void oneErrorWithAgeAddingLordMore() throws Exception {

        ObjectMapper mapper = new ObjectMapper();

        String jsonLordDto = mapper.writeValueAsString(new LordDto("CorrectName", 200));

        this.mockMvc.perform(post("/api/lords/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonLordDto))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errors", hasSize(1)));
    }

    @Test
    public void bothErrorAddingLord() throws Exception {

        ObjectMapper mapper = new ObjectMapper();

        String jsonLordDto = mapper.writeValueAsString(new LordDto(" ", 200));

        this.mockMvc.perform(post("/api/lords/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonLordDto))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errors", hasSize(2)));
    }

    @Test
    public void errorAddingPlanet() throws Exception {

        ObjectMapper mapper = new ObjectMapper();

        String jsonPlanetDto = mapper.writeValueAsString(new PlanetDto(" "));

        this.mockMvc.perform(post("/api/planets/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonPlanetDto))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errors", hasSize(1)));
    }
}

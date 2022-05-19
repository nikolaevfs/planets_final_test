package org.example.requests;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.dto.LordDto;
import org.example.dto.PlanetDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class LordTests {
    @Autowired
    MockMvc mockMvc;

    @Test
    public void addingLord() throws Exception {

        ObjectMapper mapper = new ObjectMapper();

        String jsonLordDto = mapper.writeValueAsString(new LordDto("LordName", 30));

        this.mockMvc.perform(post("/api/lords/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonLordDto))
                .andExpect(status().isOk());
    }

    @Test
    public void addingExistingLord() throws Exception {

        ObjectMapper mapper = new ObjectMapper();

        String jsonLordDto = mapper.writeValueAsString(new LordDto("Fedor", 30));

        this.mockMvc.perform(post("/api/lords/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonLordDto))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void freeLords() throws Exception {
        this.mockMvc.perform(get("/api/lords/free"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Ksenia"))
                .andExpect(jsonPath("$[0].age").value(28))
                .andExpect(jsonPath("$[1].name").value("David"))
                .andExpect(jsonPath("$[1].age").value(20))
                .andExpect(jsonPath("$[2].name").value("Alex"))
                .andExpect(jsonPath("$[2].age").value(32))
                .andExpect(jsonPath("$[3].name").value("Rita"))
                .andExpect(jsonPath("$[3].age").value(27))
                .andExpect(jsonPath("$[4].name").value("Lika"))
                .andExpect(jsonPath("$[4].age").value(35));
    }

    @Test
    public void youngestLords() throws Exception {
        this.mockMvc.perform(get("/api/lords/youngest"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.*", hasSize(10)))
                .andExpect(jsonPath("$[0].name").value("Sergey"))
                .andExpect(jsonPath("$[0].age").value(15))
                .andExpect(jsonPath("$[9].name").value("Oleg"))
                .andExpect(jsonPath("$[9].age").value(40));
    }
}

package running.tracker.integration.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import running.tracker.dto.FinishRunRequest;
import running.tracker.dto.StartRunRequest;
import running.tracker.model.Run;
import running.tracker.repository.RunRepository;
import running.tracker.helper.FinishRunRequestBuilder;
import running.tracker.helper.StartRunRequestBuilder;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class RunControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private RunRepository runRepository;

    @Test
    void startRun_ShouldReturnCreatedRunResponse() throws Exception {
        // Given
        StartRunRequest request = new StartRunRequestBuilder()
                .setUserId("user123")
                .setStartLatitude(37.7749)
                .setStartLongitude(-122.4194)
                .setStartDateTime(LocalDateTime.now())
                .build();

        // When
        MvcResult result = mockMvc.perform(post("/api/v1/runs/start")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").isNotEmpty())
                .andExpect(jsonPath("$.userId").value(request.getUserId()))
                .andExpect(jsonPath("$.startDateTime").isNotEmpty())
                .andReturn();

        String jsonResponse = result.getResponse().getContentAsString();
        JsonNode jsonNode = objectMapper.readTree(jsonResponse);
        String runId = jsonNode.get("id").asText();

        // Then
        Optional<Run> savedRun = runRepository.findById(runId);
        assertTrue(savedRun.isPresent());
        assertEquals(request.getUserId(), savedRun.get().getUserId());
        assertEquals(request.getStartLatitude(), savedRun.get().getStartLatitude());
        assertEquals(request.getStartLongitude(), savedRun.get().getStartLongitude());
    }

    @Test
    void finishRun_ShouldReturnFinishedRunResponse() throws Exception {
        // Given
        FinishRunRequest request = new FinishRunRequestBuilder()
                .setUserId("user123")
                .setFinishLatitude(37.7749)
                .setFinishLongitude(-122.4194)
                .setFinishDateTime(LocalDateTime.now())
                .setDistance(5.0)
                .build();

        // When
        MvcResult result = mockMvc.perform(post("/api/v1/runs/finish")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").isNotEmpty())
                .andExpect(jsonPath("$.userId").value(request.getUserId()))
                .andExpect(jsonPath("$.finishDateTime").isNotEmpty())
                .andExpect(jsonPath("$.distance").value(request.getDistance()))
                .andReturn();

        String jsonResponse = result.getResponse().getContentAsString();
        JsonNode jsonNode = objectMapper.readTree(jsonResponse);
        String runId = jsonNode.get("id").asText();

        // Then
        Optional<Run> savedRun = runRepository.findById(runId);
        assertTrue(savedRun.isPresent());
        assertEquals(request.getUserId(), savedRun.get().getUserId());
        assertEquals(request.getFinishLatitude(), savedRun.get().getFinishLatitude());
        assertEquals(request.getFinishLongitude(), savedRun.get().getFinishLongitude());
        assertEquals(request.getDistance(), savedRun.get().getDistance());
    }

    @Test
    void getAllRunsForUser_ShouldReturnListOfRuns() throws Exception {
        // Given
        String userId = "user123";
        LocalDateTime fromDateTime = LocalDateTime.now().minusDays(7);
        LocalDateTime toDateTime = LocalDateTime.now();

        // When
        MvcResult result = mockMvc.perform(get("/api/v1/runs/user/{userId}", userId)
                        .param("fromDateTime", fromDateTime.toString())
                        .param("toDateTime", toDateTime.toString())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].userId").value(userId))
                .andReturn();

        String jsonResponse = result.getResponse().getContentAsString();
        JsonNode jsonArray = objectMapper.readTree(jsonResponse);

        // Then
        assertTrue(jsonArray.isArray());
        assertFalse(jsonArray.isEmpty());
        assertEquals(userId, jsonArray.get(0).get("userId").asText());
    }

    @Test
    void getUserStatistics_ShouldReturnUserStatistics() throws Exception {
        // Given
        String userId = "user123";
        LocalDateTime fromDatetime = LocalDateTime.now().minusDays(30);
        LocalDateTime toDatetime = LocalDateTime.now();

        // When
        MvcResult result = mockMvc.perform(get("/api/v1/runs/user/{userId}/statistics", userId)
                        .param("fromDatetime", fromDatetime.toString())
                        .param("toDatetime", toDatetime.toString())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.userId").value(userId))
                .andExpect(jsonPath("$.totalDistance").isNumber())
                .andExpect(jsonPath("$.averageSpeed").isNumber())
                .andReturn();

        String jsonResponse = result.getResponse().getContentAsString();
        JsonNode jsonNode = objectMapper.readTree(jsonResponse);

        // Then
        assertEquals(userId, jsonNode.get("userId").asText());
        assertTrue(jsonNode.get("totalDistance").isNumber());
        assertTrue(jsonNode.get("averageSpeed").isNumber());
    }
}
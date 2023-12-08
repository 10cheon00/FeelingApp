package com.feeling.app.feeling;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.feeling.app.feeling.entity.Feeling;
import com.feeling.app.util.TimestampUtil;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@SpringBootTest
@TestPropertySource(locations = "classpath:application.properties")
@AutoConfigureMockMvc
@Transactional
public class FeelingTest {
    @Autowired
    private MockMvc mockMvc;

    private final String feelingURI = "/api/v1/feelings";

    private final ObjectMapper objectMapper = new ObjectMapper();

    private final TypeReference<List<Feeling>> FeelingListType = new TypeReference<List<Feeling>>() {};

    @Test
    public void 감정데이터_생성() throws Exception {
        Feeling feeling = new Feeling(TimestampUtil.createTimestamp("2010-01-01"));
        MvcResult result = requestCreateFeeling(feeling);

        Feeling createdFeeling = objectMapper.readValue(result.getResponse().getContentAsString(), Feeling.class);
        Timestamp createdDate = createdFeeling.getCreatedDate();

        assertThat(createdDate.getTime()).isEqualTo(feeling.getCreatedDate().getTime());
    }

    @Test
    public void 같은날_중복된_감정데이터_생성_실패() throws Exception {
        Feeling feeling = new Feeling(TimestampUtil.createTimestamp("2010-01-01"));
        requestCreateFeeling(feeling);

        // create duplicated feeling on same date.
        mockMvc.perform(post(feelingURI)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(feeling)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void 감정데이터_조회() throws Exception {
        MvcResult result = mockMvc.perform(get(feelingURI))
                .andExpect(status().isOk())
                .andReturn();

        List<Feeling> createdFeelingList = objectMapper.readValue(
                result.getResponse().getContentAsString(), FeelingListType);

        assertThat(createdFeelingList.size()).isEqualTo(0);

        List<Feeling> feelingList = new ArrayList<>();
        feelingList.add(new Feeling(TimestampUtil.createTimestamp("2010-01-01")));
        feelingList.add(new Feeling(TimestampUtil.createTimestamp("2010-01-02")));
        for(Feeling feeling : feelingList) {
            requestCreateFeeling(feeling);
        }

        result = mockMvc.perform(get(feelingURI))
                .andExpect(status().isOk())
                .andReturn();

        createdFeelingList = objectMapper.readValue(
                result.getResponse().getContentAsString(), FeelingListType);

        assertThat(createdFeelingList.size()).isEqualTo(feelingList.size());

    }

    @Test
    public void 감정데이터_연도별_조회() {

    }

    @Test
    public void 감정데이터_월별_조회() {

    }

    @Test
    public void 감정데이터_일별_조회() {

    }

    @Test
    public void 감정데이터_업데이트() {

    }

    @Test
    public void 감정데이터_삭제() {

    }

    private MvcResult requestCreateFeeling(Feeling feeling) throws Exception {
        return mockMvc.perform(post(feelingURI)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(feeling)))
                .andExpect(status().isCreated())
                .andReturn();
    }
}

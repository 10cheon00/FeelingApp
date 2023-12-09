package com.feeling.app.feeling;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.feeling.app.feeling.entity.Feeling;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDate;
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
    private final MockMvc mockMvc;

    private final String feelingURI = "/api/v1/feelings";

    private final ObjectMapper objectMapper;
    private final TypeReference<List<Feeling>> FeelingListType = new TypeReference<List<Feeling>>() {
    };
    private final List<Feeling> feelingList;

    @Autowired
    public FeelingTest(MockMvc mockMvc) {
        this.mockMvc = mockMvc;

        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

        feelingList = new ArrayList<>();
        Integer[][] dateFormat = new Integer[][]{
                {2010, 1, 1},
                {2010, 2, 1},
                {2010, 2, 2},
                {2011, 1, 1},
                {2011, 1, 2},
        };
        for (Integer[] format : dateFormat) {
            feelingList.add(new Feeling(
                    LocalDate.of(format[0], format[1], format[2])));
        }
    }

    @Test
    public void 감정데이터_생성() throws Exception {
        Feeling feeling = new Feeling(LocalDate.of(2010, 1, 1));
        MvcResult result = requestCreateFeeling(feeling);

        Feeling createdFeeling = objectMapper.readValue(result.getResponse().getContentAsString(), Feeling.class);
        LocalDate createdDate = createdFeeling.getCreatedDate();

        assertThat(createdDate).isEqualTo(feeling.getCreatedDate());
    }

    @Test
    public void 같은날_중복된_감정데이터_생성_실패() throws Exception {
        Feeling feeling = new Feeling(LocalDate.of(2010, 1, 1));
        requestCreateFeeling(feeling);

        // request to create same date feeling data.
        mockMvc.perform(post(feelingURI)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(feeling)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void 감정데이터_조회() throws Exception {
        // return 0 item
        mockMvc.perform(get(feelingURI))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.size()").value(0));

        // create 5 items
        requestCreateFeelingList();

        // return 2 items
        mockMvc.perform(get(feelingURI))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.size()").value(5));
    }

    @Test
    public void 감정데이터_연도별_조회() throws Exception {
        // create 5 items
        requestCreateFeelingList();

        // get 3 items of year 2010.
        mockMvc.perform(get(feelingURI).param("year", "2010"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.size()").value(3));

        // get 2 items of year 2011.
        mockMvc.perform(get(feelingURI).param("year", "2011"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.size()").value(2));
    }


    @Test
    public void 감정데이터_월별_조회() throws Exception {
        // create 5 items
        requestCreateFeelingList();

        // get 3 items of month 1.
        mockMvc.perform(get(feelingURI).param("month", "1"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.size()").value(3));

        // get 2 items of month 2.
        mockMvc.perform(get(feelingURI).param("month", "2"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.size()").value(2));
    }

    @Test
    public void 감정데이터_일별_조회() throws Exception {
        // create 5 items
        requestCreateFeelingList();

        // get 3 items of day 1.
        mockMvc.perform(get(feelingURI).param("day", "1"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.size()").value(3));

        // get 2 items of day 2.
        mockMvc.perform(get(feelingURI).param("day", "2"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.size()").value(2));
    }

    @Test
    public void 감정데이터_특정년월일_조회() throws Exception {
        // create 5 items
        requestCreateFeelingList();

        // get 1 item of 2011-01-01.
        mockMvc.perform(get(feelingURI)
                        .param("year", "2010")
                        .param("month", "1")
                        .param("day", "1"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.size()").value(1));

    }

    @Test
    public void 감정데이터_업데이트() {

    }

    @Test
    public void 감정데이터_삭제() {

    }

    private void requestCreateFeelingList() throws Exception {
        for (Feeling feeling : feelingList) {
            requestCreateFeeling(feeling);
        }
    }


    private MvcResult requestCreateFeeling(Feeling feeling) throws Exception {
        return mockMvc.perform(post(feelingURI)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(feeling)))
                .andExpect(status().isCreated())
                .andReturn();
    }
}

package com.feeling.app.feeling;

import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@TestPropertySource(locations = "classpath:test.properties")
@AutoConfigureMockMvc
@Transactional
public class FeelingTest {
    @Autowired
    private MockMvc mockMvc;

    private String feelingURI = "api/v1/feelings";

    @Test
    public void 감정데이터_생성() {

    }

    @Test
    public void 같은날_중복된_감정데이터_생성_실패() {

    }

    @Test
    public void 감정데이터_조회() {

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
}

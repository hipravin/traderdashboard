package com.hipravin.traderdashboard.api;

import com.hipravin.traderdashboard.api.dto.TradeAggregationDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
class TradeFrameControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void testSampleVtbr() {
        ResponseEntity<TradeAggregationDto> sampleVtbr =
                restTemplate.getForEntity("http://localhost:" + port + "/api/v1/aggregation-daily/VTBR/2021-05-18", TradeAggregationDto.class);

        assertEquals(HttpStatus.OK, sampleVtbr.getStatusCode());
        assertNotNull(sampleVtbr.getBody());
        assertEquals(21, sampleVtbr.getBody().getPriceGrid().getPriceGrid().size());
    }

    @Test
    void testBadDateFormat() {
        ResponseEntity<String> badDate =
                restTemplate.getForEntity("http://localhost:" + port + "/api/v1/aggregation-daily/GAZP/invalid-date", String.class);

        assertEquals(HttpStatus.BAD_REQUEST, badDate.getStatusCode());
        assertNotNull(badDate.getBody());
        assertTrue(badDate.getBody().contains("invalid-date"));
    }
}
package io.github.arch2be.ordertakingservice.framework.in.rest;

import io.github.arch2be.ordertakingservice.OrderTakingApplication;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.Base64;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = OrderTakingApplication.class)
@Testcontainers
@AutoConfigureMockMvc
@ExtendWith(SpringExtension.class)
class OrderControllerTest extends TestContainersConfig {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Test
    void processCorrectOrderRequestAndPushToQueue() throws Exception {
        final Long expectedAmountMsgOnTestQueue = 1L;

        mockMvc.perform(post("/api/v1/order")
                        .headers(getHttpHeaders("erp", "erp"))
                        .content(correctOrderRequestJson())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        final Long actualAmountMsgOnTestQueue = rabbitTemplate.execute(i -> i.messageCount("test-queue"));

        Assertions.assertEquals(expectedAmountMsgOnTestQueue, actualAmountMsgOnTestQueue);
    }

    @Test
    void shouldThrowUnAuthorizeCode() throws Exception {
        mockMvc.perform(post("/api/v1/order")
                        .headers(getHttpHeaders("wrongusername", "wrongpassword"))
                        .content(correctOrderRequestJson())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnauthorized());
    }

    private HttpHeaders getHttpHeaders(String username, String password) {
        HttpHeaders httpHeaders = new HttpHeaders();
        byte[] encodedBytes = Base64.getEncoder().encode((username + ":" + password).getBytes());

        String USER_PASS = new String(encodedBytes);
        httpHeaders.add("Content-Type", "application/json");
        httpHeaders.add("Authorization", "Basic " + USER_PASS);

        return httpHeaders;
    }

    private String correctOrderRequestJson() {
        return """
        {
            "clientName": "James",
            "clientSurname": "Dean",
            "installationAddress": "some address",
            "preferredInstallationDate": "2024-10-18T08:04:30Z",
            "timeSlotDetails": "1hr",
            "productToInstall": [
                {
                    "productType": "MOBILE",
                    "details": "some details to product"
                }
            ]
        }
        """;
    }
}
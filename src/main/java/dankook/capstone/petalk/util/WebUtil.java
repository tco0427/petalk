package dankook.capstone.petalk.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Slf4j
@Component
public class WebUtil {
    public Map<String, String> requestApi(String url, HttpMethod httpMethod,
                                          HttpEntity<MultiValueMap<String, String>> parameter) throws JsonProcessingException {
        RestTemplate restTemplate = new RestTemplate();
        ObjectMapper objectMapper = new ObjectMapper();

        // 전송
        ResponseEntity<String> response = restTemplate.exchange(
                url,
                httpMethod,
                parameter,
                String.class
        );

        // 리턴값 맵형식으로 변환
        Map<String, String> result = objectMapper.readValue(response.getBody(), Map.class);

        return result;
    }
}


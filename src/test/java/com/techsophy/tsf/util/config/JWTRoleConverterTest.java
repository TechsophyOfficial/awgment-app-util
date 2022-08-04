package com.techsophy.tsf.util.config;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.techsophy.tsf.util.utils.WebClientWrapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.web.reactive.function.client.WebClient;
import javax.servlet.http.HttpServletRequest;
import java.time.Instant;
import java.util.*;
import static com.techsophy.tsf.util.constants.PropertiesTestConstants.GET;
import static com.techsophy.tsf.util.constants.PropertiesTestConstants.TEST_ACTIVE_PROFILE;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@ActiveProfiles(TEST_ACTIVE_PROFILE)
@SpringBootTest
class JWTRoleConverterTest
{
    @Mock
    HttpServletRequest mockHttpServletRequest;

    @Mock
    ObjectMapper mockObjectMapper;

    @Mock
    WebClientWrapper webClientWrapper;

    @InjectMocks
    JWTRoleConverter jwtRoleConverter;

    @Test
    void convertTest() throws JsonProcessingException {

        Map<String, Object> map = new HashMap<>();
        map.put("clientRoles", "abc");
        List<String> list=new ArrayList<>();
        list.add("augment");
        list.add("augment");
        Jwt jwt= new Jwt("1", Instant.now(),null,Map.of("abc","abc"),Map.of("abc","abc"));
        WebClient webClient= WebClient.builder().build();
        when(webClientWrapper.webclientRequest(eq(jwt.getTokenValue()), any(),eq(GET),any()))
                .thenReturn("abc");
        when(mockObjectMapper.readValue("abc",Map.class)).thenReturn(map);
        when(mockObjectMapper.convertValue(any(),eq(List.class))).thenReturn(list);
        Collection grantedAuthority =  jwtRoleConverter.convert(jwt);
        Assertions.assertNotNull(grantedAuthority);
    }

}

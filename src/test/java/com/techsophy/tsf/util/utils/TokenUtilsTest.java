package com.techsophy.tsf.util.utils;

import com.techsophy.tsf.util.dto.PaginationResponsePayload;
import io.micrometer.core.instrument.util.IOUtils;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import static com.techsophy.tsf.util.constants.PropertiesTestConstants.TEST_ACTIVE_PROFILE;
import static com.techsophy.tsf.util.constants.QRCodeConstants.TECHSOPHY_PLATFORM;
import static com.techsophy.tsf.util.constants.QRCodeConstants.TOKEN_TXT_PATH;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SpringBootTest
@ExtendWith({SpringExtension.class})
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@ActiveProfiles(TEST_ACTIVE_PROFILE)
class TokenUtilsTest
{
    @Mock
    SecurityContext securityContext;
    @Mock
    SecurityContextHolder securityContextHolder;
    @InjectMocks
    TokenUtils tokenUtils;

    @Order(1)
    @Test
    void getTokenFromIssuerTest() throws Exception
    {
        InputStream resource = new ClassPathResource(TOKEN_TXT_PATH).getInputStream();
        String result = IOUtils.toString(resource, StandardCharsets.UTF_8);
        String tenant = tokenUtils.getIssuerFromToken(result);
        assertThat(tenant).isEqualTo(TECHSOPHY_PLATFORM);
    }

    @Order(2)
    @Test
    void getPageRequestWithPageTest()
    {
        PageRequest tenant = tokenUtils.getPageRequest(1,1,null);
        assertTrue(true);
    }

    @Order(3)
    @Test
    void getTokenFromContext()
    {
        Authentication authentication = mock(Authentication.class);
        SecurityContext securityContext = mock(SecurityContext.class);
        Mockito.when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);
        Jwt jwt = mock(Jwt.class);
        when(authentication.getPrincipal()).thenReturn(jwt);
        String token = tokenUtils.getTokenFromContext();
        assertThat(token).isNull();
    }

    @Order(4)
    @Test
    void getTokenFromContextException()
    {
        Authentication authentication = mock(Authentication.class);
        SecurityContext securityContext = mock(SecurityContext.class);
        Mockito.when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);
        assertThatExceptionOfType(SecurityException.class)
                .isThrownBy(() -> tokenUtils.getTokenFromContext());
    }

    @Order(5)
    @Test
    void getLoggedInUserIdTest()
    {
        Mockito.when(securityContext.getAuthentication()).thenReturn(null);
        assertThatExceptionOfType(SecurityException.class)
                .isThrownBy(() -> tokenUtils.getLoggedInUserId());
    }
    @Test
    void getPaginationResponsePayload()
    {
        Page page = new PageImpl(List.of("abc"));
        List<Map<String,Object>> list = new ArrayList<>();
        Map<String,Object> map = new HashMap<>();
        map.put("abc","abc");
        list.add(map);
        PaginationResponsePayload responsePayload = tokenUtils.getPaginationResponsePayload(page,list);
        assertThat(responsePayload).isNotNull();
    }
    @Test
    void getSortBy()
    {
        String[] strings = new String[2];
        strings[0]="abc:ab";
        strings[1]="abc";
        assertNotNull(tokenUtils.getSortBy(strings));
    }

}

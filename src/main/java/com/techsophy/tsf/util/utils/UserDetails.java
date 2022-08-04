package com.techsophy.tsf.util.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.techsophy.tsf.util.config.GlobalMessageSource;
import com.techsophy.tsf.util.exception.InvalidInputException;
import com.techsophy.tsf.util.exception.UserDetailsIdNotFoundException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import java.util.List;
import java.util.Map;
import static com.techsophy.tsf.util.constants.ErrorConstants.LOGGED_IN_USER_ID_NOT_FOUND_EXCEPTION;
import static com.techsophy.tsf.util.constants.PropertiesConstants.*;

@RefreshScope
@Slf4j
@Service
@AllArgsConstructor(onConstructor_ = {@Autowired})
public class UserDetails
{
    private final GlobalMessageSource globalMessageSource;
    private final TokenUtils tokenUtils;
    private final ObjectMapper objectMapper;
    private final WebClientWrapper webClientWrapper;
    @Value(GATEWAY_URI)
    String gatewayApi;

    public List<Map<String,Object>> getUserDetails() throws JsonProcessingException
    {
        Map<String,Object> response;
        List<Map<String, Object>> userDetailsResponse;
        String loggedInUserId = tokenUtils.getLoggedInUserId();
        if (StringUtils.isEmpty(loggedInUserId))
        {
            throw new InvalidInputException(LOGGED_IN_USER_ID_NOT_FOUND_EXCEPTION,globalMessageSource.get(LOGGED_IN_USER_ID_NOT_FOUND_EXCEPTION,loggedInUserId));
        }
        String token = tokenUtils.getTokenFromContext();
        log.info(LOGGED_USER + loggedInUserId);
        log.info(TOKEN + token);
        log.info(GATEWAY+gatewayApi);
        String userDetails = webClientWrapper.webclientRequest(token, gatewayApi + ACCOUNT_URL + FILTER_COLUMN+ loggedInUserId+MANDATORY_FIELDS,GET,null);
        if (StringUtils.isEmpty(userDetails)||userDetails.isEmpty())
        {
            throw new UserDetailsIdNotFoundException(LOGGED_IN_USER_ID_NOT_FOUND_EXCEPTION,globalMessageSource.get(LOGGED_IN_USER_ID_NOT_FOUND_EXCEPTION,userDetails));
        }
        response = this.objectMapper.readValue(userDetails, new TypeReference<>()
        {
        });
        log.info(RESPONSE+ response);
        List<Map<String, Object>> data = (List<Map<String, Object>>) response.get(DATA);
        log.info(DATA +data);
        if (!data.isEmpty())
        {
            userDetailsResponse = this.objectMapper.convertValue(response.get(DATA), List.class);
            return userDetailsResponse;
        }
        throw new UserDetailsIdNotFoundException(LOGGED_IN_USER_ID_NOT_FOUND_EXCEPTION,globalMessageSource.get(LOGGED_IN_USER_ID_NOT_FOUND_EXCEPTION,loggedInUserId));
    }
}



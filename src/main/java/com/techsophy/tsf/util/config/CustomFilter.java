package com.techsophy.tsf.util.config;

import com.techsophy.multitenancy.mongo.config.TenantContext;
import com.techsophy.tsf.util.utils.TokenUtils;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import static com.techsophy.tsf.util.constants.PropertiesConstants.AUTHORIZATION;
import static com.techsophy.tsf.util.constants.PropertiesConstants.MULTI_TENANCY_PACKAGE_NAME;
import static com.techsophy.tsf.util.constants.QRCodeConstants.PACKAGE_NAME;

@ComponentScan({PACKAGE_NAME, MULTI_TENANCY_PACKAGE_NAME})
@Configuration
@AllArgsConstructor(onConstructor_ = {@Autowired})
public class CustomFilter implements Filter
{
    private TokenUtils tokenUtils;

    @SneakyThrows
    @Override
    public void doFilter(ServletRequest request, ServletResponse response,FilterChain chain)
    {
        HttpServletRequest httpRequest=(HttpServletRequest) request;
        String tenant= tokenUtils.getIssuerFromToken(httpRequest.getHeader(AUTHORIZATION));
        if(StringUtils.isNotEmpty(tenant))
        {
            TenantContext.setTenantId(tenant);
        }
        chain.doFilter(request,response);
    }
}
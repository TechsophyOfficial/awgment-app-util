package com.techsophy.tsf.util.config;

import com.techsophy.idgenerator.IdGeneratorImpl;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.BufferedImageHttpMessageConverter;
import org.springframework.http.converter.HttpMessageConverter;
import java.awt.image.BufferedImage;
import java.util.List;
import static com.techsophy.tsf.util.constants.QRCodeConstants.*;

@Configuration
public class ApplicationConfig
{
    @Bean
    public IdGeneratorImpl idGeneratorImpl()
    {
        return new IdGeneratorImpl();
    }

    @Bean
    public HttpMessageConverter<BufferedImage> httpMessageConverter()
    {
        return new BufferedImageHttpMessageConverter();
    }

    @Value(GATEWAY_URL)
    String gatewayUrl;

    @Bean
    public OpenAPI customOpenAPI()
    {
        return new OpenAPI()
                .info(new io.swagger.v3.oas.models.info.Info().title(UTIL).version(VERSION_1).description(UTIL_MODELER_API_VERSION_1))
                .servers( List.of(new Server().url(gatewayUrl)));
    }
}

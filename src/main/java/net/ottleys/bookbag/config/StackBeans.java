package net.ottleys.bookbag.config;

import java.io.IOException;

import org.apache.tika.config.TikaConfig;
import org.apache.tika.exception.TikaException;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.ResourceUtils;
import org.xml.sax.SAXException;

/**
 * Configuration
 */
@Configuration
public class StackBeans {

    @Bean
    public TikaConfig tikaConfiguration() throws IOException, TikaException, SAXException
    {
        return new TikaConfig(ResourceUtils.getFile("classpath:tika-config.xml"));
    }

    
}
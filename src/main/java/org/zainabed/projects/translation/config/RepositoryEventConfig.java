package org.zainabed.projects.translation.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.zainabed.projects.translation.repository.event.TranslationRepositoryEvent;

@Configuration
public class RepositoryEventConfig {

    @Bean
    public TranslationRepositoryEvent translationRepositoryEvent(){
        return new TranslationRepositoryEvent();
    }
}

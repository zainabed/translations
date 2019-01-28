package org.zainabed.projects.translation.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.zainabed.projects.translation.repository.event.KeyRepositoryEventHandler;
import org.zainabed.projects.translation.repository.event.TranslationRepositoryEventHandler;

@Configuration
public class RepositoryEventConfig {

    @Bean
    public TranslationRepositoryEventHandler translationRepositoryEvent() {
        return new TranslationRepositoryEventHandler();
    }

    @Bean
    public KeyRepositoryEventHandler keyRepositoryEventHandler() {
        return new KeyRepositoryEventHandler();
    }
}

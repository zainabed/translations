package org.zainabed.projects.translation.repository.event;

import org.springframework.data.rest.core.annotation.RepositoryEventHandler;
import org.zainabed.projects.translation.model.Translation;

@RepositoryEventHandler(Translation.class)
public class TranslationRepositoryEventHandler extends AbstractModelEventHandler<Translation> {


}

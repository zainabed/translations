package org.zainabed.projects.translation.repository.event;

import org.springframework.data.rest.core.annotation.RepositoryEventHandler;
import org.zainabed.projects.translation.model.Key;

@RepositoryEventHandler(Key.class)
public class KeyRepositoryEventHandler extends AbstractModelEventHandler<Key> {


}

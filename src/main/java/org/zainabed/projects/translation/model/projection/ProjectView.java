package org.zainabed.projects.translation.model.projection;

import org.springframework.data.rest.core.config.Projection;
import org.zainabed.projects.translation.model.Project;


//@Projection(name="view", types= Project.class)
public interface ProjectView {
    String getName();
    String getDescription();
    String getImageUri();
    Long getExtended();
}

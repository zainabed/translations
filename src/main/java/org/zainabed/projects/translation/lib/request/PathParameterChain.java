package org.zainabed.projects.translation.lib.request;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

@Component
@Qualifier("PathParameterChain")
public class PathParameterChain extends AbstractRequestParameterChain {

    public PathParameterChain() {
        super(null);
    }

    @Override
    public String getParameter(HttpServletRequest request, String parameter) {
        parameterValue = getProjectId(request.getRequestURI(), parameter);
        try {
            Integer.parseInt(parameterValue);
        } catch (NumberFormatException e) {
            parameterValue = null;
        }
        return next(request, parameter);
    }
}

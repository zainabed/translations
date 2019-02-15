package org.zainabed.projects.translation.lib.request;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

/**
 * Implementation of {@link RequestParameterChain} abstract class to retrieve request
 * parameter value present inside {@link HttpServletRequest} request path.
 * <p>
 * It is qualified with 'PathParameterChain' to auto wired this bean at run time.
 * It is consider to last handler of request chain.
 *
 * @author Zainul Shaikh
 */
@Component
@Qualifier("PathParameterChain")
public class PathParameterChain extends AbstractRequestParameterChain {

    /**
     * On contrary to other implementation it does not maintain next request handler.
     * There chain request will stop at this handler.
     */
    public PathParameterChain() {
        super(null);
    }

    /**
     * Method returns request parameter value which would be present in
     * {@link HttpServletRequest} request path as {@link PathVariable}.
     *
     * @param request   {@link HttpServletRequest} request object
     * @param parameter Parameter name
     * @return
     */
    @Override
    public String getParameter(HttpServletRequest request, String parameter) {
        parameterValue = getPathParameter(request.getRequestURI(), parameter);
        try {
            Integer.parseInt(parameterValue);
        } catch (NumberFormatException e) {
            parameterValue = null;
        }
        return next(request, parameter);
    }
}

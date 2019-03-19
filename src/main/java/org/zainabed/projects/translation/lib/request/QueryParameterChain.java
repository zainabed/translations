package org.zainabed.projects.translation.lib.request;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.util.logging.Logger;

/**
 * Implementation of {@link RequestParameterChain} abstract class to retrieve request
 * parameter value present inside {@link HttpServletRequest} request query parameters.
 * <p>
 * It is qualified with 'QueryParameterChain' to auto wired this bean at run time.
 * It is consider to intermediary handler of request chain right after 'PostParameterChain' handler.
 *
 * @author Zainul Shaikh
 */
@Component
@Qualifier("QueryParameterChain")
public class QueryParameterChain extends AbstractRequestParameterChain {

    Logger logger = Logger.getLogger(QueryParameterChain.class.getName());

    /**
     * Constructor auto wired {@link RequestParameterChain} bean with qualified named 'PathParameterChain'
     * as next handler of chain.
     *
     * @param requestParameterChain {@link RequestParameterChain} implementation
     */
    @Autowired
    public QueryParameterChain(@Qualifier("PathParameterChain") RequestParameterChain requestParameterChain) {
        super(requestParameterChain);
    }

    /**
     * Method retrieve request parameter value present inside {@link HttpServletRequest} as
     * {@link RequestParam}.
     *
     * @param request   {@link HttpServletRequest} request object
     * @param parameter Parameter name
     * @return Returns value of given parameter otherwise returns null
     */
    @Override
    public String getParameter(HttpServletRequest request, String parameter) {
        parameterValue = request.getParameter(parameter);
        return next(request, parameter);
    }
}

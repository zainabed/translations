package org.zainabed.projects.translation.lib.request;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.logging.Logger;

@Component
@Qualifier("QueryParameterChain")
public class QueryParameterChain extends AbstractRequestParameterChain {

    Logger logger = Logger.getLogger(QueryParameterChain.class.getName());

    @Autowired
    public QueryParameterChain(@Qualifier("PathParameterChain") RequestParameterChain requestParameterChain) {
        super(requestParameterChain);
    }

    @Override
    public String getParameter(HttpServletRequest request, String parameter) {
        parameterValue = request.getParameter(parameter);
        logger.info("query parameter=" + parameterValue);
        parameterValue = filterPath(parameterValue, parameter);
        logger.info("query parameter end =" + parameterValue);
        return next(request, parameter);
    }
}

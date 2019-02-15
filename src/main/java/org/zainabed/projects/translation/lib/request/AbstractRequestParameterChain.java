package org.zainabed.projects.translation.lib.request;

import javax.servlet.http.HttpServletRequest;
import java.util.logging.Logger;

/**
 * This class provides a skeletal implementation of the {@link RequestParameterChain} interface
 * to minimize the effort required to implement this interface to retrieve parameter value.
 * <p>
 * This abstract class maintain next {@link RequestParameterChain} implementation in handler object.
 * This abstract class implements next method of {@link RequestParameterChain} to return request parameter
 * value or invoke next handler if value is null.
 *
 * @author Zainul Shaikh
 */
public abstract class AbstractRequestParameterChain implements RequestParameterChain {

    /**
     * {@link RequestParameterChain} object to maintain next handler of this chain.
     */
    protected RequestParameterChain handler = null;

    /**
     * Request parameter value
     */
    protected String parameterValue;

    Logger logger = Logger.getLogger(AbstractRequestParameterChain.class.getName());

    /**
     * Constructor is design to set next {@link RequestParameterChain} implementation.
     *
     * @param handler {@link RequestParameterChain} next chain handler
     */
    public AbstractRequestParameterChain(RequestParameterChain handler) {
        this.handler = handler;
    }


    /**
     * Method returns retrieve request parameter value. if value is empty or null then
     * it delegate responsibility to next handler of chain.
     * It is overridden method of {@link RequestParameterChain}, implementation of this clas
     * does not need to implement it unless it is necessary.
     *
     * @param request   {@link HttpServletRequest} request object
     * @param parameter Parameter name
     * @return Returns value of given parameter otherwise returns null
     */
    @Override
    public String next(HttpServletRequest request, String parameter) {
        if ((parameterValue == null || parameterValue.isEmpty()) && handler != null) {
            parameterValue = handler.getParameter(request, parameter);
        }
        return parameterValue;
    }


    /**
     * Method returns parameter value which is wrapped around request path.
     * It retrieve parameter value attached next to parameter name inside request path.
     *
     * @param path      Request path value
     * @param parameter Parameter name
     * @return Returns value of given parameter otherwise returns null
     */
    protected String getPathParameter(String path, String parameter) {
        String value = null;
        int projectIdIndex = path.indexOf(parameter + "/");

        if (projectIdIndex != -1) {
            path = path.replace(parameter + "/", "$");
            value = path.substring(path.indexOf("$") + 1);
            int nextParamIndex = value.indexOf("/");
            if (nextParamIndex != -1) {
                value = value.substring(0, nextParamIndex);
            }
        }
        return value;
    }
}

package org.zainabed.projects.translation.lib.request;

import javax.servlet.http.HttpServletRequest;

/**
 * Interface provide chain of responsibility design pattern to fetch given parameter's value from
 * given {@link HttpServletRequest} request.
 * Implementations could be divided into different set of classes according to various method
 * to read request parameter value.
 * <p>
 * Implementation should preserve {@link RequestParameterChain} implementation in a handler to
 * forward request for processing is it is not able to retrieve request parameter value.
 *
 * @author Zainul Shaikh
 */
public interface RequestParameterChain {

    /**
     * Method should process given request to fetch value of given parameter.
     * It should return value by invoking next method interface.
     *
     * @param request   {@link HttpServletRequest} request object
     * @param parameter Parameter name
     * @return Returns value of given parameter otherwise returns null
     */
    String getParameter(HttpServletRequest request, String parameter);

    /**
     * Method is design to serve chain of responsibility design pattern.
     * it should return parameter value if implementation is able to retrieve it
     * otherwise it should invoke next handler to process request.
     *
     * @param request   {@link HttpServletRequest} request object
     * @param parameter Parameter name
     * @return Returns value of given parameter otherwise returns null
     */
    String next(HttpServletRequest request, String parameter);
}

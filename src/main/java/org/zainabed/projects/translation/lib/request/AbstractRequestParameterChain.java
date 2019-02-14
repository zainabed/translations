package org.zainabed.projects.translation.lib.request;

import javax.servlet.http.HttpServletRequest;
import java.util.logging.Logger;

/**
 *
 */
public abstract class AbstractRequestParameterChain implements RequestParameterChain {

    protected RequestParameterChain handler = null;
    protected String parameterValue;

    Logger logger = Logger.getLogger(AbstractRequestParameterChain.class.getName());

    /**
     *
     * @param handler
     */
    public AbstractRequestParameterChain(RequestParameterChain handler) {
        this.handler = handler;
    }


    /**
     *
     * @param request
     * @param parameter
     * @return
     */
    @Override
    public String next(HttpServletRequest request, String parameter) {
        logger.info("Handler=" + handler);
        if ((parameterValue == null || parameterValue.isEmpty()) && handler != null) {
            parameterValue = handler.getParameter(request, parameter);
        }
        return parameterValue;
    }

    /**
     *
     * @param value
     * @param parameter
     * @return
     */
    protected String filterPath(String value, String parameter){
        if (value != null && value.indexOf("/") != -1) {
            return getProjectId(parameterValue, parameter);
        }
        return value;
    }

    /**
     *
     * @param path
     * @param parameter
     * @return
     */
    protected String getProjectId(String path, String parameter) {
        String value = null;
        int projectIdIndex = path.indexOf(parameter + "/");

        if (projectIdIndex != -1) {
            logger.info("Request URI = " + path);
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

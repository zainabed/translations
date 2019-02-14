package org.zainabed.projects.translation.lib.request;

import javax.servlet.http.HttpServletRequest;

/**
 *
 */
public interface RequestParameterChain {

    /**
     *
     * @param request
     * @param parameter
     * @return
     */
    String getParameter(HttpServletRequest request, String parameter);

    /**
     *
     * @param request
     * @param parameter
     * @return
     */
    String next(HttpServletRequest request, String parameter);
}

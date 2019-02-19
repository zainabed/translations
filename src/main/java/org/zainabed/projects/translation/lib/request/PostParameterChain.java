package org.zainabed.projects.translation.lib.request;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;
import java.util.stream.Collectors;

/**
 * Implementation of {@link RequestParameterChain} abstract class to retrieve request
 * parameter value present inside {@link HttpServletRequest} request payload body when
 * request method is either 'POST' or 'PUT'.
 * <p>
 * It is qualified with 'PostParameterChain' to auto wired this bean at run time.
 * It is consider to first handler of request chain.
 *
 * @author Zainul Shaikh
 */
@Component
@Qualifier("PostParameterChain")
public class PostParameterChain extends AbstractRequestParameterChain {
    Logger logger = Logger.getLogger(PostParameterChain.class.getName());

    /**
     * Constructor auto wired {@link RequestParameterChain} bean with qualified named 'QueryParameterChain'
     * as next handler of chain.
     *
     * @param requestParameterChain {@link RequestParameterChain} implementation
     */
    @Autowired
    public PostParameterChain(@Qualifier("QueryParameterChain") RequestParameterChain requestParameterChain) {
        super(requestParameterChain);
    }

    /**
     * Method returns request parameter value which would be present in {@link HttpServletRequest} request
     * payload body when request method is either 'POST' or 'PUT'.
     * <p>
     * It transform request payload string into JSON object and return parameter value from given parameter name
     * as key of generated request parameter {@link Map}.
     *
     * @param request   {@link HttpServletRequest} request object
     * @param parameter Parameter name
     * @return Returns value of given parameter otherwise returns null
     */
    @Override
    public String getParameter(HttpServletRequest request, String parameter) {
        parameterValue = null;
        String requestMethod = request.getMethod();
        try {
            if ("POST".equalsIgnoreCase(requestMethod) || "PUT".equalsIgnoreCase(requestMethod)) {
                String pathJson = request.getReader().lines().collect(Collectors.joining(System.lineSeparator()));
                Map<String, String> paramMap = new Gson().fromJson(
                        pathJson, new TypeToken<HashMap<String, String>>() {
                        }.getType()
                );

                if (paramMap != null) {
                    parameterValue = paramMap.get(parameter);
                }
                parameterValue = getPathParameter(parameterValue, parameter);

            }
        } catch (Exception e) {
            logger.warning(e.getMessage());
        }

        return next(request, parameter);
    }
}

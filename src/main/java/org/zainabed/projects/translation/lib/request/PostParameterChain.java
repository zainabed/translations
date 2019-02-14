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

@Component
@Qualifier("PostParameterChain")
public class PostParameterChain extends AbstractRequestParameterChain {
    Logger logger = Logger.getLogger(PostParameterChain.class.getName());

    @Autowired
    public PostParameterChain(@Qualifier("QueryParameterChain") RequestParameterChain requestParameterChain) {
        super(requestParameterChain);
    }

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
                logger.info("Post params = " + paramMap);
                logger.info("Post json = " + pathJson);
                logger.info("Post value = " + parameterValue);

                parameterValue = filterPath(parameterValue, parameter);

            }
        } catch (Exception e) {
            logger.warning(e.getMessage());
        }

        return next(request, parameter);
    }
}

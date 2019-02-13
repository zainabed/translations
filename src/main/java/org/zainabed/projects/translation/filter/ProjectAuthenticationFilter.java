package org.zainabed.projects.translation.filter;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.zainabed.spring.security.jwt.entity.UserDetail;
import com.zainabed.spring.security.jwt.entity.UserDetailImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Service;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerMapping;
import org.springframework.web.util.ContentCachingRequestWrapper;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@Service
public class ProjectAuthenticationFilter extends OncePerRequestFilter {

    Logger logger = Logger.getLogger(ProjectAuthenticationFilter.class.getName());
    public static final String PROJECT_PATH = "projects";

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        //HttpServletRequest requestToCache = new ContentCachingRequestWrapper((HttpServletRequest) request);
        request = new RequestWrapper((HttpServletRequest) request);
        String projectValue = getProjectId(request);

        try{
           Integer.parseInt(projectValue);
        } catch (NumberFormatException e) {
            projectValue = null;
        }
        if (projectValue == null || projectValue.isEmpty()) {
            filterChain.doFilter(request, response);
            return;
        }
        String projectId = projectValue;
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            logger.info("Authentication:" + authentication);
            if (authentication == null) {
                filterChain.doFilter(request, response);
                return;
            }
            logger.info("Authorities:" + authentication.getAuthorities());
            List<GrantedAuthority> authorities = authentication.getAuthorities().stream().filter(a -> {
                String grantedAuthority = ((GrantedAuthority) a).getAuthority();
                logger.info("Authority: " + grantedAuthority);
                return grantedAuthority.startsWith(projectId + "_");
            }).map(a -> {
                String grantedAuthority = ((GrantedAuthority) a).getAuthority();
                logger.info("Replace=" + grantedAuthority.replace(projectId + "_", ""));
                return new SimpleGrantedAuthority(grantedAuthority.replace(projectId + "_", ""));
            }).collect(Collectors.toList());

            authorities.stream().forEach(a -> {
                logger.info("Auhothrity=" + a.getAuthority());
            });
            logger.info("Principal=" + authentication.getPrincipal());
            UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(authentication.getPrincipal(), null, authorities);
            usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
        } catch (Exception e) {
            logger.warning(e.getMessage());
            logger.warning(e.getStackTrace().toString());
        }

        filterChain.doFilter(request, response);
    }

    protected String getProjectId(HttpServletRequest request) {
        String projectId = null;
        String requestMethod = request.getMethod();
        try {


            if ("POST".equalsIgnoreCase(requestMethod) || "PUT".equalsIgnoreCase(requestMethod)) {
                String pathJson = request.getReader().lines().collect(Collectors.joining(System.lineSeparator()));
                Map<String, String> paramMap = new Gson().fromJson(
                        pathJson, new TypeToken<HashMap<String, String>>() {
                        }.getType()
                );

                if(paramMap != null){
                    projectId = paramMap.get(PROJECT_PATH);
                }

                logger.info("Post params = " + pathJson);

            }

            if( projectId == null) {
                projectId = request.getParameter(PROJECT_PATH);
            }


            if (projectId == null) {
                projectId = getProjectId(request.getRequestURI());
            } else if (projectId.indexOf("/") != -1) {
                projectId = getProjectId(projectId);
            }
            logger.info("Path variables = " + projectId);
        } catch (Exception e) {
            logger.warning(e.getMessage());
        }
        return projectId;
    }

    protected String getProjectId(String path) {
        String projectId = null;
        int projectIdIndex = path.indexOf(PROJECT_PATH + "/");

        if (projectIdIndex != -1) {
            logger.info("Request URI = " + path);
            path = path.replace(PROJECT_PATH + "/", "$");
            projectId = path.substring(path.indexOf("$") + 1);
            int nextParamIndex = projectId.indexOf("/");
            if (nextParamIndex != -1) {
                projectId = projectId.substring(0, nextParamIndex);
            }
        }
        return projectId;
    }
}

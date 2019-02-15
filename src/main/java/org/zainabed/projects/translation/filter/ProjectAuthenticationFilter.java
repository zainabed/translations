package org.zainabed.projects.translation.filter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Service;
import org.springframework.web.filter.OncePerRequestFilter;
import org.zainabed.projects.translation.lib.request.RequestParameterChain;
import org.zainabed.projects.translation.lib.request.RequestWrapper;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.function.Function;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@Service
public class ProjectAuthenticationFilter extends OncePerRequestFilter {

    Logger logger = Logger.getLogger(ProjectAuthenticationFilter.class.getName());
    public static final String PROJECT_PATH = "projects";

    @Autowired
    @Qualifier("PostParameterChain")
    RequestParameterChain requestParameterChain;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        request = new RequestWrapper((HttpServletRequest) request);

        Function<GrantedAuthority, String> authorityToString = a -> {
            return ((GrantedAuthority) a).getAuthority();
        };

        try {
            String projectId = requestParameterChain.getParameter(request, PROJECT_PATH);

            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            logger.info("Authentication:" + authentication);
            if (projectId == null || projectId.isEmpty() || authentication == null) {
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


}

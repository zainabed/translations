package org.zainabed.projects.translation.filter;

import com.zainabed.spring.security.jwt.filter.JwtAuthorizationFilter;
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
import java.util.function.Predicate;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import org.zainabed.projects.translation.model.Project;

/**
 * Spring security filter class. This class is design to authorize HTTP request by
 * verifying user's credential against project id present in HTTP request.
 * <p>
 * It should be called after {@link JwtAuthorizationFilter} filter as this filter generates Spring security authentication object.
 * <p>
 * Process of this filter is to first fetch project id from incoming HTTP request using {@link RequestParameterChain} interface.
 * Then match this project id against user Roles present in {@link Authentication} object on spring security.
 * If given project id is found then generate new {@link Authentication} with credential given for that project id.
 * otherwise execute next filter chain.
 */
@Service
public class ProjectAuthenticationFilter extends OncePerRequestFilter {

    Logger logger = Logger.getLogger(ProjectAuthenticationFilter.class.getName());

    /**
     * HTTP request parameter name constant for {@link Project} entity id.
     */
    public static final String PROJECT_PATH = "projects";

    String projectId;

    /**
     * Object of {@link RequestParameterChain} which is qualified for 'PostParameterChain'.
     */
    @Autowired
    @Qualifier("PostParameterChain")
    private RequestParameterChain requestParameterChain;

    /**
     * Lambda functional interface to convert {@link GrantedAuthority} to {@link String} object.
     */
    protected Function<GrantedAuthority, String> authorityToString = a -> {
        return ((GrantedAuthority) a).getAuthority();
    };

    /**
     * Lambda predicate interface to filter String authority name by comparing it with HTTP requests project id.
     */
    protected Predicate<String> filterAuthority = a -> a.startsWith(projectId + "_");

    /**
     * Lambda functional interface to get authority roles from removing HTTP requests project id.
     */
    protected Function<String, String> authorityMap = a -> a.replace(projectId + "_", "");

    /**
     * This is core method if this class, it is called on each secured {@link HttpServletRequest}.
     * It process request object to create new {@link Authentication} object whose roles specific to
     * current user's assigned project ids.
     * <p>
     * If {@link Authentication} object is null which mean that current request does not have any credentials
     * hence this method should not process it and leave it for default Spring security filter to deal with it.
     * <p>
     * If project id is missing then this method will proceed with default Spring security filter.
     * <p>
     * If both {@link Authentication} and project id is present in current request then method will create
     * new {@link Authentication} object and set it inside current security context.
     *
     * @param request     {@link HttpServletRequest} object
     * @param response    {@link HttpServletResponse} object
     * @param filterChain Spring {@link FilterChain} object
     * @throws ServletException It may throw {@link ServletException} exception
     * @throws IOException      It may throw {@link IOException} exception
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        request = new RequestWrapper(request);

        try {
            projectId = requestParameterChain.getParameter(request, PROJECT_PATH);
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

            if (projectId == null || projectId.isEmpty() || authentication == null) {
                filterChain.doFilter(request, response);
                return;
            }

            buildAuthentication(authentication, request);

        } catch (Exception e) {
            logger.warning(e.getMessage());
        }

        filterChain.doFilter(request, response);
    }

    /**
     * This method creates list of new {@link SimpleGrantedAuthority} objects by transforming
     * given user authorities to project specific authorities.
     * It filter only those authorities which are assigned to project id present in HTTP request.
     *
     * @param authentication {@link Authentication} object of current request
     * @return List of transformed authorities
     */
    private List<GrantedAuthority> buildUserAuthority(Authentication authentication) {
        return authentication.getAuthorities().stream()
                .map(authorityToString)
                .filter(filterAuthority)
                .map(authorityMap)
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }

    /**
     * Method build new {@link Authentication} object from {@link HttpServletRequest} and current {@link Authentication}
     * objects.
     * New {@link Authentication} object is specific to project id present in current {@link HttpServletRequest}.
     *
     * @param authentication Current request's {@link Authentication} object
     * @param request        Current {@link HttpServletRequest} object
     */
    private void buildAuthentication(Authentication authentication, HttpServletRequest request) {
        List<GrantedAuthority> authorities = buildUserAuthority(authentication);
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(authentication.getPrincipal(), null, authorities);
        usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
    }


}

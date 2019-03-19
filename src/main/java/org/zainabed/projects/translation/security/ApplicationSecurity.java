package org.zainabed.projects.translation.security;

import com.zainabed.spring.security.jwt.annotation.EnableJwtSecurity;
import com.zainabed.spring.security.jwt.filter.JwtAuthorizationFilter;
import com.zainabed.spring.security.jwt.security.JwtWebSecuriy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.zainabed.projects.translation.filter.ProjectAuthenticationFilter;

@EnableJwtSecurity
public class ApplicationSecurity extends JwtWebSecuriy {

    @Autowired
    ProjectAuthenticationFilter projectAuthenticationFilter;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/users").permitAll()
                .antMatchers("/static/**").permitAll()
                .antMatchers("/translations/export/**").permitAll();
       
        super.configure(http);
        http.addFilterAfter(projectAuthenticationFilter, JwtAuthorizationFilter.class);
    }

    /*@Override
    public void configure(WebSecurity web) throws Exception {

        web.ignoring().antMatchers(HttpMethod.POST, "/users");
    }*/
}

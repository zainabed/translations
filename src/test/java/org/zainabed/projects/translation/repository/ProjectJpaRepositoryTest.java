package org.zainabed.projects.translation.repository;

import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.restdocs.JUnitRestDocumentation;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.security.web.FilterChainProxy;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.zainabed.projects.translation.Application;
import org.zainabed.projects.translation.model.User;
import org.zainabed.projects.translation.model.UserEntity;
import org.zainabed.projects.translation.security.ApplicationSecurity;

import com.zainabed.spring.security.jwt.entity.AuthenticationToken;
import com.zainabed.spring.security.jwt.service.AuthorizationHeaderService;
import com.zainabed.spring.security.jwt.service.JwtTokenService;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = { Application.class, ApplicationSecurity.class })
public class ProjectJpaRepositoryTest {

	MockMvc mvc;

	@Autowired
	WebApplicationContext context;

	@Rule
	public JUnitRestDocumentation restDocumentation = new JUnitRestDocumentation();

	@Autowired
	JwtTokenService tokenService;

	@Autowired
	private FilterChainProxy springSecurityFilterChain;

	User user;
	AuthenticationToken token;
	String authToken;
	String authHeader;
	String authHeaderDesc = "Authorization Header";

	@Before
	public void setup() {
		mvc = MockMvcBuilders.webAppContextSetup(context).addFilters(this.springSecurityFilterChain)
				.apply(documentationConfiguration(restDocumentation)).build();

		user = new User();
		user.setUsername("testuser");
		user.setEmail("testuser@test.org");
		user.setPassword("abcdef");

		UserEntity userEntity = new UserEntity(user);

		token = tokenService.getToken(userEntity);
		authToken = AuthorizationHeaderService.AUTH_TYPE_BEARER + token.getToken();
		authHeader = AuthorizationHeaderService.AUTH_HEADER;
	}

	@Test
	public void shouldReturnProjectList() throws Exception {
		mvc.perform(get("/projects").header(authHeader, authToken)).andDo(print()).andExpect(status().isOk()).andDo(
				document("project-list", requestHeaders(headerWithName(authHeader).description(authHeaderDesc))));
	}

	@Test
	public void shouldReturnSingleProject() throws Exception {
		mvc.perform(RestDocumentationRequestBuilders.get("/projects/{id}", 1).header(authHeader, authToken)).andDo(print()).andExpect(status().isOk())
				.andDo(document("project-get", requestHeaders(headerWithName(authHeader).description(authHeaderDesc)),
						pathParameters(parameterWithName("id").description("Project Id"))));
	}

}

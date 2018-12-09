package org.zainabed.projects.translation.repository;

import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.restdocs.request.RequestDocumentation.requestParameters;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.restdocs.JUnitRestDocumentation;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.security.web.FilterChainProxy;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.zainabed.projects.translation.Application;
import org.zainabed.projects.translation.model.Project;
import org.zainabed.projects.translation.model.Role;
import org.zainabed.projects.translation.model.User;
import org.zainabed.projects.translation.model.UserEntity;
import org.zainabed.projects.translation.security.ApplicationSecurity;

import com.google.gson.Gson;
import com.zainabed.spring.security.jwt.entity.AuthenticationToken;
import com.zainabed.spring.security.jwt.service.AuthorizationHeaderService;
import com.zainabed.spring.security.jwt.service.JwtTokenService;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;

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
	Gson gson = new Gson();

	Project project;

	@Before
	public void setup() {
		mvc = MockMvcBuilders.webAppContextSetup(context).addFilters(this.springSecurityFilterChain)
				.apply(documentationConfiguration(restDocumentation)).build();

		user = new User();
		user.setUsername("testuser");
		user.setEmail("testuser@test.org");
		user.setPassword("abcdef");
		List<Role> roles = new ArrayList<>();
		Role role = new Role();
		role.setName("ROLE_ADMIN");
		roles.add(role);
		role = new Role();
		role.setName("ROLE_USER");
		roles.add(role);
		user.setRoles(roles);

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
		mvc.perform(RestDocumentationRequestBuilders.get("/projects/{id}", 1).header(authHeader, authToken))
				.andDo(print()).andExpect(status().isOk())
				.andDo(document("project-get", requestHeaders(headerWithName(authHeader).description(authHeaderDesc)),
						pathParameters(parameterWithName("id").description("Project Id"))));
	}

	@Test
	public void shouldCreateNewProject() throws Exception {
		mvc.perform(RestDocumentationRequestBuilders.post("/projects").header(authHeader, authToken)
				.content(gson.toJson(getProject())).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isCreated())
				.andDo(document("project-post", requestHeaders(headerWithName(authHeader).description(authHeaderDesc)),
						requestFields(fieldWithPath("name").description("Project name"),
								fieldWithPath("description").description("Project description"),
								fieldWithPath("id").optional().description("Project unique value"))));
	}

	@Test
	public void shouldUpdateProject() throws Exception {
		project = getProject();
		project.setId(1);
		mvc.perform(RestDocumentationRequestBuilders.put("/projects/{id}", 1).header(authHeader, authToken)
				.content(gson.toJson(project)))
				.andExpect(status().is2xxSuccessful())
				.andDo(document("project-put", requestHeaders(headerWithName(authHeader).description(authHeaderDesc)),
						pathParameters(parameterWithName("id").description("Project unique id")),
						requestFields(fieldWithPath("name").description("Project name"),
								fieldWithPath("description").description("Project description"),
								fieldWithPath("id").optional().description("Project unique value"))));

	}
	

	/*@Test
	public void shouldDeleteProject() throws Exception {
		project = getProject();
		project.setId(1);
		mvc.perform(RestDocumentationRequestBuilders.post("/projects").header(authHeader, authToken)
				.content(gson.toJson(project)).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isCreated());
		
		mvc.perform(delete("/projects/{id}", project.getId())
				.header(authHeader, authToken))
		.andExpect(status().is2xxSuccessful());
	}*/

	public Project getProject() {
		project = new Project();
		project.setName("test project");
		project.setDescription("test description");
		return project;
	}
}

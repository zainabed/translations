package org.zainabed.projects.translation.repository;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.restdocs.JUnitRestDocumentation;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;

import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.security.web.FilterChainProxy;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
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
import static org.springframework.restdocs.headers.HeaderDocumentation.*;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@ExtendWith(SpringExtension.class)
@ExtendWith(RestDocumentationExtension.class)
@SpringBootTest(classes = { Application.class, ApplicationSecurity.class })
@ActiveProfiles("test")
public class ProjectJpaRepository {

	MockMvc mvc;

	@Autowired
	WebApplicationContext context;

	@Autowired
	JwtTokenService tokenService;

	@Autowired
	private FilterChainProxy springSecurityFilterChain;

	User user;
	AuthenticationToken token;
	String authToken;
	String authHeader;
	String authHeaderAdminDesc = "Authorization Header with user role ADMIN";
	String authHeaderUserDesc = "Authorization Header with user role USER";
	Gson gson = new Gson();

	Project project;

	@BeforeEach
	public void setup(RestDocumentationExtension restDocumentation) {
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
		mvc.perform(get("/projects").header(authHeader, authToken)).andExpect(status().isOk()).andDo(
				document("project-list", requestHeaders(headerWithName(authHeader).description(authHeaderUserDesc))));
	}

	@Test
	public void shouldCreateNewProject() throws Exception {
		mvc.perform(post("/projects").header(authHeader, authToken).content(gson.toJson(getProject()))
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isCreated())
				.andDo(document("project-post",
						requestHeaders(headerWithName(authHeader).description(authHeaderAdminDesc)),
						requestFields(fieldWithPath("name").description("Project name"),
								fieldWithPath("description").description("Project description")
						//		,fieldWithPath("id").optional().description("Project unique value")
						)));
	}

	@Test
	public void shouldUpdateProject() throws Exception {
		project = getProject();
		project.setId(1L);
		mvc.perform(put("/projects/{id}", 1).header(authHeader, authToken).content(gson.toJson(project)))
				.andExpect(status().is2xxSuccessful())
				.andDo(document("project-put",
						requestHeaders(headerWithName(authHeader).description(authHeaderAdminDesc)),
						pathParameters(parameterWithName("id").description("Project unique id")),
						requestFields(fieldWithPath("name").description("Project name"),
								fieldWithPath("description").description("Project description"),
								fieldWithPath("id").optional().description("Project unique value"))));

	}

	@Test
	public void shouldGetOneProject() throws Exception {

		mvc.perform(get("/projects/{id}", 2).header(authHeader, authToken)).andExpect(status().is2xxSuccessful())
				.andDo(document("project-get-one",
						requestHeaders(headerWithName(authHeader).description(authHeaderUserDesc)),
						pathParameters(parameterWithName("id").description("Project unique id"))));
	}

	@Test
	public void shouldDeleteProject() throws Exception {
		mvc.perform(delete("/projects/{id}", 3).header(authHeader, authToken)).andExpect(status().is2xxSuccessful())
				.andDo(document("project-delete-one",
						requestHeaders(headerWithName(authHeader).description(authHeaderAdminDesc)),
						pathParameters(parameterWithName("id").description("Project unique id"))));

	}

	@Test
	public void shouldDeleteLocaleFromProject() throws Exception {
		mvc.perform(delete("/projects/{id}/locales/{localeId}", 1, 1).header(authHeader, authToken))
				.andExpect(status().isOk());

		mvc.perform(get("/projects/{id}/locales", 1).header(authHeader, authToken)).andDo(print())
				.andExpect(status().isOk()).andExpect(jsonPath("$._embedded.locales", hasSize(1)));
	}

	@Test
	public void shouldAddLocaleFromProject() throws Exception {
		mvc.perform(post("/projects/{id}/locales/{localeId}", 4, 2).header(authHeader, authToken))
				.andExpect(status().isOk());

		mvc.perform(get("/projects/{id}/locales", 4).header(authHeader, authToken)).andDo(print())
				.andExpect(status().isOk()).andExpect(jsonPath("$._embedded.locales", hasSize(2)));
	}

	public Project getProject() {
		project = new Project();
		project.setName("test project");
		project.setDescription("test description");
		return project;
	}

}

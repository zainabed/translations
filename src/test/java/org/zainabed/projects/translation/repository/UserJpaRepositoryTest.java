package org.zainabed.projects.translation.repository;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.zainabed.projects.translation.Application;
import org.zainabed.projects.translation.model.User;
import org.zainabed.projects.translation.model.UserEntity;

import com.google.gson.Gson;
import com.zainabed.spring.security.jwt.entity.AuthenticationToken;
import com.zainabed.spring.security.jwt.service.AuthorizationHeaderService;
import com.zainabed.spring.security.jwt.service.JwtTokenService;

@ExtendWith(SpringExtension.class)
@ExtendWith(RestDocumentationExtension.class)
@SpringBootTest(classes = Application.class)
@ActiveProfiles("test")
// @AutoConfigureMockMvc
public class UserJpaRepositoryTest {

	MockMvc mvc;
	@Autowired
	private WebApplicationContext context;

	@Autowired
	private UserJpaRepository repository;



	@Autowired
	JwtTokenService tokenService;

	User user;
	Gson gson = new Gson();
	AuthenticationToken token;

	@BeforeEach
	public void loadModel(RestDocumentationExtension restDocumentation) {
		this.mvc = MockMvcBuilders.webAppContextSetup(this.context)
				.apply(documentationConfiguration(restDocumentation)).build();

		user = new User();
		user.setUsername("testuser");
		user.setEmail("testuser@test.org");
		user.setPassword("abcdef");

		UserEntity userEntity = new UserEntity(user);

		token = tokenService.getToken(userEntity);
	}

	@AfterEach
	public void releaseModel() {
		user = null;
		repository.deleteAll();
	}

	@Test
	public void shouldPostSingleEntity() throws Exception {

		mvc.perform(post("/users").header(AuthorizationHeaderService.AUTH_HEADER, token.getToken())
				.content(gson.toJson(user)).contentType(MediaType.APPLICATION_JSON)).andExpect(status().isCreated())
				.andDo(document("users"));
	}

	// Unit test for User constraint violations

	@Test
	public void shouldReturn400StatusCodeForInvalidUsername() throws Exception {
		user.setUsername(null);
		mvc.perform(post("/users").content(gson.toJson(user)).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().is4xxClientError());
	}

	@Test
	public void shouldReturn400StatusCauseUsernameLengthIsLessThen5() throws Exception {
		user.setUsername("abcd");
		mvc.perform(post("/users").content(gson.toJson(user)).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().is4xxClientError());
	}

	@Test
	public void shouldReturn400StatusCauseUsernameLengthIsMoreThen20() throws Exception {
		user.setUsername("123456789012345678901");
		mvc.perform(post("/users").content(gson.toJson(user)).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().is4xxClientError());
	}

	@Test
	public void shouldReturn400StatusCodeForInvalidEmail() throws Exception {
		user.setEmail("abcdefg");
		mvc.perform(post("/users").content(gson.toJson(user)).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().is4xxClientError());
	}

	@Test
	public void shouldReturn400StatusCodeForEmptyEmail() throws Exception {
		user.setEmail(null);
		mvc.perform(post("/users").content(gson.toJson(user)).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().is4xxClientError());
	}

	@Test
	public void shouldReturn400StatusCauseEmailLengthIsLessThen5() throws Exception {
		user.setEmail("a@as");
		mvc.perform(post("/users").content(gson.toJson(user)).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().is4xxClientError());
	}

	@Test
	public void shouldReturn400StatusCauseEamilLengthIsMoreThen30() throws Exception {
		user.setEmail("12345678@012345678901.345678901");
		mvc.perform(post("/users").content(gson.toJson(user)).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().is4xxClientError());
	}

	@Test
	public void shouldReturn400StatusCodeForEmptyPassword() throws Exception {
		user.setPassword(null);
		mvc.perform(post("/users").content(gson.toJson(user)).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().is4xxClientError());
	}

	// End of Unit test for User constraint violations

	/*@Test
	public void shouldReturnSingleUserObject() throws Exception {
		mvc.perform(post("/users").content(gson.toJson(user))).andExpect(status().isCreated());
		mvc.perform(get("/users")).andExpect(status().isOk()).andExpect(jsonPath("$._embedded.users", hasSize(1)))
				.andDo(document("users",
						links(linkWithRel("self").description("Link to user resource"),
								linkWithRel("profile").description("Link to user profile"),
								linkWithRel("search").description("Link for search method for user resource")),
						responseFields(

								subsectionWithPath("_embedded.users").description("Users json response"),
								subsectionWithPath("_links").description("Users json links"),
								subsectionWithPath("page").description("Users json pagging"))

		));

	}

	@Test public void shouldUpdateUserModel() throws Exception { String
	  updatedEmailId = "updated@email.org";
	  
	  mvc.perform(post("/users").content(gson.toJson(user))).andExpect(status()
	  .isCreated()); user.setEmail(updatedEmailId);
	  mvc.perform(patch("/users/100").content(gson.toJson(user))).andExpect(
	  status().is2xxSuccessful());
	  
	  mvc.perform(get("/users/1")).andExpect(status().isOk());
	  //.andExpect((ResultMatcher) jsonPath("$users.email",
	  equals(updatedEmailId))); 
	  }*/
}

package org.zainabed.projects.translation.repository;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.restdocs.JUnitRestDocumentation;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.zainabed.projects.translation.Application;
import org.zainabed.projects.translation.model.User;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.hamcrest.Matchers.*;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.*;
import static org.springframework.restdocs.hypermedia.HypermediaDocumentation.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;

@RunWith(SpringRunner.class)
@SpringBootTest
// @AutoConfigureMockMvc
public class UserJpaRepositoryIT {

	MockMvc mvc;
	@Autowired
	private WebApplicationContext context;

	@Autowired
	private UserJpaRepository repository;

	@Rule
	public JUnitRestDocumentation restDocumentation = new JUnitRestDocumentation();

	User user;
	Gson gson = new Gson();

	@Before
	public void loadModel() {
		this.mvc = MockMvcBuilders.webAppContextSetup(this.context)
				.apply(documentationConfiguration(this.restDocumentation)).build();

		user = new User();
		user.setUsername("testuser");
		user.setEmail("testuser@test.org");
		user.setPassword("abcdef");

	}

	@After
	public void releaseModel() {
		user = null;
		repository.deleteAll();
	}

	@Test
	public void shouldPostSingleEntity() throws Exception {

		mvc.perform(post("/users").content(gson.toJson(user)).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isCreated()).andDo(document("users"));
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

	@Test
	public void shouldReturnSingleUserObject() throws Exception {
		mvc.perform(post("/users").content(gson.toJson(user))).andExpect(status().isCreated());
		mvc.perform(get("/users")).andExpect(status().isOk()).andExpect(jsonPath("$._embedded.users", hasSize(1)))
				.andDo(document("users",
						links(linkWithRel("self").description("Link to user resource"),
								linkWithRel("profile").description("Link to user profile"),
								linkWithRel("search").description("Link for search method for user resource")),
						responseFields(
								/*
								 * fieldWithPath("users.username").description(
								 * "Unique username of user"),
								 * fieldWithPath("users.emaill").description(
								 * "Email address of user"),
								 * fieldWithPath("users.password").description(
								 * "Password of user"),
								 * fieldWithPath("users.created_at").
								 * description("Date of creation"),
								 * fieldWithPath("users.updated_at").
								 * description("Last date of update"),
								 * fieldWithPath("users.status") .description(
								 * "Status of user {active, deactive or blocked}"
								 * )
								 */
								subsectionWithPath("_embedded.users").description("Users json response"),
								subsectionWithPath("_links").description("Users json links"),
								subsectionWithPath("page").description("Users json pagging"))

		));

	}

	/*@Test
	public void shouldUpdateUserModel() throws Exception {
		String updatedEmailId = "updated@email.org";

		mvc.perform(post("/users").content(gson.toJson(user))).andExpect(status().isCreated());
		user.setEmail(updatedEmailId);
		mvc.perform(patch("/users/100").content(gson.toJson(user))).andExpect(status().is2xxSuccessful());

		mvc.perform(get("/users/1")).andExpect(status().isOk());
				//.andExpect((ResultMatcher) jsonPath("$users.email", equals(updatedEmailId)));
	}*/
}

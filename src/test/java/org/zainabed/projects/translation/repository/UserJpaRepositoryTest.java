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
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.zainabed.projects.translation.Application;
import org.zainabed.projects.translation.model.User;

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
public class UserJpaRepositoryTest {

	MockMvc mvc;
	@Autowired
	private WebApplicationContext context;

	String testUsername = "testuser";
	List<User> users;

	@Rule
	public JUnitRestDocumentation restDocumentation = new JUnitRestDocumentation();

	public UserJpaRepositoryTest() {

	}

	@Before
	public void loadModel() {
		this.mvc = MockMvcBuilders.webAppContextSetup(this.context)
				.apply(documentationConfiguration(this.restDocumentation)).build();
		users = new ArrayList<User>();
		for (int i = 1; i < 5; i++) {
			User user = new User();
			user.setUsername("username " + i);
			users.add(user);
		}
	}

	@After
	public void releaseModel() {
		users = null;
	}

	@Test
	public void shouldPostSingleEntity() throws Exception {
		User user = new User();
		user.setUsername(testUsername);
		user.setEmail("test@test.com");
		user.setPassword("12345");

		mvc.perform(post("/users")
				.content("{ \"username\": \"zainabed\", \"email\":\"test@test.org\",\"password\":\"abcde\"}")
				.contentType(MediaType.APPLICATION_JSON)).andExpect(status().isCreated()).andDo(document("users"));
	}

	@Test
	public void shouldReturnSingleUserObject() throws Exception {
		mvc.perform(get("/users")).andExpect(status().isOk()).andExpect(jsonPath("$._embedded.users", hasSize(1)))
				.andDo(document("users",
						links(linkWithRel("self").description("Link to user resource"),
								linkWithRel("profile").description("Link to user profile"),
								linkWithRel("search").description("Link for search method for user resource")),
						responseFields(
								/*fieldWithPath("users.username").description("Unique username of user"),
								fieldWithPath("users.emaill").description("Email address of user"),
								fieldWithPath("users.password").description("Password of user"),
								fieldWithPath("users.created_at").description("Date of creation"),
								fieldWithPath("users.updated_at").description("Last date of update"),
								fieldWithPath("users.status")
										.description("Status of user {active, deactive or blocked}")*/
								subsectionWithPath("_embedded.users").description("Users json response"),
								subsectionWithPath("_links").description("Users json links"),
								subsectionWithPath("page").description("Users json pagging")
										)
						
										));
		;
	}
}

package org.zainabed.projects.translation.repository;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.zainabed.projects.translation.Application;
import org.zainabed.projects.translation.model.User;



import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.hamcrest.Matchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class UserJpaRepositoryTest {

	@Autowired
	MockMvc mvc;
	String testUsername = "testuser";
	
	
	
	List<User> users;
	
	@Before
	public void loadModel(){
		users = new ArrayList<User>();
		for(int i=1;i<5;i++){
			User user = new User();
			user.setUsername("username " + i);
			users.add(user);
		}
	}
	
	
	@After
	public void releaseModel(){
		users = null;
	}
	
	@Test
	public void shouldPostSingleEntity() throws Exception{
		User user = new User();
		user.setUsername(testUsername);
		user.setEmail("test@test.com");
		user.setPassword("12345");
		
		mvc.perform(post("/users").content(
				"{ \"username\": \"zainabed\", \"email\":\"test@test.org\",\"password\":\"abcde\"}")
				.contentType(MediaType.APPLICATION_JSON)).andExpect(status().isCreated());
	}
	
	@Test
	public void shouldReturnSingleUserObject() throws Exception{
		mvc.perform(get("/users"))
		.andExpect(status().isOk())
		.andExpect(jsonPath("$", hasSize(1)));
	}
}

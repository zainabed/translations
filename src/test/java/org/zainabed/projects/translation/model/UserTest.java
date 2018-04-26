package org.zainabed.projects.translation.model;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class UserTest extends TestModelConfig<User> {

	@Override
	@Before
	public void loadModel() {
		model = new User();
		model.setUsername("zainabed");
		model.setEmail("zainabed@test.org");
		model.setPassword("testpassword");

	}

	@Override
	@After
	public void releaseModel() {
		model = null;
	}

	@Test
	public void shouldFailCauseEmptyUsername() {
		model.setUsername(null);
		validateModelForConstraints(1);
	}

	@Test
	public void shouldFailCauseUsernameLengthIsLessThan5() {
		model.setUsername("abcd");
		validateModelForConstraints(1);
	}

	@Test
	public void shouldFailCauseUsernameLengthIsMoreThan20() {
		model.setUsername("123456789012345678901");
		validateModelForConstraints(1);
	}

	@Test
	public void shouldFailCauseInValidEmail() {
		model.setEmail("aaaaaa");
		validateModelForConstraints(1);
	}
	
	@Test
	public void shouldFailCauseEmailLengthIsLessThan5() {
		model.setEmail("abcd");
		validateModelForConstraints(2);
	}

	@Test
	public void shouldFailCauseEmailLengthIsMoreThan30() {
		model.setEmail("1234567890123456789012345678901");
		validateModelForConstraints(2);
	}

	@Test
	public void shouldFailCauseEmptyPassword() {
		model.setPassword(null);
		validateModelForConstraints(1);
	}

}

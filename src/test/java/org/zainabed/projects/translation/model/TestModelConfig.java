package org.zainabed.projects.translation.model;

import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.junit.Test;

import static org.junit.Assert.*;

public abstract class TestModelConfig<T> {
	protected T model;
	private Set<ConstraintViolation<T>> constraints;
	private Validator validator;

	public TestModelConfig() {
		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
		validator = factory.getValidator();
	}

	abstract public void loadModel();

	abstract public void releaseModel();

	public void validateModelForConstraints( int violationCout) {
		constraints = validator.validate(model);
		assertEquals(constraints.size(), violationCout);
	}
	
	@Test
	public void shouldPassCauseValidModel() {
		validateModelForConstraints(0);
	}
}

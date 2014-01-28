package org.springframework.samples.petclinic.model;

import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;


import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

/**
 * 
 * @author Michael Isvy
 * Simple test to make sure that Bean Validation is working 
 * (useful when upgrading to a new version of Hibernate Validator/ Bean Validation)
 *
 */
public class ValidatorTests {
	
	private Validator createValidator() {
	      LocalValidatorFactoryBean localValidatorFactoryBean = new LocalValidatorFactoryBean();
	      localValidatorFactoryBean.afterPropertiesSet();
	      return localValidatorFactoryBean;
	  }

	@Test
    public void emptyFirstName() {

        Person person = new Person();
        person.setFirstName("");
        person.setLastName("smith");

        Validator validator = createValidator();
        Set<ConstraintViolation<Person>> constraintViolations = validator.validate(person);

        Assert.assertEquals(1, constraintViolations.size());
        ConstraintViolation<Person> violation =  constraintViolations.iterator().next();
        Assert.assertEquals(violation.getPropertyPath().toString(), "firstName");
        Assert.assertEquals(violation.getMessage(), "may not be empty");
    }
	
}

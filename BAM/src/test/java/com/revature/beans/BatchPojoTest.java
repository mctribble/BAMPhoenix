package com.revature.beans;

import com.bam.beans.Batch;
import static com.google.code.beanmatchers.BeanMatchers.*;
import static org.hamcrest.CoreMatchers.allOf;
import static org.hamcrest.MatcherAssert.assertThat;

import org.junit.Test;

/**
 * 
 * @author Ramses Testing the Batch Pojo's setter and getters, no-args
 *         constructor and toString method using JUnit.
 *
 */
public class BatchPojoTest {
	// Individual, well named tests

	@Test
	public void shouldHaveANoArgsConstructor() {
		assertThat(Batch.class, hasValidBeanConstructor());
	}

	@Test
	public void gettersAndSettersShouldWorkForEachProperty() {
		assertThat(Batch.class, hasValidGettersAndSetters());
	}

	@Test
	public void allPropertiesShouldBeRepresentedInToStringOutput() {
		assertThat(Batch.class, hasValidBeanToString());
	}

}

package com.bam.bean.unit;

import static com.google.code.beanmatchers.BeanMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

import org.junit.Test;

import com.bam.bean.BamUser;

/**
 * @author Ramses
 * Testing the BamUser Bean's setter and getters, no-args constructor
 * and toString method using JUnit.
 */
public class BamUserTest {
	
	//PASS: Ensures a bean has a working no-args constructor.
    @Test
    public void shouldHaveANoArgsConstructor() {
        assertThat(BamUser.class, hasValidBeanConstructor());
    }
    //PASS: Ensure all properties on the bean have working getters and setters.
    @Test
    public void gettersAndSettersShouldWorkForEachProperty() {
        assertThat(BamUser.class, hasValidGettersAndSetters());
    }
    //PASS: Ensure all properties on the bean are included in the string value.
    @Test
    public void allPropertiesShouldBeRepresentedInToStringOutput() {
        assertThat(BamUser.class, hasValidBeanToStringFor("userId"));
        assertThat(BamUser.class, hasValidBeanToStringFor("fName"));
        assertThat(BamUser.class, hasValidBeanToStringFor("mName"));
        assertThat(BamUser.class, hasValidBeanToStringFor("lName"));
        assertThat(BamUser.class, hasValidBeanToStringFor("email"));
        assertThat(BamUser.class, hasValidBeanToStringFor("pwd"));
        assertThat(BamUser.class, hasValidBeanToStringFor("role"));
        assertThat(BamUser.class, hasValidBeanToStringFor("phone"));
        assertThat(BamUser.class, hasValidBeanToStringFor("phone2"));
        assertThat(BamUser.class, hasValidBeanToStringFor("skype"));
        assertThat(BamUser.class, hasValidBeanToStringFor("pwd2"));
    }
	
}


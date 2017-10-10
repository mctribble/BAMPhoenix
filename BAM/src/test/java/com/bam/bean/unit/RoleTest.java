package com.bam.bean.unit;

import com.bam.bean.Role;
import org.junit.Test;

import static com.google.code.beanmatchers.BeanMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertEquals;

public class RoleTest {
    //PASS: Ensures a bean has a working no-args constructor.
    @Test
    public void shouldHaveANoArgsConstructor() {
        assertThat(Role.class, hasValidBeanConstructor());
    }
    //PASS: Ensure all properties on the bean have working getters and setters.
    @Test
    public void gettersAndSettersShouldWorkForEachProperty() {
        assertThat(Role.class, hasValidGettersAndSetters());
    }
    //PASS: Ensure all properties on the bean are included in the string value.
    @Test
    public void allPropertiesShouldBeRepresentedInToStringOutput() {
        assertThat(Role.class, hasValidBeanToString());
    }

    @Test
    public void testParameterizedConstructor()
    {
        Role testRole = new Role(1, "test");
        assertEquals(1, testRole.getRoleId());
        assertEquals("test", testRole.getUserRole());
    }
}
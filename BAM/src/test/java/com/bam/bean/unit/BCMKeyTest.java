package com.bam.bean.unit;

import com.bam.bean.BCMKey;
import org.junit.Test;

import static com.google.code.beanmatchers.BeanMatchers.hasValidBeanConstructor;
import static com.google.code.beanmatchers.BeanMatchers.hasValidBeanToString;
import static com.google.code.beanmatchers.BeanMatchers.hasValidGettersAndSetters;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.*;

public class BCMKeyTest
{
    @Test
    public void shouldHaveANoArgsConstructor() {
        assertThat(BCMKey.class, hasValidBeanConstructor());
    }
    //PASS: Ensure all properties on the bean have working getters and setters.
    @Test
    public void gettersAndSettersShouldWorkForEachProperty() {
        assertThat(BCMKey.class, hasValidGettersAndSetters());
    }
}
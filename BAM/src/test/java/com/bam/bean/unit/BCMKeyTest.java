package com.bam.bean.unit;

import com.bam.bean.BCMKey;
import org.junit.Test;

import static com.google.code.beanmatchers.BeanMatchers.hasValidBeanConstructor;
import static com.google.code.beanmatchers.BeanMatchers.hasValidGettersAndSetters;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertEquals;

public class BCMKeyTest
{
    @Test
    public void shouldHaveANoArgsConstructor() {
        assertThat(BCMKey.class, hasValidBeanConstructor());
    }

    @Test
    public void gettersAndSettersShouldWorkForEachProperty() {
        assertThat(BCMKey.class, hasValidGettersAndSetters());
    }

    @Test
    public void testParameterizedConstructors()
    {
        BCMKey test = new BCMKey(1, "test name");

        assertEquals(1, test.getBid());
        assertEquals("test name", test.getCname());
    }
}
package com.bam.bean.unit;

import com.bam.bean.Force;
import org.junit.Test;

import static com.google.code.beanmatchers.BeanMatchers.hasValidBeanConstructor;
import static org.hamcrest.MatcherAssert.assertThat;

public class ForceTest {

    @Test
    public void shouldHaveANoArgsConstructor() {
        assertThat(Force.class, hasValidBeanConstructor());
    }
}

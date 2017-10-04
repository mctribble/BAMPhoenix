package com.bam.dto.unit;

import com.bam.dto.CurriculumSubtopicDTO;
import com.bam.dto.MetaDTO;
import com.bam.dto.WeeksDTO;
import org.junit.Test;

import static com.google.code.beanmatchers.BeanMatchers.hasValidBeanConstructor;
import static com.google.code.beanmatchers.BeanMatchers.hasValidBeanToStringFor;
import static com.google.code.beanmatchers.BeanMatchers.hasValidGettersAndSettersFor;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class CurriculumSubtopicDTOTest {

    //PASS: Ensures the DTO has a working no-args constructor.
    @Test
    public void shouldHaveANoArgsConstructor(){
        assertThat(CurriculumSubtopicDTO.class, hasValidBeanConstructor());
    }

    //PASS: Ensures all states of the DTO have valid Getters and Setters
    @Test
    public void gettersAndSettersShouldWorkForEachState(){
        assertThat(CurriculumSubtopicDTO.class, hasValidGettersAndSettersFor());

        MetaDTO testMeta = new MetaDTO();
        WeeksDTO[] testWeeks = {new WeeksDTO(), new WeeksDTO(), new WeeksDTO()};

        CurriculumSubtopicDTO test = new CurriculumSubtopicDTO();
        test.setMeta(testMeta);
        test.setWeeks(testWeeks);

        assertThat(test.getMeta(), is(testMeta));
        assertThat(test.getWeeks(), is(testWeeks));
    }
    //PASS: Ensures all properties of the DTO have valid ToString
    @Test
    public void toStringShouldWorkForEachState(){
        assertThat(CurriculumSubtopicDTO.class, hasValidBeanToStringFor("meta"));
        assertThat(CurriculumSubtopicDTO.class, hasValidBeanToStringFor("weeks"));
    }
}

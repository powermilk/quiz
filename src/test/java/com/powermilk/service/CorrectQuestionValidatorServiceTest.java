package com.powermilk.service;

import com.powermilk.TestEntities;
import com.powermilk.model.BooleanWrapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

import static junit.framework.TestCase.assertFalse;
import static junit.framework.TestCase.assertTrue;
import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
@TestPropertySource(locations = "classpath:test.properties")
public class CorrectQuestionValidatorServiceTest {

    @Autowired
    private CorrectQuestionValidatorService service;

    @Test
    public void shouldReturnAnswerIsCorrect() {
        boolean actual = service.isAnswerCorrect(TestEntities.question1, TestEntities.question1answer2);
        assertTrue(actual);
    }

    @Test
    public void shouldReturnAnswerIsIncorrect() {
        boolean actual = service.isAnswerCorrect(TestEntities.question1, TestEntities.question1answer1);
        assertFalse(actual);
    }

    @Test
    public void shouldReturnProperValueOfCorrectAnswers() {
        List<BooleanWrapper> list = new ArrayList<>();

        list.add(new BooleanWrapper(true));
        list.add(new BooleanWrapper(true));
        list.add(new BooleanWrapper(false));
        list.add(new BooleanWrapper(true));
        list.add(new BooleanWrapper(false));

        long actual = service.getNumberOfCorrectAnswers(list);
        assertThat(actual).isEqualTo(3);
    }
}
package com.powermilk.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.powermilk.TestEntities;
import com.powermilk.model.Question;
import com.powermilk.repository.QuestionRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.TestPropertySource;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@SpringBootTest
@TestPropertySource(locations = "classpath:test.properties")
@RunWith(MockitoJUnitRunner.class)
public class QuestionControllerTest {

    @Mock
    private QuestionRepository repository;

    @InjectMocks
    private QuestionController controller;

    private static boolean sameElements(List firstList, List secondList) {
        if (firstList.size() != secondList.size()) {
            return false;
        }

        if (!firstList.isEmpty()) {
            for (Object aFirstList : firstList) {
                if (!secondList.contains(aFirstList)) {
                    return false;
                }
            }
        }

        return true;
    }

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        JacksonTester.initFields(this, new ObjectMapper());
    }

    @Test
    public void shouldGetAllQuestions() {
        Mockito.when(repository.findAll()).thenReturn(TestEntities.question_List);
        final List<Question> questionList = controller.getAllQuestions();
        Mockito.verify(repository).findAll();
        assertEquals(TestEntities.question_List.size(), questionList.size());
        assertTrue(sameElements(TestEntities.question_List, questionList));
    }

    @Test
    public void shouldGetAllQuestionsEmpty() {
        final List<Question> emptyList = new ArrayList<>();

        Mockito.when(repository.findAll()).thenReturn(emptyList);
        final List<Question> questionList = controller.getAllQuestions();
        Mockito.verify(repository).findAll();

        assertEquals(emptyList.size(), questionList.size());
        assertTrue(sameElements(emptyList, questionList));
        assertTrue(questionList.isEmpty());
    }

    @Test
    public void shouldGetOnlyOneQuestionById() {
        Mockito.when(repository.findById(ArgumentMatchers.anyLong())).thenReturn(
                Optional.of(TestEntities.question1)
        );
        final ResponseEntity<Question> question = controller.getQuestionById(1L);
        Mockito.verify(repository).findById(1L);
        assertEquals(TestEntities.question1.getId(), question.getBody().getId());
    }

    @Test
    public void shouldReturnNotFoundForEmptyQuestionById() {
        Mockito.when(repository.findById(ArgumentMatchers.anyLong())).thenReturn(Optional.empty());

        final ResponseEntity<Question> actual = controller.getQuestionById(1L);
        Mockito.verify(repository).findById(1L);
        assertEquals(ResponseEntity.notFound().build(), actual);
    }

    @Test
    public void shouldDeleteQuestion() {
        Mockito.when(repository.findById(ArgumentMatchers.anyLong())).thenReturn(
                Optional.of(TestEntities.questionWithId1)
        );

        Mockito.doNothing().when(repository).delete(
                TestEntities.questionWithId1
        );

        ResponseEntity<Question> actual = controller.deleteQuestion(1L);

        Mockito.verify(repository).delete(TestEntities.questionWithId1);
        assertEquals(ResponseEntity.ok().build(), actual);
    }

    @Test
    public void shouldNotDeleteQuestionWhenGivenEmpty() {
        Mockito.when(repository.findById(ArgumentMatchers.anyLong())).thenReturn(Optional.empty());
        ResponseEntity<Question> actual = controller.deleteQuestion(1L);

        assertEquals(ResponseEntity.notFound().build(), actual);
    }

    @Test
    public void shouldSaveQuestion() {
        Mockito.when(repository.save(TestEntities.question1)).thenReturn(TestEntities.question1);
        Question actual = controller.createQuestion(TestEntities.question1);
        Mockito.verify(repository).save(TestEntities.question1);

        assertEquals(repository.save(TestEntities.question1), actual);
    }

    @Test
    public void shouldUpdateQuestion() {
        Mockito.when(repository.findById(ArgumentMatchers.anyLong())).thenReturn(
                Optional.of(TestEntities.question1)
        );

        ResponseEntity<Question> actual = controller.updateQuestion(1L, TestEntities.question2);
        Mockito.verify(repository).findById(1L);

        assertEquals(ResponseEntity.ok().build(), actual);
    }

    @Test
    public void shouldNotUpdateQuestionWhenGivenEmpty() {
        Mockito.when(repository.findById(ArgumentMatchers.anyLong())).thenReturn(Optional.empty());

        ResponseEntity<Question> actual = controller.updateQuestion(1L, TestEntities.question2);
        Mockito.verify(repository).findById(1L);

        assertEquals(ResponseEntity.notFound().build(), actual);
    }
}

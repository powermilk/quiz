package com.powermilk.controller;

import com.google.gson.Gson;
import com.powermilk.TestEntities;
import com.powermilk.model.Question;
import com.powermilk.repository.QuestionRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.times;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class QuestionControllerSpringBootTest {

    @MockBean
    private QuestionRepository repository;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void shouldFindAllQuestions() {
        Mockito.when(repository.findAll()).thenReturn(TestEntities.questions_with_id_list);

        Iterable response = restTemplate.getForObject("/v1/api/questions/", Iterable.class);

        assertThat(response).isNotNull().isNotEmpty();
        assertThat(response).hasSize(3);
        assertThat(response).isEqualTo((TestEntities.questions_with_id_list));

        Mockito.verify(repository, times(1)).findAll();
        Mockito.verifyNoMoreInteractions(repository);
    }

    @Test
    public void shouldFindAllQuestionsWhenGivenEmptyList() {
        Mockito.when(repository.findAll()).thenReturn(new ArrayList<>());

        Iterable response = restTemplate.getForObject("/v1/api/questions/", Iterable.class);
        assertThat(response).isEmpty();
    }

    @Test
    public void shouldFindOnlyOneQuestionById() {
        Mockito.when(repository.findById(1L)).thenReturn(Optional.of(TestEntities.questionWithId1));

        ResponseEntity<Question> response = restTemplate.getForEntity("/v1/api/questions/1", Question.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo((TestEntities.questionWithId1));

        Mockito.verify(repository, times(1)).findById(1L);
        Mockito.verifyNoMoreInteractions(repository);
    }

    @Test
    public void shouldReturnNotFoundQuestionWhenGivenIncorrectId() {
        Mockito.when(repository.findById(1L)).thenReturn(Optional.empty());

        ResponseEntity<Question> response = restTemplate.getForEntity("/v1/api/questions/1", Question.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);

        Mockito.verify(repository, times(1)).findById(1L);
        Mockito.verifyNoMoreInteractions(repository);
    }

    @Test
    public void shouldCreateQuestion() {
        Mockito.when(repository.existsById(TestEntities.questionWithId1.getId())).thenReturn(false);
        Mockito.when(repository.save(TestEntities.questionWithId1)).thenReturn(TestEntities.questionWithId1);

        ResponseEntity<Question> response = restTemplate.postForEntity("/v1/api/questions/",
                TestEntities.questionWithId1, Question.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(response.getBody()).isEqualTo((TestEntities.questionWithId1));

        Mockito.verify(repository, times(1)).existsById(1L);
        Mockito.verify(repository, times(1)).save(TestEntities.questionWithId1);
        Mockito.verifyNoMoreInteractions(repository);
    }

    @Test
    public void shouldNotCreateQuestionWhenExists() {
        Mockito.when(repository.existsById(TestEntities.questionWithId1.getId())).thenReturn(true);

        ResponseEntity<Question> response = restTemplate.postForEntity("/v1/api/questions/",
                TestEntities.questionWithId1, Question.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CONFLICT);

        Mockito.verify(repository, times(1)).existsById(1L);
        Mockito.verifyNoMoreInteractions(repository);
    }

    @Test
    public void shouldUpdateQuestion() {
        Mockito.when(repository.findById(TestEntities.questionWithId1.getId()))
                .thenReturn(Optional.of(TestEntities.questionWithId1));
        Mockito.when(repository.save(TestEntities.questionWithId1)).thenReturn(TestEntities.questionWithId1);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        headers.add("Accept", "*/*");

        String json = new Gson().toJson(TestEntities.questionWithId1);

        HttpEntity<String> requestEntity = new HttpEntity<>(json, headers);

        ResponseEntity<Question> response = restTemplate
                .exchange("/v1/api/questions/1", HttpMethod.PUT, requestEntity, Question.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

        Mockito.verify(repository, times(1)).findById(1L);
        Mockito.verify(repository, times(1)).save(TestEntities.questionWithId1);
        Mockito.verifyNoMoreInteractions(repository);
    }

    @Test
    public void shouldNotUpdateQuestionWhenGivenInvalidID() {
        Mockito.when(repository.findById(TestEntities.questionWithId1.getId()))
                .thenReturn(Optional.empty());

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        headers.add("Accept", "*/*");

        String json = new Gson().toJson(TestEntities.questionWithId1);

        HttpEntity<String> requestEntity = new HttpEntity<>(json, headers);

        ResponseEntity<Question> response = restTemplate
                .exchange("/v1/api/questions/1", HttpMethod.PUT, requestEntity, Question.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);

        Mockito.verify(repository, times(1)).findById(1L);
        Mockito.verifyNoMoreInteractions(repository);
    }

    @Test
    public void shouldDeleteQuestion() {
        Mockito.when(repository.findById(TestEntities.questionWithId1.getId()))
                .thenReturn(Optional.of(TestEntities.questionWithId1));
        Mockito.doNothing().when(repository).delete(TestEntities.questionWithId1);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        headers.add("Accept", "*/*");

        String json = new Gson().toJson(TestEntities.questionWithId1);

        HttpEntity<String> requestEntity = new HttpEntity<>(json, headers);

        ResponseEntity<Question> response = restTemplate
                .exchange("/v1/api/questions/1", HttpMethod.DELETE, requestEntity, Question.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

        Mockito.verify(repository, times(1)).findById(1L);
        Mockito.verify(repository, times(1)).delete(TestEntities.questionWithId1);
        Mockito.verifyNoMoreInteractions(repository);
    }

    @Test
    public void shouldNotDeleteQuestionWhenGivenInvalidID() {
        Mockito.when(repository.findById(TestEntities.questionWithId1.getId())).thenReturn(Optional.empty());

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        headers.add("Accept", "*/*");

        String json = new Gson().toJson(TestEntities.questionWithId1);

        HttpEntity<String> requestEntity = new HttpEntity<>(json, headers);

        ResponseEntity<Question> response = restTemplate
                .exchange("/v1/api/questions/1", HttpMethod.DELETE, requestEntity, Question.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);

        Mockito.verify(repository, times(1)).findById(1L);
        Mockito.verifyNoMoreInteractions(repository);
    }
}
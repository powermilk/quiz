package com.powermilk.controller;

import com.google.gson.Gson;
import com.powermilk.TestEntities;
import com.powermilk.model.Question;
import com.powermilk.repository.QuestionRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.Optional;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.times;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(Question.class)
@RunWith(SpringRunner.class)
public class QuestionControllerContextTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private QuestionRepository repository;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void shouldFindAllQuestions() throws Exception {
        Mockito.when(repository.findAll()).thenReturn(TestEntities.questions_with_id_list);

        mockMvc.perform(get("/v1/api/questions", 1L))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].questionContent",
                        is(TestEntities.questionWithId1.getQuestionContent())))
                .andExpect(jsonPath("$[1].id", is(2)))
                .andExpect(jsonPath("$[1].questionContent",
                        is(TestEntities.questionWithId2.getQuestionContent())));

        Mockito.verify(repository, times(1)).findAll();
        Mockito.verifyNoMoreInteractions(repository);
    }

    @Test
    public void shouldFindAllQuestionsWhenGivenEmptyList() throws Exception {
        Mockito.when(repository.findAll()).thenReturn(new ArrayList<>());

        mockMvc.perform(get("/v1/api/questions", 1L))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$", hasSize(0)));

        Mockito.verify(repository, times(1)).findAll();
        Mockito.verifyNoMoreInteractions(repository);
    }

    @Test
    public void shouldFindOnlyOneQuestionById() throws Exception {
        Mockito.when(repository.findById(1L)).thenReturn(Optional.of(TestEntities.questionWithId1));

        mockMvc.perform(get("/v1/api/questions/{id}", 1L))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.questionContent", is(TestEntities.questionWithId1.getQuestionContent())));

        Mockito.verify(repository, times(1)).findById(1L);
        Mockito.verifyNoMoreInteractions(repository);
    }

    @Test
    public void shouldReturnNotFoundQuestionWhenGivenIncorrectId() throws Exception {
        Mockito.when(repository.findById(1L)).thenReturn(Optional.empty());

        mockMvc.perform(get("/v1/api/questions/{id}", 1L))
                .andExpect(status().isNotFound());

        Mockito.verify(repository, times(1)).findById(1L);
        Mockito.verifyNoMoreInteractions(repository);
    }

    @Test
    public void shouldCreateQuestion() throws Exception {
        String json = new Gson().toJson(TestEntities.questionWithId1);

        Mockito.when(repository.existsById(TestEntities.questionWithId1.getId())).thenReturn(false);
        Mockito.when(repository.save(TestEntities.questionWithId1)).thenReturn(TestEntities.questionWithId1);

        mockMvc.perform(post("/v1/api/questions")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isCreated())
                .andExpect(header().string("location", containsString("http://localhost/v1/api/questions/")));

        Mockito.verify(repository, times(1)).existsById(1L);
        Mockito.verify(repository, times(1)).save(TestEntities.questionWithId1);
        Mockito.verifyNoMoreInteractions(repository);
    }

    @Test
    public void shouldNotCreateQuestionWhenExists() throws Exception {
        String json = new Gson().toJson(TestEntities.questionWithId1);

        Mockito.when(repository.existsById(TestEntities.questionWithId1.getId())).thenReturn(true);

        mockMvc.perform(post("/v1/api/questions")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isConflict());

        Mockito.verify(repository, times(1)).existsById(1L);
        Mockito.verifyNoMoreInteractions(repository);
    }

    @Test
    public void shouldUpdateQuestion() throws Exception {
        String json = new Gson().toJson(TestEntities.questionWithId1);

        Mockito.when(repository.findById(TestEntities.questionWithId1.getId()))
                .thenReturn(Optional.of(TestEntities.questionWithId1));
        Mockito.when(repository.save(TestEntities.questionWithId1)).thenReturn(TestEntities.questionWithId1);

        mockMvc.perform(put("/v1/api/questions/{id}", TestEntities.questionWithId1.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isOk());

        Mockito.verify(repository, times(1)).findById(1L);
        Mockito.verify(repository, times(1)).save(TestEntities.questionWithId1);
        Mockito.verifyNoMoreInteractions(repository);
    }

    @Test
    public void shouldNotUpdateQuestionWhenGivenInvalidID() throws Exception {
        String json = new Gson().toJson(TestEntities.questionWithId1);

        Mockito.when(repository.findById(TestEntities.questionWithId1.getId()))
                .thenReturn(Optional.empty());

        mockMvc.perform(put("/v1/api/questions/{id}", TestEntities.questionWithId1.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isNotFound());

        Mockito.verify(repository, times(1)).findById(1L);
        Mockito.verifyNoMoreInteractions(repository);
    }

    @Test
    public void shouldDeleteQuestion() throws Exception {
        String json = new Gson().toJson(TestEntities.questionWithId1);

        Mockito.when(repository.findById(TestEntities.questionWithId1.getId()))
                .thenReturn(Optional.of(TestEntities.questionWithId1));
        Mockito.doNothing().when(repository).delete(TestEntities.questionWithId1);

        mockMvc.perform(delete("/v1/api/questions/{id}", TestEntities.questionWithId1.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isOk());

        Mockito.verify(repository, times(1)).findById(1L);
        Mockito.verify(repository, times(1)).delete(TestEntities.questionWithId1);
        Mockito.verifyNoMoreInteractions(repository);
    }

    @Test
    public void shouldNotDeleteQuestionWhenGivenInvalidID() throws Exception {
        String json = new Gson().toJson(TestEntities.questionWithId1);

        Mockito.when(repository.findById(TestEntities.questionWithId1.getId())).thenReturn(Optional.empty());

        mockMvc.perform(delete("/v1/api/questions/{id}", TestEntities.questionWithId1.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isNotFound());

        Mockito.verify(repository, times(1)).findById(1L);
        Mockito.verifyNoMoreInteractions(repository);
    }
}
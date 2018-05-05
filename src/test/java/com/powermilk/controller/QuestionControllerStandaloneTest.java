package com.powermilk.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.powermilk.TestEntities;
import com.powermilk.model.Question;
import com.powermilk.repository.QuestionRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Optional;

import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.times;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(MockitoJUnitRunner.class)
public class QuestionControllerStandaloneTest {

    private static MockMvc mockMvc;

    @Mock
    private QuestionRepository repository;

    @InjectMocks
    private QuestionController controller;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(controller).
                build();
    }

    @Test
    public void shouldReturnQuestionFindbyId() throws Exception {
        Mockito.when(repository.findById(1L)).thenReturn(Optional.of(TestEntities.questionWithId1));

        mockMvc.perform(get("/v1/api/questions/{id}", 1L))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.questionContent", is(TestEntities.questionWithId1.getQuestionContent())));

        Mockito.verify(repository, times(1)).findById(1L);
        Mockito.verifyNoMoreInteractions(repository);
    }
}
package com.powermilk.repository;

import com.powermilk.TestEntities;
import com.powermilk.model.Question;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Objects;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
public class QuestionRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private QuestionRepository repository;

    @Test
    public void shouldReturnEmptyListForEmptyRepository() {
        Iterable<Question> questions = repository.findAll();

        assertThat(questions).isEmpty();
    }

    @Test
    public void shouldReturnAllEntities() {
        Question question1 = new Question(TestEntities.questionContent1, TestEntities.answerMap1);
        Question question2 = new Question(TestEntities.questionContent2, TestEntities.answerMap2);

        entityManager.persist(question1);
        entityManager.persist(question2);

        Iterable<Question> questions = repository.findAll();

        assertThat(questions).hasSize(2).contains(question1, question2);
    }

    @Test
    public void shouldFindQuestionById() {
        Question question1 = new Question(TestEntities.questionContent1, TestEntities.answerMap1);
        Question question2 = new Question(TestEntities.questionContent2, TestEntities.answerMap2);

        entityManager.persist(question1);
        entityManager.persist(question2);

        Question result = repository.findById(question1.getId()).orElse(null);

        assertThat(result).isEqualTo(question1);
    }

    @Test
    public void shouldUpdateQuestion() {
        Question question1 = new Question(TestEntities.questionContent1, TestEntities.answerMap1);
        Question question2 = new Question(TestEntities.questionContent2, TestEntities.answerMap2);

        entityManager.persist(question1);

        Question result = repository.findById(question1.getId()).orElse(null);
        Objects.requireNonNull(result).setQuestionContent(question2.getQuestionContent());
        result.setAnswerOptions(question2.getAnswerOptions());

        assertThat(result.getId()).isEqualTo(question1.getId());
        assertThat(result.getQuestionContent()).isEqualTo(question2.getQuestionContent());
        assertThat(result.getAnswerOptions()).isEqualTo(question2.getAnswerOptions());
    }

    @Test
    public void shouldNotFoundQuestion() {
        Question question1 = new Question(1L, TestEntities.questionContent1, TestEntities.answerMap1);

        Question result = repository.findById(question1.getId()).orElse(null);

        assertThat(result).isEqualTo(null);
    }

    @Test
    public void shouldReturnStoredParameters() {
        Question question1 = new Question(TestEntities.questionContent1, TestEntities.answerMap1);

        Question result = repository.save(question1);

        assertThat(result).hasFieldOrPropertyWithValue("questionContent", TestEntities.questionContent1);
    }

    @Test
    public void shouldDeleteAllEntities() {
        Question question1 = new Question(TestEntities.questionContent1, TestEntities.answerMap1);
        Question question2 = new Question(TestEntities.questionContent1, TestEntities.answerMap1);

        entityManager.persist(question1);
        entityManager.persist(question2);

        Iterable<Question> questions = repository.findAll();

        assertThat(questions).hasSize(2).contains(question1, question2);

        repository.deleteAll();

        assertThat(repository.findAll()).isEmpty();
    }
}
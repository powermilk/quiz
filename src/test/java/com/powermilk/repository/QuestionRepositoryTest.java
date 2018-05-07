package com.powermilk.repository;

import com.powermilk.TestEntities;
import com.powermilk.model.Question;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@Transactional
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
        entityManager.persist(TestEntities.question1);
        entityManager.persist(TestEntities.question2);

        Iterable<Question> questions = repository.findAll();

        assertThat(questions)
                .isNotNull()
                .isNotEmpty()
                .hasSize(2)
                .contains(TestEntities.question1)
                .contains(TestEntities.question2);
    }

    @Test
    public void shouldFindQuestionById() {
        entityManager.merge(TestEntities.question1);

        Optional<Question> result = repository.findById(TestEntities.questionWithId1.getId());

        assertThat(result).isEqualTo(Optional.of(TestEntities.question1));
    }

    @Test
    public void shouldNotFoundQuestion() {
        Optional<Question> result = repository.findById(TestEntities.questionWithId1.getId());

        assertThat(result).isEqualTo(Optional.empty());
    }

    @Test
    public void shouldReturnStoredParameters() {
        Question result = repository.save(TestEntities.questionWithId1);

        assertThat(result).hasFieldOrPropertyWithValue("id", TestEntities.questionWithId1.getId());
        assertThat(result).hasFieldOrPropertyWithValue("questionContent",
                TestEntities.questionWithId1.getQuestionContent());
    }
}
package com.powermilk.controller;

import com.powermilk.model.Question;
import com.powermilk.repository.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/v1/api")
public class QuestionController {
    @Autowired
    private QuestionRepository repository;

    @GetMapping("/questions")
    public List<Question> getAllQuestions() {
        return repository.findAll();
    }

    @GetMapping("/questions/{id}")
    public ResponseEntity<Question> getQuestionById(@PathVariable(value = "id") Long id) {
        Optional<Question> question = repository.findById(id);
        return question.map(question1 -> ResponseEntity.ok().body(question1)).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/questions")
    public Question createQuestion(@Valid @RequestBody Question question) {
        return repository.save(question);
    }

    @PutMapping("/questions/{id}")
    public ResponseEntity<Question> updateQuestion(@PathVariable(value = "id") Long id,
                                              @Valid @RequestBody Question newQuestionData) {
        Optional<Question> questionOptional = repository.findById(id);

        if (questionOptional.isPresent()) {
            Question question = questionOptional.get();

            question.setQuestionContent(newQuestionData.getQuestionContent());
            question.setAnswerOptions(newQuestionData.getAnswerOptions());

            Question updatedQuestion = repository.save(question);
            return ResponseEntity.ok(updatedQuestion);
        }

        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/questions/{id}")
    public ResponseEntity<Question> deleteQuestion(@PathVariable(value = "id") Long id) {
        Optional<Question> question = repository.findById(id);

        if(question.isPresent()) {
            repository.delete(question.get());
            return ResponseEntity.ok().build();
        }

        return ResponseEntity.notFound().build();
    }
}

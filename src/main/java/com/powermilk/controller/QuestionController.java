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
    QuestionRepository repository;

    @GetMapping("/questions")
    public List<Question> getAllQuestions() {
        return repository.findAll();
    }

    @GetMapping("/questions/{id}")
    public ResponseEntity<Question> getQuestionById(@PathVariable(value = "id") Long id) {
        Question question = repository.findById(id).orElse(null);
        if (question == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().body(question);
    }

    @PostMapping("/questions/{id}")
    public Question createQuestion(@Valid @RequestBody Question question) {
        return repository.save(question);
    }

    @PutMapping("/questions/{id}")
    public ResponseEntity<Question> updateQuestion(@PathVariable(value = "id") Long id,
                                              @Valid @RequestBody Question newQuestionData) {
        Question question = repository.findById(id).orElse(null);
        if (question == null) {
            return ResponseEntity.notFound().build();
        }

        question.setQuestionContent(newQuestionData.getQuestionContent());
        question.setAnswerOptions(newQuestionData.getAnswerOptions());

        Question updatedQuestion = repository.save(question);
        return ResponseEntity.ok(updatedQuestion);
    }

    @DeleteMapping("/questions/{id}")
    public ResponseEntity<Question> deleteQuestion(@PathVariable(value = "id") Long id) {
        Optional<Question> question = repository.findById(id);
        if(question == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().body(question.get());
    }
}

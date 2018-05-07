package com.powermilk.controller;

import com.powermilk.model.Question;
import com.powermilk.repository.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.web.util.UriComponents;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/v1/api")
class QuestionController {
    @Autowired
    private QuestionRepository repository;

    @GetMapping("/questions")
    List<Question> getAllQuestions() {
        return repository.findAll();
    }

    @GetMapping("/questions/{id}")
    ResponseEntity<Question> getQuestionById(@PathVariable(value = "id") Long id) {
        Optional<Question> question = repository.findById(id);
        return question.map(q -> ResponseEntity.ok().body(q)).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/questions")
    ResponseEntity<Question> createQuestion(@Valid @RequestBody Question question) {
        if (repository.existsById(question.getId())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(question);
        }

        repository.save(question);

        final UriComponents location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/v1/api/questions/{id}")
                .buildAndExpand(question.getId());

        return ResponseEntity.created(location.toUri()).body(question);
    }

    @PutMapping("/questions/{id}")
    ResponseEntity<Question> updateQuestion(@PathVariable(value = "id") Long id,
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
    ResponseEntity<Question> deleteQuestion(@PathVariable(value = "id") Long id) {
        Optional<Question> question = repository.findById(id);

        if (question.isPresent()) {
            repository.delete(question.get());
            return ResponseEntity.ok().build();
        }

        return ResponseEntity.notFound().build();
    }
}

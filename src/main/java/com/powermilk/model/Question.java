package com.powermilk.model;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.Map;

@Entity
@DynamicUpdate
public class Question implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @NotBlank
    private String questionContent;
    @ElementCollection
    @CollectionTable(name = "answer_map", joinColumns = @JoinColumn(name = "id"))
    @MapKeyColumn(name = "answer_content")
    private Map<String, BooleanWrapper> answerOptions;

    public Question() {
    }

    public Question(Long id, @NotBlank String questionContent, Map<String, BooleanWrapper> answerOptions) {
        this.id = id;
        this.questionContent = questionContent;
        this.answerOptions = answerOptions;
    }

    public Question(@NotBlank String questionContent, Map<String, BooleanWrapper> answerOptions) {
        this.questionContent = questionContent;
        this.answerOptions = answerOptions;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getQuestionContent() {
        return questionContent;
    }

    public void setQuestionContent(String questionContent) {
        this.questionContent = questionContent;
    }

    public Map<String, BooleanWrapper> getAnswerOptions() {
        return answerOptions;
    }

    public void setAnswerOptions(Map<String, BooleanWrapper> answerOptions) {
        this.answerOptions = answerOptions;
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .append(id)
                .append(questionContent)
                .append(answerOptions)
                .build();
    }

    @org.jetbrains.annotations.Contract(value = "null -> false", pure = true)
    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }

        if (obj == null) {
            return false;
        }

        if (getClass() != obj.getClass()) {
            return false;
        }

        final Question question = (Question) obj;
        return new EqualsBuilder()
                .append(this.id, question.id)
                .append(this.questionContent, question.questionContent)
                .append(this.answerOptions, question.answerOptions)
                .build();
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}

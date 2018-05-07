package com.powermilk;

import com.powermilk.model.BooleanWrapper;
import com.powermilk.model.Question;

import java.util.*;

public class TestEntities {
    public static final String questionContent1 = "Which city is capital of Norway?";
    private static final String questionContent2 = "Name a seventh planet from Sun";

    public static final String question1answer1 = "Bergen";
    public static final String question1answer2 = "Oslo";
    private static final String question1answer3 = "Arendal";
    private static final String question1answer4 = "Stavanger";

    private static final String question2answer1 = "Jupiter";
    private static final String question2answer2 = "Venus";
    private static final String question2answer3 = "Earth";
    private static final String question2answer4 = "Uranus";

    private static final BooleanWrapper correctAnswer = new BooleanWrapper(true);
    private static final BooleanWrapper incorrectAnswer = new BooleanWrapper(false);

    private static final Map<String, BooleanWrapper> answerMap1 = new HashMap<String, BooleanWrapper>() {
        {
            put(question1answer1, incorrectAnswer);
            put(question1answer2, correctAnswer);
            put(question1answer3, incorrectAnswer);
            put(question1answer4, incorrectAnswer);
        }
    };
    private static final Map<String, BooleanWrapper> answerMap2 = new HashMap<String, BooleanWrapper>() {
        {
            put(question2answer1, incorrectAnswer);
            put(question2answer2, incorrectAnswer);
            put(question2answer3, incorrectAnswer);
            put(question2answer4, correctAnswer);
        }
    };

    public static final Question question1 = new Question(questionContent1, answerMap1);
    public static final Question question2 = new Question(questionContent2, answerMap2);

    public static final Question questionWithId1 = new Question(1L, questionContent1, answerMap1);
    public static final Question questionWithId2 = new Question(2L, questionContent2, answerMap2);

    public static final List<Question> questions_list = new ArrayList<>(Arrays.asList(question1, question2));
    public static final List<Question> questions_with_id_list = new ArrayList<>(Arrays.asList(questionWithId1, questionWithId2));
}

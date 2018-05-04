package com.powermilk;

import com.powermilk.model.BooleanWrapper;
import com.powermilk.model.Question;

import java.util.HashMap;
import java.util.Map;

public class TestEntities {
    private static final BooleanWrapper correctAnwser = new BooleanWrapper(true);
    private static final BooleanWrapper incorrectAnwser = new BooleanWrapper(false);

    public static final String questionContent1 = "Which city is capital of Norway?";
    public static final String questionContent2 = "Name a seventh planet from Sun";

    public static final String question1answer1 = "Bergen";
    public static final String question1answer2 = "Oslo";
    private static final String question1answer3 = "Arendal";
    private static final String question1answer4 = "Stavanger";

    private static final String question2answer1 = "Jupiter";
    private static final String question2answer2 = "Venus";
    private static final String question2answer3 = "Earth";
    private static final String question2answer4 = "Uranus";

    public static final Map<String, BooleanWrapper> answerMap1 = new HashMap<String, BooleanWrapper>() {
        {
            put(question1answer1, incorrectAnwser);
            put(question1answer2, correctAnwser);
            put(question1answer3, incorrectAnwser);
            put(question1answer4, incorrectAnwser);
        }
    };
    public static final Map<String, BooleanWrapper> answerMap2 = new HashMap<String, BooleanWrapper>() {
        {
            put(question2answer1, incorrectAnwser);
            put(question2answer2, incorrectAnwser);
            put(question2answer3, incorrectAnwser);
            put(question2answer4, correctAnwser);
        }
    };

    public static final Question question1 = new Question(questionContent1, answerMap1);
    public static final Question question2 = new Question(questionContent2, answerMap2);
}

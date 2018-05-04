package com.powermilk.service;

import com.powermilk.model.BooleanWrapper;
import com.powermilk.model.Question;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class CorrectQuestionValidatorService {
    public boolean isAnswerCorrect(Question question, String answer) {

        Map<String, BooleanWrapper> map = question.getAnswerOptions();

        for(Map.Entry<String, BooleanWrapper> entry : map.entrySet()) {
            if(entry.getKey().equals(answer)) {
                return entry.getValue().getBooleanValue();
            }
        }
        return false;
    }
}

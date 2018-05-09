package com.powermilk.service;

import com.powermilk.model.BooleanWrapper;
import com.powermilk.model.Question;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
class CorrectQuestionValidatorService {
    boolean isAnswerCorrect(Question question, String answer) {
        Map<String, BooleanWrapper> map = question.getAnswerOptions();

        for (Map.Entry<String, BooleanWrapper> entry : map.entrySet()) {
            if (entry.getKey().equals(answer)) {
                return entry.getValue().getBooleanValue();
            }
        }
        return false;
    }

    long getNumberOfCorrectAnswers(List<BooleanWrapper> list) {
        return list.stream().filter(x -> x.getBooleanValue()).count();
    }


}

package pl.mmichonski.domain.wrappers;

import pl.mmichonski.domain.Answer;
import pl.mmichonski.domain.Question;

import java.util.List;

public class TestCollections {
    private List<Question> questions;
    private List<Long> answersId;

    public List<Question> getQuestions() {
        return questions;
    }

    public void setQuestions(List<Question> questions) {
        this.questions = questions;
    }

    public List<Long> getAnswersId() {
        return answersId;
    }

    public void setAnswersId(List<Long> answersId) {
        this.answersId = answersId;
    }


    @Override
    public String toString() {
        return "TestCollections{" +
                "questions=" + questions +
                ", answersId=" + answersId +
                '}';
    }
}

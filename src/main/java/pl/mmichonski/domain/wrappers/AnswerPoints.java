package pl.mmichonski.domain.wrappers;

import pl.mmichonski.domain.Answer;

public class AnswerPoints {
    private Answer answer;
    private Integer points;

    public AnswerPoints() {
    }

    public AnswerPoints(Answer answer, Integer points) {
        this.answer = answer;
        this.points = points;
    }

    public Answer getAnswer() {
        return answer;
    }

    public void setAnswer(Answer answer) {
        this.answer = answer;
    }

    public Integer getPoints() {
        return points;
    }

    public void setPoints(Integer points) {
        this.points = points;
    }

    @Override
    public String toString() {
        return "AnswerPoints{" +
                "answer=" + answer +
                ", points=" + points +
                '}';
    }
}

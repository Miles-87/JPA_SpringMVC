package pl.mmichonski.domain.wrappers;

import pl.mmichonski.domain.Question;

public class Test {
    private Question question;
    private Integer answerId;

    public Test() {
    }

    public Test(Question question, Integer answerId) {
        this.question = question;
        this.answerId = answerId;
    }

    public Question getQuestion() {
        return question;
    }

    public void setQuestion(Question question) {
        this.question = question;
    }

    public Integer getAnswerId() {
        return answerId;
    }

    public void setAnswerId(Integer answerId) {
        this.answerId = answerId;
    }
}

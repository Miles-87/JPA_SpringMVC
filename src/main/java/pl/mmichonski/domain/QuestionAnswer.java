package pl.mmichonski.domain;

import javax.persistence.*;

/**
 * Created by Mateusz on 2017-06-26.
 */
@Entity
public class QuestionAnswer {
    @Id
    @GeneratedValue
    private Long id;
    private Integer points;
    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "question_id")
    private Question question;
    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "answer_id")
    private Answer answer;

    public QuestionAnswer(){}

    public QuestionAnswer(Integer points, Question question, Answer answer) {
        this.points = points;
        this.question = question;
        this.answer = answer;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getPoints() {
        return points;
    }

    public void setPoints(Integer points) {
        this.points = points;
    }

    public Question getQuestion() {
        return question;
    }

    public void setQuestion(Question question) {
        this.question = question;
    }

    public Answer getAnswer() {
        return answer;
    }

    public void setAnswer(Answer answer) {
        this.answer = answer;
    }
}

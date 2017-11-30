package pl.mmichonski.domain;

import javax.persistence.*;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * Created by Mateusz on 2017-06-26.
 */
@Entity
public class Answer {
    @Id
    @GeneratedValue
    private Long id;
    private String text;
    @OneToMany(cascade = CascadeType.PERSIST, mappedBy = "answer")
    private Set<QuestionAnswer> questionAnswers = new LinkedHashSet<>();

    public Answer(){}

    public Answer(String text) {
        this.text = text;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Set<QuestionAnswer> getQuestionAnswers() {
        return questionAnswers;
    }

    public void setQuestionAnswers(Set<QuestionAnswer> questionAnswers) {
        this.questionAnswers = questionAnswers;
    }

    @Override
    public String toString() {
        return "Answer{" +
                "id=" + id +
                ", text='" + text + '\'' +
                '}';
    }
}

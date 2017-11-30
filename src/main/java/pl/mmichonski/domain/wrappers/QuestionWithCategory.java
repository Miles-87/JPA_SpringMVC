package pl.mmichonski.domain.wrappers;

import pl.mmichonski.domain.Answer;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class QuestionWithCategory {
    private String text;
    private Long categoryId;
    private List<AnswerPoints> answerPoints = new ArrayList<>();

    public QuestionWithCategory() {
    }

    public QuestionWithCategory(String text, Long categoryId, List<AnswerPoints> answerPoints) {
        this.text = text;
        this.categoryId = categoryId;
        this.answerPoints = answerPoints;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public List<AnswerPoints> getAnswerPoints() {
        return answerPoints;
    }

    public void setAnswerPoints(List<AnswerPoints> answerPoints) {
        this.answerPoints = answerPoints;
    }

    @Override
    public String toString() {
        return "QuestionWithCategory{" +
                "text='" + text + '\'' +
                ", categoryId=" + categoryId +
                ", answerPoints=" + answerPoints +
                '}';
    }

    public boolean checkPoints()
    {
        return answerPoints.stream().map(AnswerPoints::getPoints).distinct().count() == answerPoints.size();
    }
}

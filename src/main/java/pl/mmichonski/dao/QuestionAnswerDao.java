package pl.mmichonski.dao;

import pl.mmichonski.domain.Answer;
import pl.mmichonski.domain.Category;
import pl.mmichonski.domain.Question;
import pl.mmichonski.domain.QuestionAnswer;

import java.util.List;

public interface QuestionAnswerDao {
    void addQuestionAnserCategoryPoints(Question question, Answer answer, Integer points);
    void delete(Long id);
    List<QuestionAnswer> getAll();
    int getPointsForQuestionAnswer(Long questionId, Long answerId);
}

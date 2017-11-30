package pl.mmichonski.dao;

import pl.mmichonski.domain.Question;

import java.util.List;
import java.util.Optional;

/**
 * Created by Mateusz on 2017-07-02.
 */
public interface QuestionDao {
    void addQuestion(Question answer);
    void updateQuestion(Question answer);
    void deleteQuestion(Long id);
    List<Question> getAllQuestionsWithCategoriesWithoutQuestionsAnswers();
    Optional<Question> getQuestionByIdWithCategoryWithoutQuestionsAnswers(Long id);
}

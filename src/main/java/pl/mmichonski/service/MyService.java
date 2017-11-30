package pl.mmichonski.service;

import pl.mmichonski.domain.Answer;
import pl.mmichonski.domain.Category;
import pl.mmichonski.domain.Question;
import pl.mmichonski.domain.QuestionAnswer;
import pl.mmichonski.domain.wrappers.QuestionWithCategory;

import java.util.List;
import java.util.Optional;

public interface MyService {
    //--------------------------- ANSWERS -------------------------------
    void addAnswer(Answer answer);
    void updateAnswer(Answer answer);
    void deleteAnswer(Long answerId);
    Optional<Answer> getAnswerById(Long answerId);
    List<Answer> getAllAnswers();

    //--------------------------- CATEGORY -------------------------------
    void addCategory(Category category);
    void updateCategory(Category category);
    void deleteCategory(Long categoryId);
    Optional<Category> getCategoryByIdWithQuestions(Long categoryId);
    Optional<Category> getCategoryByIdWithoutQuestions(Long categoryId);
    List<Category> getAllCategories();

    //--------------------------- QUESTIONS -------------------------------
    void addQuestion(String text, Long categoryId);
    void updateQuestionText(Question question, String text);
    void updateQuestionCategory(Question question, String category);
    List<Question> getAllQuestions();

    //------------ QUESTIONS WITH CATEGORIES ANSWERS AND POINTS -----------
    void addQuestionWithCategoryAndAnswers(QuestionWithCategory qc);
    void deleteQuestionWithCategoryAndAnswers(Long id);
    List<QuestionAnswer> getAllQuestionAnswers();
    int getPointsForQuestionAnswer(Long questionId, Long answerId);
}

package pl.mmichonski.dao;

import pl.mmichonski.domain.Answer;

import java.util.List;
import java.util.Optional;

/**
 * Created by Mateusz on 2017-06-28.
 */
public interface AnswerDao {
    void addAnswer(Answer answer);
    void updateAnswer(Answer answer);
    void deleteAnswer(Long id);
    List<Answer> getAllAnswers();
    Optional<Answer> getAnswerById(Long id);
    Answer getAnswerByText(String text);
}

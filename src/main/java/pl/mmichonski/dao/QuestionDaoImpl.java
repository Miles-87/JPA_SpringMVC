package pl.mmichonski.dao;

import org.springframework.stereotype.Repository;
import pl.mmichonski.domain.Answer;
import pl.mmichonski.domain.Question;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

/**
 * Created by Mateusz on 2017-07-02.
 */

@Repository
public class QuestionDaoImpl implements QuestionDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void addQuestion(Question question) {
        entityManager.persist(question);
    }

    @Override
    public void updateQuestion(Question question) {
        Question q = entityManager.find(Question.class, question.getId());
        q.setText(question.getText());
        q.setCategory(question.getCategory());
    }

    @Override
    public void deleteQuestion(Long id) {
        //pobierz obiekt ktory chesz usunac
        Question q = entityManager.find(Question.class, id);
        if (q != null) {
            entityManager.remove(q);
        }
    }

    @Override
    public List<Question> getAllQuestionsWithCategoriesWithoutQuestionsAnswers() {

        Query query = entityManager.createQuery("select q from Question q");
        return query.getResultList();
    }

    @Override
    public Optional<Question> getQuestionByIdWithCategoryWithoutQuestionsAnswers(Long id) {

        Question q = entityManager.find(Question.class, id);
        return Optional.empty().ofNullable(q);
    }
}

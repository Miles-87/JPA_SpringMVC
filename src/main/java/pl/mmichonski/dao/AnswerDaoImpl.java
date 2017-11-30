package pl.mmichonski.dao;

import org.springframework.stereotype.Repository;
import pl.mmichonski.domain.Answer;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

/**
 * Created by Mateusz on 2017-06-28.
 */

@Repository
public class AnswerDaoImpl implements AnswerDao {

    @PersistenceContext
    private EntityManager entityManager;


    @Override
    public void addAnswer(Answer answer) {
        entityManager.persist(answer);
    }

    @Override
    public void updateAnswer(Answer answer) {
        //zeby zaktualizowac obiekt musisz najpierw pobrac z bazy obiekt
        //o id, ktore ma obiekt przekazany jako argument a potem zaktualizowac mu
        //wszystkie pola
        Answer answerFromDb = entityManager.find(Answer.class, answer.getId());
        if (answerFromDb != null) {
            answerFromDb.setText(answer.getText());
            answerFromDb.setQuestionAnswers(answer.getQuestionAnswers());
        }
    }

    @Override
    public void deleteAnswer(Long id) {
        //najpierw pobierz obiekt ktory chcesz usunac
        Answer answerFromDb = entityManager.find(Answer.class, id);
        entityManager.remove(answerFromDb);

    }

    @SuppressWarnings("JpaQlInspection")
    @Override
    public List<Answer> getAllAnswers() {
        Query query = entityManager.createQuery("select a from Answer a");
        return query.getResultList();
    }

    @Override
    public Optional<Answer> getAnswerById(Long id) {

        return Optional.ofNullable(entityManager.find(Answer.class, id));
    }

    @Override
    public Answer getAnswerByText(String text) {
        Query query = entityManager.createQuery("select a from Answer a where a.text = :text");
        query.setParameter("text", text);
        Answer answer = (Answer)query.getSingleResult();
        return answer;
    }


}

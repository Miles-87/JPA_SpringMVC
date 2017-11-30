package pl.mmichonski.dao;

import org.springframework.stereotype.Repository;
import pl.mmichonski.domain.Answer;
import pl.mmichonski.domain.Question;
import pl.mmichonski.domain.QuestionAnswer;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

@Repository
public class QuestionAnswerDaoImpl implements QuestionAnswerDao {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void addQuestionAnserCategoryPoints(Question question, Answer answer, Integer points) {
        QuestionAnswer questionAnswer = new QuestionAnswer(points,question,answer);
        entityManager.persist(questionAnswer);
    }

    @Override
    public void delete(Long id) {
        QuestionAnswer questionAnswer = entityManager.find(QuestionAnswer.class, id);
        Query query = entityManager.createQuery("select qa from QuestionAnswer qa where qa.question.id = :id");
        query.setParameter("id", questionAnswer.getQuestion().getId());
        List<QuestionAnswer> questionAnswers = query.getResultList();
        for(QuestionAnswer qa : questionAnswers)
        {
            entityManager.remove(qa);
        }
    }

    @Override
    public List<QuestionAnswer> getAll() {
        Query q = entityManager.createQuery("select qa from QuestionAnswer qa");
        List<QuestionAnswer> questionAnswers = q.getResultList();
        return questionAnswers;
    }

    @Override
    public int getPointsForQuestionAnswer(Long questionId, Long answerId) {
        Query q = entityManager.createQuery("select qa from QuestionAnswer qa");
        List<QuestionAnswer> qas = q.getResultList();

        if (qas != null)
        {
            for (QuestionAnswer qa : qas)
            {
                if(qa.getQuestion().getId() == questionId && qa.getAnswer().getId() == answerId)
                {
                    return qa.getPoints();
                }
            }
        }
        return 0;
    }
}

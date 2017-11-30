package pl.mmichonski.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.mmichonski.dao.AnswerDao;
import pl.mmichonski.dao.CategoryDao;
import pl.mmichonski.dao.QuestionAnswerDao;
import pl.mmichonski.dao.QuestionDao;
import pl.mmichonski.domain.Answer;
import pl.mmichonski.domain.Category;
import pl.mmichonski.domain.Question;
import pl.mmichonski.domain.QuestionAnswer;
import pl.mmichonski.domain.wrappers.AnswerPoints;
import pl.mmichonski.domain.wrappers.QuestionWithCategory;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class MyServiceImpl implements MyService {
    private AnswerDao answerDao;
    private QuestionDao questionDao;
    private CategoryDao categoryDao;
    private QuestionAnswerDao questionAnswerDao;

    @Autowired
    public MyServiceImpl(AnswerDao answerDao, QuestionDao questionDao, CategoryDao categoryDao, QuestionAnswerDao questionAnswerDao) {
        this.answerDao = answerDao;
        this.questionDao = questionDao;
        this.categoryDao = categoryDao;
        this.questionAnswerDao = questionAnswerDao;
    }

    //------------------------------------------------------------------------------
    //-------------------------------- ANSWERS -------------------------------------
    //------------------------------------------------------------------------------
    @Transactional
    @Override
    public void addAnswer(Answer answer) {
        if (answer != null) {
            answerDao.addAnswer(answer);
        }
    }

    @Transactional
    @Override
    public void updateAnswer(Answer answer) {
        if (answer != null)
        {
            answerDao.updateAnswer(answer);
        }
    }

    @Transactional
    @Override
    public void deleteAnswer(Long answerId) {
        answerDao.deleteAnswer(answerId);
    }

    @Transactional
    @Override
    public Optional<Answer> getAnswerById(Long answerId) {
        return answerDao.getAnswerById(answerId);
    }

    @Transactional
    @Override
    public List<Answer> getAllAnswers() {
        return answerDao.getAllAnswers();
    }


    //------------------------------------------------------------------------------
    //------------------------------- CATEGORY -------------------------------------
    //------------------------------------------------------------------------------
    @Transactional
    @Override
    public void addCategory(Category category) {
        categoryDao.addCategory(category);
    }

    @Transactional
    @Override
    public void updateCategory(Category category) {

        categoryDao.updateCategory(category);
    }

    @Transactional
    @Override
    public void deleteCategory(Long categoryId) {
        categoryDao.deleteCategory(categoryId);
    }

    @Transactional
    @Override
    public Optional<Category> getCategoryByIdWithQuestions(Long categoryId) {
        return categoryDao.getCategoryByIdWithQuestions(categoryId);
    }

    @Transactional
    @Override
    public Optional<Category> getCategoryByIdWithoutQuestions(Long categoryId) {
        return categoryDao.getCategoryByIdWithoutQuestions(categoryId);
    }

    @Transactional
    @Override
    public List<Category> getAllCategories() {
        return categoryDao.getAllCategories();
    }

    //------------------------------------------------------------------------------
    //------------------------------- QUESTION -------------------------------------
    //------------------------------------------------------------------------------
    @Override
    @Transactional
    public void addQuestion(String text, Long id) {
        //1. Pobierzemy kategorie z bazy o nazwie ktora podano jako argument
        Category c =
                categoryDao
                        .getCategoryByIdWithoutQuestions(id)
                        .orElseThrow(() -> new NullPointerException("CATEGORY NOT FOUND"));
        Question question = new Question(text, c);
        questionDao.addQuestion(question);
    }

    @Transactional
    @Override
    public void updateQuestionText(Question question, String text) {
        question.setText(text);
    }

    @Transactional
    @Override
    public void updateQuestionCategory(Question question, String category) {
        Category categoryOb =
                categoryDao
                        .getCategoryByName(category)
                        .orElseThrow(() -> new NullPointerException("CATEGORY NOT FOUND"));
        question.setCategory(categoryOb);
    }

    @Transactional
    @Override
    public List<Question> getAllQuestions() {
        return questionDao.getAllQuestionsWithCategoriesWithoutQuestionsAnswers();
    }

    @Transactional
    @Override
    public void addQuestionWithCategoryAndAnswers(QuestionWithCategory qc) {
        Category category = categoryDao.getCategoryByIdWithoutQuestions(qc.getCategoryId()).orElseThrow(() -> new NullPointerException("CATEGORY NOT FOUND"));
        Question question = new Question(qc.getText(), category);
        List<Answer> answers = answerDao.getAllAnswers();
        for(int i = 0; i < answers.size(); ++i)
        {
            questionAnswerDao.addQuestionAnserCategoryPoints(question, answers.get(i), qc.getAnswerPoints().get(i).getPoints());
        }
    }

    @Transactional
    @Override
    public void deleteQuestionWithCategoryAndAnswers(Long id) {
        questionAnswerDao.delete(id);
    }

    @Transactional
    @Override
    public List<QuestionAnswer> getAllQuestionAnswers() {
        return questionAnswerDao.getAll();
    }

    @Transactional
    @Override
    public int getPointsForQuestionAnswer(Long questionId, Long answerId) {
        return questionAnswerDao.getPointsForQuestionAnswer(questionId, answerId);
    }
}

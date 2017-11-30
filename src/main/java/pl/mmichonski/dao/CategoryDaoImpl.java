package pl.mmichonski.dao;

import org.springframework.stereotype.Repository;
import pl.mmichonski.domain.Answer;
import pl.mmichonski.domain.Category;
import pl.mmichonski.domain.Question;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.Set;

/**
 * Created by Mateusz on 2017-07-02.
 */
@Repository
public class CategoryDaoImpl implements CategoryDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void addCategory(Category category) {
        entityManager.persist(category);
    }

    @Override
    public void updateCategory(Category category) {
        Category categoryFromDb = entityManager.find(Category.class, category.getId());
        if (categoryFromDb != null) {
            categoryFromDb.setName(category.getName());
            categoryFromDb.setQuestions(category.getQuestions());
        }
    }

    @Override
    public void deleteCategory(Long id) {
        Category category = entityManager.find(Category.class, id);
        entityManager.remove(category);
    }

    @SuppressWarnings("JpaQlInspection")
    @Override
    public List<Category> getAllCategories() {

        Query query = entityManager.createQuery("select c from Category c");
        return query.getResultList();
    }

    @Override
    public Set<Question> getAllQuestionsByCategory(Long id) {
        Category category = entityManager.find(Category.class, id);
        return category.getQuestions();
    }

    @Override
    public Optional<Category> getCategoryByIdWithQuestions(Long id) {

        Category c = entityManager.find(Category.class, id);
        //pobieram pytania dla kategorii bo chce je miec
        c.getQuestions().size();
        return Optional.ofNullable(c);
    }

    @Override
    public Optional<Category> getCategoryByIdWithoutQuestions(Long id) {

        Category c = entityManager.find(Category.class, id);
        return Optional.ofNullable(c);
    }

    @Override
    public Optional<Category> getCategoryByName(String name) {
        Query q = entityManager.createQuery("select c from Category c where c.name = :name");
        q.setParameter("name", name);
        if (q.getSingleResult() != null) {
            return Optional.of((Category) q.getSingleResult());
        }
        return Optional.ofNullable(null);
    }
}

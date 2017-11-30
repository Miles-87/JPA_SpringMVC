package pl.mmichonski.dao;


import pl.mmichonski.domain.Category;
import pl.mmichonski.domain.Question;

import java.util.List;
import java.util.Optional;
import java.util.Set;

/**
 * Created by Mateusz on 2017-07-02.
 */
public interface CategoryDao {
    void addCategory(Category answer);
    void updateCategory(Category answer);
    void deleteCategory(Long id);
    List<Category> getAllCategories();
    Set<Question> getAllQuestionsByCategory(Long id);
    Optional<Category> getCategoryByIdWithQuestions(Long id);
    Optional<Category> getCategoryByIdWithoutQuestions(Long id);
    Optional<Category> getCategoryByName(String name);
}

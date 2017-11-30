package pl.mmichonski.controllers;

import javafx.geometry.HPos;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import pl.mmichonski.domain.Answer;
import pl.mmichonski.domain.Category;
import pl.mmichonski.domain.Question;
import pl.mmichonski.domain.QuestionAnswer;
import pl.mmichonski.domain.wrappers.Test;
import pl.mmichonski.domain.wrappers.TestCollections;
import pl.mmichonski.service.MyService;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Controller
public class TestController {
    private MyService service;

    @Autowired
    public TestController(MyService service) {
        this.service = service;
    }

    private List<Question> questionsS = null;

    @RequestMapping(value = "/test", method = RequestMethod.GET)
    public String generateTestGet(Model model)
    {
        List<QuestionAnswer> questionAnswers = service.getAllQuestionAnswers();
        List<Question> questions = service.getAllQuestions();
        List<Answer> answers = service.getAllAnswers();

        List<Long> ids =
                questionAnswers
                        .stream()
                        .map(qa -> qa.getQuestion().getId())

                        .collect(Collectors.toList());

        final int HOW_MANY_QUESTIONS_FOR_ONE_CATEGORY = 3;

        //NA POCZATEK MUSZE SPRAWDZIC, CZY MAM PO TYLE PYTAN DLA KAZDEJ KATEGORII
        //ILE ZAPISALEM DO STALEJ
        Map<Category, Long> categoryMap =
                questions
                        .stream()
                        .map(q -> q.getCategory())
                        .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));

        if (categoryMap.entrySet().stream().anyMatch(e -> e.getValue() < HOW_MANY_QUESTIONS_FOR_ONE_CATEGORY)) {
            return "welcome"; //TUTAJ DOROBIC STRONE OD WYJATKU
        }

        //tutaj losujemy dla kazdej kategori pytania
        questionsS
                = generateQuestions(HOW_MANY_QUESTIONS_FOR_ONE_CATEGORY)
                .entrySet()
                .stream()
                .flatMap(e -> e.getValue().stream())
                .collect(Collectors.toList());

        TestCollections test = new TestCollections();
        test.setQuestions(questionsS);
        test.setAnswersId(Arrays.asList(new Long[questionsS.size()]));

        model.addAttribute("test", test);
        model.addAttribute("answers", answers);
        return "test/test";
    }

    @RequestMapping(value = "/test", method = RequestMethod.POST)
    public String generateTestPost(@ModelAttribute TestCollections tests, Model model)
    {
        tests.setQuestions(questionsS);
        Long suma = 0L;
        for (int i = 0; i < tests.getAnswersId().size(); i++) {
            suma += service
                    .getPointsForQuestionAnswer(
                            tests.getQuestions().get(i).getId(),
                            tests.getAnswersId().get(i)
                    );
        }

        model.addAttribute("suma", suma);
        return "test/testResult";
    }

    private Map<Category, Set<Question>> generateQuestions(int howManyQuestionForCategory)
    {
        List<Question> questions = service.getAllQuestions();
        List<Category> categories = service.getAllCategories();

        Map<Category,List<Question>> m1
                = questions
                .stream()
                .collect(Collectors.groupingBy(Question::getCategory));

        Random rnd = new Random();
        Map<Category, Set<Question>> m2 = new LinkedHashMap<>();
        for(Category c : categories)
        {
            m2.put(c, new LinkedHashSet<>());
            while(m2.get(c).size() != howManyQuestionForCategory)
            {
                m2.get(c).add(m1.get(c).get(rnd.nextInt(m1.get(c).size())));
            }
        }

        return m2;
    }

}

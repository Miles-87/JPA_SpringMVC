package pl.mmichonski.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import pl.mmichonski.domain.Answer;
import pl.mmichonski.domain.Category;
import pl.mmichonski.domain.wrappers.AnswerPoints;
import pl.mmichonski.domain.wrappers.QuestionWithCategory;
import pl.mmichonski.service.MyService;

import javax.xml.ws.RequestWrapper;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
public class QuestionController {
    private MyService service;

    @Autowired
    public QuestionController(MyService service) {
        this.service = service;
    }

    @RequestMapping(value="/question/select/all", method = RequestMethod.GET)
    public String questionSelectAll(Model model)
    {
        model.addAttribute("questions", service.getAllQuestions());
        return "question/questionSelectAll";
    }

    @RequestMapping(value = "/question/insert", method = RequestMethod.GET)
    public String questionInsertGet(Model model)
    {
        QuestionWithCategory questionWithCategory = new QuestionWithCategory();
        List<Answer> answers = service.getAllAnswers();
        for(Answer a : answers)
        {
            questionWithCategory.getAnswerPoints().add(new AnswerPoints(a, 0));
        }
        model.addAttribute("questionWithCategory", questionWithCategory);

        List<Category> categories = service.getAllCategories();
        model.addAttribute("categories", categories);

        List<Integer> points = new ArrayList<>();
        for(int i = 1; i <= answers.size(); ++i)
        {
            points.add(i);
        }
        model.addAttribute("points", points);

        return "question/questionInsert";
    }

    @RequestMapping(value = "/question/insert", method = RequestMethod.POST)
    public String questionInsertPost(@ModelAttribute("questionWithCategory") QuestionWithCategory questionWithCategory, Model model) {
        List<AnswerPoints> list = questionWithCategory.getAnswerPoints();
        if (list.stream().map(ap -> ap.getPoints()).distinct().count() == list.size()) {
            service.addQuestionWithCategoryAndAnswers(questionWithCategory);
            return "redirect:/questionanswers/select/all";
        }
        else
        {
            model.addAttribute("msg", "Kazda odpowiedz musi miec inna ilosc punktow!");
            return "questionanswers/error";
        }
    }

    @RequestMapping(value = "/questionanswers/select/all", method = RequestMethod.GET)
    public String questionAnswersSelectAll(Model model)
    {
        model.addAttribute("qa", service.getAllQuestionAnswers());
        return "questionanswers/questionAnswersSelectAll";
    }

    @RequestMapping(value = "/questionanswers/delete/{id}", method = RequestMethod.GET)
    public String questionAnswersSelectAll(@PathVariable("id") Long id)
    {
        service.deleteQuestionWithCategoryAndAnswers(id);
        return "redirect:/questionanswers/select/all";
    }


}

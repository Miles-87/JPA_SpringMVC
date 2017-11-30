package pl.mmichonski.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import pl.mmichonski.dao.QuestionDao;
import pl.mmichonski.domain.Answer;
import pl.mmichonski.domain.Category;
import pl.mmichonski.service.MyService;

import java.util.List;

/**
 * Created by Mateusz on 2017-06-19.
 */
@Controller
public class MyController {

    private QuestionDao questionDao;
    private MyService myService;

    //-------------------------------- ANSWERS --------------------------------------
    @Autowired
    public MyController(QuestionDao questionDao, MyService myService) {
        this.questionDao = questionDao;
        this.myService = myService;
    }


    //----------------------------------------------------------------------------
    //-------------------------------- MAIN PAGE ---------------------------------
    //----------------------------------------------------------------------------
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String welcome()
    {
        return "welcome";
    }

    //----------------------------------------------------------------------------
    //-------------------------------- ANSWERS -----------------------------------
    //----------------------------------------------------------------------------


    //----------------------------------------------------------------------------
    //----------------------------- ANSWER INSERT --------------------------------
    //----------------------------------------------------------------------------
    @RequestMapping(value = "/answer/insert", method = RequestMethod.GET)
    public String insertAnswerGet(Model model)
    {
        Answer answer = new Answer();
        model.addAttribute("answer", answer);
        return "answer/answerInsert";
    }

    @RequestMapping(value = "/answer/insert", method = RequestMethod.POST)
    public String insertAnswerPost(@ModelAttribute("answer") Answer answer)
    {
        myService.addAnswer(answer);
        return "redirect:/answer/select/all";
    }

    //----------------------------------------------------------------------------
    //------------------------------ ANSWER UPDATE -------------------------------
    //----------------------------------------------------------------------------
    @RequestMapping(value = "/answer/update/{id}", method = RequestMethod.GET)
    public String updateAnswerGet(@PathVariable("id") Long id, Model model)
    {

        Answer answer = myService.getAnswerById(id).orElseThrow(() -> new NullPointerException("Nie udalo sie pobrac answer"));
        model.addAttribute("answer", answer);
        return "answer/answerUpdate";
    }

    @RequestMapping(value = "/answer/update", method = RequestMethod.POST)
    public String updateAnswerPost(@ModelAttribute("answer") Answer answer)
    {
        myService.updateAnswer(answer);
        return "redirect:/answer/select/all";
    }

    //----------------------------------------------------------------------------
    //------------------------------ ANSWER DELETE -------------------------------
    //----------------------------------------------------------------------------
    @RequestMapping(value = "/answer/delete/{id}", method = RequestMethod.GET)
    public String deleteAnswer(@PathVariable("id") Long id)
    {
        myService.deleteAnswer(id);
        return "redirect:/answer/select/all";
    }

    //----------------------------------------------------------------------------
    //------------------------------ ANSWER SELECT -------------------------------
    //----------------------------------------------------------------------------
    @RequestMapping("/answer/select/all")
    public String selectAnswers(Model model)
    {
        List<Answer> answers = myService.getAllAnswers();
        model.addAttribute("answers", answers);
        return "answer/answerSelectAll";
    }


    @RequestMapping("/select/answer/{id}")
    public String selectAnswerById(@PathVariable("id") Long id)
    {
        System.out.println(myService.getAnswerById(id).get());
        return "welcome";
    }


    //----------------------------------------------------------------------------
    //-------------------------------- CATEGORY ----------------------------------
    //----------------------------------------------------------------------------

    //----------------------------------------------------------------------------
    //----------------------------- CATEGORY INSERT ------------------------------
    //----------------------------------------------------------------------------
    @RequestMapping(value = "/category/insert", method = RequestMethod.GET)
    public String insertCategoryGet(Model model) {
        Category category = new Category();
        model.addAttribute("category", category);
        return "category/categoryInsert";
    }

    @RequestMapping(value = "/category/insert", method = RequestMethod.POST)
    public String insertCategoryGet(@ModelAttribute("category") Category category) {
        myService.addCategory(category);
        return "redirect:/category/select/all";
    }

    //----------------------------------------------------------------------------
    //------------------------------ CATEGORY UPDATE -----------------------------
    //----------------------------------------------------------------------------
    @RequestMapping(value = "/category/update/{id}", method = RequestMethod.GET)
    public String updateCategoryById(@PathVariable("id") Long id,  Model model)
    {
        Category category = myService.getCategoryByIdWithoutQuestions(id).orElseThrow(() -> new NullPointerException("Nie ma takiej kategorii"));
        model.addAttribute("category",category);
        return "category/categoryUpdate";
    }

    @RequestMapping(value = "/category/update", method = RequestMethod.POST)
    public String updateCategory(@ModelAttribute("category") Category category)
    {
        myService.updateCategory(category);
        return "redirect:/category/select/all";
    }

    //----------------------------------------------------------------------------
    //------------------------------ CATEGORY DELETE -----------------------------
    //----------------------------------------------------------------------------
    @RequestMapping("/category/delete/{id}")
    public String deleteCategory(@PathVariable("id") Long id)
    {
        myService.deleteCategory(id);
        return "redirect:/category/select/all";
    }

    //----------------------------------------------------------------------------
    //------------------------------ CATEGORY SELECT -----------------------------
    //----------------------------------------------------------------------------
    @RequestMapping("/category/select/all")
    public String categorySelectAll(Model model)
    {
        model.addAttribute("categories", myService.getAllCategories());
        return "category/categorySelectAll";
    }







}
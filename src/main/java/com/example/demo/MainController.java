package com.example.demo;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.example.demo.UserRepository;

import jakarta.annotation.PostConstruct;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;

@Controller
public class MainController {
  
  @Autowired
  UserRepository repository;
  
  @Autowired
  UserDAOUserImpl dao;   // ☆
  

  @RequestMapping(value = "/find", method = RequestMethod.GET)
  public ModelAndView index(ModelAndView mav) {
    System.out.println("デバッグ用");
    mav.setViewName("find");
    mav.addObject("msg","Personのサンプルです。");
    Iterable<User> list = dao.getAll();   // ☆
    mav.addObject("data", list);
    return mav;
  }


  @RequestMapping(value = "/find", method = RequestMethod.POST)
  public ModelAndView search(HttpServletRequest request,
      ModelAndView mav) {
    mav.setViewName("find");
    String param = request.getParameter("find_str");
    if (param == ""){
      mav = new ModelAndView("redirect:/find");
    } else {
      mav.addObject("title","Find result");
      mav.addObject("msg","「" + param + "」の検索結果");
      mav.addObject("value",param);
      //User data = dao.findById(Integer.parseInt(param)); 

      Long id = Long.parseLong(param);
      User data = dao.findById(id);

      User[] list = new User[] {data};
      mav.addObject("data", list);
    }
    return mav;
  }


}

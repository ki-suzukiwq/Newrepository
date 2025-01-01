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
  

  //検索用
  @RequestMapping(value = "/find", method = RequestMethod.GET)
  public ModelAndView index(ModelAndView mav) {
    System.out.println("デバッグ用");
    mav.setViewName("find");
    mav.addObject("msg","Userのサンプルです。");
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
      List<User> list = dao.findByName(param);
      mav.addObject("data", list);
    }
    return mav;
  }

  //登録用
  @RequestMapping(value = "/add", method = RequestMethod.GET)
public ModelAndView addUserForm(ModelAndView mav) {
    mav.setViewName("edit");
    mav.addObject("formModel", new User());
    mav.addObject("title", "Add New User");
    mav.addObject("msg", "Enter user details:");
    return mav;
}

@RequestMapping(value = "/add", method = RequestMethod.POST)
@Transactional
public ModelAndView addUser(@ModelAttribute("formModel") User user, ModelAndView mav) {
    dao.save(user);
    mav.setViewName("redirect:/find");
    return mav;
}

  //削除用
  @RequestMapping(value = "/delete", method = RequestMethod.POST)
  @Transactional

  public ModelAndView delete(HttpServletRequest request, ModelAndView mav) {
      String param = request.getParameter("delete_id");
      if (param != null && !param.isEmpty()) {
          Long id = Long.parseLong(param);
          dao.deleteById(id); // DAOを使って削除処理を実行
          mav.addObject("msg", "ID: " + id + " のユーザを削除しました。");
      } else {
          mav.addObject("msg", "削除するIDを指定してください。");
      }
      mav.setViewName("redirect:/find"); // 削除後に検索画面へリダイレクト
      return mav;
  }
  

  }



package com.example.demo;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
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
import jakarta.validation.Valid;

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
    mav.addObject("msg","名前検索");
    Iterable<User> list = dao.getAll();   // ☆
    mav.addObject("data", list);
    return mav;
  }

  //一覧用
  @RequestMapping(value = "/find", method = RequestMethod.POST)
  public ModelAndView search(HttpServletRequest request,
      ModelAndView mav) {
    mav.setViewName("find");
    String param = request.getParameter("find_str");
    if (param == ""){
      mav = new ModelAndView("redirect:/find");
    } else {
      mav.addObject("title","絞り込み結果");
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
public ModelAndView addUser(
    @ModelAttribute("formModel") @Valid User user,  // バリデーション適用
    BindingResult result,  // バリデーション結果を受け取る
    ModelAndView mav
) {
    if (result.hasErrors()) {  // バリデーションエラーがある場合
        mav.setViewName("edit");  // 入力フォームに戻る
        mav.addObject("title", "Add New User");
        mav.addObject("msg", "エラーがあります。修正してください。");
        return mav;
    }

    dao.save(user);  // バリデーションOKなら保存
    mav.setViewName("redirect:/find");
    return mav;
}

 //更新用_20250202///
 //更新対象のHTML返却
 @RequestMapping(value = "/update/{id}", method = RequestMethod.GET)
 public ModelAndView updUserForm(@PathVariable("id") Integer id, ModelAndView mav) {
     User user = dao.findById(id); // ID で対象のユーザを検索

     if (user == null) {
         mav.setViewName("redirect:/find"); // ユーザが見つからなければリストにリダイレクト
         return mav;
     }

     mav.setViewName("upd");
     mav.addObject("formModel", user);
     mav.addObject("title", "以下ユーザ情報を更新可能です");
     mav.addObject("msg", "更新対象ユーザ:");
     return mav;
 }
  

 //ユーザ情報更新
 @RequestMapping(value = "/update", method = RequestMethod.POST)
 @Transactional
 public ModelAndView updUser(@ModelAttribute("formModel") User user, ModelAndView mav) {
     dao.saveOrUpdate(user); // 変更を保存
     mav.setViewName("redirect:/find"); // ユーザ一覧にリダイレクト
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



package com.example.demo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.example.demo.UserRepository;

@Controller
public class MainController {
  
  @Autowired
  UserRepository repository;

  @RequestMapping("/")
  public ModelAndView index(ModelAndView mav) {
    mav.setViewName("index");
    mav.addObject("title", "Hello page");
    mav.addObject("msg","this is JPA sample data.");
   Iterable<User> list = repository.findAll();
    mav.addObject("data",list);
    return mav;
  }

}

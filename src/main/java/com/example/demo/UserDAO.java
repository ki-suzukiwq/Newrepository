package com.example.demo;

import java.io.Serializable;
import java.util.List;

public interface UserDAO <T> extends Serializable {

  public List<T> getAll();
  public T findById(Long id);

  /* 
  public List<T> findByName(String name);
  */
}

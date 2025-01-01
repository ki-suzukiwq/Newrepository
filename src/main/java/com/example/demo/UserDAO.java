package com.example.demo;

import java.io.Serializable;
import java.util.List;

public interface UserDAO <T> extends Serializable {

  public List<T> getAll();
  public T findById(Long id);
  public List<T> findByName(String name);

  public void save(T entity); // 登録追加
  public void deleteById(Long id);// 削除追加



}

package com.example.demo;

import java.io.Serializable;
import java.util.List;

//DBアクセッサクラスとして一般的に、データを扱うクラス（DAO, Entity など）はシリアライズ可能にしておくのがベストプラクティス
//もし、セッション管理やキャッシュを使わない場合、Serializable を継承する必要はない。
public interface UserDAO <T> extends Serializable {

  //全件検索
  public List<T> getAll();
  //使っていない
  public T findById(Integer id);
  //名前検索
  public List<T> findByName(String name);
  //登録保存用
  public void save(T entity); // 登録追加.Userimplクラスのsaveメソッドを呼び出し
  //更新保存用
  public void saveOrUpdate(T entity);//更新追加.Userimplクラスのsaveメソッドを呼び出し
  //削除用
  public void deleteById(Long id);// 削除追加



}

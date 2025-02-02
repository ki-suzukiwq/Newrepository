package com.example.demo;


import java.util.List;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;

import org.springframework.stereotype.Repository;

@Repository
public class UserDAOUserImpl implements UserDAO<User> {
  private static final long serialVersionUID = 1L;
  
  //SQL実装（CRUD）のためのライブラリ
  @PersistenceContext
  private EntityManager entityManager;
  
  public UserDAOUserImpl(){
    super();
  }

  //全件検索用
  @Override
  public List<User> getAll() {
    Query query = entityManager.createQuery("from User");
    @SuppressWarnings("unchecked")
    List<User> list = query.getResultList();
    entityManager.close();
    return list;
  }

  //特定ID検索用（これID検索作ったけど使っていない。）
  @Override
  public User findById(Integer id){
    return (User)entityManager.createQuery("from User where id ="+ id).getSingleResult();
  }

  //名前検索用
  @Override
  public List<User> findByName(String name) {
    //return (List<User>)entityManager.createQuery("from User where name = '" + name + "'").getResultList();
    return (List<User>) entityManager.createQuery("from User where name LIKE :name").setParameter("name", "%" + name + "%").getResultList();
  }
    //シングルクォーとはSELECT * FROM Person WHERE name = 'John';

  //新規登録用
  @Override
    public void save(User user) { // 新規追加
        entityManager.persist(user);// 新しい User のみ INSERT される
    }

  //更新用
  @Override  
    public void saveOrUpdate(User user) { 
      entityManager.merge(user); // ID があれば UPDATE、なければ INSERT
  }

    //削除用
   @Override
    public void deleteById(Long id) {
    User user = entityManager.find(User.class, id); // 対象ユーザを取得
    if (user != null) {
        entityManager.remove(user); // ユーザを削除
    }
}


}

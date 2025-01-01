package com.example.demo;


import java.util.List;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;

import org.springframework.stereotype.Repository;

@Repository
public class UserDAOUserImpl implements UserDAO<User> {
  private static final long serialVersionUID = 1L;
  
  @PersistenceContext
  private EntityManager entityManager;
  
  public UserDAOUserImpl(){
    super();
  }

  @Override
  public List<User> getAll() {
    Query query = entityManager.createQuery("from User");
    @SuppressWarnings("unchecked")
    List<User> list = query.getResultList();
    entityManager.close();
    return list;
  }

  @Override
  public User findById(Long id){
    return (User)entityManager.createQuery("from User where id ="+ id).getSingleResult();
  }

/*   
  @Override
  public List<User> findByName(String name) {
    return (List<User>)entityManager.createQuery("from Person where name = '" + name + "'").getResultList();
  }
    //シングルクォーとはSELECT * FROM Person WHERE name = 'John';
*/

}

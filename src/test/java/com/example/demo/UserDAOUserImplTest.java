package com.example.demo;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Transactional // テスト後にデータをロールバックするため
public class UserDAOUserImplTest {

    @Autowired
    private UserDAOUserImpl dao;

    @PersistenceContext
    private EntityManager entityManager;

     /*
    @BeforeEach
    void setup() {
        // テスト用のデータを準備
        User user1 = new User();
        user1.setName("Test User 1");
        user1.setEmail("test1@example.com");

        User user2 = new User();
        user2.setName("Test User 2");
        user2.setEmail("test2@example.com");

        entityManager.persist(user1);
        entityManager.persist(user2);
    }


    @Test
    void testFindById() {
        // 準備されたデータのIDを取得
        User user = dao.getAll().get(0); // 最初のユーザを取得
        Long id = user.g


        // findByIdで検索
        User foundUser = dao.findById(id);
        assertThat(foundUser).isNotNull();
        assertThat(foundUser.getName()).isEqualTo("Test User 1");
    }

    @Test
    void testDeleteById() {
        // 準備されたデータのIDを取得
        User user = dao.getAll().get(0);
        Long id = user.getId();

        // 削除処理を実行
        dao.deleteById(id);

        // 削除後の確認
        User deletedUser = entityManager.find(User.class, id);
        assertThat(deletedUser).isNull(); // 削除されたためnullであるべき
    }

     */
    @Test
    void testGetAll() {
        // 全てのデータを取得
        List<User> users = dao.getAll();

        System.out.println(users);

        // データの数と内容を確認
        //assertThat(users).hasSize(5);
        assertThat(users.get(0).getName()).isEqualTo("Jane Smith");
        assertThat(users.get(1).getName()).isEqualTo("Alice Johnson");
    }
}

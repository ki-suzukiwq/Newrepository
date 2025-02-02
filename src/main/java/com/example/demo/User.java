package com.example.demo;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.*;

@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @NotBlank(message = "名前は必須です")
    private String name;

    private String gender;

    @NotNull(message = "年齢を入力してください")
    @Min(value = 0, message = "年齢は0以上である必要があります")
    @Max(value = 120, message = "年齢は120以下である必要があります")
    private Integer age;

    @NotBlank(message = "メールアドレスは必須です")
    @Email(message = "正しいメールアドレスを入力してください")
    @Size(max = 255, message = "メールアドレスは255文字以下である必要があります")
    private String email;

    private String notes;

    // Getter & Setter
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNotes() {
      return notes;
  }

  public void setNotes(String notes) {
      this.notes = notes;
  }
}

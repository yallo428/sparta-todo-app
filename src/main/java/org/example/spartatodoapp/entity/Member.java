package org.example.spartatodoapp.entity;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
public class Member {

    @Id @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(unique = true, nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;


    public Member(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public boolean isNotUserName(String username){
        return !this.username.equals(username);
    }

    public void passwordCheck(PasswordEncoder passwordEncoder, String password){
        if(!passwordEncoder.matches(password, this.password)){
            throw new IllegalArgumentException("패스워드가 다릅니다.");
        }
    }

}

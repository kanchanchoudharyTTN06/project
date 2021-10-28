package com.ttn.bootcamp.token;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class LogoutToken
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 500)
    private String token;

    public LogoutToken() {
    }

    public LogoutToken(String token) {
        this.token = token;
    }
}

package com.startsoftbr.domestikapro.model;

public class AuthRequest {
    private String email;
    private String senha;

    public AuthRequest(String email, String senha) {
        this.email = email;
        this.senha = senha;
    }
}

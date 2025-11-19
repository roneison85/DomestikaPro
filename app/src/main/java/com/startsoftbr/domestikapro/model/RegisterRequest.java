package com.startsoftbr.domestikapro.model;

public class RegisterRequest {
    private String nome;
    private String email;
    private String senha;

    public RegisterRequest(String nome, String email, String senha) {
        this.nome = nome;
        this.email = email;
        this.senha = senha;
    }
}
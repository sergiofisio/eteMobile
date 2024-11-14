package com.example.projetofinal;

public class agenda {
    private int id;
    private String nome, telefone, email;

    public agenda(int id, String nome, String telefone, String email) {
        this.id = id;
        this.nome = nome;
        this.telefone = telefone;
        this.email = email;
    }

    public int getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public String getTelefone() {
        return telefone;
    }

    public String getEmail() {
        return email;
    }

    @Override
    public String toString() {
        return "Nome: " + nome + "\nTelefone: " + telefone + "\nEmail: " + email;
    }
}

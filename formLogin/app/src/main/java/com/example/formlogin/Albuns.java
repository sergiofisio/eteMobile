package com.example.formlogin;

import java.io.Serializable;
import java.util.List;

public class Albuns implements Serializable {

    private String nome;
    private List<Integer> capasAlbuns;

    public Albuns(String nome, List<Integer> capasAlbuns) {
        this.nome = nome;
        this.capasAlbuns = capasAlbuns;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public List<Integer> getCapasAlbuns() {
        return capasAlbuns;
    }

    public void setCapasAlbuns(List<Integer> capasAlbuns) {
        this.capasAlbuns = capasAlbuns;
    }

    public void adicionarCapaAlbum(Integer capaAlbum) {
        this.capasAlbuns.add(capaAlbum);
    }
}

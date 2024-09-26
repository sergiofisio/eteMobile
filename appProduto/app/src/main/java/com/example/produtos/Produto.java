package com.example.produtos;

public class Produto {

    private String nome;
    private String descricao;
    private Double valor;
    private boolean selected;
    public Produto(String nome, String descricao, Double valor, Boolean selected) {
        this.nome = nome;
        this.descricao = descricao;
        this.valor = valor;
        this.selected = selected;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Double getValor() {
        return valor;
    }

    public void setValor(Double valor) {
        this.valor = valor;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    @Override
    public String toString() {
        return  "nome='" + nome + '\'' +
                ", descricao='" + descricao + '\'' +
                ", valor=" + valor;
    }
}

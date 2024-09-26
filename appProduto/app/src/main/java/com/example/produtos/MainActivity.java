package com.example.produtos;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    CustomToast customToast;
    EditText txtNome, txtDescricao, txtValor;
    Button btnCadastro, btnCalcular;
    ListView lista;

    ArrayList<Produto> arrayLista = new ArrayList<>();
    Double soma = 0.00;
    ArrayAdapter<Produto> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        inicializarComponentes();
        configurarEventos();
    }

    private void inicializarComponentes() {
        customToast = new CustomToast(this);

        btnCadastro = findViewById(R.id.btnCadastro);
        btnCalcular = findViewById(R.id.btnCalcular);
        txtDescricao = findViewById(R.id.txtDescricao);
        txtNome = findViewById(R.id.txtNome);
        txtValor = findViewById(R.id.txtValor);
        lista = findViewById(R.id.lista);

        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, arrayLista);
        lista.setAdapter(adapter);
    }

    private void configurarEventos() {
        btnCadastro.setOnClickListener(view -> adicionarProduto());
        lista.setOnItemClickListener(this::selecionarProduto);
        btnCalcular.setOnClickListener(view -> calcularSoma());
    }

    private void adicionarProduto() {
        String nome = txtNome.getText().toString().trim();
        String descricao = txtDescricao.getText().toString().trim();
        String valorString = txtValor.getText().toString().trim();

        if (TextUtils.isEmpty(nome) || TextUtils.isEmpty(descricao) || TextUtils.isEmpty(valorString)) {
            customToast.show("Por favor, preencha todos os campos!", 2000, "#FF0000", "error");
            return;
        }

        try {
            Double valor = Double.parseDouble(valorString);
            Produto produto = new Produto(nome, descricao, valor, false);
            arrayLista.add(produto);
            adapter.notifyDataSetChanged();

            limparCampos();
            esconderTeclado();
        } catch (NumberFormatException e) {
            customToast.show("Valor inválido! Digite um número válido.", 2000, "#FF0000", "error");
        }
    }

    private void selecionarProduto(AdapterView<?> adapterView, View view, int posicao, long l) {
        Produto produto = arrayLista.get(posicao);

        if (produto.isSelected()) {
            customToast.show("Produto já selecionado", 2000, "#FF0000", "error");
            return;
        }

        soma += produto.getValor();
        produto.setSelected(true);
        adapter.notifyDataSetChanged();
    }

    private void calcularSoma() {
        if (soma.equals(0.00)) {
            customToast.show("Selecione um produto", 2000, "#FF0000", "error");
        } else {
            customToast.show("Soma: " + String.format("R$ %.2f", soma), 5000, "#000000", "success");
        }
    }

    private void limparCampos() {
        txtNome.setText("");
        txtDescricao.setText("");
        txtValor.setText("");
    }

    private void esconderTeclado() {
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }
}

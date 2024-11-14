package com.example.projetofinal;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.List;
import java.util.Map;

public class atualizar extends AppCompatActivity {

    private TextView lblBackUpdate;
    private EditText txtNameUp, txtPhoneUp, txtEmailUp;
    private Button btnUpdate, btnDelete;
    private int contatoId;
    private String nome, telefone, email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_atualizar);

        setupUI();
        contatoId = getIntent().getIntExtra("contatoId", -1);

        carregarDadosContato();

        btnUpdate.setOnClickListener(v -> atualizarContato());
        btnDelete.setOnClickListener(v -> deletarContato());

        lblBackUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void setupUI() {
        txtNameUp = findViewById(R.id.txtNameUp);
        txtPhoneUp = findViewById(R.id.txtPhoneUp);
        txtEmailUp = findViewById(R.id.txtEmailUp);
        btnUpdate = findViewById(R.id.btnUpdate);
        btnDelete = findViewById(R.id.btnDelete);
        lblBackUpdate = findViewById(R.id.lblBackUpdate);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    private void carregarDadosContato() {
        try (db database = new db(this)) {
            List<Map<String, Object>> dados = database.getContact("contatos", "id = ?", new String[]{String.valueOf(contatoId)});

            if (!dados.isEmpty()) {
                Map<String, Object> contato = dados.get(0);
                nome = contato.get("nome").toString();
                telefone = contato.get("telefone").toString();
                email = contato.get("email").toString();

                txtNameUp.setText(nome);
                txtNameUp.setEnabled(false);
                txtPhoneUp.setText(telefone);
                txtEmailUp.setText(email);
            } else {
                Toast.makeText(this, "Contato não encontrado", Toast.LENGTH_SHORT).show();
                finish();
            }
        } catch (Exception e) {
            Toast.makeText(this, "Erro ao carregar dados: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    private void atualizarContato() {
        String newTelefone = txtPhoneUp.getText().toString().trim();
        String newEmail = txtEmailUp.getText().toString().trim();

        if (newTelefone.isEmpty() || newEmail.isEmpty()) {
            Toast.makeText(this, "Preencha todos os campos", Toast.LENGTH_SHORT).show();
            return;
        }

        if (newTelefone.equals(telefone) && newEmail.equals(email)) {
            Toast.makeText(this, "Nenhuma alteração detectada", Toast.LENGTH_SHORT).show();
            return;
        }

        try (db database = new db(this)) {
            String resultado = database.addOrUpdate(nome, newTelefone, newEmail, "update", "contatos", contatoId);
            Toast.makeText(this, resultado, Toast.LENGTH_SHORT).show();
            carregarDadosContato(); // Atualiza os dados na interface
        } catch (Exception e) {
            Toast.makeText(this, "Erro ao atualizar contato: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    private void deletarContato() {
        try (db database = new db(this)) {
            String resultado = database.delete(contatoId, "contatos");
            Toast.makeText(this, resultado, Toast.LENGTH_SHORT).show();
            finish();
        } catch (Exception e) {
            Toast.makeText(this, "Erro ao deletar contato: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
}

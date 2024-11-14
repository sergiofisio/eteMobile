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

public class adicionar extends AppCompatActivity {

    private TextView lblBackAdd;
    private EditText txtName, txtPhone, txtEmail;
    private Button btnAdd, btnReset;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_adicionar);

        setupUI();

        btnAdd.setOnClickListener(v -> adicionarContato());
        btnReset.setOnClickListener(v -> resetCampos());

        lblBackAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void setupUI() {
        txtName = findViewById(R.id.txtNameUp);
        txtPhone = findViewById(R.id.txtPhone);
        txtEmail = findViewById(R.id.txtEmail);
        btnAdd = findViewById(R.id.btnAdd);
        btnReset = findViewById(R.id.btnReset);
        lblBackAdd = findViewById(R.id.lblBack);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    private void adicionarContato() {
        String nome = txtName.getText().toString().trim();
        String telefone = txtPhone.getText().toString().trim();
        String email = txtEmail.getText().toString().trim();

        if (nome.isEmpty() || telefone.isEmpty() || email.isEmpty()) {
            Toast.makeText(this, "Preencha todos os campos", Toast.LENGTH_SHORT).show();
            return;
        }

        try (db database = new db(this)) {
            String message = database.addOrUpdate(nome, telefone, email, "add", "contatos", 0);
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
            resetCampos();
        } catch (Exception e) {
            Toast.makeText(this, "Erro ao adicionar contato: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }



    private void resetCampos() {
        txtName.setText("");
        txtPhone.setText("");
        txtEmail.setText("");
    }
}

package com.example.projetofinal;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private ListView lista;
    private Button btnAddCon;
    private ArrayAdapter<agenda> adapter;
    private final ArrayList<agenda> arrayLista = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        setupUI();
        carregarDados();

        btnAddCon.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, adicionar.class);
            startActivity(intent);
        });

        lista.setOnItemClickListener((parent, view, position, id) -> {
            int contatoId = arrayLista.get(position).getId();
            Intent intent = new Intent(MainActivity.this, atualizar.class);
            intent.putExtra("contatoId", contatoId);
            startActivity(intent);
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        carregarDados();
    }

    private void setupUI() {
        lista = findViewById(R.id.lista);
        btnAddCon = findViewById(R.id.btnAddCon);

        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, arrayLista);
        lista.setAdapter(adapter);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    private void carregarDados() {
        arrayLista.clear();
        adapter.clear();

        try (db database = new db(this)) {
            List<Map<String, Object>> dados = database.getInfo("contatos");

            for (Map<String, Object> row : dados) {
                agenda contato = new agenda(
                        (int) row.get("id"),
                        row.get("nome").toString(),
                        row.get("telefone").toString(),
                        row.get("email").toString()
                );
                arrayLista.add(contato);
            }
            adapter.notifyDataSetChanged();
        } catch (Exception e) {
            Toast.makeText(this, "Erro ao carregar dados: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
}

package com.example.formlogin;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class LoginActivity extends AppCompatActivity {

    CustomToast customToast;
    Spinner spinner;
    int artistId;
    Albuns albuns;
    TextView logout, txtNext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        customToast = new CustomToast(this);
        spinner = findViewById(R.id.spinner);
        logout = findViewById(R.id.logout);
        txtNext = findViewById(R.id.txtNext);

        // Opções de artistas para o Spinner
        ArrayList<String> options = new ArrayList<>();
        options.add("");
        options.add("Elton John");
        options.add("Guns N Roses");
        options.add("Iron Maiden");
        options.add("Metallica");

        ArrayAdapter<String> adapter =
                new ArrayAdapter<>(LoginActivity.this,
                        android.R.layout.simple_list_item_1,
                        options);

        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                artistId = position;
                albuns = null; // Reseta o valor de albuns antes de atribuir novos dados

                if (artistId > 0) {
                    switch (artistId) {
                        case 1:
                            albuns = new Albuns("Elton John", Arrays.asList(
                                    R.drawable.elton_john_rocket_man,
                                    R.drawable.elton_john_yellow,
                                    R.drawable.elton_john_your_song
                            ));
                            break;
                        case 2:
                            albuns = new Albuns("Guns N Roses", Arrays.asList(
                                    R.drawable.guns_appetitefordestruction,
                                    R.drawable.guns_illuion1,
                                    R.drawable.guns_illuion2
                            ));
                            break;
                        case 3:
                            albuns = new Albuns("Iron Maiden", Arrays.asList(
                                    R.drawable.iron_fear,
                                    R.drawable.iron_number,
                                    R.drawable.iron_peice
                            ));
                            break;
                        case 4:
                            albuns = new Albuns("Metallica", Arrays.asList(
                                    R.drawable.metalica_black,
                                    R.drawable.metalica_load,
                                    R.drawable.metalica_master
                            ));
                            break;
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // Sem ação necessária
            }
        });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                customToast.show("Ja vai playboy? Até a próxima", 5000, "#000000", "success");

                logout.setEnabled(false);

                Intent it = new Intent(LoginActivity.this, MainActivity.class);
                new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        startActivity(it);
                    }
                }, 4000);
            }
        });

        txtNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    if (artistId == 0) {
                        throw new Exception("Esqueceu de selecionar a banda/artista playboy!");
                    }

                    if (albuns != null) {
                        // Certifica-se de que a lista de capas de álbuns não está vazia
                        List<Integer> capasAlbuns = albuns.getCapasAlbuns();
                        if (capasAlbuns != null && !capasAlbuns.isEmpty()) {
                            Intent it = new Intent(LoginActivity.this, Artist.class);
                            it.putExtra("info", albuns); // Só envia se albuns não for nulo e a lista não estiver vazia
                            startActivity(it);
                        } else {
                            throw new Exception("Nenhuma capa de álbum disponível.");
                        }
                    } else {
                        throw new Exception("Erro ao carregar os álbuns do artista selecionado.");
                    }
                } catch (Exception e) {
                    customToast.show(e.getMessage(), Toast.LENGTH_LONG, "#FF0000", "error");
                }
            }
        });
    }
}

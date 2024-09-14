package com.example.formlogin;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class Artist extends AppCompatActivity {

    TextView txtArtist, back;
    ImageView imgAlbum1, imgAlbum2, imgAlbum3;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_artist);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        back = findViewById(R.id.back);
        txtArtist = findViewById(R.id.txtArtist);
        imgAlbum1 = findViewById(R.id.imgAlbum1);
        imgAlbum2 = findViewById(R.id.imgAlbum2);
        imgAlbum3 = findViewById(R.id.imgAlbum3);

        Albuns albuns = (Albuns) getIntent().getSerializableExtra("info");

        if (albuns != null) {
            String nome = albuns.getNome();

            txtArtist.setText(nome);
            imgAlbum1.setImageResource(albuns.getCapasAlbuns().get(0));
            imgAlbum2.setImageResource(albuns.getCapasAlbuns().get(1));
            imgAlbum3.setImageResource(albuns.getCapasAlbuns().get(2));
        }

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent it = new Intent(Artist.this, LoginActivity.class);

                startActivity(it);
            }
        });
    }
}
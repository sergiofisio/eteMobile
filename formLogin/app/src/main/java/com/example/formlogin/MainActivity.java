package com.example.formlogin;

import android.content.Intent;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    private boolean isPasswordVisible = false;

    EditText txtLogin, txtPass;
    Button btnEnter;
    GradientDrawable shape = new GradientDrawable();
    CustomToast customToast;
    ImageView showPass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        txtLogin = findViewById(R.id.txtLogin);
        txtPass =  findViewById(R.id.txtPass);
        btnEnter = findViewById(R.id.btnEnter);
        showPass = findViewById(R.id.showPass);

        customToast = new CustomToast(this);

        shape.setShape(GradientDrawable.RECTANGLE);
        shape.setCornerRadius(20f);
        shape.setStroke(2, 0xFFCCCCCC);
        shape.setColor(0xFFFFFFFF);

        txtLogin.setBackground(shape);
        txtPass.setBackground(shape);

        showPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isPasswordVisible) {
                    txtPass.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    showPass.setImageResource(R.drawable.closeeye); 
                } else {
                    txtPass.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                    showPass.setImageResource(R.drawable.openeye);
                }

                txtPass.setSelection(txtPass.length());
                isPasswordVisible = !isPasswordVisible;
            }
        });

        btnEnter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                  try {

                      String login = txtLogin.getText().toString(), senha = txtPass.getText().toString();
                        if(login.isEmpty() || senha.isEmpty()){
                            throw new Exception("Preencha todos os campos");
                        }

                        if(!login.equals("admin") || !senha.equals("admin")){
                            throw new Exception("Login e/ou senha errada playboy. Tenta de novo porra");
                        }

                        customToast.show("Login realizado com sucesso", Toast.LENGTH_LONG, "#00FF00", "success");

                        btnEnter.setEnabled(false);

                      Intent it = new Intent(MainActivity.this, LoginActivity.class);
                      new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
                          @Override
                          public void run() {
                              startActivity(it);
                          }
                      },2000);


                  }   catch(Exception e){
                      customToast.show(e.getMessage(), Toast.LENGTH_LONG, "#FF0000", "error");
                }
            }
        });

    }
}
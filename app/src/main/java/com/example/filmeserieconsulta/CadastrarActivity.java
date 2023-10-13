package com.example.filmeserieconsulta;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RatingBar;
import android.widget.Spinner;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class CadastrarActivity extends AppCompatActivity {
    EditText editTextNomeFilme;
    Spinner spinnerGenero;
    RadioButton radioButtonFilme, radioButtonSerie;
    RatingBar ratingBarNota;
    Button buttonCadastrar, buttonVoltar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastrar);

        xmlJava();
        Util.carregarGeneros(CadastrarActivity.this, spinnerGenero);

        buttonCadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean validar = Util.validarFormulario(CadastrarActivity.this, editTextNomeFilme, spinnerGenero, radioButtonFilme, radioButtonSerie);
                if(validar){
                    SerieFilmeDao dao = new SerieFilmeDao(CadastrarActivity.this);
                    SerieFilmeModel model = new SerieFilmeModel();

                    model.nome = editTextNomeFilme.getText().toString().trim();
                    model.categoria = spinnerGenero.getSelectedItem().toString();
                    model.tipo = (radioButtonFilme.isChecked()) ? "Filme" : "Série";
                    model.nota = ratingBarNota.getRating();
                    
                    long qntdLinhas = dao.inserir(model);
                    
                    if(qntdLinhas > 0){
                        Util.myToasts(CadastrarActivity.this, "Cadastrado");
                    } else {
                        Util.myToasts(CadastrarActivity.this, "Erro");
                    }
                    Intent main = new Intent(CadastrarActivity.this, MainActivity.class);
                    startActivity(main);
                }
            }
        });
        buttonVoltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent main = new Intent(CadastrarActivity.this, MainActivity.class);
                startActivity(main);
            }
        });
    }
    private void xmlJava() {
        editTextNomeFilme = findViewById(R.id.editTextNomeFilme);
        spinnerGenero = findViewById(R.id.spinnerGeneroFilme);
        radioButtonFilme = findViewById(R.id.radioButtonFilme);
        radioButtonSerie = findViewById(R.id.radioButtonSerie);
        ratingBarNota = findViewById(R.id.ratingBarNota);
        buttonCadastrar = findViewById(R.id.buttonCadastrar);
        buttonVoltar = findViewById(R.id.buttonVoltarCadastrar);
    }
}
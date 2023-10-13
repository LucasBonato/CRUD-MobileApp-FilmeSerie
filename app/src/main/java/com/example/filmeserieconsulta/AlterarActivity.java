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

public class AlterarActivity extends AppCompatActivity {
    EditText editTextNomeFilmeAlterar;
    Spinner spinnerGeneroAlterar;
    RadioButton radioButtonFilmeAlterar, radioButtonSerieAlterar;
    RatingBar ratingBarNotaAlterar;
    Button buttonAlterar, buttonVoltar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alterar);

        xmlJava();
        Util.carregarGeneros(AlterarActivity.this, spinnerGeneroAlterar);
        carregarInformacoes();

        buttonAlterar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean validar = Util.validarFormulario(AlterarActivity.this, editTextNomeFilmeAlterar, spinnerGeneroAlterar, radioButtonFilmeAlterar, radioButtonSerieAlterar);
                if(validar){
                    SerieFilmeDao dao = new SerieFilmeDao(AlterarActivity.this);
                    SerieFilmeModel model = new SerieFilmeModel();

                    model.id = Util.model.id;
                    model.nome = editTextNomeFilmeAlterar.getText().toString().trim();
                    model.categoria = spinnerGeneroAlterar.getSelectedItem().toString();
                    model.tipo = (radioButtonFilmeAlterar.isChecked()) ? "Filme" : "Série";
                    model.nota = ratingBarNotaAlterar.getRating();

                    long qntdLinhas = dao.alterar(model);

                    if(qntdLinhas > 0){
                        Util.myToasts(AlterarActivity.this, "Cadastrado");
                    } else {
                        Util.myToasts(AlterarActivity.this,"Erro");
                    }
                    Intent main = new Intent(AlterarActivity.this, MainActivity.class);
                    startActivity(main);
                }
            }
        });
        buttonVoltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent main = new Intent(AlterarActivity.this, MainActivity.class);
                startActivity(main);
            }
        });
    }
    private void carregarInformacoes() {
        editTextNomeFilmeAlterar.setText(Util.model.nome);
        ratingBarNotaAlterar.setRating((float) Util.model.nota);
        for(int i = 0; i < Util.vetorGeneros.size(); i++){
            if(Util.vetorGeneros.get(i).equals(Util.model.categoria)){
                spinnerGeneroAlterar.setSelection(i);
            }
        }
        if(Util.model.tipo.equals("Filme")){
            radioButtonFilmeAlterar.setChecked(true);
        } else {
            radioButtonSerieAlterar.setChecked(true);
        }
    }
    private void xmlJava() {
        editTextNomeFilmeAlterar = findViewById(R.id.editTextNomeFilmeAlterar);
        spinnerGeneroAlterar = findViewById(R.id.spinnerGeneroFilmeAlterar);
        radioButtonFilmeAlterar = findViewById(R.id.radioButtonFilmeAlterar);
        radioButtonSerieAlterar = findViewById(R.id.radioButtonSerieAlterar);
        ratingBarNotaAlterar = findViewById(R.id.ratingBarNotaAlterar);
        buttonAlterar = findViewById(R.id.buttonAlterar);
        buttonVoltar = findViewById(R.id.buttonVoltarAlterar);
    }
}
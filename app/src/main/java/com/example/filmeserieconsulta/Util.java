package com.example.filmeserieconsulta;

import android.content.Context;
import android.content.Intent;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;

public class Util {
    static SerieFilmeModel model = new SerieFilmeModel();
    static String[] generos = {"Selecione o Gênero", "Ação", "Drama", "Sci-fi", "Comédia", "Terror"};
    static ArrayList<String> vetorGeneros;
    static void carregarGeneros(Context context, Spinner spinner){
        vetorGeneros = new ArrayList<>();

        vetorGeneros.addAll(Arrays.asList(Util.generos));

        ArrayAdapter adapter = new ArrayAdapter(context, android.R.layout.simple_spinner_dropdown_item, vetorGeneros);
        spinner.setAdapter(adapter);
    }
    static boolean validarFormulario(Context context, EditText editText, Spinner spinner, RadioButton radioButtonOne, RadioButton radioButtonTwo){
        String nomeFilme = editText.getText().toString().trim();
        if(nomeFilme.length() < 2){
            editText.setError("Minimo de 2 letras!");
            return false;
        } else if(spinner.getSelectedItemPosition() == 0) {
            myToasts(context,"Selecione um Gênero");
            return false;
        } else if(!(radioButtonOne.isChecked() || radioButtonTwo.isChecked())) {
            myToasts(context,"Selecione um tipo");
            return false;
        }
        return true;
    }
    static void myToasts(Context context, String message){
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }
}
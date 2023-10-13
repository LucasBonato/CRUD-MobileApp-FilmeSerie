package com.example.filmeserieconsulta;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    EditText editTextPesquisar;
    Spinner spinnerSelecionarGenero;
    Button buttonCadastrarFilme;
    ListView listViewFilmesCadastrado;
    private ArrayList<SerieFilmeModel> arrayListModel;
    private final SerieFilmeDao DAO = new SerieFilmeDao(MainActivity.this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        xmlJava();
        Util.carregarGeneros(MainActivity.this, spinnerSelecionarGenero);

        editTextPesquisar.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {}
            @Override
            public void afterTextChanged(Editable editable) {
                arrayListModel = DAO.consultarPorNome(editTextPesquisar.getText().toString());
                atualizarListView();
            }
        });
        spinnerSelecionarGenero.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String textGenre = spinnerSelecionarGenero.getSelectedItem().toString();
                arrayListModel = (i != 0) ? DAO.consultarPorGenero(textGenre) : DAO.consultarTodos();
                atualizarListView();
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {}
        });
        listViewFilmesCadastrado.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Util.model = arrayListModel.get(i);
                Intent alterar = new Intent(MainActivity.this, AlterarActivity.class);
                startActivity(alterar);
            }
        });
        listViewFilmesCadastrado.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int position, long l) {
                AlertDialog.Builder msg = new AlertDialog.Builder(MainActivity.this);
                msg.setMessage("Você deseja apagar esse item?");
                msg.setPositiveButton("Sim", excluir(position));
                msg.setNegativeButton("Não", null);
                msg.show();
                return true;
            }
        });
        buttonCadastrarFilme.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent cadastrar = new Intent(MainActivity.this, CadastrarActivity.class);
                startActivity(cadastrar);
            }
        });
    }
    private DialogInterface.OnClickListener excluir(int position) {
        return new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                SerieFilmeModel model = arrayListModel.get(position);
                long qntdLinhas = DAO.deletar(model);
                if(qntdLinhas <= 0){
                    Util.myToasts(MainActivity.this, "Erro ao excluir item!");
                }
                arrayListModel = DAO.consultarTodos();
                atualizarListView();
            }
        };
    }
    private void atualizarListView() {
        ArrayAdapter adapter = new ArrayAdapter(
                MainActivity.this,
                android.R.layout.simple_list_item_1,
                arrayListModel
        );
        listViewFilmesCadastrado.setAdapter(adapter);
    }
    private void xmlJava() {
        editTextPesquisar = findViewById(R.id.editTextPesquisar);
        spinnerSelecionarGenero = findViewById(R.id.spinnerSelecionarGenero);
        listViewFilmesCadastrado = findViewById(R.id.listViewFilmesCadastrados);
        buttonCadastrarFilme = findViewById(R.id.buttonCadastrarFilme);
    }
}
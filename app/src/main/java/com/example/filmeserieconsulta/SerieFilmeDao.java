package com.example.filmeserieconsulta;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
public class SerieFilmeDao extends SQLiteOpenHelper {
    private final  String TABELA = "tb_serie_filme";
    public SerieFilmeDao(Context context) {
        super(context, "db_serie_filme", null, 1);
    }
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        //chamado so uma vez, quando o db nao existe
        String comando = "create table " + TABELA + "(" +
                "id integer primary key," +
                "nome varchar(100) not null," +
                "categoria varchar(500)," +
                "tipo varchar(10),"+
                "nota decimal(3,1))";

        sqLiteDatabase.execSQL(comando);
    }
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        /*
        String comando = "ALTER TABLE " + TABELA + " ADD collumn teste";
        sqLiteDatabase.execSQL();
        */
    }
    public long inserir(SerieFilmeModel serieFilmeModel){
        return getWritableDatabase().insertOrThrow(TABELA, null, definirValores(serieFilmeModel));
    }
    public long alterar(SerieFilmeModel serieFilmeModel){
        String where = "id = ?";
        String[] whereValues = {serieFilmeModel.id + ""};

        return getWritableDatabase().update(TABELA, definirValores(serieFilmeModel), where, whereValues);
    }
    public long deletar(SerieFilmeModel serieFilmeModel){
        String where = "id = ?";
        String[] whereValues = {serieFilmeModel.id + ""};

        return getWritableDatabase().delete(TABELA, where, whereValues);
    }
    public ArrayList<SerieFilmeModel> consultarTodos(){
        String comando = "SELECT * FROM " + TABELA;
        return select(comando);
    }
    public ArrayList<SerieFilmeModel> consultarPorNome(String nome){
        String comando = "SELECT * FROM " + TABELA + " WHERE nome LIKE '%" + nome + "%'";
        return select(comando);
    }
    public ArrayList<SerieFilmeModel> consultarPorGenero(String genero){
        String comando = "SELECT * FROM " + TABELA + " WHERE categoria = '" + genero + "'";
        return select(comando);
    }
    private ArrayList<SerieFilmeModel> select(String comando){
        ArrayList<SerieFilmeModel> arrayListFilme = new ArrayList<>();
        Cursor cursor =  getReadableDatabase().rawQuery(comando, null);
        //cursor representa uma tabela
        while(cursor.moveToNext()) {
            SerieFilmeModel model = new SerieFilmeModel();
            model.id = cursor.getLong(0);
            model.nome = cursor.getString(1);
            model.categoria = cursor.getString(2);
            model.tipo = cursor.getString(3);
            model.nota = cursor.getDouble(4);
            arrayListFilme.add(model);
        }
        cursor.close();
        return arrayListFilme;
    }
    private ContentValues definirValores(SerieFilmeModel model){
        ContentValues values = new ContentValues();
        values.put("nome", model.nome);
        values.put("categoria", model.categoria);
        values.put("tipo", model.tipo);
        values.put("nota", model.nota);
        return values;
    }
}
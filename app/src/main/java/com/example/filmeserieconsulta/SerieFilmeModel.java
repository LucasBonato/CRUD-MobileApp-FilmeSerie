package com.example.filmeserieconsulta;

import androidx.annotation.NonNull;

public class SerieFilmeModel {
    public long id;
    public String nome, categoria, tipo;
    public double nota;

    @NonNull
    @Override
    public String toString() {
        return nome + " - " + categoria + " - " + nota + " - (" + tipo + ")";
    }
}
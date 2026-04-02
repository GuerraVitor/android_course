package com.example.minhaagenda.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;
import java.util.List;

@Dao
public interface ContatoDAO {
    @Insert
    void inserirContato(Contato contato);

    @Update
    void alteraContato(Contato contato);

    @Delete
    void apagarContato(Contato contato);

    @Query("SELECT * FROM Contatos ORDER BY nome ASC")
    List<Contato> getLista();

    @Query("SELECT * FROM Contatos WHERE telefone = :telefone LIMIT 1")
    Contato buscaPorTelefone(String telefone);
}

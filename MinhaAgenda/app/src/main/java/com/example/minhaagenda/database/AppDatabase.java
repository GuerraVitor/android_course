package com.example.minhaagenda.database;

import android.content.Context;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {Contato.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase{

    public abstract ContatoDAO contatoDAO();

    private static volatile AppDatabase INSTANCE;

    public static AppDatabase getDatabase(final Context ccontext){
        if (INSTANCE == null) {
            synchronized (AppDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(ccontext.getApplicationContext(),
                            AppDatabase.class, "banco_agenda_contatos")
                            .allowMainThreadQueries()
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}

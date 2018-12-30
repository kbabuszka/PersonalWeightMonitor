package net.babuszka.personalweightmonitor.model.db;

import android.content.Context;

import net.babuszka.personalweightmonitor.model.Weight;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = Weight.class, version = 1)
public abstract class WeightDatabase extends RoomDatabase {

    private static WeightDatabase instance;
    public abstract WeightDao weigthDao();

    public static synchronized WeightDatabase getInstance(Context context) {
        if(instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                    WeightDatabase.class, "weight_database")
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return instance;
    }
}

package net.babuszka.personalweightmonitor.data.db;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;
import android.content.Context;

import net.babuszka.personalweightmonitor.data.model.Weight;

@Database(entities = {Weight.class}, version = 1, exportSchema = false)
@TypeConverters({Converters.class})
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

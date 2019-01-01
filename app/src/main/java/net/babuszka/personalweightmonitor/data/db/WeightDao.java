package net.babuszka.personalweightmonitor.data.db;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import net.babuszka.personalweightmonitor.data.model.Weight;

import java.util.List;

@Dao
public interface WeightDao {
    @Insert
    void insert(Weight weight);

    @Update
    void update(Weight weight);

    @Delete
    void delete(Weight weight);

    @Query("SELECT * FROM weight_table ORDER BY date DESC")
    LiveData<List<Weight>> getAllWeight();
}

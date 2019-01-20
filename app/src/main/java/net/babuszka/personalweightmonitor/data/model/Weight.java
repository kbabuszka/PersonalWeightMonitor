package net.babuszka.personalweightmonitor.data.model;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import java.time.LocalDate;


@Entity(tableName = "weight_table")
public class Weight {

    @PrimaryKey(autoGenerate = true)
    private int id;
    private Double weight;
    private LocalDate date;

    public Weight(Double weight, LocalDate date) {
        this.weight = weight;
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public Double getWeight() {
        return weight;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setId(int id) {
        this.id = id;
    }
}

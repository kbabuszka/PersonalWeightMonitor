package net.babuszka.personalweightmonitor.model;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import java.util.Date;


@Entity(tableName = "weight_table")
public class Weight {

    @PrimaryKey(autoGenerate = true)
    private int id;
    private Double weight;
    private Date date;

    public Weight(Double weight, Date date) {
        this.weight = weight;
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public Double getWeight() {
        return weight;
    }

    public Date getDate() {
        return date;
    }

    public void setId(int id) {
        this.id = id;
    }
}

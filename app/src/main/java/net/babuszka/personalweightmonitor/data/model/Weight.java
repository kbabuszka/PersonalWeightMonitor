package net.babuszka.personalweightmonitor.model;

import java.util.Date;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

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

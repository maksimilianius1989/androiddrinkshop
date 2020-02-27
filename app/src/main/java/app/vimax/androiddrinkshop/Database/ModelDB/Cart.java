package app.vimax.androiddrinkshop.Database.ModelDB;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "Cart")
public class Cart {
    @NonNull
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name="id")
    public int id;

    @ColumnInfo(name="name")
    public String name;

    @ColumnInfo(name="amount")
    public int amount;

    @ColumnInfo(name="price")
    public double price;

    @ColumnInfo(name="sugar")
    public int sugar;

    @ColumnInfo(name="ice")
    public int ice;

    @ColumnInfo(name="toppingExtras")
    public String toppingExtras;
}
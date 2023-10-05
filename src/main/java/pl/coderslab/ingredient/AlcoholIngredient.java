package pl.coderslab.ingredient;

import pl.coderslab.drink.Drink;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "alcohol_ingredients")
public class AlcoholIngredient {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String alcoholType;
    private int volumeMillilitres;
    //private double price;
    @ManyToMany(mappedBy = "alcoholIngredients")
    List<Drink> drinks = new ArrayList<>();

    public AlcoholIngredient() {
    }

    public Long getId() {
        return id;
    }

    public List<Drink> getDrinks() {
        return drinks;
    }

    public void setDrinks(List<Drink> drinks) {
        this.drinks = drinks;
    }

    public String getAlcoholType() {
        return alcoholType;
    }

    public void setAlcoholType(String alcoholType) {
        this.alcoholType = alcoholType;
    }

    public int getVolumeMillilitres() {
        return volumeMillilitres;
    }

    public void setVolumeMillilitres(int volumeMillilitres) {
        this.volumeMillilitres = volumeMillilitres;
    }
}

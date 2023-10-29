package pl.coderslab.ingredient;

import pl.coderslab.drink.Drink;

import javax.persistence.*;
import java.util.*;

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

    @Override
    public String toString() {
        return alcoholType + ", " + volumeMillilitres;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AlcoholIngredient)) return false;
        AlcoholIngredient that = (AlcoholIngredient) o;
        return volumeMillilitres == that.volumeMillilitres && id.equals(that.id) && alcoholType.equals(that.alcoholType);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, alcoholType, volumeMillilitres);
    }
}

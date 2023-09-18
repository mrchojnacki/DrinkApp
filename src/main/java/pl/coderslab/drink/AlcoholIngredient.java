package pl.coderslab.drink;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "alcohol_ingredients")
public class AlcoholIngredient {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String alcoholType;
    private int volumeMillilitres;
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

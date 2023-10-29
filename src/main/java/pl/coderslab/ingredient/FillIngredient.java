package pl.coderslab.ingredient;

import pl.coderslab.drink.Drink;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "fill_ingredients")
public class FillIngredient {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String fill;
    private String amount;
    // private Double price;

    @ManyToMany(mappedBy = "fillIngredients")
    private List<Drink> drinkList = new ArrayList<>();


    public FillIngredient() {
    }

    public Long getId() {
        return id;
    }


    public String getFill() {
        return fill;
    }

    public void setFill(String fill) {
        this.fill = fill;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public List<Drink> getDrinkList() {
        return drinkList;
    }

    public void setDrinkList(List<Drink> drinkList) {
        this.drinkList = drinkList;
    }

    @Override
    public String toString() {
        return fill + ", " + amount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof FillIngredient)) return false;
        FillIngredient that = (FillIngredient) o;
        return id.equals(that.id) && fill.equals(that.fill) && amount.equals(that.amount);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, fill, amount);
    }
}

package pl.coderslab.drink;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "fill_ingredients")
public class FillIngredient {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    String fill;
    String amount;

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
}

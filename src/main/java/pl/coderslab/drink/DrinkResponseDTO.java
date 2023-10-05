package pl.coderslab.drink;

import pl.coderslab.ingredient.AlcoholIngredient;
import pl.coderslab.ingredient.FillIngredient;

import java.util.List;

public class DrinkResponseDTO {
    private Long id;
    private String name;
    private String method;
    private List<AlcoholIngredient> alcoholIngredientList;
    private List<FillIngredient> fillIngredientList;

    public DrinkResponseDTO() {
    }

    public DrinkResponseDTO(Long id, String name, String method, List<AlcoholIngredient> alcoholIngredientList, List<FillIngredient> fillIngredientList) {
        this.id = id;
        this.name = name;
        this.method = method;
        this.alcoholIngredientList = alcoholIngredientList;
        this.fillIngredientList = fillIngredientList;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public List<AlcoholIngredient> getAlcoholIngredientList() {
        return alcoholIngredientList;
    }

    public void setAlcoholIngredientList(List<AlcoholIngredient> alcoholIngredientList) {
        this.alcoholIngredientList = alcoholIngredientList;
    }

    public List<FillIngredient> getFillIngredientList() {
        return fillIngredientList;
    }

    public void setFillIngredientList(List<FillIngredient> fillIngredientList) {
        this.fillIngredientList = fillIngredientList;
    }
}

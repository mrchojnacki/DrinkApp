package pl.coderslab.drink;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import pl.coderslab.ingredient.*;
import pl.coderslab.user.UserRepository;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/drink")
@Transactional
public class DrinkController {
    private DrinkService drinkService;
    private IngredientService ingredientService;
    private UserRepository userRepository;

    public DrinkController(DrinkService drinkService, IngredientService ingredientService, UserRepository userRepository) {
        this.drinkService = drinkService;
        this.userRepository = userRepository;
        this.ingredientService = ingredientService;
    }


    @GetMapping("/list")
    @ResponseBody
    public String listAllDrinkFromDb(Model model) {
        model.addAttribute("listOfAllDrinks", drinkService.getDrinkResponseDTOList());
        return "lista";
    }

    /*    localhost:8080/add/drink/malibu/zrobdrinka/5/malibu/200   */
/*    @GetMapping("/add/drink/{name}/{method}/{rating}/{alcohol}/{volume}")
    @ResponseBody
    public String mockCreateNewDrink(@PathVariable String name,
                                 @PathVariable String method,
                                 @PathVariable String rating,
                                 @PathVariable String alcohol,
                                 @PathVariable String volume) {
        Drink drink = new Drink();
        AlcoholIngredient alcoholIngredient = new AlcoholIngredient();
        List<AlcoholIngredient> alcoholIngredientList = new ArrayList<>();
        drink.setName(name);
        drink.setMethod(method);
        drink.setRating(Double.parseDouble(rating));
        alcoholIngredient.setAlcoholType(alcohol);
        alcoholIngredient.setVolumeMillilitres(Integer.parseInt(volume));
        alcoholIngredientList.add(alcoholIngredient);
        drink.setAlcoholIngredients(alcoholIngredientList);
        drinkRepository.createDrink(drink);
        return "created!";
    }*/
    @GetMapping("/addNewDrink")
    public String createNewDrinkForm(Model model) {
        List<AlcoholIngredient> alcoholIngredientList = ingredientService.getAllAlcoholIngredients();
        List<FillIngredient> fillIngredientList = ingredientService.getAllFillIngredients();
        model.addAttribute("alcoholIngredients", alcoholIngredientList);
        model.addAttribute("fillIngredients", fillIngredientList);
        return "/form/addNewDrink.jsp";
    }

    @PostMapping("/addNewDrink")
    @ResponseBody
    public String createNewDrink(@RequestParam String name,
                                 @RequestParam String method,
                                 @RequestParam String[] existingAlcoholIds,
                                 @RequestParam String[] existingFillIds,
                                 @RequestParam String[] newAlcoholName,
                                 @RequestParam String[] newAlcoholVolume,
                                 @RequestParam String[] newFillName,
                                 @RequestParam String[] newFillAmount) {
        List<AlcoholIngredient> newAlcoholIngredients = castingNewAlcoholIngredients(newAlcoholName, newAlcoholVolume);
        List<FillIngredient> newFillIngredients = castingNewFillIngredients(newFillName, newFillAmount);
        DrinkRequestDTO drinkRequestDTO = new DrinkRequestDTO(
                name,
                method,
                newAlcoholIngredients,
                newFillIngredients,
                existingAlcoholIds,
                existingFillIds);
        drinkService.addDrinkToDb(drinkRequestDTO);
        return "udało sie!"; //"redirect:drink/list;
    }

    private List<AlcoholIngredient> castingNewAlcoholIngredients (String[] newAlcoholName, String [] newAlcoholVolume) {
        List<AlcoholIngredient> newAlcoholIngredients = new ArrayList<>();
        for (int i = 0; i < newAlcoholName.length; i++) {
            AlcoholIngredient alcoholIngredient = new AlcoholIngredient();
            alcoholIngredient.setAlcoholType(newAlcoholName[i]);
            alcoholIngredient.setVolumeMillilitres(Integer.parseInt(newAlcoholVolume[i]));
            newAlcoholIngredients.add(alcoholIngredient);
        }
        return newAlcoholIngredients;
    }
    private List<FillIngredient> castingNewFillIngredients(String[] newFillName, String[] newFillAmount) {
        List<FillIngredient> newFillIngredients = new ArrayList<>();
        for (int i = 0; i < newFillName.length; i++) {
            FillIngredient fillIngredient = new FillIngredient();
            fillIngredient.setFill(newFillName[i]);
            fillIngredient.setAmount(newFillAmount[i]);
            newFillIngredients.add(fillIngredient);
        }
        return newFillIngredients;
    }

/*    @PostMapping("/addNewDrink")
    @ResponseBody
    public String createNewDrink(@ModelAttribute("drinkDTO") DrinkDTO drinkDTO, Model model) {
        *//*Long userId = (Long) model.asMap().get("userId");*//*
        Long userId = 1L;
        Drink drink = new Drink();
        *//*drink.setUser(userRepository.findUserById(userId));*//*
        drink.setCreatedOn(LocalDateTime.now());
        drink.setLastUpdatedOn(LocalDateTime.now());
        drink.setRatingCount(0L);
        drink.setRating(0.0);
        drink.setName(drinkDTO.getName());
        drink.setMethod(drinkDTO.getMethod());
*//*
        List<AlcoholIngredient> mergedAlcoholIngredients = mergeAllAlcoholIngredients(drinkDTO.getAlcoholIngredients());
*//*
        drink.setAlcoholIngredients(drinkDTO.getAlcoholIngredients());
*//*
        List<FillIngredient> mergedFillerIngredients = mergeAllFillerIngredients(drinkDTO.getFillIngredients());
*//*
        drink.setFillIngredients(drinkDTO.getFillIngredients());
        drinkRepository.createDrink(drink);
        return "udalo sie!";
    }*/
    /*private List<AlcoholIngredient> mergeAllAlcoholIngredients(List<AlcoholIngredient> alcoholIngredients) {
        List<AlcoholIngredient> mergedAlcoholIngredients = new ArrayList<>();
        for (AlcoholIngredient alcoholIngredient : alcoholIngredients) {
            AlcoholIngredient mergedAlcoholIngredient = alcoholIngredientRepository.updateAlcoholIngredient(alcoholIngredient);
            mergedAlcoholIngredients.add(mergedAlcoholIngredient);
        }
        return mergedAlcoholIngredients;
    }
    private List<FillIngredient> mergeAllFillerIngredients(List<FillIngredient> fillIngredients) {
        List<FillIngredient> mergedFillerIngredientList = new ArrayList<>();
        for (FillIngredient fillIngredient: fillIngredients) {
            FillIngredient mergedFillIngredient = fillerIngredientRepository.updateFillerIngredient(fillIngredient);
            mergedFillerIngredientList.add(mergedFillIngredient);
        }
        return mergedFillerIngredientList;
    }*/
}

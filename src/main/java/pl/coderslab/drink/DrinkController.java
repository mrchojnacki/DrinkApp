package pl.coderslab.drink;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import pl.coderslab.comments.CommentService;
import pl.coderslab.ingredient.*;
import pl.coderslab.rating.RatingService;
import pl.coderslab.user.UserRepository;
import pl.coderslab.user.UserService;

import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Controller
@Transactional
@MultipartConfig
public class DrinkController {
    private final DrinkService drinkService;
    private final IngredientService ingredientService;
    private final UserService userService;
    private final CommentService commentService;
    private final RatingService ratingService;

    public DrinkController(DrinkService drinkService, IngredientService ingredientService, UserService userService, CommentService commentService, RatingService ratingService) {
        this.drinkService = drinkService;
        this.ingredientService = ingredientService;
        this.userService = userService;
        this.commentService = commentService;
        this.ratingService = ratingService;
    }

    @GetMapping("/")
    public String landingPage(HttpSession session, Model model) {
        if (session.getAttribute("authenticatedUserId")==null) {
            return "/landing-page.jsp";
        } else {
            Long userId = (Long) session.getAttribute("authenticatedUserId");
            if(userService.findUserById(userId).getUserMadeDrinks().size()!=0) {
                model.addAttribute("lastDrink", drinkService.findLastDrinkOfUser((Long) session.getAttribute("authenticatedUserId")));
            }
            model.addAttribute("randomDrink", drinkService.getRandomDrinkResponseDTO());
        }
        return "/dashboard.jsp";
    }


    @GetMapping("/list")
    public String listAllDrinkFromDb(Model model) {
        model.addAttribute("pageOfDrinks", drinkService.getPagedAllDrinkResponseDTOList("1"));
        model.addAttribute("noOfPages", drinkService.getPagedAllDrinkResponseDTOList("1").getTotalPages());
        model.addAttribute("pageNumber", 1);
        return "/drinks/list-of-drinks.jsp";
    }

    @GetMapping("/list/page/{pageNumber}")
    public String nextPageOfDrinks(Model model, @PathVariable String pageNumber) {
        model.addAttribute("pageOfDrinks", drinkService.getPagedAllDrinkResponseDTOList(pageNumber));
        model.addAttribute("noOfPages", drinkService.getPagedAllDrinkResponseDTOList("1").getTotalPages());
        model.addAttribute("pageNumber", Integer.parseInt(pageNumber));
        return "/drinks/list-of-drinks.jsp";
    }

    @GetMapping("/userList")
    public String listUserDrinkFromDb(Model model, HttpSession sess) {
        if (sess.getAttribute("authenticatedUserId")==null) {
            return "/landing-page.jsp";
        }
        model.addAttribute("pageOfDrinks", drinkService.getPagedUserDrinkResponseDTOList("1", sess));
        model.addAttribute("noOfPages", drinkService.getPagedUserDrinkResponseDTOList("1", sess).getTotalPages());
        model.addAttribute("pageNumber", 1);
        model.addAttribute("userQuantity", userService.findUserMadeDrinkList((Long) sess.getAttribute("authenticatedUserId")).size());
        return "/drinks/list-of-user-made-drinks.jsp";
    }

    @GetMapping("/userList/page/{pageNumber}")
    public String nextPageOfUserDrinks(Model model, @PathVariable String pageNumber, HttpSession sess) {
        if (sess.getAttribute("authenticatedUserId")==null) {
            return "/landing-page.jsp";
        }
        model.addAttribute("pageOfDrinks", drinkService.getPagedUserDrinkResponseDTOList(pageNumber, sess));
        model.addAttribute("noOfPages", drinkService.getPagedUserDrinkResponseDTOList("1", sess).getTotalPages());
        model.addAttribute("pageNumber", Integer.parseInt(pageNumber));
        return "/drinks/list-of-user-made-drinks.jsp";
    }

    @GetMapping("/favList")
    public String listFavDrinkFromDb(Model model, HttpSession sess) {
        if (sess.getAttribute("authenticatedUserId")==null) {
            return "/landing-page.jsp";
        }
        model.addAttribute("pageOfDrinks", drinkService.getPagedFavoriteDrinkResponseDTOList("1", sess));
        model.addAttribute("noOfPages", drinkService.getPagedFavoriteDrinkResponseDTOList("1", sess).getTotalPages());
        model.addAttribute("pageNumber", 1);
        model.addAttribute("favQuantity", userService.getFavoriteDrinksOfUser((Long) sess.getAttribute("authenticatedUserId")).size());
        return "/drinks/list-of-fav-drinks.jsp";
    }

    @GetMapping("/favList/page/{pageNumber}")
    public String nextPageOfFavDrinks(Model model, @PathVariable String pageNumber, HttpSession sess) {
        if (sess.getAttribute("authenticatedUserId")==null) {
            return "/landing-page.jsp";
        }
        model.addAttribute("pageOfDrinks", drinkService.getPagedFavoriteDrinkResponseDTOList(pageNumber, sess));
        model.addAttribute("noOfPages", drinkService.getPagedFavoriteDrinkResponseDTOList("1", sess).getTotalPages());
        model.addAttribute("pageNumber", Integer.parseInt(pageNumber));
        return "/drinks/list-of-fav-drinks.jsp";
    }

    @GetMapping("/list/drink/{drinkId}")
    public String showDrink(@PathVariable String drinkId, Model model, HttpSession sess) {
        model.addAttribute("drink", drinkService.getDrinkResponseDTOFromId(drinkId));
        model.addAttribute("commentList", commentService.getCommentsToShow(drinkId));
        model.addAttribute("avgRating", ratingService.getAverageRatingForDrink(drinkId));
        model.addAttribute("isFavorite", drinkService.isFavorite(Long.parseLong(drinkId), sess));
        model.addAttribute("isUserMade", drinkService.isMadeByLoggedUser(drinkId, sess));
        return "/drinks/show-drink.jsp";
    }

    @PostMapping("/addComment")
    public String addCommentForDrink(@RequestParam String commentContent,
                                     @RequestParam String drinkId,
                                     HttpSession sess) {
        if (sess.getAttribute("authenticatedUserId")==null) {
            return "/landing-page.jsp";
        }
        commentService.addCommentForDrinkToDb(commentContent, drinkId, sess);
        return "redirect:/list/drink/"+drinkId;
    }

    @PostMapping("/addRating")
    public String addRating(@RequestParam String drinkId,
                            @RequestParam String rating,
                            HttpSession sess) {
        if (sess.getAttribute("authenticatedUserId")==null) {
            return "/landing-page.jsp";
        }
        drinkService.addNewRating(Long.parseLong(drinkId), Integer.parseInt(rating), sess);
        return "redirect:/list/drink/"+drinkId;
    }

    @PostMapping("/toggleToFavorites")
    public String addToFavorites(@RequestParam String drinkId,
                                 HttpSession sess) {
        if (sess.getAttribute("authenticatedUserId")==null) {
            return "/landing-page.jsp";
        }
        drinkService.toggleDrinkToFavorites(Long.parseLong(drinkId), sess);
        return "redirect:/list/drink/"+drinkId;
    }


    @GetMapping("/addNewDrink")
    public String createNewDrinkForm(Model model, HttpSession sess) {
        if (sess.getAttribute("authenticatedUserId")==null) {
            return "/landing-page.jsp";
        }
        model.addAttribute("alcoholIngredients", ingredientService.getAllAlcoholIngredients());
        model.addAttribute("fillIngredients", ingredientService.getAllFillIngredients());
        return "/form/addNewDrink.jsp";
    }

    @PostMapping("/addNewDrink")
    public String createNewDrink(@RequestParam String name,
                                 @RequestParam String method,
                                 @RequestParam (required=false) String[] existingAlcoholIds,
                                 @RequestParam (required=false) String[] existingFillIds,
                                 @RequestParam (required=false) String[] newAlcoholName,
                                 @RequestParam (required=false) String[] newAlcoholVolume,
                                 @RequestParam (required=false) String[] newFillName,
                                 @RequestParam (required=false) String[] newFillAmount,
                                 @RequestParam MultipartFile image,
                                 Model model,
                                 HttpSession sess) {
        List<AlcoholIngredient> newAlcoholIngredients = new ArrayList<>();

        model.addAttribute("alcoholIngredients", ingredientService.getAllAlcoholIngredients());
        model.addAttribute("fillIngredients", ingredientService.getAllFillIngredients());
        model.addAttribute("name", name);
        model.addAttribute("method", method);

        existingAlcoholIds = drinkService.removeEmptyStrings(existingAlcoholIds);
        existingFillIds = drinkService.removeEmptyStrings(existingFillIds);
        newAlcoholName = drinkService.removeEmptyStrings(newAlcoholName);
        newAlcoholVolume = drinkService.removeEmptyStrings(newAlcoholVolume);
        newFillName = drinkService.removeEmptyStrings(newFillName);
        newFillAmount = drinkService.removeEmptyStrings(newFillAmount);

        if (newAlcoholName!=null) {
            try {
                for (String s : newAlcoholVolume) {
                    Integer.parseInt(s);
                }
            } catch (NumberFormatException e) {
                model.addAttribute("parseError", "Insert a number in alcohol volume field");
                return "/form/addNewDrink.jsp";
            }
            newAlcoholIngredients = drinkService.castingNewAlcoholIngredients(newAlcoholName, newAlcoholVolume);
        }
        List<FillIngredient> newFillIngredients = new ArrayList<>();
        if (newFillName!=null) {
             newFillIngredients = drinkService.castingNewFillIngredients(newFillName, newFillAmount);
        }
        if (name.length()<3) {
            model.addAttribute("nameError", "Name of your drink should be at least 3 characters long");
            return "/form/addNewDrink.jsp";
        }
        if (drinkService.checkIfDrinkNameAlreadyInDb(name)) {
            model.addAttribute("nameTakenError", "Drink with this name already exists");
            return "/form/addNewDrink.jsp";
        }
        if (existingAlcoholIds==null&&existingFillIds==null&&newAlcoholName==null&&newFillName==null) {
            model.addAttribute("noIngredientsError", "Add at least one ingredient");
            return "/form/addNewDrink.jsp";
        }
        if (newAlcoholName.length!=newAlcoholVolume.length || newFillName.length!=newFillAmount.length) {
            model.addAttribute("nonInPairIngredientInputAmountError", "Make sure you have paired all new ingredients with their quantities");
            return "/form/addNewDrink.jsp";
        }
        DrinkRequestDTO drinkRequestDTO = new DrinkRequestDTO(
                name,
                method,
                newAlcoholIngredients,
                newFillIngredients,
                existingAlcoholIds,
                existingFillIds);
        drinkService.addDrinkToDb(drinkRequestDTO, sess);
        String drinkId = drinkService.getDrinkIdFromName(name).toString();
        if(!image.isEmpty()) {
            drinkService.saveImageToDirectory(image, name);
        }
        return "redirect:/list/drink/" + drinkId;
    }

    @GetMapping("/editDrink/{drinkId}")
    public String editDrinkGet(@PathVariable String drinkId, Model model, HttpSession sess) {
        if (sess.getAttribute("authenticatedUserId")==null) {
            return "/landing-page.jsp";
        }
        model.addAttribute("drink", drinkService.getDrinkResponseDTOFromId(drinkId));
        model.addAttribute("alcoholIngredients", ingredientService.getAllAlcoholIngredients());
        model.addAttribute("fillIngredients", ingredientService.getAllFillIngredients());
        return "/form/editDrink.jsp";
    }

    @PostMapping("/editDrink")
    public String editDrinkPost(@RequestParam String name,
                                @RequestParam String method,
                                @RequestParam (required=false) String[] existingAlcoholIds,
                                @RequestParam (required=false) String[] existingFillIds,
                                @RequestParam (required=false) String[] newAlcoholName,
                                @RequestParam (required=false) String[] newAlcoholVolume,
                                @RequestParam (required=false) String[] newFillName,
                                @RequestParam (required=false) String[] newFillAmount,
                                @RequestParam MultipartFile image,
                                @RequestParam String drinkId,
                                Model model,
                                HttpSession sess) {
        List<AlcoholIngredient> newAlcoholIngredients = new ArrayList<>();
        model.addAttribute("alcoholIngredients", ingredientService.getAllAlcoholIngredients());
        model.addAttribute("fillIngredients", ingredientService.getAllFillIngredients());
        model.addAttribute("drink", drinkService.getDrinkResponseDTOFromId(drinkId));
        if (newAlcoholName!=null) {
            try {
                for (String s : newAlcoholVolume) {
                    Integer.parseInt(s);
                }
            } catch (NumberFormatException e) {
                model.addAttribute("parseError", "Insert a number in alcohol volume field");
                return "/form/editDrink.jsp";
            }
            newAlcoholIngredients = drinkService.castingNewAlcoholIngredients(newAlcoholName, newAlcoholVolume);
        }
        List<FillIngredient> newFillIngredients = new ArrayList<>();
        if (newFillName!=null) {
            newFillIngredients = drinkService.castingNewFillIngredients(newFillName, newFillAmount);
        }
        if (name.length()<3) {
            model.addAttribute("nameError", "Name of your drink should be at least 3 characters long");
            return "/form/editDrink.jsp";
        }
        if (drinkService.checkIfDrinkNameAlreadyInDb(name) && !name.equals(drinkService.getDrinkResponseDTOFromId(drinkId).getName())) {
            model.addAttribute("nameTakenError", "Drink with this name already exists");
            return "/form/editDrink.jsp";
        }
        if (existingAlcoholIds==null&&existingFillIds==null&&newAlcoholName==null&&newFillName==null) {
            model.addAttribute("noIngredientsError", "Add at least one ingredient");
            return "/form/editDrink.jsp";
        }
        if (newAlcoholName.length!=newAlcoholVolume.length || newFillName.length!=newFillAmount.length) {
            model.addAttribute("nonInPairIngredientInputAmountError", "Make sure you have paired all new ingredients with their quantities");
            return "/form/editDrink.jsp";
        }
        DrinkRequestDTO drinkRequestDTO = new DrinkRequestDTO(
                name,
                method,
                newAlcoholIngredients,
                newFillIngredients,
                existingAlcoholIds,
                existingFillIds);
        drinkService.editDrinkToDb(drinkRequestDTO, sess, drinkId);
        if(!image.isEmpty()) {
            drinkService.saveImageToDirectory(image, name);
        }
        return "redirect:/list/drink/" + drinkId;
    }

    @GetMapping("/possibleDrink")
    public String possibleDrinkForm(Model model) {
        List<AlcoholIngredient> alcoholIngredientList = ingredientService.getAllAlcoholIngredients();
        List<FillIngredient> fillIngredientList = ingredientService.getAllFillIngredients();
        model.addAttribute("alcoholIngredients", alcoholIngredientList);
        model.addAttribute("fillIngredients", fillIngredientList);
        return "/form/possibleDrink.jsp";
    }
}

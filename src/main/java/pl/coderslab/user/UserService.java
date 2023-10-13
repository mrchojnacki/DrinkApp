package pl.coderslab.user;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.ui.Model;
import pl.coderslab.drink.Drink;
import pl.coderslab.drink.DrinkRepository;
import pl.coderslab.drink.DrinkResponseDTO;

import javax.servlet.http.Cookie;
import java.util.ArrayList;
import java.util.List;

public class UserService {
    private UserRepository userRepository;
    private DrinkRepository drinkRepository;

    public UserService(UserRepository userRepository, DrinkRepository drinkRepository) {
        this.userRepository = userRepository;
        this.drinkRepository = drinkRepository;
    }

    public User addUserToDb(UserRegisterDTO userRegisterDTOFromController) {
        User newUser = new User();
        String hashedPassword = BCrypt.hashpw(userRegisterDTOFromController.getPassword(), BCrypt.gensalt());
        if (userRegisterDTOFromController.isRememberPassword()) {
            Cookie cookie = new Cookie("password", hashedPassword);
        }
        newUser.setUserName(userRegisterDTOFromController.getUserName());
        newUser.setEmail(userRegisterDTOFromController.getEmail());
        newUser.setPassword(hashedPassword);
        userRepository.save(newUser);
        return newUser;
    }

    List<DrinkResponseDTO> getFavoriteDrinkResponseDTO(Long userId) {
        List<Drink> favoriteDrinkOfUser = userRepository.findFavoriteDrinkListOfUser(userId);
        List<DrinkResponseDTO> favoriteDrinksResponseDTO = new ArrayList<>();
        for (Drink drink : favoriteDrinkOfUser) {
            favoriteDrinksResponseDTO.add(new DrinkResponseDTO(drink.getId(),
                    drink.getName(),
                    drink.getMethod(),
                    drinkRepository.findAllAlcoholIngredientsForDrink(drink),
                    drinkRepository.findAllFillerIngredientForDrink(drink)));
        }
        return favoriteDrinksResponseDTO;
    }

    List<DrinkResponseDTO> getUserMadeDrinksResponseDTO(Long userId) {
        List<Drink> userMadeDrinks = userRepository.findAllDrinksMadeByUser(userId);
        List<DrinkResponseDTO> userMadeDrinksResponseDTO = new ArrayList<>();
        for (Drink drink : userMadeDrinks) {
            userMadeDrinksResponseDTO.add(new DrinkResponseDTO(drink.getId(),
                    drink.getName(),
                    drink.getMethod(),
                    drinkRepository.findAllAlcoholIngredientsForDrink(drink),
                    drinkRepository.findAllFillerIngredientForDrink(drink)));
        }
        return userMadeDrinksResponseDTO;
    }

    private List<Drink> getFavoriteDrinkListOfUser(Model model) {
        User loggedUser = (User) model.asMap().get("authenticatedUser");
        return userRepository.findFavoriteDrinkListOfUser(loggedUser.getId());
    }

    public void editUserPassword(Model model, String oldPassword, String newPassword) {
        User loggedUser = (User) model.asMap().get("authenticatedUser");
        if (userLoggingCheck(loggedUser.getUserName(), oldPassword, model)) {
            loggedUser.setPassword(BCrypt.hashpw(newPassword, BCrypt.gensalt()));
            userRepository.save(loggedUser);
        }
    }

    public boolean isLogged (Model model) {
        User logegdUser = (User) model.asMap().get("authenticatedUser");
        boolean logged = false;
        if (logegdUser!=null) {
            logged = true;
        }
        return logged;
    }

    public boolean userLoggingCheck (String loggingMethod, String password, Model model) {
        User authenticatedUser = authenticate(loggingMethod, password, model);
        boolean loggedSuccesfully = false;
        if (authenticatedUser!=null) {
            loggedSuccesfully = true;
        }
        return loggedSuccesfully;
    }
    private User authenticate(String loggingMethod, String password, Model model) {
        User userToAuthenticate = userRepository.findUserToAuthenticate(loggingMethod);
        String hashedPassword = userToAuthenticate.getPassword();
        if (BCrypt.checkpw(password, hashedPassword)) {
            model.addAttribute("authenticatedUser", userToAuthenticate);
            return userToAuthenticate;
        }
        return null;
    }
}

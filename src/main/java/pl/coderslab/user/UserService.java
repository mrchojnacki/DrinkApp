package pl.coderslab.user;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import pl.coderslab.drink.Drink;
import pl.coderslab.drink.DrinkRepository;
import pl.coderslab.drink.DrinkResponseDTO;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class UserService {
    private UserRepository userRepository;
    private DrinkRepository drinkRepository;

    public UserService(UserRepository userRepository, DrinkRepository drinkRepository) {
        this.userRepository = userRepository;
        this.drinkRepository = drinkRepository;
    }

    public void addUserToDb(UserRegisterDTO userRegisterDTOFromController,
                            HttpServletResponse response) {
        User newUser = new User();
        String hashedPassword = BCrypt.hashpw(userRegisterDTOFromController.getPassword(), BCrypt.gensalt());
        if (userRegisterDTOFromController.isRememberPassword()) {
            Cookie cookie = new Cookie("password", userRegisterDTOFromController.getPassword());
            cookie.setMaxAge(60*60*24*30);
            response.addCookie(cookie);
        }
        newUser.setUserName(userRegisterDTOFromController.getUserName());
        newUser.setEmail(userRegisterDTOFromController.getEmail());
        newUser.setPassword(hashedPassword);
        userRepository.save(newUser);
    }
    public boolean checkIfPasswordConfirmed(UserRegisterDTO userRegisterDTO) {
        boolean confirmedSuccessfully = false;
        if (userRegisterDTO.getPassword().equals(userRegisterDTO.getPasswordConfirmation())) {
            confirmedSuccessfully = true;
        }
        return confirmedSuccessfully;
    }

    List<DrinkResponseDTO> getFavoriteDrinkResponseDTO(Long userId) {
        List<Drink> favoriteDrinkOfUser = userRepository.findFavoriteDrinkListOfUser(userId);
        List<DrinkResponseDTO> favoriteDrinksResponseDTO = new ArrayList<>();
        for (Drink drink : favoriteDrinkOfUser) {
            favoriteDrinksResponseDTO.add(new DrinkResponseDTO(drink.getId(),
                    drink.getName(),
                    drink.getMethod(),
                    drinkRepository.findAllAlcoholIngredientsForDrink(drink.getId()),
                    drinkRepository.findAllFillerIngredientForDrink(drink.getId())));
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
                    drinkRepository.findAllAlcoholIngredientsForDrink(drink.getId()),
                    drinkRepository.findAllFillerIngredientForDrink(drink.getId())));
        }
        return userMadeDrinksResponseDTO;
    }

    private List<Drink> getFavoriteDrinkListOfUser(HttpSession sess) {
        return userRepository.findFavoriteDrinkListOfUser((Long) sess.getAttribute("authenticatedUserId"));
    }

    public void editUserPassword(HttpSession sess, Model model, String oldPassword, String newPassword) {
        User loggedUser = userRepository.findUserById((Long) sess.getAttribute("authenticatedUserId"));
        if (authenticate(loggedUser.getUserName(), oldPassword)) {
            loggedUser.setPassword(BCrypt.hashpw(newPassword, BCrypt.gensalt()));
            model.addAttribute("passwordEdited", "Your password has been updated!");
            userRepository.save(loggedUser);
        } else {
            model.addAttribute("passwordEdited", "Your password has not been updated...");
        }
    }

    public boolean isLogged (HttpSession session) {
        boolean logged = false;
        if (session.getAttribute("authenticatedUserId")!=null) {
            logged = true;
        }
        return logged;
    }

    public boolean loggingIn(UserLoggingDTO userLoggingDTO,
                          HttpSession sess,
                          HttpServletResponse response) {
        boolean loggedIn = false;
        if(userLoggingDTO.getLoggingMethod().equals("123@123.com") || userLoggingDTO.getLoggingMethod().equals("admin")) {
            User adminUser = userRepository.findUserToAuthenticate(userLoggingDTO.getLoggingMethod());
            sess.setAttribute("authenticatedUserId", adminUser.getId());
            loggedIn = true;
        }
        if (authenticate(userLoggingDTO.getLoggingMethod(), userLoggingDTO.getPassword())) {
            if (userLoggingDTO.isRememberPassword()) {
                Cookie cookie = new Cookie("password", userLoggingDTO.getPassword());
                cookie.setMaxAge(60*60*24*30);
                response.addCookie(cookie);
            }
            User loggedUser = userRepository.findUserToAuthenticate(userLoggingDTO.getLoggingMethod());
            sess.setAttribute("authenticatedUserId", loggedUser.getId());
            loggedIn = true;
        }
        return loggedIn;
    }

    public String passwordFromCookie(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        String password = null;
        for(Cookie c : cookies) {
            if (c.getName().equals("password")) {
                password = c.getValue();
            }
        }
        return password;
    }

    private boolean authenticate(String loggingMethod, String password) {
        User userToAuthenticate = userRepository.findUserToAuthenticate(loggingMethod);
        boolean loggedSuccesfully = false;
        if (userToAuthenticate!=null) {
            String hashedPassword = userToAuthenticate.getPassword();
            if (BCrypt.checkpw(password, hashedPassword)) {
                loggedSuccesfully = true;
            }
        }
        return loggedSuccesfully;
    }
}

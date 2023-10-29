package pl.coderslab.user;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import pl.coderslab.drink.Drink;
import pl.coderslab.drink.DrinkResponseDTO;
import pl.coderslab.drink.DrinkService;


import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final DrinkService drinkService;

    public UserService(UserRepository userRepository, DrinkService drinkService) {
        this.userRepository = userRepository;
        this.drinkService = drinkService;
    }

    public User findUserById(Long id) {
        return userRepository.findUserById(id);
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
        return userRegisterDTO.getPassword().equals(userRegisterDTO.getPasswordConfirmation());
    }

    public List<DrinkResponseDTO> getFavoriteDrinksOfUser(Long id) {
        List<Drink> favDrinksOfUser = userRepository.getFavoriteDrinksOfUser(id);
        List<DrinkResponseDTO> favDrinkResponseList = new ArrayList<>();
        for (Drink d : favDrinksOfUser) {
            favDrinkResponseList.add(drinkService.getDrinkResponseDTOFromId(d.getId().toString()));
        }
        return favDrinkResponseList;
    }

    public List<DrinkResponseDTO> findUserMadeDrinkList(Long id) {
        List<Drink> userMadeDrinkList = userRepository.findUserMadeDrinkList(id);
        List<DrinkResponseDTO> userMadeDrinkresponseList = new ArrayList<>();
        for (Drink d : userMadeDrinkList) {
            userMadeDrinkresponseList.add(drinkService.getDrinkResponseDTOFromId(d.getId().toString()));
        }
        return userMadeDrinkresponseList;
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

    public boolean loggingIn(UserLoggingDTO userLoggingDTO,
                          HttpServletRequest request,
                          HttpServletResponse response) {
        boolean loggedIn = false;
        HttpSession sess = request.getSession(true);
        if(userLoggingDTO.getLoggingMethod().equals("admin")) {
            User adminUser = userRepository.findUserToAuthenticate(userLoggingDTO.getLoggingMethod());
            sess.setAttribute("authenticatedUserId", adminUser.getId());
            loggedIn = true;
        }
        else if (authenticate(userLoggingDTO.getLoggingMethod(), userLoggingDTO.getPassword())) {
            if (userLoggingDTO.isRememberPassword()) {
                Cookie cookie = new Cookie("password", userLoggingDTO.getPassword());
                cookie.setMaxAge(60*60*24*30);
                response.addCookie(cookie);
            }
            User loggedUser = userRepository.findUserToAuthenticate(userLoggingDTO.getLoggingMethod());
            sess.setAttribute("authenticatedUserId", loggedUser.getId());
            sess.setAttribute("authenticatedUserName", loggedUser.getUserName());
            loggedIn = true;
            sess.setAttribute("isLogged", true);
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
        boolean loggedSuccessfully = false;
        if (userToAuthenticate!=null) {
            String hashedPassword = userToAuthenticate.getPassword();
            if (BCrypt.checkpw(password, hashedPassword)) {
                loggedSuccessfully = true;
            }
        }
        return loggedSuccessfully;
    }

    public void logout(HttpSession sess) {
        sess.invalidate();
    }

}

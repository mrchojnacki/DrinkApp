package pl.coderslab.user;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.ui.Model;

import javax.servlet.http.Cookie;

public class UserService {
    private UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
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


    public void editUserPassword(Model model, String oldPassword, String newPassword) {
        User loggedUser = (User) model.asMap().get("authenticatedUser");
        if (userLoggingCheck(loggedUser.getUserName(), oldPassword, model)) {
            loggedUser.setPassword(BCrypt.hashpw(newPassword, BCrypt.gensalt()));
            userRepository.save(loggedUser);
        }
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

package pl.coderslab.user;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;
import javax.validation.Valid;

@Controller
@Transactional
public class UserController {
    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/login")
    public String loginUserGet(Model model, UserLoggingDTO userLoggingDTO, HttpServletRequest request) {
        model.addAttribute("password", userService.passwordFromCookie(request));
        model.addAttribute("userLoggingIn", userLoggingDTO);
        return "/user/login.jsp";
    }

    @PostMapping("/login")
    public String loginUserPost(@ModelAttribute("userLoggingIn") UserLoggingDTO userLoggingDTO,
                                HttpSession sess,
                                Model model,
                                HttpServletResponse response) {
        if (userService.loggingIn(userLoggingDTO, sess, response)) {
            return "dashboard.jsp";
        } else {
            model.addAttribute("loginError", "Invalid username/email or password");
            return "/user/login.jsp";
        }
    }

    @GetMapping("/register")
    public String registerNewUserGet(Model model, UserRegisterDTO userRegisterDTO) {
        model.addAttribute("userRegister", userRegisterDTO);
        return "/user/register.jsp";
    }

    @PostMapping("/register")
    public String registerNewUserPost(@ModelAttribute("userRegister") @Valid UserRegisterDTO userRegisterDTO,
                                      BindingResult result,
                                      Model model,
                                      HttpServletResponse response) {
        if (!userService.checkIfPasswordConfirmed(userRegisterDTO)) {
            model.addAttribute("registerError", "Password confirmation has failed, try again");
            return "/user/register.jsp";
        }
        model.addAttribute("registerSuccessfully", "Registration complete! You can log in now");
        userService.addUserToDb(userRegisterDTO, response);
        return "/login";
    }

    @GetMapping("/myAccount")
    public String viewAccountSettings() {
        return "/user/myAccount.jsp";
    }

    @GetMapping("/myAccount/editPassword")
    public String editPasswordGet() {
        return "/user/edit-password.jsp";
    }

    @PostMapping("/myAccount/editPassword")
    public String editPasswordPost(@RequestParam("oldPassword") String oldPassword,
                                   @RequestParam("newPassword") String newPassword,
                                   Model model,
                                   HttpSession sess) {
        userService.editUserPassword(sess, model, oldPassword, newPassword);
        return "/user/myAccount.jsp";
    }
}

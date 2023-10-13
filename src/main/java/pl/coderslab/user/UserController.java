package pl.coderslab.user;

import org.springframework.stereotype.Controller;

import javax.transaction.Transactional;

@Controller
@Transactional
public class UserController {
    private UserService userService;


}

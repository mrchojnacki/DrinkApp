package pl.coderslab.user;

public class UserService {
    private UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    public boolean userLoggingCheck (String loggingMethod, String password) {
        User authenticatedUser = userRepository.authenticate(loggingMethod, password);
        boolean loggedSuccesfully = false;
        if (authenticatedUser!=null) {
            loggedSuccesfully = true;
        }
        return loggedSuccesfully;
    }
}

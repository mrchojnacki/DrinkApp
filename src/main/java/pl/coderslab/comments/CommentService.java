package pl.coderslab.comments;


import org.springframework.stereotype.Service;
import pl.coderslab.drink.DrinkRepository;
import pl.coderslab.user.UserRepository;

import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
public class CommentService {
    private final CommentsRepository commentsRepository;
    private final DrinkRepository drinkRepository;
    private final UserRepository userRepository;

    public CommentService(CommentsRepository commentsRepository, DrinkRepository drinkRepository, UserRepository userRepository) {
        this.commentsRepository = commentsRepository;
        this.drinkRepository = drinkRepository;
        this.userRepository = userRepository;
    }

    public List<CommentDTO> getCommentsToShow(String drinkId) {
        List<CommentDTO> listOfCommentsToShow = new ArrayList<>();
        List<CommentEntity> listOfCommentsFromDb = commentsRepository.getCommentEntitiesForDrinkFromDrinkId(Long.parseLong(drinkId));
        if(listOfCommentsFromDb==null) {
            return null;
        }
        for(CommentEntity comment : listOfCommentsFromDb) {
            CommentDTO commentDTO = new CommentDTO(comment.getCommentContent(),
                    comment.getNoOfLikes(),
                    formatDateForComment(comment.getCreatedOn()),
                    commentsRepository.getUserNameForComment(comment.getUser().getId()));
            listOfCommentsToShow.add(commentDTO);
        }
        return listOfCommentsToShow;
    }

    public void addCommentForDrinkToDb(String commentContent, String drinkId, HttpSession sess) {
        CommentEntity comment = new CommentEntity();
        comment.setCommentContent(commentContent);
        comment.setDrink(drinkRepository.findById(Long.parseLong(drinkId)).orElse(null));
        comment.setUser(userRepository.findUserById((Long) sess.getAttribute("authenticatedUserId")));
        comment.setNoOfLikes(0L);
        commentsRepository.save(comment);
    }

    private String formatDateForComment(LocalDateTime createdOn) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        return createdOn.format(formatter);
    }
    public void deleteComments(String drinkId) {
        List<CommentEntity> drinkComments = commentsRepository.getCommentEntitiesForDrinkFromDrinkId(Long.parseLong(drinkId));
        for (CommentEntity c : drinkComments) {
            commentsRepository.delete(c);
        }
    }
}

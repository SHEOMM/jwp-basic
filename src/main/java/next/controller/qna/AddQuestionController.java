package next.controller.qna;

import core.mvc.AbstractController;
import core.mvc.ModelAndView;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import next.controller.UserSessionUtils;
import next.dao.QuestionDao;
import next.model.Question;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AddQuestionController extends AbstractController {

    private final QuestionDao questionDao = new QuestionDao();

    private static final Logger log = LoggerFactory.getLogger(AddAnswerController.class);
    @Override
    public ModelAndView execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        // jsp는 생략 구현. mapper에 추가해주면 됨.
        HttpSession session = request.getSession();
        if (!UserSessionUtils.isLogined(session)) {
            return jspView("redirect:/users/loginForm");
        }

        Question question = new Question(request.getParameter("writer"), request.getParameter("title"), request.getParameter("contents"));
        log.debug("question : {}", question);

        Question savedQuestion = questionDao.insert(question);
        return jspView("/").addObject("questions", questionDao.findAll());
    }
}

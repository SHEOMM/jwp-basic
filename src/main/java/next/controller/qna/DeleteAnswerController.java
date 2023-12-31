package next.controller.qna;

import core.jdbc.AbstractController;
import core.mvc.ModelAndView;
import core.mvc.View;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;

import core.mvc.Controller;
import next.dao.AnswerDao;
import next.model.Result;
import next.view.JsonView;

public class DeleteAnswerController extends AbstractController {

    private final AnswerDao answerDao = new AnswerDao();
    @Override
    public ModelAndView execute(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        Long answerId = Long.parseLong(req.getParameter("answerId"));
        answerDao.delete(answerId);
        return jsonView();
    }
}

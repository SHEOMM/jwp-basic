package next.web;

import core.db.DataBase;
import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import next.model.User;


@WebServlet("/user/update")
public class UpdateUserServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        Object value = session.getAttribute("user");
        if (value == null){
            resp.sendRedirect("/user/login.html");
        }

        RequestDispatcher requestDispatcher = req.getRequestDispatcher("/user/update.jsp");
        requestDispatcher.forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        Object value = session.getAttribute("user");
        if (value == null){
            resp.sendRedirect("/user/login.html");
        }
        User loginUser = (User) value;

        User user = new User(req.getParameter("userId"), req.getParameter("password"), req.getParameter("name"),
                req.getParameter("email"));
        if(!loginUser.getUserId().equals(user.getUserId()) || !loginUser.getPassword().equals(user.getPassword())){
            resp.sendRedirect("index.html");
        }

        DataBase.addUser(user);
        resp.sendRedirect("/user/list");
    }
}

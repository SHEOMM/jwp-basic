package core.jdbc;

import core.mvc.Controller;
import core.mvc.ModelAndView;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import next.view.JsonView;
import next.view.JspView;

public abstract class AbstractController implements Controller {
    protected ModelAndView jspView(String forwardUrl){
        return new ModelAndView(new JspView(forwardUrl));
    }

    protected ModelAndView jsonView(){
        return new ModelAndView(new JsonView());
    }
}

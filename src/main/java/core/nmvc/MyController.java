package core.nmvc;

import core.annotation.Controller;
import core.annotation.RequestMapping;
import core.annotation.RequestMethod;
import core.mvc.ModelAndView;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
public class MyController {
    @RequestMapping("/users/findUserId")
    public ModelAndView findUserId(HttpServletRequest request, HttpServletResponse response){
        log.debug("findUserId");
        return null;
    }
    @RequestMapping(value = "/users", method = RequestMethod.POST)
    public ModelAndView save(HttpServletRequest request, HttpServletResponse response){
        log.debug("save");
        return null;
    }

}

package my.work.stock.system.web.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebFilter(filterName = "login", urlPatterns = {"/sys", "/"})
public class LoginFilter implements Filter {
    private static final Logger logger = LoggerFactory.getLogger(LoginFilter.class);

    @Override
    public void init(FilterConfig config) throws ServletException {

    }


    @Override
    public void doFilter(ServletRequest request, ServletResponse response,
                         FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        String servletPath = httpServletRequest.getServletPath();
        HttpSession session = httpServletRequest.getSession(true);
        Object hasLogin = session.getAttribute("hasLogin");
        if (hasLogin == null) {
            HttpServletResponse httpServletResponse = (HttpServletResponse) response;
            httpServletResponse.sendRedirect("/login");
        } else {
            chain.doFilter(request, response);
        }
    }

    @Override
    public void destroy() {

    }
}

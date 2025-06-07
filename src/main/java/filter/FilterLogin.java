package filter;

import java.io.IOException;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.*;

@WebFilter("/*")
public class FilterLogin implements Filter {

    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
            throws IOException, ServletException {
        
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) res;

        String uri = request.getRequestURI();

        boolean isLoginPage = uri.endsWith("login.jsp") || uri.endsWith("login");
        boolean isResource = uri.contains("/css/") || uri.contains("/js/") || uri.contains("/images/");
        HttpSession session = request.getSession(false);

        boolean isLoggedIn = (session != null && session.getAttribute("usuarioLogado") != null);

        if (isLoggedIn || isLoginPage || isResource) {
            chain.doFilter(req, res);
        } else {
            response.sendRedirect(request.getContextPath() + "/login.jsp");
        }
    }
}

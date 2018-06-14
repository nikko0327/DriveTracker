package app.filters;

import javax.servlet.*;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AuthenticationFilter implements Filter {

    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
        System.out.println("--**-- Filtering");

        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) res;
        System.out.println(request.getRequestURI());

        response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate"); // HTTP 1.1.
        response.setHeader("Pragma", "no-cache"); // HTTP 1.0.
        response.setDateHeader("Expires", 0); // Proxies.

        try {
            Cookie cookie = null;
            Cookie[] cookies = request.getCookies();
            if (cookies != null) {
                for (Cookie c : cookies) {
                    System.out.println(c.getName() + ": " + c.getValue());
                    if ("username".equals(c.getName())) {
                        cookie = c;
                        //System.out.println("Cookie found: " + c.getName() + ", " + c.getValue());
                    }
                }
            }

            if (cookie == null) {
                //request.getRequestDispatcher("").forward(request, response);
                if (cookies != null) {
                    for (Cookie c : cookies) {
                        c.setMaxAge(0);
                        response.addCookie(c);
                    }
                }

                // Disables back button

                //response.sendRedirect(request.getRequestURI() + "");
                request.getRequestDispatcher("").forward(request, response);
                return;
            } else {
                chain.doFilter(request, response);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void init(FilterConfig config) throws ServletException {
        // some initialization code called when the filter is loaded
    }

    public void destroy() {

    }
}

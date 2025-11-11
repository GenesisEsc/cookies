package servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Optional;

@WebServlet({"/login","/login.html"})
public class LoginServlet extends HttpServlet {
    //inicializamos las variables estáticas para el login
    final static String USERNAME = "admin";
    final static String PASSWORD = "12345";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //Creamos la cookie
        Cookie[] cookies = req.getCookies()!= null ? req.getCookies(): new Cookie[0];
        //Busco dentro de la cookie si es que existe información
        Optional<String> cookieOptional = Arrays.stream(cookies)
                .filter(c->"username".equals(c.getName()))
                //Convertimos la cookie a tipo string
                .map(Cookie::getValue)
                .findAny();
        if (cookieOptional.isPresent()) {
            resp.setContentType("text/html;charset=UTF-8");
            try(PrintWriter out = resp.getWriter()){
                //Creamos la plantilla html
                resp.setContentType("text/html;charset=UTF-8");
                //Crea la plantilla html
                out.println("<!DOCTYPE html>");
                out.println("<html>");
                out.println("<head>");
                out.println("<meta charset='utf-8'>");
                out.println("<title>login" + cookieOptional.get()+"</title>");
                out.println("<link rel='stylesheet' href='" + req.getContextPath() + "/styles.css'>");
                out.println("</head>");
                out.println("<body>");
                out.println("<div class='message-container'>");
                out.println("<h1>Login</h1>");
                out.println("<p>Bienvenido al sistema <strong>" + cookieOptional.get() + "</strong>, has iniciado sesión correctamente.</p>");
                out.println("<a href='" + req.getContextPath() + "/index.html'>Volver al inicio</a>");
                out.println("</div>");
                out.println("</body>");
                out.println("</html>");
            }

        }else {
            getServletContext().getRequestDispatcher("/login.jsp").forward(req,resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String username = req.getParameter("user");
        String password = req.getParameter("password");
        if(username.equals(USERNAME) && password.equals(PASSWORD)){
            resp.setContentType("text/html;charset=UTF-8");
            Cookie cookie = new Cookie("username", username);
            resp.addCookie(cookie);
            try(PrintWriter out = resp.getWriter()){
                out.println("<!DOCTYPE html>");
                out.println("<html>");
                out.println("<head>");
                out.println("<meta charset='utf-8'>");
                out.println("<title>Login Correcto</title>");
                out.println("<link rel='stylesheet' href='" + req.getContextPath() + "/styles.css'>");
                out.println("</head>");
                out.println("<body>");
                out.println("<div class='message-container'>");
                out.println("<h1>Bienvenido al sistema " + username + "</h1>");
                out.println("<a href='" +req.getContextPath() +"/index.html'>Volver al inicio</a>");
                out.println("</div>");
                out.println("</body>");
                out.println("</html>");
            }
            resp.sendRedirect(req.getContextPath()+"/index.html");
        }else{
            resp.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Lo sentimos no tiene acceso o revise las credenciales");
        }
    }
}

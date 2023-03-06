
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

@WebServlet("/register")
public class FormServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html");

        String username = req.getParameter("username");
        String password = req.getParameter("password");
        String email = req.getParameter("email");
        String pais = req.getParameter("pais");
        String[] lenguajes = req.getParameterValues("lenguajes");
        String[] roles = req.getParameterValues("roles");
        String idioma = req.getParameter("idioma");
        //declaración y validación de variable
        boolean habilitar = req.getParameter("habilitar") != null &&
                req.getParameter("habilitar").equals("on");
        String secreto = req.getParameter("secreto");

        Map<String, String> errores = new HashMap();

        if (email == null || !email.contains("@")) {
            errores.put("email", "email requerido! ");
        }

        if (pais == null || pais.equals("") || pais.equals(" ")) {
            errores.put("pais", "pais es requerido!");
        }

        if (lenguajes == null || lenguajes.length == 0) {
            errores.put("lenguajes", "Selecciona al menos un lenguaje");
        }

        if (username == null || username.isBlank()) {
            errores.put("username", "Username requerido y no facilitado!");
        }

        if (password == null || password.isBlank()) {
            errores.put("password", "Password requerido! ");
        }

        if (idioma == null) {
            errores.put("idioma", "selecciona un idioma");
        }
        if (errores.isEmpty()) {
            try (PrintWriter out = resp.getWriter()) {

                StringBuilder Sbuilder = new StringBuilder();
                Formatter htmlString = new Formatter(Sbuilder);

                //build string beguinning
                //if errors to put," ,  add
                //build end string

                htmlString.format("""
                        <!Doctype html>
                        <html>
                            <head>
                            <meta charset="UTF-8">
                            <title> Form Result ! </title>
                            </head>
                            
                            <body>
                                <h1> Resultado form!</h1>
                                <ul>
                                    <!-- IN THIS UL INSERT ERRORS LIST IF ANY-->
                            """);

                //JSP diff approach, show it side by side in the form field
                //insert errors if any
                //            if (!errores.isEmpty()) {
                //                errores.forEach(error -> {
                //                    htmlString.format("<li>" + error + "</li>");
                //                });
                //            }

                //continue building String in open <ul>
                htmlString.format("""
                                    <li> username: %s </li>
                                    <li> password: %s </li>
                                    <li> email: %s </li>
                                    <li> País: %s </li>
                                    <li> lenguajes: %s </li>
                                    <li> roles: %s </li>
                                    <li> idioma: %s </li>
                                    <li> habilitar: %s </li>
                                    <li> secreto: %s </li>
                                </ul>
                                <p> <a href="/webapp"/index.jsp> Volver al formulario </p>
                                    </body>
                                    </html>
                                        """, username, password, email, pais, Arrays.toString(lenguajes),
                        Arrays.toString(roles), idioma, habilitar, secreto);

                //print html string to show it on screen
                out.print(htmlString);
            }
        } else {
            req.setAttribute("errores", errores);
            getServletContext().getRequestDispatcher("/index.jsp").forward(req, resp);
        }
    }
}




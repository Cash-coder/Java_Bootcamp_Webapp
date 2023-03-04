
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Formatter;
import java.util.List;

@WebServlet("/register")
public class FormServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html");

        try (PrintWriter out = resp.getWriter()) {
            String username = req.getParameter("username");
            String password = req.getParameter("password");
            String email = req.getParameter("email");
            String pais = req.getParameter("pais");
            String[] lenguajes = req.getParameterValues("lenguajes");
            String[] roles = req.getParameterValues("roles");
            String idioma = req.getParameter("idioma");
            String habilitar = req.getParameter("habilitar");
            String secreto = req.getParameter("secreto");

            List<String> errores = new ArrayList<>();

            if (email == null || !email.contains("@")) {
                errores.add("email requerido! ");
            }

            if (pais == null || pais.equals("") || pais.equals(" ")){
                errores.add("pais es requerido!");
            }

            if (lenguajes == null || lenguajes.length == 0) {
                errores.add("Selecciona al menos un lenguaje");
            }

            if (username == null || username.isBlank()) {
                errores.add("Username requerido y no facilitado!");
            }

            if (password == null || password.isBlank()) {
                errores.add("Password requerido! ");
            }

            if (idioma == null) {
                errores.add("selecciona un idioma");
            }

            StringBuilder Sbuilder = new StringBuilder();
            Formatter htmlString = new Formatter(Sbuilder);

            //build string beguinning
            //if errors to add, add
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

            //insert errors if any
            if (!errores.isEmpty()) {
                errores.forEach(error -> {
                    htmlString.format("<li>" + error + "</li>");
                });
            }

            //continue building String
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
                    </body>
                    </html>
                    """, username, password, email, pais, Arrays.toString(lenguajes),
                    Arrays.toString(roles), idioma, habilitar, secreto);

            //print html string to show it on sccreen
            out.print(htmlString);

        }
    }
}

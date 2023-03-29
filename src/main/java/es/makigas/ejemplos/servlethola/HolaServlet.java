package es.makigas.ejemplos.servlethola;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@ApplicationScoped
@WebServlet("/inicio")
public class HolaServlet extends HttpServlet {
    
    @Inject
    private NameStorage storage;
    
    @Inject
    private RelojService reloj;
    
    @Inject
    private DatabaseService db;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("storage", storage);
        req.setAttribute("reloj", reloj);
        req.setAttribute("db", db);
        req.getServletContext().getRequestDispatcher("/hola.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            int id = Integer.parseInt(req.getParameter("id"));
            String nombre = req.getParameter("nombre");
            String apellido = req.getParameter("apellido");
            Person p = new Person(id, nombre, apellido);
            this.db.insertPerson(p);
            resp.sendRedirect(req.getContextPath() + "/inicio");
        } catch (Exception e) {
            resp.getWriter().append(e.getMessage());
        }
    }
    
}

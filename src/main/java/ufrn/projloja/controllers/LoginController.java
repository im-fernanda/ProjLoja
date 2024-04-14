package ufrn.projloja.controllers;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import ufrn.projloja.classes.Cliente;
import ufrn.projloja.classes.Lojista;
import ufrn.projloja.persistencia.ClienteDAO;
import ufrn.projloja.persistencia.LojistaDAO;

import java.io.IOException;

@Controller
public class LoginController {

    @RequestMapping(value = "/doLogin", method = RequestMethod.POST)
    public void doLogin(HttpServletRequest request, HttpServletResponse response) throws IOException {
        var login = request.getParameter("login");
        var senha = request.getParameter("senha");

        ClienteDAO cDAO = new ClienteDAO();
        LojistaDAO lDAO = new LojistaDAO();

        boolean cliente_existe = cDAO.procurar(login, senha);
        boolean lojista_existe = lDAO.procurar(login, senha);


        if(cliente_existe){
            HttpSession session = request.getSession(true);
            session.setAttribute("logado", true);

            response.sendRedirect("home_cliente.html");
            return;
        } else if (lojista_existe) {
            HttpSession session = request.getSession(true);
            session.setAttribute("logado", true);

            response.sendRedirect("home_lojista.html");
            return;
        }
        else {
            response.sendRedirect("index.html?msg=Login falhou");
        }
    }
    @RequestMapping(value="/logout")
    public void doLogout(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession session = request.getSession();
        session.invalidate();

        response.sendRedirect("index.html?msg=Saiu");
    }


}

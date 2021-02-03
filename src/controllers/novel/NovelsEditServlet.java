package controllers.novel;

import java.io.IOException;

import javax.persistence.EntityManager;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import models.Novel;
import models.User;
import utils.DBUtil;

/**
 * Servlet implementation class NovelsEditServlet
 */
@WebServlet("/novels/edit")
public class NovelsEditServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public NovelsEditServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        EntityManager em = DBUtil.createEntityManager();

        Novel n = em.find(Novel.class, Integer.parseInt(request.getParameter("id")));

        em.close();

        User login_user = (User)request.getSession().getAttribute("login_user");
        if(n != null && login_user.getId() == n.getUser().getId()) {
            request.setAttribute("novel", n);
            request.setAttribute("_token", request.getSession().getId());
            request.getSession().setAttribute("novel_id", n.getId());
        }

        RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/novels/edit.jsp");
        rd.forward(request, response);
    }

}

package controllers.users;

import java.io.IOException;

import javax.persistence.EntityManager;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import models.User;
import utils.DBUtil;

/**
 * Servlet implementation class UsersDestroyServlet
 */
@WebServlet("/users/destroy")
public class UsersDestroyServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public UsersDestroyServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */
    //ユーザーの削除処理、現段階ではまだ使われていない
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //CSRF対策
        String _token = (String)request.getParameter("_token");
        if(_token != null && _token.equals(request.getSession().getId())) {
            EntityManager em = DBUtil.createEntityManager();

            User u = em.find(User.class, (Integer)(request.getSession().getAttribute("user_id")));


            em.getTransaction().begin();
            em.remove(u);
            em.getTransaction().commit();

            em.close();

            request.getSession().removeAttribute("login_user");
            request.getSession().setAttribute("flush", "削除が完了しました");
            response.sendRedirect(request.getContextPath() + "/login");

        }
    }

}

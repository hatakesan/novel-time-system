package controllers.follows;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import models.Follow;
import models.User;
import utils.DBUtil;

/**
 * Servlet implementation class FollowsFollowShowServlet
 */
@WebServlet("/follows/followshow")
public class FollowsFollowShowServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public FollowsFollowShowServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        EntityManager em = DBUtil.createEntityManager();

        int user_id = Integer.parseInt(request.getParameter("id"));


        List<Follow> follows = em.createNamedQuery("getAllFollows", Follow.class)
                .setParameter("user_id", user_id)
                .getResultList();

        //followsテーブルのuser_idを使用し、フォローしているUserを取得
        List<User> users = new ArrayList<>();

        for(Follow follow : follows) {
            users.add(em.find(User.class, follow.getFollow_id()));
        }

        em.close();

        if(users != null) {
            request.setAttribute("users", users);
        }

        RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/users/follows.jsp");
        rd.forward(request, response);
    }

}

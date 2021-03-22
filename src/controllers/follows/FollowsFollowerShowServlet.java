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
 * Servlet implementation class FollowsFollowerShowServlet
 */
@WebServlet("/follows/followershow")
public class FollowsFollowerShowServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public FollowsFollowerShowServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        EntityManager em = DBUtil.createEntityManager();

        int follow_id = Integer.parseInt(request.getParameter("id"));

        List<Follow> followers = em.createNamedQuery("getAllFollowers", Follow.class)
                .setParameter("follow_id", follow_id)
                .getResultList();

        //followsテーブルのfollow_idを使用し、フォロワのUserを取得
        List<User> users = new ArrayList<>();

        for(Follow follower : followers) {
            users.add(em.find(User.class, follower.getUser_id()));
        }

        em.close();

        request.setAttribute("users", users);

        RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/users/follows.jsp");
        rd.forward(request, response);
    }

}

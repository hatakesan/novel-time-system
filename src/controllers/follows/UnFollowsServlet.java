package controllers.follows;

import java.io.IOException;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import models.Follow;
import models.Novel;
import models.User;
import utils.DBUtil;

/**
 * Servlet implementation class UnFollowsServlet
 */
@WebServlet("/follows/unfollow")
public class UnFollowsServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public UnFollowsServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        EntityManager em = DBUtil.createEntityManager();

        //フォローを外すために、ログインユーザーのidと外す対象のidを取得
        User u = (User) request.getSession().getAttribute("login_user");
        int user_id = u.getId();
        int follow_id = Integer.parseInt(request.getParameter("id"));

        //フォローしているかしていないかの確認、フォローしていたらDBからそのデータを削除
        Follow followRelation = null;

        try{
            followRelation = em.createNamedQuery("getFollowRelation", Follow.class).setParameter("user_id", user_id).setParameter("follow_id", follow_id).getSingleResult();
        }catch (NoResultException ex) {

        }

        if(followRelation != null) {
            em.getTransaction().begin();
            em.remove(followRelation);
            em.getTransaction().commit();
            followRelation = null;
            request.setAttribute("followRelation", followRelation);
        }


        User user = em.find(User.class, Integer.parseInt(request.getParameter("id")));

        //ページネーション
        int page;
        try{
            page = Integer.parseInt(request.getParameter("page"));
        }catch(Exception e) {
            page = 1;
        }

        List<Novel> novels = em.createNamedQuery("getMyAllNovels", Novel.class)
                .setParameter("user", user)
                .setFirstResult(15 * (page - 1))
                .setMaxResults(15)
                .getResultList();

        long novels_count = (long)em.createNamedQuery("getMyNovelsCount", Long.class)
                .setParameter("user", user)
                .getSingleResult();

        long follow_count = (long)em.createNamedQuery("getFollowCount", Long.class)
                .setParameter("user_id", follow_id)
                .getSingleResult();

        long follower_count = (long)em.createNamedQuery("getFollowerCount", Long.class)
                .setParameter("follow_id", follow_id)
                .getSingleResult();

        em.close();

        request.setAttribute("novels", novels);
        request.setAttribute("novels_count", novels_count);
        request.setAttribute("page", page);
        request.getSession().setAttribute("user", user);
        request.setAttribute("follow_count", follow_count);
        request.setAttribute("follower_count", follower_count);

        RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/users/show.jsp");
        rd.forward(request, response);




    }

}

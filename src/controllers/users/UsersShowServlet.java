package controllers.users;

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
 * Servlet implementation class UsersShowServlet
 */
@WebServlet("/users/show")
public class UsersShowServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public UsersShowServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */

    //ユーザー個人のページへ
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        EntityManager em = DBUtil.createEntityManager();

        User u = em.find(User.class, Integer.parseInt(request.getParameter("id")));

        //ページネーション
        int page;
        try{
            page = Integer.parseInt(request.getParameter("page"));
        }catch(Exception e) {
            page = 1;
        }


        //ユーザーのNovelを取得
        List<Novel> novels = em.createNamedQuery("getMyAllNovels", Novel.class)
                .setParameter("user", u)
                .setFirstResult(15 * (page - 1))
                .setMaxResults(15)
                .getResultList();

        long novels_count = (long)em.createNamedQuery("getMyNovelsCount", Long.class)
                .setParameter("user", u)
                .getSingleResult();

        User login_user = (User) request.getSession().getAttribute("login_user");


        //ログインユーザーがフォローしているかどうか
        Follow followRelation = null;

        try{
            followRelation = em.createNamedQuery("getFollowRelation", Follow.class).setParameter("user_id", login_user.getId()).setParameter("follow_id", u.getId()).getSingleResult();
        }catch (NoResultException ex) {

        }

        //フォローフォロワ数の取得
        long follow_count = (long)em.createNamedQuery("getFollowCount", Long.class)
                .setParameter("user_id", u.getId())
                .getSingleResult();

        long follower_count = (long)em.createNamedQuery("getFollowerCount", Long.class)
                .setParameter("follow_id", u.getId())
                .getSingleResult();





        em.close();


        if(followRelation != null) {
            request.setAttribute("followRelation", followRelation);
        }
        request.setAttribute("novels", novels);
        request.setAttribute("novels_count", novels_count);
        request.setAttribute("page", page);
        request.getSession().setAttribute("user", u);
        request.setAttribute("follow_count", follow_count);
        request.setAttribute("follower_count", follower_count);

        RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/users/show.jsp");
        rd.forward(request, response);
    }

}

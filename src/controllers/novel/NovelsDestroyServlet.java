package controllers.novel;

import java.io.IOException;
import java.util.List;

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
 * Servlet implementation class NovelsDestroyServlet
 */
@WebServlet("/novels/destroy")
public class NovelsDestroyServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public NovelsDestroyServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */

    //投稿の削除
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //CSRF対策
        String _token = (String)request.getParameter("_token");

        if(_token != null && _token.equals(request.getSession().getId())){
            EntityManager em = DBUtil.createEntityManager();

            int novel_id = Integer.parseInt(request.getParameter("id"));
            Novel n = em.find(Novel.class, novel_id);

            em.getTransaction().begin();
            em.remove(n);
            em.getTransaction().commit();

            User login_user = (User) request.getSession().getAttribute("login_user");

            //ページネーション
            int page;
            try{
                page = Integer.parseInt(request.getParameter("page"));
            }catch(Exception e) {
                page = 1;
            }

            List<Novel> novels = em.createNamedQuery("getMyAllNovels", Novel.class)
                    .setParameter("user", login_user)
                    .setFirstResult(15 * (page - 1))
                    .setMaxResults(15)
                    .getResultList();

            long novels_count = (long)em.createNamedQuery("getMyNovelsCount", Long.class)
                    .setParameter("user", login_user)
                    .getSingleResult();

            //フォローフォロワ数
            long follow_count = (long)em.createNamedQuery("getFollowCount", Long.class)
                    .setParameter("user_id", login_user.getId())
                    .getSingleResult();

            long follower_count = (long)em.createNamedQuery("getFollowerCount", Long.class)
                    .setParameter("follow_id", login_user.getId())
                    .getSingleResult();

            em.close();

            request.setAttribute("novels", novels);
            request.setAttribute("novels_count", novels_count);
            request.setAttribute("page", page);
            request.getSession().setAttribute("user", login_user);
            request.setAttribute("follow_count", follow_count);
            request.setAttribute("follower_count", follower_count);
            request.getSession().setAttribute("flush", "削除しました");

            RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/users/show.jsp");
            rd.forward(request, response);
        }



    }

}

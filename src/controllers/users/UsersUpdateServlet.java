package controllers.users;

import java.io.IOException;
import java.util.List;

import javax.persistence.EntityManager;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import models.User;
import models.validators.UserValidator;
import utils.DBUtil;
import utils.EncryptUtil;

/**
 * Servlet implementation class UsersUpdateServlet
 */
@WebServlet("/users/update")
public class UsersUpdateServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public UsersUpdateServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */

    //ユーザーの更新処理
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //CSRF対策
        String _token = (String)request.getParameter("_token");
        if(_token != null && _token.equals(request.getSession().getId())) {
            EntityManager em = DBUtil.createEntityManager();

            User u = em.find(User.class, (Integer)(request.getSession().getAttribute("user_id")));


            //codeが変更されていれば、すでに登録されているcodeかどうか確認する処理をさせる
            Boolean codeDuplicateCheckFlag = true;
            if(u.getCode().equals(request.getParameter("code"))) {
                codeDuplicateCheckFlag = false;
            }else {
                u.setCode(request.getParameter("code"));
            }

            //passwordが入力されていれば、ハッシュ化して新しいパスワードとして登録
            Boolean passwordCheckFlag = true;
            String password = request.getParameter("password");
            if(password == null || password.equals("")) {
                passwordCheckFlag = false;
            }else {
                u.setPassword(EncryptUtil.getPasswordEncrypt(password, (String)this.getServletContext().getAttribute("pepper")));

            }

            u.setName(request.getParameter("name"));
            u.setDelete_flag(0);

            //バリデーション、codeとpasswordのバリデーションは選択できるようになっている（上の記述で）
            List<String> errors = UserValidator.validate(u, codeDuplicateCheckFlag, passwordCheckFlag);
            if(errors.size() > 0) {
                em.close();

                request.setAttribute("_token", request.getSession().getId());
                request.setAttribute("user", u);
                request.setAttribute("errors", errors);

                RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/users/edit.jsp");
                rd.forward(request, response);
            }else {
                em.getTransaction().begin();
                em.getTransaction().commit();
                em.close();
                request.getSession().setAttribute("flush", "更新が完了しました");
                request.getSession().removeAttribute("user_id");

                response.sendRedirect(request.getContextPath() + "/");
            }
        }
    }

}

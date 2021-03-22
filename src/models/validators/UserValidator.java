package models.validators;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;

import models.User;
import utils.DBUtil;

public class UserValidator {
    //Userのバリデーション
    public static List<String> validate(User u, Boolean codeDuplicateCheckFlag, Boolean passwordCheckFlag) {
        List<String> errors = new ArrayList<String>();

        //codeのバリデーション
        String code_error = validateCode(u.getCode(), codeDuplicateCheckFlag);
        if(!code_error.equals("")) {
            errors.add(code_error);
        }

        //nameのバリデーション
        String name_error = validateName(u.getName());
        if(!name_error.equals("")) {
            errors.add(name_error);
        }

        //passwordのバリデーション
        String password_error = validatePassword(u.getPassword(), passwordCheckFlag);
        if(!password_error.equals("")) {
            errors.add(password_error);
        }

        return errors;

    }


    private static String validateCode(String code, Boolean codeDuplicateCheckFlag) {
        //codeが空だった場合
        if(code == null || code.equals("")) {
            return "ユーザーコードを入力してください。";
        }

        //codeがすでに存在しているかどうか
        if(codeDuplicateCheckFlag) {
            EntityManager em = DBUtil.createEntityManager();
            long users_count = (long)em.createNamedQuery("checkRegisteredCode", Long.class)
                    .setParameter("code", code)
                    .getSingleResult();
            em.close();
            if(users_count > 0) {
                return "入力されたユーザーコードは既に存在しています";
            }
        }
        return "";
    }

    private static String validateName(String name) {
        if(name == null || name.equals("")) {
            return "氏名を入力してください";
        }
        return "";
    }


    private static String validatePassword(String password, Boolean passwordCheckFlag) {
        if(passwordCheckFlag && (password == null || password.equals(""))) {
            return "パスワードを入力してください。";
        }
        return "";
    }

}

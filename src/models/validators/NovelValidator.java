package models.validators;

import java.util.ArrayList;
import java.util.List;

import models.Novel;

//Novelのバリデーション
public class NovelValidator {
    //引数でNovelインスタンス、戻り値は注意のリスト
    public static List<String> validate(Novel n) {
        List<String> errors = new ArrayList<String>();

        //タイトルのバリデーション
        String title_error = _validateTitle(n.getTitle());
        if(!title_error.equals("")) {
            errors.add(title_error);
        }

        //内容のバリデーション
        String sentence_error = _validateSentence(n.getSentence());
        if(!sentence_error.equals("")) {
            errors.add(sentence_error);
        }
        return errors;
    }

    private static String _validateTitle(String title) {
        if(title == null || title.equals("")) {
            return "タイトルを入力してください";
        }
        return "";
    }

    private static String _validateSentence(String sentence) {
        if(sentence == null || sentence.equals("")) {
            return "内容を入力してください";
        }
        return "";
    }
}

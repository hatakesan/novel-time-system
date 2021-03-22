<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:import url="/WEB-INF/views/layout/app.jsp">
    <c:param name="content">
        <c:choose>
            <c:when test="${user != null}">
                <h2 id="edit_user_name">${user.name}さんの編集ページ</h2>

                <p id="attention">パスワードは変更する場合のみ入力してください</p>
                <form  method="POST" action="<c:url value='/users/update'/>">
                    <c:import url="_form.jsp"></c:import>
                </form>
            </c:when>
            <c:otherwise>
                <h2>お探しのデータは見つかりませんでした</h2>
            </c:otherwise>
        </c:choose>
        <p><a href="<c:url value='/'/>">一覧に戻る</a></p>
    </c:param>
</c:import>
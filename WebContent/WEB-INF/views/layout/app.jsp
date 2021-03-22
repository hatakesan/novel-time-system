<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="ja">
    <head>
        <meta charset="UTF-8">
        <title>NovelTime</title>
        <link rel="stylesheet" href="<c:url value='/css/reset.css' />">
        <link rel="stylesheet" href="<c:url value='/css/style.css' />">
    </head>
    <body>
        <div id="wrapper">
            <div id="header_pc">
                <div id="header_menu_pc">
                    <h1><a href="<c:url value='/' />">NovelTime</a></h1>
                    <c:if test="${sessionScope.login_user != null}">
                        <a href="<c:url value='/users/edit?id=${sessionScope.login_user.id}'/>">ユーザー管理</a>&nbsp;
                        <a href="<c:url value='/users/show?id=${sessionScope.login_user.id}'/>">マイページ</a>&nbsp;
                        <a href="<c:url value="/novels/new"/>">投稿する</a>
                        <a href="<c:url value="/favorites/my" />">お気に入り</a>
                    </c:if>
                </div>
                <c:if test="${sessionScope.login_user != null}">
                    <div id="logout">
                        <a href="<c:url value='/logout' />">ログアウト</a>
                    </div>
                </c:if>
            </div>
            <div id="header_phone">
                <div id="header_menu_pc">
                    <h1 id="noveltime_phone"><a href="<c:url value='/' />">NovelTime</a></h1>
                    <c:if test="${sessionScope.login_user != null}">
                        <div id="menu_phone">
                            <a href="<c:url value='/users/edit?id=${sessionScope.login_user.id}'/>">ユーザー管理</a>&nbsp;
                            <a href="<c:url value='/users/show?id=${sessionScope.login_user.id}'/>">マイページ</a>&nbsp;
                            <a href="<c:url value="/novels/new"/>">投稿する</a>
                            <a href="<c:url value="/favorites/my" />">お気に入り</a>&nbsp;
                            <a href="<c:url value='/logout' />">ログアウト</a>
                        </div>
                    </c:if>
                </div>
            </div>
            <div id="content">
                ${param.content}
            </div>
        </div>

    </body>
</html>
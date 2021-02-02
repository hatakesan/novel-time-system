<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:import url="/WEB-INF/views/layout/app.jsp">
    <c:param name="content">
        <h2>小説　投稿ページ</h2>

        <form method="POST" action="<c:url value='/novels/create' />">
            <c:import url="_form.jsp"></c:import>
        </form>
        <p><a href="<c:url value='/novels/index' />">一覧に戻る</a></p>
    </c:param>
</c:import>
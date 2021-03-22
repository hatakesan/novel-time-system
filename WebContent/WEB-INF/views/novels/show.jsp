<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:import url="/WEB-INF/views/layout/app.jsp">
    <c:param name="content">
        <c:choose>
            <c:when test="${novel != null}">
                <div id="novel_zone_show">
                    <div id="single_novel_show">
                        <div id="tud_center">
                            <div id="title_show">${novel.title}</div>
                            <div id="user_name_show"><a href="<c:url value="/users/show?id=${novel.user.id}"/>"><c:out value="${novel.user.name}"></c:out></a></div>
                            <div id="date_show"><fmt:formatDate value="${novel.novel_date}" pattern='yyyy-MM-dd'/></div>
                        </div>

                        <div id="main_show"><p><c:out value="${novel.sentence}"></c:out></p></div>
                    </div>
                </div>
                <c:if test="${login_user.id != novel.user.id}">
                    <c:choose>
                        <c:when test="${favoriteRelation != null}">

                                <a href="<c:url value="/favorites/un?id=${novel.id}"/>">お気に入りを解除する</a>

                        </c:when>
                        <c:otherwise>

                                <a href="<c:url value="/favorites/be?id=${novel.id}"/>">お気に入りに登録する</a>

                        </c:otherwise>
                    </c:choose>
                </c:if>
                <div>
                    お気に入り登録数:<c:out value="${favorite_count}"></c:out>
                </div>

                <c:if test="${sessionScope.login_user.id == novel.user.id}">
                    <p><a href="<c:url value='/novels/edit?id=${novel.id}'/>">文を編集する</a></p>
                </c:if>
            </c:when>
            <c:otherwise>
                <h2>お探しのデータは見つかりませんでした</h2>
            </c:otherwise>
        </c:choose>
        <p><a href="<c:url value='/'/>">一覧に戻る</a></p>
    </c:param>
</c:import>
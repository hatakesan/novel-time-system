<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:import url="/WEB-INF/views/layout/app.jsp">
    <c:param name="content">
        <c:choose>
            <c:when test="${user != null}">
                <c:if test="${flush != null}">
                    <div>
                        <c:out value="${flush}"></c:out>
                    </div>
                </c:if>
                <div style="margin: 15px;">
                    <p style="display: inline;">名前　：　</p><c:out value="${user.name}"></c:out> &nbsp;
                    <c:if test="${login_user.id != user.id}">
                            <c:choose>
                                <c:when test="${followRelation != null}">
                                    <a href="<c:url value="/follows/unfollow?id=${user.id}"/>">フォローを解除する</a>                                    </c:when>
                                <c:otherwise>
                                    <a href="<c:url value="/follows/follow?id=${user.id}"/>">フォローする</a>
                                </c:otherwise>
                            </c:choose>
                     </c:if>
                </div>
                <div style="margin: 15px;">
                    <c:choose>
                            <c:when test="${follow_count == 0}">
                                フォロー
                            </c:when>
                            <c:otherwise>
                                <a href="<c:url value="/follows/followshow?id=${user.id}"/>">フォロー</a>
                            </c:otherwise>
                    </c:choose>

                     <c:out value="${follow_count}"></c:out>

                     <c:choose>
                            <c:when test="${follower_count == 0}">
                                フォロワー
                            </c:when>
                            <c:otherwise>
                                <a href="<c:url value="/follows/followershow?id=${user.id}"/>">フォロワー</a>
                            </c:otherwise>
                     </c:choose>

                     <c:out value="${follower_count}"></c:out>
                </div>

                <div id="novel_zone">
            <c:forEach var="novel" items="${novels}" varStatus="status">
                    <div id="single_novel">
                        <div id="user_name"><a href="<c:url value="/users/show?id=${novel.user.id}"/>"><c:out value="${novel.user.name}"></c:out></a></div>
                        <div id="date"><fmt:formatDate value="${novel.novel_date}" pattern='yyyy-MM-dd'/></div>
                        <div id="title">${novel.title}</div>
                        <div id="main"><c:out value="${novel.sentence}"></c:out></div>
                        <div id="show"><a href="<c:url value="/novels/show?id=${novel.id}"/>">詳細を見る</a></div>
                    </div>
                </c:forEach>
        </div>


        <div id="pagination">
            （全 ${novels_count}件）<br />
            <c:forEach var="i" begin="1" end="${((novels_count - 1) / 15) + 1}" step="1">
                <c:choose>
                    <c:when test="${i == page}">
                        <c:out value="${i}"></c:out>&nbsp;
                    </c:when>
                    <c:otherwise>
                        <a href="<c:url value='/users/show?page=${i}&id=${user.id}'/>"><c:out value="${i}"></c:out></a>
                    </c:otherwise>
                </c:choose>
            </c:forEach>
        </div>

            </c:when>
            <c:otherwise>
                <h2>お探しのデータは見つかりませんでした。</h2>
            </c:otherwise>
        </c:choose>
        <p id="new"><a href="<c:url value='/novels/new'/>">投稿する</a></p>
    </c:param>
</c:import>
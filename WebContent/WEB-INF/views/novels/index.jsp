<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:import url="/WEB-INF/views/layout/app.jsp">
    <c:param name="content">
        <c:if test="${flush != null}">
            <div id="flush_success">
                <c:out value="${flush}"></c:out>
            </div>
        </c:if>
        <div id="welcome"><h2>NovelTimeへようこそ</h2></div>
        <div id="my_novel"><h3>最近の投稿</h3></div>
        <div id="novel_zone">
            <c:forEach var="novel" items="${novels}" varStatus="status">
                    <div id="single_novel">
                        <div id="user_name"><a href="<c:url value="/users/show?id=${novel.user.id}"/>"><c:out value="${novel.user.name}"></c:out></a></div>
                        <div id="date"><fmt:formatDate value="${novel.novel_date}" pattern='yyyy-MM-dd'/></div>
                        <div id="title">${novel.title}</div>
                        <div id="main"><c:out value="${novel.sentence}"></c:out></div>
                        <div id="show"><a href="<c:url value='/novels/show?id=${novel.id}'/>">詳細を見る</a></div>
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
                        <a href="<c:url value='/?page=${i}'/>"><c:out value="${i}"></c:out></a>
                    </c:otherwise>
                </c:choose>
            </c:forEach>
        </div>
        <p id="new"><a href="<c:url value='/novels/new'/>">新規投稿</a></p>
    </c:param>
</c:import>
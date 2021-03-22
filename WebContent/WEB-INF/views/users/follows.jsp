<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:import url="/WEB-INF/views/layout/app.jsp">
    <c:param name="content">
        <c:choose>
            <c:when test="${users != null}">
                <c:forEach var="user" items="${users}" varStatus="status">
                    <div>
                        <a href="<c:url value="/users/show?id=${user.id}"/>">
                            名前　：　<c:out value="${user.name}"></c:out>
                        </a>
                    </div>
                </c:forEach>
            </c:when>
        </c:choose>
    </c:param>
</c:import>
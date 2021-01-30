<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:import url="/WEB-INF/views/layout/app.jsp">
    <c:param name="content">
        <c:choose>
            <c:when test="${user != null}">
                <h2>id: ${user.id}の情報</h2>
                <table>
                    <tbody>
                        <tr>
                            <th>ユーザーコード</th>
                            <td><c:out value="${user.code}"></c:out></td>
                        </tr>
                        <tr>
                            <th>名前</th>
                            <td><c:out value="${user.name}"></c:out></td>
                        </tr>
                    </tbody>
                </table>
                <p><a href="<c:url value='/users/edit?id=${user.id}'/>">この従業員を編集する</a></p>

            </c:when>
            <c:otherwise>
                <h2>お探しのデータは見つかりませんでした。</h2>
            </c:otherwise>
        </c:choose>
        <p><a href="<c:url value='/users/index'/>">一覧に戻る</a></p>
    </c:param>
</c:import>
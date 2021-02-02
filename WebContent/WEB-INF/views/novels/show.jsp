<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:import url="/WEB-INF/views/layout/app.jsp">
    <c:param name="content">
        <c:choose>
            <c:when test="${novel != null}">
                <table>
                    <tbody>
                        <tr>
                            <th>氏名</th>
                            <td><c:out value="${novel.user.name}"></c:out></td>
                        </tr>
                        <tr>
                            <th>日付</th>
                            <td><fmt:formatDate value="${novel.novel_date}" pattern="yyyy-MM-dd"/></td>
                        </tr>
                        <tr>
                            <th>内容</th>
                            <td>
                                <pre><c:out value="${novel.sentence}"></c:out></pre>
                            </td>
                        </tr>
                        <tr>
                            <th>投稿日時</th>
                            <td>
                                <fmt:formatDate value="${novel.created_at}" pattern="yyyy-MM-dd HH:mm:ss"/>
                            </td>
                        </tr>
                        <tr>
                            <th>更新日時</th>
                            <td>
                                <fmt:formatDate value="${novel.updated_at}" pattern="yyyy-MM-dd HH:mm:ss" />
                            </td>
                        </tr>
                    </tbody>
                </table>
                <c:if test="${sessionScope.login_user.id == novel.user.id}">
                    <p><a href="<c:url value='/novels/edit?id=${novel.id}'/>">文を編集する</a></p>
                </c:if>
            </c:when>
            <c:otherwise>
                <h2>お探しのデータは見つかりませんでした</h2>
            </c:otherwise>
        </c:choose>
        <p><a href="<c:url value='/novels/index'/>">一覧に戻る</a></p>
    </c:param>
</c:import>
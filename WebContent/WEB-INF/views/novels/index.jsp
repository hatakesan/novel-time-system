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
        <h2>小説　一覧</h2>
        <table id="novel_list">
            <tbody>
                <tr>
                    <th class="novel_name">名前</th>
                    <th class="novel_date">日付</th>
                    <th class="novel_title">タイトル</th>
                    <th class="novel_action">操作</th>
                </tr>
                <c:forEach var="novel" items="${novels}" varStatus="status">
                    <tr class="row${status.count % 2}">
                        <td class="novel_name"><c:out value="${novel.user.name}"></c:out></td>
                        <td class="novel_date"><fmt:formatDate value="${novel.novel_date}" pattern='yyyy-MM-dd'/></td>
                        <td class="novel_title">${novel.title}</td>
                        <td class="novel_action"><a href="<c:url value='/novels/show?id=${novel.id}' />">詳細を見る</a></td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>

        <div id="pagination">
            （全${novels_count}件）<br />
            <c:forEach var="i" begin="1" end="${((novels_count - 1) / 15) + 1}" step="1">
                <c:choose>
                    <c:when test="${i == page}">
                        <c:out value="${i}"></c:out>&nbsp;
                    </c:when>
                    <c:otherwise>
                        <a href="<c:url value='/novels/index?page=${i}' />"><c:out value="${i}"></c:out></a>&nbsp;
                    </c:otherwise>
                </c:choose>
            </c:forEach>
        </div>
        <p><a href="<c:url value='/novels/new' />">新しい小説を書く</a></p>
    </c:param>
</c:import>
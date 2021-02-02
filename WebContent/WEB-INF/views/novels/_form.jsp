<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:if test="${errors != null}">
    <div id="flush_error">
        入力内容にエラーがあります <br />
        <c:forEach var="error" items="${errors}">
            ・<c:out value="${error}"></c:out><br/>
        </c:forEach>
    </div>
</c:if>
<label for="novel_date">日付</label>
<input type="date" name="novel_date" value="<fmt:formatDate value='${novel.novel_date}' pattern='yyyy-MM-dd' />"/>
<br /><br />

<label for="name">氏名</label><br />
<c:out value="${sessionScope.login_user.name}"></c:out>
<br /><br />

<label for="title">タイトル</label><br />
<input type="text" name="title" value="${novel.title}"/>
<br /><br />

<label for="sentence">内容</label>
<textarea name="sentence" rows="10" cols="50">${novel.sentence}</textarea>
<br /><br />

<input type="hidden" name="_token" value="${_token}"/>
<button type="submit">投稿</button>
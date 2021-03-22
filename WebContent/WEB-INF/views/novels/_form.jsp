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
&nbsp;&nbsp;<label for="novel_date">日付</label>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
<input type="date" name="novel_date" value="<fmt:formatDate value='${novel.novel_date}' pattern='yyyy-MM-dd' />"/>
<br /><br />

&nbsp;&nbsp;<label for="title">タイトル</label>
<input type="text" name="title" value="${novel.title}"/>
<br /><br />

&nbsp;&nbsp;<label for="sentence">内容</label><br />
<textarea id="main_form" name="sentence" maxlength="6000">${novel.sentence}</textarea>
<br /><br />

<input type="hidden" name="_token" value="${_token}"/>
<button id="submit" type="submit">投稿</button>
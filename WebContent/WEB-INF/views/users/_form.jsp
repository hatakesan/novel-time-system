<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:if test="${errors != null}">
    <div id="flush_error">
        入力にエラーがあります。<br />
        <c:forEach var="error" items="${errors}">
            ・<c:out value="${error}"></c:out><br />
        </c:forEach>
    </div>
</c:if>

<div></div>
<label id="user_label"for="code">ユーザーコード</label>
<input type="text" name="code" value="${user.code}" />
<br /> <br />

<label id="user_label2"for="name">氏名</label>
<input type="text" name="name" value="${user.name}" />
<br /><br />

<label id="user_label3" for="password">パスワード</label>
<input  type="password" name="password"/>
<br /><br />

<input type="hidden" name="_token" value="${_token}">
<button id="submit" type="submit">登録</button>

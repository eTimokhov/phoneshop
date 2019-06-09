<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<%@ tag pageEncoding="UTF-8" %>
<%@ attribute name="activePage" required="true" type="java.lang.Integer" %>

<c:url value="/productList">
    <c:if test="${not empty param.orderBy}">
        <c:param name="orderBy" value="${param.orderBy}"/>
    </c:if>
    <c:if test="${not empty param.asc}">
        <c:param name="asc" value="${param.asc}"/>
    </c:if>
    <c:if test="${not empty param.searchQuery}">
        <c:param name="searchQuery" value="${param.searchQuery}"/>
    </c:if>
    <c:param name="page" value="${activePage eq 1 ? 1 : activePage - 1}"/>
</c:url>
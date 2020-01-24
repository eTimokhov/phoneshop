<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ tag pageEncoding="UTF-8" %>
<%@ attribute name="orderBy" required="true" type="java.lang.String" %>
<%@ attribute name="asc" required="true" type="java.lang.Boolean" %>

<a class="float-right" href="
    <c:url value="/productList" >
        <c:param name="orderBy" value="${orderBy}"/>
        <c:param name="asc" value="${asc}"/>
        <c:if test="${not empty param.searchTerms}">
            <c:param name="searchTerms" value="${param.searchTerms}"/>
        </c:if>
        <c:param name="page" value="1"/>
    </c:url>
">
    <c:choose>
        <c:when test="${asc}">
            <i class="far fa-arrow-alt-circle-up"></i>
        </c:when>
        <c:otherwise>
            <i class="far fa-arrow-alt-circle-down"></i>
        </c:otherwise>
    </c:choose>
</a>
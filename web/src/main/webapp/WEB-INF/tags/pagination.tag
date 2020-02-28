<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ tag pageEncoding="UTF-8" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<%@ attribute name="activePage" required="true" type="java.lang.Integer" %>
<%@ attribute name="resultsPerPage" required="true" type="java.lang.Integer" %>
<%@ attribute name="totalCount" required="true" type="java.lang.Integer" %>

<ul class="pagination float-right">
    <li class="page-item"><a class="page-link" href="<tags:pageUrl pageNumber="1"/>">❮❮</a></li>
    <li class="page-item"><a class="page-link" href="<tags:pageUrl pageNumber="${activePage eq 1 ? 1 : activePage - 1}"/>">❮</a></li>
    <c:set var="totalPagesCount" value="${(totalCount - totalCount % resultsPerPage) / resultsPerPage}"/>
    <c:if test="${totalCount mod resultsPerPage ne 0}">
        <c:set var="totalPagesCount" value="${totalPagesCount + 1}"/>
    </c:if>
    <c:set var="firstPage" value="${activePage <= 4 ? 1 : activePage - 4}"/>
    <c:set var="lastPage" value="${activePage >= totalPagesCount - 4 ? totalPagesCount : activePage + 4}"/>

    <c:forEach var="i" begin="${firstPage}" end="${lastPage}">
        <li class="page-item ${i eq activePage ? "active" : ""}">
            <a class="page-link" href="<tags:pageUrl pageNumber="${i}"/>"> ${i} </a>
        </li>
    </c:forEach>
    <li class="page-item"><a class="page-link" href="<tags:pageUrl pageNumber="${activePage eq totalPagesCount ? totalPagesCount : activePage + 1}"/>">❯</a></li>
    <li class="page-item"><a class="page-link" href="<tags:pageUrl pageNumber="${totalPagesCount}"/>">❯❯</a></li>
</ul>

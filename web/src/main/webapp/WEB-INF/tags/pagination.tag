<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="tpag" tagdir="/WEB-INF/tags/pagination" %>
<%@ tag pageEncoding="UTF-8" %>
<%@ attribute name="activePage" required="true" type="java.lang.Integer" %>

<ul class="pagination float-right">
    <li class="page-item"><a class="page-link" href="<tpag:prevPageUrl activePage="${activePage}"/>">❮</a></li>
    <c:forEach var="i" begin="1" end="10">
        <li class="page-item ${i eq activePage ? "active" : ""}">
            <a class="page-link" href="<tpag:pageUrl pageNumber="${i}"/>"> ${i} </a>
        </li>
    </c:forEach>
    <li class="page-item"><a class="page-link" href="<tpag:nextPageUrl activePage="${activePage}"/>">❯</a></li>
</ul>
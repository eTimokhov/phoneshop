<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>

<tags:template>
    <script type="text/javascript">
        let ajaxCartUrl = '<c:url value="/ajaxCart"/>';
    </script>
    <script src="<c:url value="/resources/js/script.js"/>"></script>
    <div class="container">
        <header class="clearfix">
            <h1>Phonify</h1>
            <div class="float-right">
                <c:if test="${not empty pageContext.request.userPrincipal.name}">
                    <span>Hi ${pageContext.request.userPrincipal.name}!</span>
                    <a href="<c:url value="/logout"/> ">Logout</a>
                </c:if>
                <c:if test="${empty pageContext.request.userPrincipal.name}">
                    <a href="<c:url value="/login"/> ">Login</a>
                </c:if>
                <c:if test="${pageContext.request.isUserInRole('ROLE_ADMIN')}">
                    <a href="<c:url value="/admin/orders"/> ">Admin</a>
                </c:if>
                <a href="<c:url value="/cart"/>" id="cart" class="btn btn-outline-secondary">Cart</a>
            </div>
        </header>
        <hr>
        <div>
            <h3>Phones</h3>
            <form class="form-inline float-right" action="<c:url value="/productList"/>">
                <input name="searchTerms" class="form-control" type="text" value="${param.searchTerms}"
                       placeholder="Keywords...">
                <button class="btn btn-light">Search</button>
            </form>
        </div>
        <table class="table table-bordered table-striped">
            <thead>
            <tr>
                <th>Image</th>
                <th>Brand
                    <tags:sortLink orderBy="brand" sortDirection="ASCENDING"/>
                    <tags:sortLink orderBy="brand" sortDirection="DESCENDING"/>
                </th>
                <th>Model
                    <tags:sortLink orderBy="model" sortDirection="ASCENDING"/>
                    <tags:sortLink orderBy="model" sortDirection="DESCENDING"/>
                </th>
                <th>Color</th>
                <th>Display size
                    <tags:sortLink orderBy="displaySizeInches" sortDirection="ASCENDING"/>
                    <tags:sortLink orderBy="displaySizeInches" sortDirection="DESCENDING"/>
                </th>
                <th>Price
                    <tags:sortLink orderBy="price" sortDirection="ASCENDING"/>
                    <tags:sortLink orderBy="price" sortDirection="DESCENDING"/>
                </th>
                <th>Quantity</th>
                <th>Action</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach var="phone" items="${paginationData.phones}">
                <tr class="tr-fixed-height">
                    <td>
                        <img class="img-preview"
                             src="https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/${phone.imageUrl}">
                    </td>
                    <td>${phone.brand}</td>
                    <td><a href="<c:url value="/productDetails/"/>${phone.id}">${phone.model}</a></td>
                    <td>
                        <c:forEach var="color" items="${phone.colors}" varStatus="loop">
                            ${color.code}
                            <c:if test="${not loop.last}">,</c:if>
                        </c:forEach>
                    </td>
                    <td>${phone.displaySizeInches}&#34;</td>
                    <td>$ ${phone.price}</td>
                    <td>
                        <input type="text" id="quantity-input-${phone.id}" value="1"><br>
                        <span class="error error-message" id="error-message-${phone.id}"></span>
                    </td>
                    <td>
                        <button onclick="addToCart(${phone.id}, $('#quantity-input-${phone.id}').val())">Add to cart
                        </button>
                    </td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
        <c:choose>
            <c:when test="${paginationData.totalCount > 0}">
                <tags:pagination activePage="${not empty param.page ? param.page : 1}" resultsPerPage="${paginationData.resultsPerPage}" totalCount="${paginationData.totalCount}"/>
            </c:when>
            <c:otherwise>
                <p>No results found.</p>
            </c:otherwise>
        </c:choose>
    </div>
</tags:template>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>

<tags:template>
    <div class="container">
        <header class="clearfix">
            <h1>Phonify</h1>
            <div class="float-right">
                <a href="#">Login</a>
                <a href="#" class="btn btn-outline-secondary">Cart</a>
            </div>
        </header>
        <hr>
        <div>
            <h3>Phones</h3>
            <form class="form-inline float-right" action="<c:url value="/productList"/>">
                <button class="btn btn-light">Search</button>
                <input name="searchQuery" class="form-control" type="text" value="${param.searchQuery}"
                       placeholder="Keywords...">
            </form>
        </div>
        <table class="table table-bordered table-striped">
            <thead>
            <tr>
                <th>Image</th>
                <th>Brand
                    <tags:sortLink orderBy="brand" asc="true"/>
                    <tags:sortLink orderBy="brand" asc="false"/>
                </th>
                <th>Model
                    <tags:sortLink orderBy="model" asc="true"/>
                    <tags:sortLink orderBy="model" asc="false"/>
                </th>
                <th>Color</th>
                <th>Display size
                    <tags:sortLink orderBy="displaySizeInches" asc="true"/>
                    <tags:sortLink orderBy="displaySizeInches" asc="false"/>
                </th>
                <th>Price
                    <tags:sortLink orderBy="price" asc="true"/>
                    <tags:sortLink orderBy="price" asc="false"/>
                </th>
                <th>Quantity</th>
                <th>Action</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach var="phone" items="${phones}">
                <tr class="tr-fixed-height">
                    <td>
                        <img class="img-preview"
                             src="https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/${phone.imageUrl}">
                    </td>
                    <td>${phone.brand}</td>
                    <td>${phone.model}</td>
                    <td>
                        <c:forEach var="color" items="${phone.colors}" varStatus="loop">
                            ${color.code}
                            <c:if test="${not loop.last}">,</c:if>
                        </c:forEach>
                    </td>
                    <td>${phone.displaySizeInches}&#34;</td>
                    <td>$ ${phone.price}</td>
                    <td>input</td>
                    <td>action</td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
        <tags:pagination activePage="${not empty param.page ? param.page : 1}"/>
    </div>
</tags:template>
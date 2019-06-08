<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="tags" tagdir="/WEB-INF/tags" %>

<tags:template>
    <div class="container">
        <header class="clearfix">
            <h1>Phonify</h1>
            <div class="float-right">
                <a href="#">Login</a>
                <a href="#" class="btn btn-light">Cart</a>
            </div>
        </header>
        <hr>
        <h3>Phones</h3>
        <table class="table table-bordered table-striped">
            <thead>
            <tr>
                <th>Image</th>
                <th>Brand</th>
                <th>Model</th>
                <th>Color</th>
                <th>Display size</th>
                <th>Price</th>
                <th>Quantity</th>
                <th>Action</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach var="phone" items="${phones}">
                <tr>
                    <td>
                        <img class="img-preview"
                             src="https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/${phone.imageUrl}">
                    </td>
                    <td>${phone.brand}</td>
                    <td>${phone.model}</td>
                    <td>
                        <c:forEach var="color" items="${phone.colors}" varStatus="loop">
                            ${color.code}
                            <c:if test="${not loop.last}">, </c:if>
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
        <ul class="pagination float-right">
            <li class="page-item"><a class="page-link" href="#">❮</a></li>
            <li class="page-item active"><a class="page-link" href="#">1</a></li>
            <li class="page-item"><a class="page-link" href="#">2</a></li>
            <li class="page-item"><a class="page-link" href="#">3</a></li>
            <li class="page-item"><a class="page-link" href="#">4</a></li>
            <li class="page-item"><a class="page-link" href="#">❯</a></li>
        </ul>
    </div>
</tags:template>
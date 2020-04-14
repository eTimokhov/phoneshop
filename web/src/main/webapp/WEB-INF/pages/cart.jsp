<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags/form" %>

<tags:template>
    <script type="text/javascript">
        let ajaxCartUrl = '<c:url value="/ajaxCart"/>';
    </script>
    <script src="<c:url value="/resources/js/script.js"/>"></script>

    <div class="container">
        <header class="clearfix">
            <h1>Phonify</h1>
            <div class="float-right">
                <a href="#">Login</a>
                <a href="<c:url value="/cart"/>" id="cart" class="btn btn-outline-secondary">Cart</a>
            </div>
        </header>
        <hr>
        <div>
            <h3>Cart</h3>
        </div>
        <div class="row">
            <a href="<c:url value="/productList"/>" class="btn btn-outline-secondary">Back to product list</a>
        </div>
        <c:if test="${not empty cartItemsWrapper.items}">
            <div class="row">
                <table id="cartItems" class="table table-bordered table-striped">
                    <thead>
                    <tr>
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
                    <spring:form modelAttribute="cartItemsWrapper" id="updateCart" method="PUT">
                        <c:forEach var="cartItem" items="${cartItemsWrapper.items}" varStatus="status">
                            <tr id="${cartItem.phone.id}">
                                <td>${cartItem.phone.brand}</td>
                                <td>${cartItem.phone.model}</td>
                                <td>
                                    <c:forEach var="color" items="${cartItem.phone.colors}">
                                        ${color.code}
                                    </c:forEach>
                                </td>
                                <td>${cartItem.phone.displaySizeInches}&quot;</td>
                                <td>$${cartItem.phone.price}</td>
                                <td>
                                    <spring:input id="qty-${cartItem.phone.id}" path="items[${status.index}].quantity"
                                                  type="text"/>
                                    <spring:errors path="items[${status.index}].quantity" cssClass="error"/>
                                </td>
                                <td>
                                    <button class="btn btn-danger" onclick="$('#qty-${cartItem.phone.id}').val(0)">
                                        Delete
                                    </button>
                                </td>
                            </tr>
                        </c:forEach>
                    </spring:form>
                    </tbody>
                </table>
            </div>
            <button class="btn btn-success" form="updateCart" type="submit">Update</button>
            <a class="btn btn-primary" href="<c:url value="/order"/>" role="button">Order</a>
        </c:if>
    </div>

</tags:template>
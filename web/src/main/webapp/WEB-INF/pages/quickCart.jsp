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
                <a href="<c:url value="/cart"/>" id="cart" class="btn btn-outline-secondary">Cart</a>
            </div>
        </header>
        <hr>
        <div>
            <h3>Quick add products to cart</h3>
        </div>
        <div class="row">
            <a href="<c:url value="/productList"/>" class="btn btn-outline-secondary">Back to product list</a>
        </div>
        <c:forEach var="message" items="${successMessages}">
            <div class="alert alert-success" role="alert">
                    ${message}
            </div>
        </c:forEach>
        <div class="row">
            <spring:form modelAttribute="quickCartInfo"  method="POST">
                <table class="table">
                    <thead>
                        <tr>
                            <th>Product code</th>
                            <th>Quantity</th>
                        </tr>
                    </thead>
                    <tbody>
                    <c:forEach var="cartItemInfo" items="${quickCartInfo.items}" varStatus="status">
                        <tr>
                            <td>
                                <spring:input id="qty-${cartItemInfo.phoneId}" path="items[${status.index}].phoneId"
                                              type="text"/>
                                <spring:errors path="items[${status.index}].phoneId" cssClass="error"/>
                            </td>
                            <td>
                                <spring:input id="qty-${cartItemInfo.quantity}" path="items[${status.index}].quantity"
                                              type="text"/>
                                <spring:errors path="items[${status.index}].quantity" cssClass="error"/>
                            </td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
                <button class="btn btn-success"  type="submit">Add to cart</button>
            </spring:form>
        </div>
    </div>

</tags:template>
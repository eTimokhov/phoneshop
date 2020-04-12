<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags/form" %>

<tags:template>
    <div class="container">
        <div class="row">
            <h1>Phonify</h1>
        </div>
        <hr>
        <div class="row">
            <h3>Order</h3>
        </div>
        <div class="row">
            <a href="<c:url value="/cart"/>" class="btn btn-outline-secondary">Back to cart</a>
        </div>
        <c:if test="${not empty warningMessage}">
            <div class="alert alert-warning" role="alert">
                ${warningMessage}
            </div>
        </c:if>

        <div class="row">
            <spring:form modelAttribute="currentOrder" method="POST">
                <div class="row">
                    <div class="col">

                        <table id="orderItems" class="table table-bordered table-striped">
                            <thead>
                            <tr>
                                <th>Brand</th>
                                <th>Model</th>
                                <th>Color</th>
                                <th>Display size</th>
                                <th>Quantity</th>
                                <th>Price</th>
                            </tr>
                            </thead>
                            <tbody>
                            <c:forEach var="orderItem" items="${currentOrder.orderItems}" varStatus="status">
                                <tr>
                                    <td>${orderItem.phone.brand}</td>
                                    <td>${orderItem.phone.model}</td>
                                    <td>
                                        <c:forEach var="color" items="${orderItem.phone.colors}">
                                            ${color.code}
                                        </c:forEach>
                                    </td>
                                    <td>${orderItem.phone.displaySizeInches}&quot;</td>
                                    <td>${orderItem.quantity}</td>
                                    <td>$${orderItem.phone.price}</td>
                                </tr>
                            </c:forEach>
                            <tr>
                                <td></td>
                                <td></td>
                                <td></td>
                                <td></td>
                                <td>Subtotal</td>
                                <td>$${currentOrder.subtotal}</td>
                            </tr>
                            <tr>
                                <td></td>
                                <td></td>
                                <td></td>
                                <td></td>
                                <td>Delivery</td>
                                <td>$${currentOrder.deliveryPrice}</td>
                            </tr>
                            <tr>
                                <td></td>
                                <td></td>
                                <td></td>
                                <td></td>
                                <td>Total</td>
                                <td>$${currentOrder.totalPrice}</td>
                            </tr>
                            </tbody>
                        </table>

                    </div>
                    <div class="col">

                        <div class="form-group">
                            <label for="firstName">First name</label>
                            <spring:input type="text" class="form-control" id="firstName" path="firstName"/>
                            <spring:errors path="firstName" element="div" cssClass="error"/>
                        </div>
                        <div class="form-group">
                            <label for="lastName">Last name</label>
                            <spring:input type="text" class="form-control" id="lastName" path="lastName"/>
                            <spring:errors path="firstName" cssClass="error"/>
                        </div>
                        <div class="form-group">
                            <label for="deliveryAddress">Address</label>
                            <spring:input type="text" class="form-control" id="deliveryAddress" path="deliveryAddress"/>
                            <spring:errors path="firstName" cssClass="error"/>
                        </div>
                        <div class="form-group">
                            <label for="contactPhoneNo">Phone</label>
                            <spring:input type="text" class="form-control" id="contactPhoneNo" path="contactPhoneNo"/>
                            <spring:errors path="firstName" cssClass="error"/>
                        </div>
                        <div class="form-group">
                            <label for="additionalInfo">Additional information</label>
                            <spring:textarea class="form-control" id="additionalInfo" path="additionalInfo"/>
                        </div>
                        <button type="submit" class="btn btn-primary">Submit</button>
                    </div>
                </div>
            </spring:form>
        </div>
    </div>

</tags:template>
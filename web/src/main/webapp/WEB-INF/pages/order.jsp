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
        <div class="row">
            <c:if test="${not empty warningMessage}">
                <div class="alert alert-warning" role="alert">
                        ${warningMessage}
                </div>
            </c:if>
        </div>
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
                        <c:forEach var="cartItem" items="${cart.cartItems}" varStatus="status">
                            <tr>
                                <td>${cartItem.phone.brand}</td>
                                <td>${cartItem.phone.model}</td>
                                <td>
                                    <c:forEach var="color" items="${cartItem.phone.colors}">
                                        ${color.code}
                                    </c:forEach>
                                </td>
                                <td>${cartItem.phone.displaySizeInches}&quot;</td>
                                <td>${cartItem.quantity}</td>
                                <td>$${cartItem.phone.price}</td>
                            </tr>
                        </c:forEach>
                        <tr>
                            <td></td>
                            <td></td>
                            <td></td>
                            <td></td>
                            <td>Subtotal</td>
                            <td>$${cart.subtotal}</td>
                        </tr>
                        <tr>
                            <td></td>
                            <td></td>
                            <td></td>
                            <td></td>
                            <td>Delivery</td>
                            <td>$${cart.deliveryPrice}</td>
                        </tr>
                        <tr>
                            <td></td>
                            <td></td>
                            <td></td>
                            <td></td>
                            <td>Total</td>
                            <td>$${cart.totalPrice}</td>
                        </tr>
                        </tbody>
                    </table>

                </div>
                <div class="col">
                    <spring:form modelAttribute="orderForm" method="POST">
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
                            <spring:input type="text" class="form-control" id="deliveryAddress"
                                          path="deliveryAddress"/>
                            <spring:errors path="firstName" cssClass="error"/>
                        </div>
                        <div class="form-group">
                            <label for="contactPhoneNo">Phone</label>
                            <spring:input type="text" class="form-control" id="contactPhoneNo"
                                          path="contactPhoneNo"/>
                            <spring:errors path="firstName" cssClass="error"/>
                        </div>
                        <div class="form-group">
                            <label for="additionalInfo">Additional information</label>
                            <spring:textarea class="form-control" id="additionalInfo" path="additionalInfo"/>
                        </div>
                        <button type="submit" class="btn btn-primary">Submit</button>
                    </spring:form>
                </div>
            </div>
    </div>

</tags:template>
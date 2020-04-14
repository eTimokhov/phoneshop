<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags/form" %>

<tags:template>
    <div class="container">
        <header class="clearfix">
            <h1>Phonify</h1>
            <div class="float-right">
                <span>admin</span>
                <a href="#">Login</a>
            </div>
        </header>
        <hr>
        <div class="row">
            <a class="btn btn-secondary" href="<c:url value="/admin/orders"/>" role="button">Back to orders</a>
        </div>
        <div class="row">
            <h4>Order number: ${order.id}</h4>
        </div>
        <div class="row">
            <h4>Order status: ${order.status}</h4>
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
                    <c:forEach var="orderItem" items="${order.orderItems}" varStatus="status">
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
                        <td>$${order.subtotal}</td>
                    </tr>
                    <tr>
                        <td></td>
                        <td></td>
                        <td></td>
                        <td></td>
                        <td>Delivery</td>
                        <td>$${order.deliveryPrice}</td>
                    </tr>
                    <tr>
                        <td></td>
                        <td></td>
                        <td></td>
                        <td></td>
                        <td>Total</td>
                        <td>$${order.totalPrice}</td>
                    </tr>
                    </tbody>
                </table>

            </div>
            <div class="col">
                <div class="row">
                    <table class="table table-borderless">
                        <tr>
                            <td>First name</td>
                            <td>${order.firstName}</td>
                        </tr>
                        <tr>
                            <td>Last name</td>
                            <td>${order.lastName}</td>
                        </tr>
                        <tr>
                            <td>Address</td>
                            <td>${order.deliveryAddress}</td>
                        </tr>
                        <tr>
                            <td>Phone</td>
                            <td>${order.contactPhoneNo}</td>
                        </tr>
                    </table>
                    <p>${order.additionalInfo}</p>
                </div>
                <div class="row">
                    <form method="post">
                        <input type="hidden" name="orderId" value="${order.id}">
                        <input type="hidden" name="orderStatus" value="NEW">
                        <button class="btn btn-primary">New</button>
                    </form>
                    <form method="post">
                        <input type="hidden" name="orderId" value="${order.id}">
                        <input type="hidden" name="orderStatus" value="DELIVERED">
                        <button class="btn btn-primary">Delivered</button>
                    </form>
                    <form method="post">
                        <input type="hidden" name="orderId" value="${order.id}">
                        <input type="hidden" name="orderStatus" value="REJECTED">
                        <button class="btn btn-primary">Rejected</button>
                    </form>
                </div>
            </div>
        </div>
    </div>

</tags:template>
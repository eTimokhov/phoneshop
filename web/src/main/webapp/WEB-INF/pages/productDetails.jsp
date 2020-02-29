<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix = "fn" uri = "http://java.sun.com/jsp/jstl/functions"%>

<tags:template>
    <script type="text/javascript">
        let addToCartUrl = '<c:url value="/ajaxCart"/>';
        let getCartInfoUrl = '<c:url value="/ajaxCart"/>';
    </script>
    <script src="<c:url value="/resources/js/productList.js"/>"></script>
    <div class="container">
        <header class="clearfix">
            <h1>Phonify</h1>
            <div class="float-right">
                <a href="#">Login</a>
                <a href="#" id="cart" class="btn btn-outline-secondary">Cart</a>
            </div>
        </header>
        <div class="row">
            <a href="<c:url value="/productList"/>" class="btn btn-outline-secondary">Back to product list</a>
        </div>
        <div class="row">
            <div class="col">
                <div class="row">
                    <h2>${phone.model}</h2>
                </div>
                <div class="row">
                    <img src="https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/${phone.imageUrl}">
                </div>
                <div class="row">
                    <p>${phone.description}</p>
                </div>
                <div class="row">
                    <div class="col">
                        <div class="row">
                            <b>Price: </b>$${empty phone.price ? "-" : phone.price}
<%--                            <c:choose>--%>
<%--                                <c:when test = "${empty phone.price}">--%>
<%--                                    ---%>
<%--                                </c:when>--%>
<%--                                <c:otherwise>--%>
<%--                                    $<fmt:formatNumber value="${phone.price}" minFractionDigits="0"/>--%>
<%--                                </c:otherwise>--%>
<%--                            </c:choose>--%>
                        </div>
                        <div class="row">
                            <input class="form-control" type="text" id="quantity-input" value="1">
                            <button class="btn btn-primary"
                                    onclick="addToCart(${phone.id}, $('#quantity-input').val())">Add
                                to cart
                            </button>
                        </div>
                        <div class="row">
                            <p class="error error-message" id="error-message-${phone.id}"></p>
                        </div>
                    </div>
                    <div class="col"></div>
                    <div class="col"></div>
                </div>
            </div>
            <div class="col">
                <div class="row">
                    <div class="col"></div>
                    <div class="col">

                        <div class="row">
                            <h5>Display</h5>
                            <table class="table table-sm">
                                <tbody>
                                <tr>
                                    <td>Size</td>
                                    <td>${phone.displaySizeInches}&quot;</td>
                                </tr>
                                <tr>
                                    <td>Resolution</td>
                                    <td>${phone.displayResolution}</td>
                                </tr>
                                <tr>
                                    <td>Technology</td>
                                    <td>${phone.displayTechnology}</td>
                                </tr>
                                <tr>
                                    <td>Pixel density</td>
                                    <td>${phone.pixelDensity}</td>
                                </tr>
                                </tbody>
                            </table>
                        </div>

                        <div class="row">
                            <h5>Dimensions & weight</h5>
                            <table class="table table-sm">
                                <tbody>
                                <tr>
                                    <td>Length</td>
                                    <td>${phone.lengthMm} mm</td>
                                </tr>
                                <tr>
                                    <td>Width</td>
                                    <td>${phone.widthMm} mm</td>
                                </tr>
                                <tr>
                                    <td>Weight</td>
                                    <td>${phone.weightGr}</td>
                                </tr>
                                </tbody>
                            </table>
                        </div>

                        <div class="row">
                            <h5>Camera</h5>
                            <table class="table table-sm">
                                <tbody>
                                    <c:if test="${not empty phone.frontCameraMegapixels}">
                                        <tr>
                                            <td>Front</td>
                                            <td>${phone.frontCameraMegapixels} megapixels</td>
                                        </tr>
                                    </c:if>
                                <tr>
                                    <td>Back</td>
                                    <td>${phone.backCameraMegapixels} megapixels</td>
                                </tr>
<%--                                <tr>--%>
<%--                                    <td>Color</td>--%>
<%--                                    <td>${phone.colors}</td>--%>
<%--                                </tr>--%>
<%--                                <tr>--%>
<%--                                    <td>Weight</td>--%>
<%--                                    <td>${phone.weightGr}</td>--%>
<%--                                </tr>--%>
                                </tbody>
                            </table>
                        </div>

                        <div class="row">
                            <h5>Battery</h5>
                            <table class="table table-sm">
                                <tbody>
                                <c:if test="${not empty phone.talkTimeHours}">
                                    <tr>
                                        <td>Talk time</td>
                                        <td>${phone.talkTimeHours} hours</td>
                                    </tr>
                                </c:if>
                                <c:if test="${not empty phone.standByTimeHours}">
                                    <tr>
                                        <td>Stand by time</td>
                                        <td>${phone.standByTimeHours} hours</td>
                                    </tr>
                                </c:if>
                                <tr>
                                    <td>Battery capacity</td>
                                    <td>${phone.batteryCapacityMah}mAh</td>
                                </tr>
                                </tbody>
                            </table>
                        </div>

                        <div class="row">
                            <h5>Other</h5>
                            <table class="table table-sm">
                                <tbody>
                                <tr>
                                    <td>Colors</td>
<%--                                    <td>${phone.colors} mm</td>--%>
                                    <td>
                                        <c:forEach var="color" items="${phone.colors}">
                                            ${color.code}
                                        </c:forEach>
                                    </td>
                                </tr>
                                <tr>
                                    <td>Device type</td>
                                    <td>${phone.deviceType}</td>
                                </tr>
                                <tr>
                                    <td>Bluetooth</td>
                                    <td>${phone.bluetooth}</td>
                                </tr>
                                </tbody>
                            </table>
                        </div>

                    </div>
                </div>
            </div>
        </div>
        <hr>
    </div>
</tags:template>
const errorMap = new Map([
    ["NotNull", "This field cannot be empty"],
    ["Min", "Value must be greater than 0"],
    ["typeMismatch", "Enter a valid number"],
    ["outOfStock", "Product is out of stock"]
]);

$(function () {
    var token = $("meta[name='_csrf']").attr("content");
    var header = $("meta[name='_csrf_header']").attr("content");
    $(document).ajaxSend(function(e, xhr, options) {
        xhr.setRequestHeader(header, token);
    });
});


function addToCart(phoneId, quantity) {
    $.ajax(ajaxCartUrl, {
        type: 'POST',
        data: {
            phoneId: phoneId,
            quantity: quantity,
        },
        success: () => {
            clearErrorMessages();
            getCartInfo();
        },
        error: response => {
            addToCartErrorCallback(response.responseText, phoneId);
        }
    })
}

function clearErrorMessages() {
    $(".error-message").text("");
}

function addToCartErrorCallback(errorCode, phoneId) {
    let errorMessageSpanId = `#error-message-${phoneId}`;
    let message = errorMap.get(errorCode);
    if (message === undefined) {
        message = errorCode;
    }
    $(errorMessageSpanId).text(message);
}

function getCartInfo() {
    $.get(ajaxCartUrl, getCartInfoCallback)
}

function getCartInfoCallback(cart) {
    updateCart(cart)
}

function updateCart(cart) {
    let totalCount = 0;
    for (let cartItem of cart.cartItems) {
        totalCount += cartItem.quantity;
    }
    $("#cart").text(`Cart: ${totalCount} items, $${cart.subtotal}`);
}

$(function () {
    getCartInfo();
});
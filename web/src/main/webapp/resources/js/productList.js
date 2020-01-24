function addToCart(phoneId, quantity) {
    $.post(addToCartUrl, {
        phoneId: phoneId,
        quantity: quantity
    }, addToCartCallback);
}

function addToCartCallback(response) {
    $(".error-message").text("");
    if (response.success) {
        getCartInfo();
    } else {
        let errorMessageSpanId = `#error-message-${response.phoneId}`;
        $(errorMessageSpanId).text(response.errorMessage);
    }
}

function getCartInfo() {
    $.get(getCartInfoUrl, getCartInfoCallback)
}

function getCartInfoCallback(cartInfo) {
    updateCart(cartInfo.totalCount, cartInfo.totalPrice)
}

function updateCart(totalCount, totalPrice) {
    $("#cart").text(`Cart: ${totalCount} items, $${totalPrice}`)
}

$(function () {
    getCartInfo();
});
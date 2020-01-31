//NotNull Min typeMismatch OutOfStockException
const errorMap = new Map([
    ["NotNull", "This field cannot be empty"],
    ["Min", "Value must be greater than 0"],
    ["typeMismatch", "Enter a valid number"],
    ["OutOfStockException", "Product is out of stock"]
]);


function addToCart(phoneId, quantity) {
    $.ajax(addToCartUrl, {
        type: 'POST',
        data: {
            phoneId: phoneId,
            quantity: quantity
        },
        success: addToCartCallback
    })
}

function addToCartCallback(response) {
    $(".error-message").text("");
    if (response.success) {
        getCartInfo();
    } else {
        let errorMessageSpanId = `#error-message-${response.phoneId}`;
        let message = errorMap.get(response.errorCode);;
        if (message === undefined) {
            message = response.errorCode;
        }
        $(errorMessageSpanId).text(message);

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
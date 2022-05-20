loadAllCustomerIds();
loadAllItemIds();

function loadAllItemIds(){
    $("#cmbItemIds").empty();

    for (const item of itemArray) {
        $("#cmbItemIds").append(new Option(item.id));
    }
}

function loadAllCustomerIds(){
    $("#customerId").empty();

    for (const customer of customerArray) {
        $("#customerId").append(new Option(customer.id));
    }
}

function setItemDetailsFromItemCode(){

}

function generateOrderId(){

}

function clearPlaceOrderFields(){
    clearItemsFieldsPlaceOrder();

    $("#customerName").val("");
    $("#customerId1").val("");
    $("#oDate").val("");

    $("#grossAmount").val("");
    $("#netAmount").val("");
    $("#cash").val("");
    $("#balanceLabel").val("");
    $("#orderDiscount").val("");

}

function placeOrder(){

    let detailsArray = new Array();

        var orderDetail = {
            itemId: $("#itemId").val(),
            description: $("#description").val(),
            customerQTY: $("#custQTY").val(),
            uniPrices: $("#unitPrices").val(),
            total: $("#netAmount").val(),

        }
        detailsArray.push(orderDetail);

    var order={
        orderId:$("#orderId").val(),
        customerId:$("#customerId1").val(),
        orderDate:$("#oDate").val(),
        netTotal:$("#netAmount").val(),
        items:detailsArray
    }

    $.ajax({
        url:"http://localhost:8080/backend/purchaseOrder?",
        method:"POST",
        contentType:"application/json",
        data: JSON.stringify(order),
        success:function (response) {
            if (response.status == 200){
                if (response.message == "Successfully Purchased Order."){
                    alert(response.message);

                }else if (response.message == "Error"){
                    alert(response.data);
                }
            }else if (response.status == "400"){
                alert(response.data);
            }
        },
        error:function (ob , statusText , error) {
            alert(statusText);
        }
    });



}

$("#customerId").on("change",function(){

        let cusId = $(this).find('option:selected').text();

        $.ajax({
            url: "http://localhost:8080/backend/customer?option=SEARCH&cusId=" + cusId,
            method: "GET",
            success: function (response) {

                $("#customerId1").val(cusId);
                $("#customerName").val(response.name);
            },
            error: function (ob, statusText, error) {
                alert("No Such Customer");
                loadAllCustomers();
            }
        });
    });

$("#cmbItemIds").on("change",function(){

    let itemId = $(this).find('option:selected').text();

    $.ajax({
        url: "http://localhost:8080/backend/item?option=SEARCH&itemCode=" + itemId,
        method: "GET",
        success: function (response) {
            $("#itemId").val(itemId);
            $("#description").val(response.description);
            $("#unitPrices").val(response.unitPrice);
            $("#qTY").val(response.qtyOnHand);

        },
        error: function (ob, statusText, error) {
            alert("No Such Customer");
            loadAllCustomers();
        }
    });
});



function searchPlaceOrder(oId){

}

function setCustomerNameCusId(){

}

function updatePlaceOrder(){

}

function clearItemsFieldsPlaceOrder(){
    $("#description").val("");
    $("#custQTY").val("");
    $("#qTY").val("");
    $("#unitPrices").val("");
    $("#discount").val("");
    $("#itemId").val("");
}

function setGrossAmount(){

}

function addToCart(){

}

function loadTable(){

}




$("#btnPlaceOrder").click(function (){


});

$("#btnSearchOrder").click(function (){

});

$("#btnUpdateOrder").click(function (){

});


$("#addToCart").click(function (){


});



/*-------------Set netAmount----------------*/

$("#orderDiscount").keyup(function (){


});


/*--------------Set balance----------------*/

$("#cash").keyup(function (){



});



/*---PlaceOrder Form-----*/

var regExDiscounts = /^([0-9.]{1,})$/;
var regExCustomerQty = /^([0-9]{1,10})$/;


$("#custQTY").keyup(function (){

    let input = parseInt($("#custQTY").val());
    let qty =0;

    for (let i = 0; i < itemArray.length; i++) {
        if ($("#itemId").val() === itemArray[i].getItemId()){
            qty = parseInt(itemArray[i].getQtyOnHand());
        }
    }

    if (regExCustomerQty.test(input)) {
        if (qty >= input){
            $("#error002").text("");
            $("#custQTY").css('border', '2px solid green');
            return true;

        }else {
            $("#custQTY").css('border', '2px solid red');
            $("#error002").text("Exceed the QTY On Hand");
            return false;
        }
    } else {
            $("#custQTY").css('border', '2px solid red');
            $("#error002").text("Wrong format : "+input);
            return false;
    }
});


$("#placeOrderTable").css('overflow-y','hidden');
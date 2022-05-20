loadAllCustomerIds();
loadAllItemIds();

var itemDetailsArray = [];

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


function clearItemsFieldsPlaceOrder(){
    $("#description").val("");
    $("#custQTY").val("");
    $("#qTY").val("");
    $("#unitPrices").val("");
    $("#discount").val("");
    $("#itemId").val("");
}

function setGrossAmount(){
    var grossTotal = 0;
    let itemId = $("#itemId").val();

    for (let i = 0; i < itemDetailsArray.length; i++) {

        grossTotal = parseInt(grossTotal) + parseInt(itemDetailsArray[i].getItemTotal());
        $("#grossAmount").val(grossTotal);
    }
}

function addToCart(){
    let itemId = $("#itemId").val();
    let description = $("#description").val();
    let cusQTY = $("#custQTY").val();
    let unitPrices = $("#unitPrices").val();
    let total = (cusQTY) * (unitPrices);


    for (let i = 0; i < itemDetailsArray.length; i++) {
        if (itemId === itemDetailsArray[i].getOrderItemCode()){

            var newCusQTY = 0;

            if ($("#addToCart").text() === "Update"){
                newCusQTY = cusQTY;
            }else {
                newCusQTY = parseInt(itemDetailsArray[i].getOrderCustomerQTY())  + parseInt(cusQTY);

                if(newCusQTY > itemArray[i].getQtyOnHand()){
                    $("#custQTY").css('border', '2px solid red');
                    $("#error002").text("Exceed the QTY On Hand");
                    return;
                }
            }


            var newTotal = (newCusQTY) * (unitPrices);

            itemDetailsArray[i].setOrderCustomerQTY(newCusQTY);
            itemDetailsArray[i].setItemTotal(newTotal);
            clearItemsFieldsPlaceOrder();
            loadTable();
            setGrossAmount();
            setNetAmount();
            return;
        }

    }
    var itemDetails = new ItemDetails(itemId,description,cusQTY,unitPrices,total);
    itemDetailsArray.push(itemDetails);
    clearItemsFieldsPlaceOrder();
    loadTable();
    setGrossAmount();
    setNetAmount();
    $("#btnPlaceOrder").attr('disabled',true);
}

function loadTable(){
    $("#placeOrderTable>tr").empty();

    for (let i of itemDetailsArray) {
        let row = `<tr><td>${i.getOrderItemCode()}</td><td>${i.getOrderItemDescription()}</td><td>${i.getOrderCustomerQTY()}</td><td>${i.getOrderUnitPrice()}</td><td>${i.getItemTotal()}</td></tr>`;
        $("#placeOrderTable").append(row);
    }
}

function setNetAmount(){
    let discount = parseInt($("#orderDiscount").val());
    let grossAmount = parseInt($("#grossAmount").val());

    if (isNaN(discount)){
        $("#netAmount").val(grossAmount);
    }else {
        $("#netAmount").val(grossAmount - discount);
    }


}

function validateAll(){
    let netAmount = $("#netAmount").val();
    let cash = $("#cash").val();

    let net = parseInt(netAmount);
    let cash1 = parseInt(cash);

    if ($("#orderId").val()!=""){
        if ($("#customerId1").val()!=""){
            if (itemDetailsArray.length!=0){
                if ($("#cash").val()!="" && cash1>=net){
                    return true;
                }else {
                    return false;
                }
            }else {
                return false;
            }
        }else {
            return false;
        }
    }else {
        return false;
    }
}

function enableDisablePlaceOrderBtn(){
    if (validateAll()){
        $("#btnPlaceOrder").attr("disabled",false);
    }else {
        $("#btnPlaceOrder").attr("disabled",true);
    }


}



$("#btnPlaceOrder").click(function (){


});

$("#btnSearchOrder").click(function (){

});


$("#addToCart").click(function (){
addToCart();
});



/*-------------Set netAmount----------------*/

$("#orderDiscount").keyup(function (){

    setNetAmount();

    let cash = $("#cash").val();
    let netAmount = $("#netAmount").val();

    if (cash!==""){
        let balance = (cash) - (netAmount);
        $("#balanceLabel").val(balance);

    }

});

/*--------------Set balance----------------*/

$("#cash").keyup(function (){

    let cash = parseInt($("#cash").val());
    let netAmount = $("#netAmount").val();

    $("#balanceLabel").val(cash - netAmount);


    let discount = $("#orderDiscount").val();
    let grossAmount = parseInt($("#grossAmount").val());

    if (discount==""){
        $("#balanceLabel").val(cash - grossAmount);
    }
enableDisablePlaceOrderBtn();
});



/*---PlaceOrder Form-----*/

var regExDiscounts = /^([0-9.]{1,})$/;
var regExCustomerQty = /^([0-9]{1,10})$/;


$("#custQTY").keyup(function (){

    let input = parseInt($("#custQTY").val());
    let qty =    parseInt($("#qTY").val());



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






/*-----------------------------------------------------------------------------------------------------*/

function ItemDetails(itemCode,description,customerQTY,unitPrice,total){
    var __itemCode = itemCode;
    var __description = description;
    var __customerQTY = customerQTY;
    var __unitPrice = unitPrice;
    var __total = total;


    this.getOrderItemCode = function (){
        return __itemCode;
    }

    this.setItemCode = function (id){
        __itemCode = id;
    }

    this.getOrderItemDescription = function (){
        return __description;
    }

    this.setOrderItemDescription = function (description){
        __description = description;
    }

    this.getOrderCustomerQTY = function (){
        return __customerQTY;
    }

    this.setOrderCustomerQTY = function (qty){
        __customerQTY = qty;
    }

    this.getOrderUnitPrice = function (){
        return __unitPrice;
    }

    this.setOrderUnitPrice = function (unitPrice){
        __unitPrice = unitPrice;
    }

    this.getItemTotal = function (){
        return __total;
    }

    this.setItemTotal = function (total){
        __total = total;
    }



}





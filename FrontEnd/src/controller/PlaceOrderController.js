generateOrderId();

setItemDetailsFromItemCode();

setCustomerDetailsFromCusId();

setCustomerNameCusId();

function loadAllItemIds(){
    $("#cmbItemIds").empty();
    $("#itemCodes").empty();


    for (let i = 0; i < itemArray.length; i++) {
        $("#cmbItemIds").append(new Option(itemArray[i].getItemId()));
        $("#itemCodes").append(new Option(itemArray[i].getItemId()));
    }
}

function loadAllCustomerIds(){
    $("#customerId").empty();

    for (let i = 0; i < customerArray.length; i++) {
        $("#customerId").append(new Option(customerArray[i].getCustomerId()));
    }
}

function setItemDetailsFromItemCode(){
    $("#cmbItemIds").on("change",function(){

        let itemId = $(this).find('option:selected').text();

        for (let i = 0; i < itemArray.length; i++) {
            if (itemId === itemArray[i].getItemId()){

                $("#itemId").val(itemId);
                $("#qTY").val(itemArray[i].getQtyOnHand());
                $("#discount").val(itemArray[i].getDiscount());
                $("#unitPrices").val(itemArray[i].getUnitPrize());
                $("#description").val(itemArray[i].getItemDescription());

            }
        }
    });
}

function generateOrderId(){
    if (orderDetailsArray.length !== 0){
        var orderId = orderDetailsArray[orderDetailsArray.length-1].getOrderId();
        let splitTxt = orderId.split("O",2);
        let newOrderId = parseInt(splitTxt[1]) + 1;

        if (parseInt(newOrderId) <= 9){
            $("#orderId").val("O00" + newOrderId);

        }else if (parseInt(newOrderId) <= 99){
            $("#orderId").val("O0" + newOrderId);

        }else if (parseInt(newOrderId) <= 99){
            $("#orderId").val("O" + newOrderId);
        }
    }else {
        $("#orderId").val("O001").css('font-weight', 'bold');
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

    let orderId = $("#orderId").val();
    let cusId = $("#customerId").find('option:selected').text();
    let orderDate = $("#oDate").val();
    let total = $("#balanceLabel").val();


    var orderDetails = new OrderDetails(orderId,cusId,orderDate,total,itemDetailsArray);
    orderDetailsArray.push(orderDetails);

    itemDetailsArray = [];
    generateOrderId();
    clearPlaceOrderFields();
    alert("Order has been place successful");

    $("#placeOrderTable>tr").remove();

}

function setCustomerDetailsFromCusId(){
    $("#customerId").on("change",function(){

        let cusId = $(this).find('option:selected').text();
        console.log(cusId);

        for (let i = 0; i < customerArray.length; i++) {
            if (cusId === customerArray[i].getCustomerId()){

                $("#customerId1").val(cusId);
                $("#customerName").val(customerArray[i].getCustomerName());
            }
        }
    });

}

function searchPlaceOrder(oId){
    for (let i = 0; i < orderDetailsArray.length; i++) {
        if (oId === orderDetailsArray[i].getOrderId()){
            return orderDetailsArray[i];
        }
    }
}

function setCustomerNameCusId(){
    $("#customerId").on("change",function(){

        let cusId = $(this).find('option:selected').text();

        for (let i = 0; i < customerArray.length; i++) {
            if (cusId === customerArray[i].getCustomerId()){

                $("#customerName").val(customerArray[i].getCustomerName());
            }
        }
    });

}

function updatePlaceOrder(){

    $("#placeOrderTable>tr").off("click");

    let itemId = $("#cmbItemIds").val();
    let description = $("#description").val();
    let cusQTY = $("#custQTY").val();
    let unitPrices = $("#unitPrices").val();
    let itemDiscount = $("#discount").val();


    $("#placeOrderTable>tr").empty();
    for (let i = 0; i < itemDetailsArray.length; i++) {
        if ( $("#cmbItemIds").val() === itemDetailsArray[i].getOrderItemCode()) {
            itemDetailsArray.splice(i, 1);

            clearPlaceOrderFields();
        }
    }


    var itemDetails = new ItemDetails(itemId,description,cusQTY,unitPrices,itemDiscount);
    itemArray.push(itemDetails);

    loadOrderDetailsToTbl();
    alert("Order has been successfully updated");

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

    var grossTotal = 0;
    let itemId = $("#itemId").val();

    for (let i = 0; i < itemDetailsArray.length; i++) {
       /* if(itemId === itemDetailsArray[i].getOrderItemCode()){

        }else{
            grossTotal = parseInt(grossTotal) + parseInt(itemDetailsArray[i].getItemTotal());
            $("#grossAmount").val(grossTotal);
        }*/


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
            return;
        }

    }
    var itemDetails = new ItemDetails(itemId,description,cusQTY,unitPrices,total);
    itemDetailsArray.push(itemDetails);
    clearItemsFieldsPlaceOrder();
    loadTable();
    setGrossAmount();
    $("#btnPlaceOrder").attr('disabled',true);
}

function loadTable(){

    $("#placeOrderTable>tr").empty();

    for (let i of itemDetailsArray) {
        let row = `<tr><td>${i.getOrderItemCode()}</td><td>${i.getOrderItemDescription()}</td><td>${i.getOrderCustomerQTY()}</td><td>${i.getOrderUnitPrice()}</td><td>${i.getItemTotal()}</td></tr>`;
        $("#placeOrderTable").append(row);
    }


    /*-----------------Set cart details when clicking-----------------------*/

    $("#placeOrderTable>tr").off('click')

    $("#placeOrderTable>tr").click(function (){

        $("#addToCart").text("Update");

        let itemCode = $(this).children(':nth-child(1)').text();
        $("#itemId").val(itemCode);

        let description = $(this).children(':nth-child(2)').text();
        $("#description").val(description);


        let cusQTY = $(this).children(':nth-child(3)').text();
        $("#custQTY").val(cusQTY);


        let unitPrice = $(this).children(':nth-child(4)').text();
        $("#unitPrices").val(unitPrice);


        for (let i = 0; i < itemArray.length; i++) {
            if ($("#itemId").val() === itemArray[i].getItemId()){
                $("#qTY").val(itemArray[i].getQtyOnHand());
            }
            continue;
        }


    });
}




$("#btnPlaceOrder").click(function (){
    generateOrderId();
    placeOrder();

    clearPlaceOrderFields();

});

$("#btnSearchOrder").click(function (){

    let obOrder = searchPlaceOrder($("#txtSearchOrder").val());

    if (obOrder){
        for (let i = 0; i < customerArray.length; i++) {
            if (obOrder.getCusId() === customerArray[i].getCustomerId()){
                $("#customerName").val(customerArray[i].getCustomerName());
            }
        }

        $("#orderId").val(obOrder.getOrderId());
        $("#customerId1").val(obOrder.getCusId());
        $("#oDate").val(obOrder.getOrdDate());


        itemDetailsArray = obOrder.getItemArray();
        console.log(itemDetailsArray);
        loadTable();

    }
});

$("#btnUpdateOrder").click(function (){
    generateOrderId();
    updatePlaceOrder();
});


$("#addToCart").click(function (){
    addToCart();
    $("#addToCart").text("Add To Cart");

});



/*-------------Set netAmount----------------*/

$("#orderDiscount").keyup(function (){

    let discount = parseInt($("#orderDiscount").val());
    let grossAmount = parseInt($("#grossAmount").val());

    $("#netAmount").val(grossAmount - discount);


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
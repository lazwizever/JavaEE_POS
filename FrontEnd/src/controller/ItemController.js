generateItemIds();

function saveItem(){
    $("#customerTable>tr").off("click");

    let itemCode = $("#itemCode").val();
    let itemDescription = $("#inputDescription").val();
    let packSize = $("#packSize").val();
    let unitPrice = $("#unitPrice").val();
    let qty = $("#inputQTy").val();
    let discount = $("#inputDiscount").val();


    $("#itemTable").empty();

    var item = new Item(itemCode,itemDescription,packSize,unitPrice,qty,discount);
    itemArray.push(item);
    clearItemTextFields();

    generateItemIds();
    loadItemDetailsToTbl();
    alert("Item has been added successfully");


}

function loadItemDetailsToTbl(){
    $("#itemTable>tr").off("click");
    $("#itemTable>tr").off("dblclick");

    for (let i of itemArray) {
        let row = `<tr><td>${i.getItemId()}</td><td>${i.getItemDescription()}</td><td>${i.getPackSize()}</td><td>${i.getUnitPrize()}</td><td>${i.getQtyOnHand()}</td></tr>`;
        $("#itemTable").append(row);

    }


/*-----Delete Item Details When Double Clicking----*/

    $("#itemTable>tr").dblclick(function (){
        confirm("Do you really want to remove this item...?");

        for (let i = 0; i < itemArray.length; i++) {
            if ( $(this).children(":eq(0)").text() === itemArray[i].getItemId()) {
                itemArray.splice(i, 1);

                $(this).remove();
                clearItemTextFields();
            }
        }
    });



/*-----Set item details to tbl--------*/

    $("#itemTable>tr").click(function(){

        let itemCode = $(this).children(':nth-child(1)').text();
        $("#itemCode").val(itemCode);

        let description = $(this).children(':nth-child(2)').text();
        $("#inputDescription").val(description);

        let packSize = $(this).children(':nth-child(3)').text();
        $("#packSize").val(packSize);

        let unitPrice = $(this).children(':nth-child(4)').text();
        $("#unitPrice").val(unitPrice);

        let QTY = $(this).children(':nth-child(5)').text();
        $("#inputQTy").val(QTY);

        let discount = $(this).children(':nth-child(6)').text();
        $("#inputDiscount").val(discount);

    });
}

function generateItemIds(){
    if (itemArray.length !== 0){
        var itemId = itemArray[itemArray.length-1].getItemId();
        let splitTxt = itemId.split("I",2);
        let newItemId = parseInt(splitTxt[1]) + 1;

        if (parseInt(newItemId) <= 9){
            $("#itemCode").val("I00" + newItemId);

        }else if (parseInt(newItemId) <= 99){
            $("#itemCode").val("I0" + newItemId);

        }else if (parseInt(newItemId) <= 99){
            $("#itemCode").val("I" + newItemId);
        }
    }else {
        $("#itemCode").val("I001").css('font-weight', 'bold');
    }
}

function clearItemTextFields(){
    $("#itemCode").val("");
    $("#inputDescription").val("");
    $("#packSize").val("");
    $("#unitPrice").val("");
    $("#inputQTy").val("");
    $("#inputDiscount").val("");
}

function deleteItem(){
    confirm("Do you really want to remove this customer...?");

    $("#itemTable").empty();
    for (let i = 0; i < itemArray.length; i++) {
        if ($("#itemCode").val() === itemArray[i].getItemId()) {
            itemArray.splice(i, 1);

            clearItemTextFields();
        }
    }
    loadItemDetailsToTbl();
    generateItemIds();
}

function updateItem(){

    $("#itemTable>tr").off("click");

    let itemCode = $("#itemCode").val();
    let itemDescription = $("#inputDescription").val();
    let packSize = $("#packSize").val();
    let unitPrice = $("#unitPrice").val();
    let qty = $("#inputQTy").val();
    let discount = $("#inputDiscount").val();


    $("#itemTable>tr").empty();
    for (let i = 0; i < itemArray.length; i++) {
        if ( $("#itemCode").val() === itemArray[i].getItemId()) {
            itemArray.splice(i, 1);

            clearItemTextFields();
        }
    }


    var item = new Item(itemCode,itemDescription,packSize,unitPrice,qty,discount);
    itemArray.push(item);

    loadItemDetailsToTbl();
    alert("Item has been successfully updated");

}

function searchItem(id){

    for (let i = 0; i < itemArray.length; i++) {
        if (id === itemArray[i].getItemId()){
            return itemArray[i];
        }
    }
}

function disableItemRegisterBtn(){
    if (validateAllItem()){
        $("#btnItemRegister").attr('disabled', false);
    }else {
        $("#btnItemRegister").attr('disabled', true);
    }
}

function validateItemDescription(){
    let input = $("#inputDescription").val();

    if (regExDescription.test(input)) {
        $("#error01").text("");
        $("#inputDescription").css('border', '2px solid green');

        $("#inputDescription").keydown(function (e) {
            if (e.key == 'Enter') {
                $("#packSize").focus();
            }
        });
        return true;
    } else {
        $("#inputDescription").css('border', '2px solid red');
        $("#error01").text("Wrong format : " + input);
        return false;
    }
}

$("#inputDescription").keyup(function (e) {
disableItemRegisterBtn();
});

function validatePackSize(){
    let input = $("#packSize").val();

    if (regExPackSize.test(input)) {
        $("#error02").text("");
        $("#packSize").css('border', '2px solid green');

        $("#packSize").keydown(function (e) {
            if (e.key == 'Enter') {
                $("#unitPrice").focus();
            }
        });
        return true;
    } else {
        $("#packSize").css('border', '2px solid red');
        $("#error02").text("Wrong format : " + input);
        return false;
    }
}

$("#packSize").keyup(function (e) {
disableItemRegisterBtn();
});

function validateUnitPrice(){
    let input = $("#unitPrice").val();

    if (regExUnitPrice.test(input)) {
        $("#error03").text("");
        $("#unitPrice").css('border', '2px solid green');

        $("#unitPrice").keydown(function (e) {
            if (e.key == 'Enter') {
                $("#inputQTy").focus();
            }
        });
        return true;
    } else {
        $("#unitPrice").css('border', '2px solid red');
        $("#error03").text("Wrong format : " + input);
        return false;
    }
}

$("#unitPrice").keyup(function (e) {
disableItemRegisterBtn();
});


function validateQTY(){
    let input = $("#inputQTy").val();

    if (regExQty.test(input)) {
        $("#error04").text("");
        $("#inputQTy").css('border', '2px solid green');

        $("#inputQTy").keydown(function (e) {
            if (e.key == 'Enter') {
                $("#inputDiscount").focus();
            }
        });
        return true;
    } else {
        $("#inputQTy").css('border', '2px solid red');
        $("#error04").text("Wrong format : " + input);
        return  false;
    }
}

$("#inputQTy").keyup(function (e) {
disableItemRegisterBtn();
});


function isItemIdExists(){
    for (let i = 0; i < itemArray.length; i++) {
        if ($("#itemCode").val() === itemArray[i].getItemId()) {
            return false;
        }
    }
        return true;
}

function validateAllItem(){

    if (isItemIdExists()){
        if (validateItemDescription()){
            if (validatePackSize()){
                if (validateUnitPrice()){
                    if (validateQTY()){
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
    }else {
        return false;
    }


}

$("#btnItemRegister").click(function (){
    saveItem();
    generateItemIds();
});

$("#btnItemDelete").click(function (){
    deleteItem();
});

$("#btnItemUpdate").click(function (){
    updateItem();
    generateItemIds();
});

$("#btnItemSearch").click(function (){

    var obItem = searchItem($("#txtItemSearch").val());

    if (obItem){
        $("#itemCode").val(obItem.getItemId());
        $("#inputDescription").val(obItem.getItemDescription());
        $("#packSize").val(obItem.getPackSize());
        $("#unitPrice").val(obItem.getUnitPrize());
        $("#inputQTy").val(obItem.getQtyOnHand());
        $("#inputDiscount").val(obItem.getDiscount());

    }else {
        clearCustomerTextFields();
        alert("No such a item");
    }

});

$("#btnClearCustomerFields").click(function (){
    clearItemTextFields();
});

/*------Validations--------*/

var regExItemCode = /^(I-)[0-9]{3,5}$/;
var regExDescription = /^([A-z0-9/,\s]{3,})$/;
var regExPackSize = /^([0-9]{1,10})$/;
var regExUnitPrice = /^([0-9.]{1,})$/;
var regExQty = /^([0-9]{1,10})$/;
var regExDiscount = /^([0-9.]{1,})$/;





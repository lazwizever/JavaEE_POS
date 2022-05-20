loadAllItems();

function saveItem(){
    var data = $("#itemForm").serialize();
    console.log(data);
    $.ajax({
        url: "http://localhost:8080/backend/item",
        method: "POST",
        data: data,
        success: function (res) {
            if (res.status == 200) {
                alert(res.message);
                loadAllItems();
            } else {
                alert(res.data);
            }

        },
        error: function (textStatus, error) {
            console.log(textStatus);
            console.log(error);
        }
    });


}

function loadAllItems(){
    $("#itemTable").empty();
    $.ajax({
        url: "http://localhost:8080/backend/item?option=GETALL",
        method: "GET",
        success: function (resp) {
            for (const item of resp.data) {
                let row = `<tr><td>${item.id}</td><td>${item.description}</td><td>${item.packSize}</td><td>${item.unitPrice}</td><td>${item.qtyOnHand}</td></tr>`;
                $("#itemTable").append(row);
                /*bindClickEvents();*/
            }
        },
        error:function (ob,state,error){
            console.log(ob,state,error)
        }
    });

}



function generateItemIds(){
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
    let itemId = $("#itemCode").val();
    console.log(cusId);
    $.ajax({
        url: "http://localhost:8080/backend/customer?CusID=" + itemId,
        method: "DELETE",

        success: function (res) {
            console.log(res);
            if (res.status == 200) {
                alert("Customer Successfully Deleted.");
                loadAllCustomers();
            } else if (res.status == 400) {
                alert("Customer Successfully Deleted.");
                loadAllCustomers();
            } else {
                alert("Customer Successfully Deleted.");
                loadAllCustomers();
            }

        },
        error: function (ob, status, t) {
            console.log(ob);
            console.log(status);
            console.log(t);
        }
    });
}

function updateItem(){


}

function searchItem(id){

    for (let i = 0; i < itemArray.length; i++) {
        if (id === itemArray[i].getItemId()){
            return itemArray[i];
        }
    }
}

function disableItemRegisterBtn(){
   /* if (validateAllItem()){
        $("#btnItemRegister").attr('disabled', false);
    }else {
        $("#btnItemRegister").attr('disabled', true);
    }*/
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
  /*  for (let i = 0; i < itemArray.length; i++) {
        if ($("#itemCode").val() === itemArray[i].getItemId()) {
            return false;
        }
    }
        return true;*/
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
   /* saveItem();
    generateItemIds();*/
});

$("#btnItemDelete").click(function (){
/*    deleteItem();*/
});

$("#btnItemUpdate").click(function (){
  /*  updateItem();
    generateItemIds();*/
});

$("#btnItemSearch").click(function (){


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





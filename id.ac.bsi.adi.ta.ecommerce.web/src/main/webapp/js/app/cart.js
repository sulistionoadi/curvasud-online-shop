function addCart(idProduct){
    
    var qty = $('#qty_cart').val();
    
    $.ajax({
        type: 'POST',
        url: 'user/cart/add',
        data: {
            q: qty,
            id: idProduct
        },
        success: function(data){
            console.log(data);
            window.location.href = "user/cart/list";
        },
        error: function(errorResp){
            $.messager.show({
                title: 'Add Cart Error',
                msg: errorResp.responseText
            });
        }
    });
    
}
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

function checkout(ongkos){
    console.log("nilai ongkos " + ongkos);
    $.ajax({
        type: 'POST',
        url: 'checkout',
        data: {
            s_name: $('#kirim_nama').val(),
            s_address: $('#kirim_alamat').val(),
            s_phone: $('#kirim_telp').val(),
            s_cost: ongkos
        },
        success: function(data){
            console.log(data);
            window.location.href = data.redirect;
        },
        error: function(errorResp){
            $.messager.show({
                title: 'Checkout Error',
                msg: errorResp.responseText
            });
        }
    });
}
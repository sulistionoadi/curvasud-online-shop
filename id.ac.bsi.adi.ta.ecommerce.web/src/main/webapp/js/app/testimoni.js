function postComments(){
    var data = $('#formTestimoni').serializeJSON();
    data.member = {memberCode:data.idmember};
    data.product = {productCode:data.idproduct};
    
    delete data.idmember;
    delete data.idproduct;
    
    $.ajax({
        type: "POST",
        url: "comment",
        data: JSON.stringify(data),
        contentType: 'application/json',
        error: function(errorResp){
            $.messager.show({
                title: 'Post Comment Error',
                msg: errorResp.responseText
            });
            if(errorResp.status=="200" && errorResp.responseText=="Sukses"){
                window.location.href = "detail-content?id=" + data.product.productCode;
            }
        },
        dataType: 'json'
    });
}
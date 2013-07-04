var urlUser = "json";
function changePassword(){
    console.log("test");
    if(!$('#formChangePasswordBox').validatebox('isValid')) {
       return; 
    }
    
    var data = $('#formChangePassword').serializeJSON();

    $.ajax({
        type: "PUT",
        url: urlUser,
        data: JSON.stringify(data),
        contentType: 'application/json',
        success: function(data){
            console.log(data);
        },
        error: function(errorResp){
            console.log("error");
            try{
                var objError = mergeFieldError(eval(errorResp.responseText));
                console.log("result error merging ", objError);
                $.each(objError, function(i,val){
                    var idcomp = "#" + val.objName + "_" + val.id;
                    $(idcomp).val('');
                    updateErrorMessageTooltip(val);
                });
            } catch(e){
                $.messager.show({
                    title: 'Save Error',
                    msg: errorResp.responseText
                });
            }
        },
        dataType: 'json'
    });
}
function refreshTotalContent(){
    $("#content_pagination").pagination('options').total = $('#txtTotalContent').val();
    $("#content_pagination").pagination('refresh');
}

function login(){
    if($('#form_login').form('validate')){
        $('#form_login').submit();
    }
}

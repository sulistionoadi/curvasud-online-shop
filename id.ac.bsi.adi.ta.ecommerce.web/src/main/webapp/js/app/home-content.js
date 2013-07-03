function fillHomeContent(){
    $.get('awal', function(data) {
        console.log("home loaded");
    });
}

function refreshTotalContent(){
    $("#content_pagination").pagination('options').total = $('#txtTotalContent').val();
    $("#content_pagination").pagination('refresh');
}

function login(){
    if($('#form_login').form('validate')){
        $('#form_login').submit();
    }
}

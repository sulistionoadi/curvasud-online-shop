var memberCity = null;

function submitRegisterMember(){
    var jsonData = $("#formRegisterasiMember").serializeJSON();
    $.ajax({
        type: "POST",
        url: "member",
        data: JSON.stringify(jsonData),
        contentType: "application/json",
        success: function(data){
            console.log("Redirect to ", data);
            window.location.href = data;
        },
        error: function(errorResp){
            $.messager.show({
                title: 'Error',
                msg: errorResp.statusText
            });
        }
    });
}

function clearRegisterMember(){
    $("#formRegisterasiMember").form('reset');
}

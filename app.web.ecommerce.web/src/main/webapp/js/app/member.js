var memberCity = null;

function submitRegisterMember(){
    var jsonData = $("#formRegisterasiMember").serializeJSON();
    var idCity = $('#member_city').combobox('getValue');
    jsonData.city = {
        code: idCity
    };
    
    $.ajax({
        type: "POST",
        url: "member",
        data: JSON.stringify(jsonData),
//        dataType: "json",
        contentType: "application/json"
    });
}

function clearRegisterMember(){
    $("#formRegisterasiMember").form('reset');
}

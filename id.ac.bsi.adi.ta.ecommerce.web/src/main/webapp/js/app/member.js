var memberCity = null;

function submitRegisterMember(){
    var jsonData = $("#formRegisterasiMember").serializeJSON();
    var idCity = $('#member_city').combobox('getValue');
    jsonData.city = {
        id: idCity
    };
    
    $.ajax({
        type: "POST",
        url: "member",
        data: JSON.stringify(jsonData),
        dataType: "json",
        contentType: "application/json",
        success: function(data){
            console.log("register sukses ", data);
            window.location.href = "registrasi/sukses";
        }
    });
}

function clearRegisterMember(){
    $("#formRegisterasiMember").form('reset');
}

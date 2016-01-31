var memberCity = null;

function submitRegisterMember(){
    var jsonData = $("#formRegisterasiMember").serializeJSON();
    $.ajax({
        type: "POST",
        url: "member",
        data: JSON.stringify(jsonData),
        dataType: "json",
        contentType: "application/json"
    });
}

function clearRegisterMember(){
    $("#formRegisterasiMember").form('reset');
}

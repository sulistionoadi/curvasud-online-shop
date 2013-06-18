function submitRegisterMember(){
    var jsonData = $("#formRegisterasiMember").serializeJSON();
    $.ajax({
        type: "POST",
        url: "",
        data: JSON.stringify(jsonData),
        dataType: "json",
        contentType: "application/json",
        success: function(){
           
        }
    });
}
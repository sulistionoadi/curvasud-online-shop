$.extend($.fn.validatebox.defaults.rules, {  
    minLength: {  
        validator: function(value, param){  
            return value.length >= param[0];  
        },  
        message: 'Minimal {0} characters.'  
    }, 
    passwordEquals: {  
        validator: function(value,param){  
            return value == $(param[0]).val();  
        },  
        message: 'Password do not match.'  
    }
});

$.fn.serializeJSON=function() {  
    var json = {};  
    $.map($(this).serializeArray(), function(n, i){  
        json[n['name']] = n['value'];  
    });  
    return json;  
};

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

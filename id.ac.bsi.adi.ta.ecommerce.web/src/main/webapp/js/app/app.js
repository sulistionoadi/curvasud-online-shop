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

function paginationAction(pn,ps,url){
    $.ajax({
        type:"GET",
        url: url,
        data: {
            'page.page':pn,
            'page.size':ps
        },
        dataType:'json',
        beforeSend: function(jqXHR, settings){
            $('#gridCategory').datagrid('loading');
        },success: function(data){
            $('#gridCategory').datagrid('loadData',data);
        },complete: function(jqXHR, textStatus){
            $('#gridCategory').datagrid('loaded');
        }
    });
}

function updateErrorMessageTooltip(err){
    var idcomp = "#" + err.objName + "_" + err.id;
    var target = $(idcomp)[0];
    $.data(target, 'validatebox').message = err.message;
    
    $(idcomp).validatebox({
        name: err.field,
        required: true,
        invalidMessage: err.message,
        missingMessage: err.message
    });
}

function mergeFieldError(errorResponse){
    
    var oea = [];
    
    for(var i=0; i<errorResponse.length; i++){
        var value = errorResponse[i];
        var dt = oea.filter(function (val) {
            return val.id === value.field;
        });
        if(dt.length < 1){
            dt = {
                id: value.field,
                objName: value.objectName,
                message: value.defaultMessage
            };
            oea.push(dt);
        } else {
            dt[0].message = dt[0].message + "<br>" + value.defaultMessage
        }
    }
    
    return oea;
}

function formatNumber(value){
    if(!value){
        return value;
    }
    value=value+"";
    var s1=value,s2="";
    var _22=value.indexOf(".");
    if(_22>=0){
        s1=value.substring(0,_22);
        s2=value.substring(_22+1,value.length);
    }
    
    var p=/(\d+)(\d{3})/;

    while(p.test(s1)){
        s1=s1.replace(p,"$1,$2");
    }
    
    if(s2){
        return s1+"."+s2;
    } else {
        return s1;
    }
}
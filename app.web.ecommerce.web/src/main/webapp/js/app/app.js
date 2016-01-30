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

function paginationAction(pn,ps,url,id){
    $.ajax({
        type:"GET",
        url: url,
        data: {
            'page.page':pn,
            'page.size':ps
        },
        dataType:'json',
        beforeSend: function(jqXHR, settings){
            $(id).datagrid('loading');
        },success: function(data){
            $(id).datagrid('loadData',data);
        },complete: function(jqXHR, textStatus){
            $(id).datagrid('loaded');
        }
    });
}

function paginationActionByDate(pn,ps,sd,ed,url,id){
    $.ajax({
        type:"GET",
        url: url,
        data: {
            'page.page':pn,
            'page.size':ps,
            'startDate':sd,
            'endDate':ed
        },
        dataType:'json',
        beforeSend: function(jqXHR, settings){
            $(id).datagrid('loading');
        },success: function(data){
            $(id).datagrid('loadData',data);
        },complete: function(jqXHR, textStatus){
            $(id).datagrid('loaded');
        }
    });
}

function paginationDataStock(pn,ps,tgl,url,id){
    $.ajax({
        type:"GET",
        url: url,
        data: {
            'page.page':pn,
            'page.size':ps,
            'tanggal':tgl,
        },
        dataType:'json',
        beforeSend: function(jqXHR, settings){
            $(id).datagrid('loading');
        },success: function(data){
            $(id).datagrid('loadData',data);
        },complete: function(jqXHR, textStatus){
            $(id).datagrid('loaded');
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

function formatDecimal(value, precision){
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
        return s1+"."+padTrailing(precision, s2, "0");
    } else {
        return s1+"."+padTrailing(precision, "", "0");
    }
}

$.fn.datebox.defaults.formatter = function(date){
    var y = date.getFullYear();
    var m = date.getMonth()+1;
    var d = date.getDate();
    return d+'-'+m+'-'+y;
}

$.fn.datebox.defaults.parser = function(s){
    var t = Date.parse(s);
    if (!isNaN(t)){
        return new Date(t);
    } else {
        return new Date();
    }
}

function padLeading(width, string, padding) { 
  return (width <= string.length) ? string : padLeading(width, padding + string, padding);
}

function padTrailing(width, string, padding) { 
  return (width <= string.length) ? string : padTrailing(width, string + padding, padding);
}
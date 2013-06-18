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

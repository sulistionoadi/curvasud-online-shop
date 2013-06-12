/* 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
function findIndexByKeyValue(obj, key, value) {
    for (var i = 0; i < obj.length; i++) {
        if (obj[i][key] == value) {
            return i;
        }
    }
    return null;
}

function findDataByKeyValue(obj, key, value){
    var result = [];
    for(var i=0; i<obj.length; i++){
        if(obj[i][key] == value){
            result.push(obj[i]);
        }
    }
    return result;
}

$.extend($.fn.datebox.defaults,{
    options:{
        formatter: function(value){
            var result = "";
            if(value){
                result = $.datepicker.formatDate("dd/mm/yy", value);
            }
            return result;
        }, parser: function(s){
            var result = new Date();
            if(s){
                result = $.datepicker.parseDate("dd/mm/yy", s);
            }
            return result;
        }
    }
});

$.extend($.fn.validatebox.defaults.rules, {
    minLength: {  
        validator: function(value, param){  
            return value.length >= param[0];  
        },  
        message: 'Input harus kurang dari atau sama dengan {0} karakter'  
    },
    maxLength: {  
        validator: function(value, param){  
            return value.length <= param[0];  
        },  
        message: 'Input harus lebih dari atau sama dengan {0} karakter'  
    },
    numberOnly: {
        validator: function(value, param){  
            var pattern=new RegExp("^[0-9]{" + param[0] + "," + param[1] + "}$");
            return pattern.test(value);
        },  
        message: "Input harus angka, dan panjang karakter antara {0} dan {1}"
    }
});

$.extend($.fn.datagrid.defaults.editors, {
    lookup: {
        init: function(container, options){
            var input = $('<input type="text" class="datagrid-editable-input" id="' + options.id + '">').appendTo(container);
            input.searchbox(options);
            return input;
        },
        getValue: function(target){
            var result = $(target).searchbox("getValue");
            return result;
        },
        setValue: function(target, value){
            $(target).searchbox("setValue",value);
        },resize: function(target, width){
            var input = $(target);
            var opts = $.data($(input)[0], 'searchbox').options;
            var sb = $.data($(input)[0], 'searchbox').searchbox;
            if ($.boxModel == true){
                $(sb).width(((opts.width - 2) - (opts.outerWidth - opts.width)) + sb.find('span.searchbox-button').width());
                sb.find('input.searchbox-text').width((opts.width - 2) - (opts.outerWidth - opts.width));
            } else {
                $(sb).width((opts.width - 2) + sb.find('span.searchbox-button').width());
                sb.find('input.searchbox-text').width(opts.width - 2);
            }
        }
    }
});

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

function showTipSearchbox(target){
    var box = target.next("span");
    var msg = $.data(target[0], 'searchbox').options.message;
    var tip = $.data(target[0], 'searchbox').tip;
    if (!tip){
        tip = $(
            '<div class="searchbox-tip">' +
                '<span class="searchbox-tip-content">' +
                '</span>' +
                '<span class="searchbox-tip-pointer">' +
                '</span>' +
            '</div>'
        ).appendTo(box);
        $.data(target[0], 'searchbox').tip = tip;
    }
    tip.find('.searchbox-tip-content').html(msg);
    tip.css({
        display:'inline-block',
        color: 'black'
    });
}

function hideTipSearchbox(target){
    var tip = $.data(target[0], 'searchbox').tip;
    if (tip){
        tip.remove();
        $.data(target[0], 'searchbox').tip = null;
    }
}
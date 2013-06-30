var urlShipping = "json";
var methodShipping = "POST";
var citySelected = null;
function createDatagridShipping(){
    $('#shippingRate_city').combobox({
        required: true,
        invalidMessage: 'value is invalid',
        missingMessage: 'this is required'
    });
    
    $('#shippingRate_express').validatebox({
        required: true,
        invalidMessage: 'value is invalid',
        missingMessage: 'this is required'
    });
    
    $('#shippingRate_reguler').validatebox({
        required: true,
        invalidMessage: 'value is invalid',
        missingMessage: 'this is required'
    });
    
    $('#gridShipping').datagrid({
        style:'width:400px; height:700px',
        method:'get',
        url:'json',
        idField:'id',
        fitColumns:'true',
        nowrap: false,
        pagination: true,
        rownumbers: true,
        singleSelect: true,
        pageSize: 10,
        queryParams: {
            'page.page':1,
            'page.size':10
        },
        columns:[[
        {
            field:'city', 
            title: 'City', 
            width:110,
            formatter: function(value,row,index){
                if (row.city){
                    return row.city.name;
                } else {
                    return value;
                }
            }
        },
        {
            field:'express', 
            title:'Express', 
            width:100,
            align:'right',
            formatter: function(value,row,index){
                return formatNumber(value);
            }
        },
        {
            field:'reguler', 
            title:'Reguler', 
            width:100,
            align:'right',
            formatter: function(value,row,index){
                return formatNumber(value);
            }
        }
        ]],
        onLoadSuccess:function(row,data){
            var pager = $('#gridShipping').datagrid('getPager');
            pager.pagination({
                onSelectPage: function(pageNumber, pageSize){
                    paginationAction(pageNumber, pageSize, 'json');
                }
            });
        },
        onLoadError:function(error){
            console.log("error loading grid data " + error);
        }
    });
}

function getObjectCity(){
    var idr = $('#shippingRate_city').combobox('getValue');
    console.log("call method getObjectCity : ", idr);
    $.ajax({
        type: 'GET',
        url: '../city/json/'+ idr,
        contentType: 'application/json',
        success: function(data){
            citySelected = data;
        },
        error: function(errorResp){
            try{
                var objError = mergeFieldError(eval(errorResp.responseText));
                console.log("result error merging ", objError);
                $.each(objError, function(i,val){
                    updateErrorMessageTooltip(val);
                });
            } catch(e){
                $.messager.show({
                    title: 'Get Shipping Error',
                    msg: errorResp.responseText
                });
            }
        },
        dataType: 'json'
    });
}

function newShipping(){
    $('#dlgFormShipping').dialog('open').dialog('setTitle','New Shipping');  
    $('#formMasterShipping').form('clear');  
    urlShipping = "json";
    methodShipping = "POST";
}

function saveShipping(){
    var data = $('#formMasterShipping').serializeJSON();
    data.city = citySelected;

    $.ajax({
        type: methodShipping,
        url: urlShipping,
        data: JSON.stringify(data),
        contentType: 'application/json',
        success: function(data){
            $('#dlgFormShipping').dialog('close');
            createDatagridShipping();
        },
        error: function(errorResp){
            try{
                var objError = mergeFieldError(eval(errorResp.responseText));
                console.log("result error merging ", objError);
                $.each(objError, function(i,val){
                    var idcomp = "#" + val.objName + "_" + val.id;
                    $(idcomp).val('');
                    updateErrorMessageTooltip(val);
                });
            } catch(e){
                $.messager.show({
                    title: 'Save Error',
                    msg: errorResp.responseText
                });
            }
        },
        dataType: 'json'
    });
}

function editShipping(){
    var row = $('#gridShipping').datagrid('getSelected');
    if (row){
        $('#dlgFormShipping').dialog('open').dialog('setTitle','Edit Shipping');
        $('#formMasterShipping').form('load',row);
        $('#shippingRate_city').combobox('setValue', row.city.id)
        citySelected = row.city;
        urlShipping = 'json/' + row.id;
        methodShipping = 'PUT';
    }
}

function removeShipping(){
    var row = $('#gridShipping').datagrid('getSelected');
    if (row){
        $.messager.confirm('Confirm','Are you sure you want to delete this shipping?',function(r){
            if (r){
                $.ajax({
                    type: 'DELETE',
                    url: 'json/' + row.id,
                    success: function(data){
                        createDatagridShipping();
                    },
                    error: function(errorResp){
                        $.messager.show({
                            title: 'Delete Error',
                            msg: errorResp.responseText
                        });
                    }
                });
            }
        });
    }
}
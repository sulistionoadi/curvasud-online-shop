var urlCity = "json";
var methodCity = "POST";
function createDatagridCity(){
    $('#city_code').validatebox({
        required: true,
        invalidMessage: 'value is invalid',
        missingMessage: 'this is required'
    });
    
    $('#city_name').validatebox({
        required: true,
        invalidMessage: 'value is invalid',
        missingMessage: 'this is required'
    });
    
    $('#gridCity').datagrid({
        style:'width:400px; height:400px',
        method:'get',
        url:'json',
        idField:'code',
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
            field:'code', 
            title:'Code', 
            width:110
        },

        {
            field:'name', 
            title: 'Name', 
            width:110
        } 
        ]],
        onLoadSuccess:function(row,data){
            var pager = $('#gridCity').datagrid('getPager');
            pager.pagination({
                onSelectPage: function(pageNumber, pageSize){
                    paginationAction(pageNumber, pageSize, 'json','#gridCity');
                }
            });
        },
        onLoadError:function(error){
            console.log("error loading grid data " + error);
        }
    });
}

function newCity(){
    $('#dlgFormCity').dialog('open').dialog('setTitle','New City');  
    $('#formMasterCity').form('clear');  
    urlCity = "json";
    methodCity = "POST";
}

function saveCity(){
    var data = $('#formMasterCity').serializeJSON();

    $.ajax({
        type: methodCity,
        url: urlCity,
        data: JSON.stringify(data),
        contentType: 'application/json',
        success: function(data){
            $('#dlgFormCity').dialog('close');
            createDatagridCity();
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
                    title: 'Save Error',
                    msg: errorResp.responseText
                });
            }
        },
        dataType: 'json'
    });
}

function editCity(){
    var row = $('#gridCity').datagrid('getSelected');
    if (row){
        $('#dlgFormCity').dialog('open').dialog('setTitle','Edit City');
        $('#formMasterCity').form('load',row);
        urlCity = 'json/' + row.code;
        methodCity = 'PUT';
    }
}

function removeCity(){
    var row = $('#gridCity').datagrid('getSelected');
    if (row){
        $.messager.confirm('Confirm','Are you sure you want to delete this city?',function(r){
            if (r){
                $.ajax({
                    type: 'DELETE',
                    url: 'json/' + row.code,
                    success: function(data){
                        createDatagridCity();
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
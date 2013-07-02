var urlSupplier = "json";
var methodSupplier = "POST";
function createDatagridSupplier(){
    $('#supplier_code').validatebox({
        required: true,
        invalidMessage: 'value is invalid',
        missingMessage: 'this is required'
    });
    
    $('#supplier_name').validatebox({
        required: true,
        invalidMessage: 'value is invalid',
        missingMessage: 'this is required'
    });
    
    $('#gridSupplier').datagrid({
        style:'width:700px; height:400px',
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
            field:'code', 
            title:'Code', 
            width:110
        },
        {
            field:'name', 
            title: 'Name', 
            width:110
        },
        {
            field:'address', 
            title: 'Address', 
            width:110
        },
        {
            field:'email', 
            title: 'E-Mail', 
            width:110
        },
        {
            field:'phone', 
            title: 'Phone', 
            width:110
        }
        ]],
        onLoadSuccess:function(row,data){
            var pager = $('#gridSupplier').datagrid('getPager');
            pager.pagination({
                onSelectPage: function(pageNumber, pageSize){
                    paginationAction(pageNumber, pageSize, 'json','#gridSupplier');
                }
            });
        },
        onLoadError:function(error){
            console.log("error loading grid data " + error);
        }
    });
}

function newSupplier(){
    $('#dlgFormSupplier').dialog('open').dialog('setTitle','New Supplier');  
    $('#formMasterSupplier').form('clear');  
    urlSupplier = "json";
    methodSupplier = "POST";
}

function saveSupplier(){
    var data = $('#formMasterSupplier').serializeJSON();

    $.ajax({
        type: methodSupplier,
        url: urlSupplier,
        data: JSON.stringify(data),
        contentType: 'application/json',
        success: function(data){
            $('#dlgFormSupplier').dialog('close');
            createDatagridSupplier();
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

function editSupplier(){
    var row = $('#gridSupplier').datagrid('getSelected');
    if (row){
        $('#dlgFormSupplier').dialog('open').dialog('setTitle','Edit Supplier');
        $('#formMasterSupplier').form('load',row);
        urlSupplier = 'json/' + row.id;
        methodSupplier = 'PUT';
    }
}

function removeSupplier(){
    var row = $('#gridSupplier').datagrid('getSelected');
    if (row){
        $.messager.confirm('Confirm','Are you sure you want to delete this supplier?',function(r){
            if (r){
                $.ajax({
                    type: 'DELETE',
                    url: 'json/' + row.id,
                    success: function(data){
                        createDatagridSupplier();
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
var urlRole = "json";
var methodRole = "POST";
function createDatagridRole(){
    $('#role_name').validatebox({
        required: true,
        invalidMessage: 'value is invalid',
        missingMessage: 'this is required'
    });
    
    $('#role_description').validatebox({
        required: true,
        invalidMessage: 'value is invalid',
        missingMessage: 'this is required'
    });
    
    $('#gridRole').datagrid({
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
            field:'name', 
            title:'Role Name', 
            width:70
        },

        {
            field:'description', 
            title: 'Description', 
            width:110
        } 
        ]],
        onLoadSuccess:function(row,data){
            var pager = $('#gridRole').datagrid('getPager');
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

function newRole(){
    $('#dlgFormRole').dialog('open').dialog('setTitle','New Role');  
    $('#formMasterRole').form('clear');  
    urlRole = "json";
    methodRole = "POST";
}

function saveRole(){
    var data = $('#formMasterRole').serializeJSON();
    var listPerm = [];
    
    var rows = $('#gridPermission').datagrid('getSelections');  
    for(var i=0; i<rows.length; i++){  
        listPerm.push(rows[i]);  
    }
    
    data.permissionSet = listPerm;
    delete data.ck;

    $.ajax({
        type: methodRole,
        url: urlRole,
        data: JSON.stringify(data),
        contentType: 'application/json',
        success: function(data){
            $('#dlgFormRole').dialog('close');
            createDatagridRole();
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

function editRole(){
    $('#gridPermission').datagrid('clearChecked');
    var row = $('#gridRole').datagrid('getSelected');
    console.log('row --> ', row);
    if (row){
        $('#dlgFormRole').dialog('open').dialog('setTitle','Edit Role');
        $('#formMasterRole').form('load',row);
        var dgrow = $('#gridPermission').datagrid('getRows');
        console.log('dgrow --> ', dgrow);
        for(var i=0; i<row.permissionSet.length; i++){
            console.log('row.id --> ', row.permissionSet[i].id);
            for(var j=0; j<dgrow.length; j++){
                console.log('dgrow.id --> ', dgrow[j].id);
                if(row.permissionSet[i].id==dgrow[j].id){
                    $('#gridPermission').datagrid('checkRow',j);
                    break;
                }
            }
        }
        
        for(var i=0; i<row.menuSet.length; i++){
            
        }
        
        urlRole = 'json/' + row.id;
        methodRole = 'PUT';
    }
}

function removeRole(){
    var row = $('#gridRole').datagrid('getSelected');
    if (row){
        $.messager.confirm('Confirm','Are you sure you want to delete this role?',function(r){
            if (r){
                $.ajax({
                    type: 'DELETE',
                    url: 'json/' + row.id,
                    success: function(data){
                        createDatagridRole();
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
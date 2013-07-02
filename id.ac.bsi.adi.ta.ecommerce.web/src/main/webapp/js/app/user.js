var urlUser = "json";
var methodUser = "POST";
var roleSelected = null;
function createDatagridUser(){
    $('#user_username').validatebox({
        required: true,
        invalidMessage: 'value is invalid',
        missingMessage: 'this is required'
    });
    
    $('#user_password').validatebox({
        required: true,
        invalidMessage: 'value is invalid',
        missingMessage: 'this is required'
    });
    
    $('#user_role').combobox({
        required: true,
        invalidMessage: 'value is invalid',
        missingMessage: 'this is required'
    });
    
    $('#gridUser').datagrid({
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
            field:'username', 
            title:'Username', 
            width:70
        },
        {
            field:'role', 
            title: 'Role', 
            width:110,
            formatter: function(value,row,index){
                if (row.role){
                    return row.role.name;
                } else {
                    return value;
                }
            }
        },
        {
            field:'member', 
            title: 'Member Name', 
            width:110,
            formatter: function(value,row,index){
                if (row.member){
                    return row.member.firstname + " " + row.member.lastname;
                } else {
                    return value;
                }
            }
        },
        {
            field:'active', 
            title: 'Active', 
            width:50,
            formatter: function(value,row,index){
                if (value){
                    return "yes";
                } else {
                    return "no";
                }
            }
        } 
        ]],
        onLoadSuccess:function(row,data){
            var pager = $('#gridUser').datagrid('getPager');
            pager.pagination({
                onSelectPage: function(pageNumber, pageSize){
                    paginationAction(pageNumber, pageSize, 'json','#gridUser');
                }
            });
        },
        onLoadError:function(error){
            console.log("error loading grid data " + error);
        }
    });
}

function getObjectRole(){
    var idr = $('#user_role').combobox('getValue');
    console.log("call method getObjectRole : ", idr);
    $.ajax({
        type: 'GET',
        url: '../role/json/'+ idr,
        contentType: 'application/json',
        success: function(data){
            roleSelected = data;
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
                    title: 'Get Role Error',
                    msg: errorResp.responseText
                });
            }
        },
        dataType: 'json'
    });
}

function newUser(){
    $('#dlgFormUser').dialog('open').dialog('setTitle','New User');  
    $('#formMasterUser').form('clear');  
    urlUser = "json";
    methodUser = "POST";
}

function saveUser(){
    if(!$('#user_confirm').validatebox('isValid') || roleSelected==null) {
       return; 
    }
    
    var data = $('#formMasterUser').serializeJSON();
    data.role = roleSelected;
    data.active = $('#user_active').is(':checked');
    data.role.permissionSet = null;
    data.role.menuSet = null;

    $.ajax({
        type: methodUser,
        url: urlUser,
        data: JSON.stringify(data),
        contentType: 'application/json',
        success: function(data){
            $('#dlgFormUser').dialog('close');
            createDatagridUser();
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

function editUser(){
    var row = $('#gridUser').datagrid('getSelected');
    console.log('row --> ', row);
    if (row){
        $('#dlgFormUser').dialog('open').dialog('setTitle','Edit User');
        $('#formMasterUser').form('load',row);
        $('#user_role').combobox('setValue', row.role.id)
        $('#user_active').prop("checked", row.active);
        roleSelected = row.role;
        urlUser = 'json/' + row.id;
        methodUser = 'PUT';
    }
}

function removeUser(){
    var row = $('#gridUser').datagrid('getSelected');
    if (row){
        $.messager.confirm('Confirm','Are you sure you want to delete this role?',function(r){
            if (r){
                $.ajax({
                    type: 'DELETE',
                    url: 'json/' + row.id,
                    success: function(data){
                        createDatagridUser();
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
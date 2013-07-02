var urlCategory = "json";
var methodCategory = "POST";
function createDatagridCategory(){
    $('#categoryProduct_categoryCode').validatebox({
        name: 'categoryCode',
        required: true,
        invalidMessage: 'value is invalid',
        missingMessage: 'this is required'
    });
    
    $('#categoryProduct_description').validatebox({
        name: 'description',
        required: true,
        invalidMessage: 'value is invalid',
        missingMessage: 'this is required'
    });
    
    $('#gridCategory').datagrid({
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
            field:'categoryCode', 
            title:'Code', 
            width:110
        },

        {
            field:'description', 
            title: 'Description', 
            width:110
        } 
        ]],
        onLoadSuccess:function(row,data){
            var pager = $('#gridCategory').datagrid('getPager');
            pager.pagination({
                onSelectPage: function(pageNumber, pageSize){
                    paginationAction(pageNumber, pageSize, 'json','#gridCategory');
                }
            });
        },
        onLoadError:function(error){
            console.log("error loading grid data " + error);
        }
    });
}

function newCategory(){
    $('#dlgFormCategory').dialog('open').dialog('setTitle','New Category');  
    $('#formMasterCategory').form('clear');  
    urlCategory = "json";
    methodCategory = "POST";
}

function saveCategory(){
    var data = $('#formMasterCategory').serializeJSON();

    $.ajax({
        type: methodCategory,
        url: urlCategory,
        data: JSON.stringify(data),
        contentType: 'application/json',
        success: function(data){
            $('#dlgFormCategory').dialog('close');
            createDatagridCategory();
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

function editCategory(){
    var row = $('#gridCategory').datagrid('getSelected');
    if (row){
        $('#dlgFormCategory').dialog('open').dialog('setTitle','Edit Category');
        $('#formMasterCategory').form('load',row);
        urlCategory = 'json/' + row.id;
        methodCategory = 'PUT';
    }
}

function removeCategory(){
    var row = $('#gridCategory').datagrid('getSelected');
    if (row){
        $.messager.confirm('Confirm','Are you sure you want to delete this category?',function(r){
            if (r){
                $.ajax({
                    type: 'DELETE',
                    url: 'json/' + row.id,
                    success: function(data){
                        createDatagridCategory();
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
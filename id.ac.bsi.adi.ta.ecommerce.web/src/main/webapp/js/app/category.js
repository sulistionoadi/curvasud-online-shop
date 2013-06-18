var url = "";
function createDatagridCategory(){
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
            title:'Kode', 
            width:110
        },

        {
            field:'description', 
            title: 'Deskripsi', 
            width:110
        } 
        ]],
        onLoadSuccess:function(row,data){
            var pager = $('#gridCategory').datagrid('getPager');
            pager.pagination({
                onSelectPage: function(pageNumber, pageSize){
                    paginationAction(pageNumber, pageSize, 'json');
                }
            });
        },
        onLoadError:function(error){
            console.log("error loading treegrid data " + error);
        }
    });
}

function newCategory(){
    $('#dlgFormCategory').dialog('open').dialog('setTitle','New Category');  
    $('#formMasterCategory').form('clear');  
    url = "json";
}

function saveCategory(){
    var data = $('#formMasterCategory').serializeJSON();

    $.ajax({
        type: "POST",
        url: 'json',
        data: JSON.stringify(data),
        contentType: 'application/json',
        success: function(data){
            $('#dlgFormCategory').dialog('close');
            createDatagridCategory();
        },
        error: function(errorResp){
            var objError = eval(errorResp.responseText);
            console.log('error -> ', objError);
        },
        dataType: 'json'
    });
    
    
//    $('#formMasterCategory').form('submit',{  
//        url: url,
//        dataType: 'json',
//        onSubmit: function(){  
//            return $(this).form('validate');  
//        },  
//        success: function(result){  
//            var result = eval('('+result+')');  
//            console.log("Result --> ", result);
//            if (result.errorMsg){  
//                $.messager.show({  
//                    title: 'Error',  
//                    msg: result.errorMsg  
//                });  
//            } else {  
//                $('#dlgFormCategory').dialog('close');
//                createDatagridCategory();
//            }  
//        }  
//    });  
}
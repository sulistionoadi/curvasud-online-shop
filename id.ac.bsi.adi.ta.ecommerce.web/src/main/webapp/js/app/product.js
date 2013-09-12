var urlProduct = "json";
var methodProduct = "POST";
var category = null;
function createDatagridProduct(){
    $('#product_category').validatebox({
        required: true,
        invalidMessage: 'value is invalid',
        missingMessage: 'this is required'
    });
    $('#product_productCode').validatebox({
        required: true,
        invalidMessage: 'value is invalid',
        missingMessage: 'this is required'
    });
    $('#product_productName').validatebox({
        required: true,
        invalidMessage: 'value is invalid',
        missingMessage: 'this is required'
    });
    $('#product_price').validatebox({
        required: true,
        invalidMessage: 'value is invalid',
        missingMessage: 'this is required'
    });
    $('#product_weight').validatebox({
        required: true,
        invalidMessage: 'value is invalid',
        missingMessage: 'this is required'
    });

    $('#gridProduct').datagrid({
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
        view: detailview,
        detailFormatter:function(index,row){
            return '<div id="ddv-' + index + '" style="padding:5px 0"></div>';
        },
        onExpandRow: function(index,row){
            $('#ddv-'+index).panel({
                height:80,
                border:false,
                cache:false,
                href:'info?id='+row.productCode,
                onLoad:function(){
                    $('#gridProduct').datagrid('fixDetailRowHeight',index);
                }
            });
            $('#gridProduct').datagrid('fixDetailRowHeight',index);
        },
        columns:[[
                {
                    field:'category', 
                    title:'Category', 
                    width:110,
                    formatter: function(value,row,index){
                        if (row.category){
                            return row.category.description;
                        } else {
                            return value;
                        }
                    }
                },
                {
                    field:'productCode', 
                    title: 'Code', 
                    width:110
                }, 
                {
                    field:'productName', 
                    title: 'Name', 
                    width:110
                },
                {
                    field:'price', 
                    title: 'Price', 
                    width:110,
                    align: 'right',
                    formatter: function(value,row,index){
                        return formatNumber(value);
                    }
                },
                {
                    field:'weight', 
                    title: 'Weight (kg)', 
                    width:110,
                    align: 'right',
                    formatter: function(value,row,index){
                        return formatDecimal(value,2);
                    }
                }
            ]],
        onLoadSuccess:function(row,data){
            var pager = $('#gridProduct').datagrid('getPager');
            pager.pagination({
                onSelectPage: function(pageNumber, pageSize){
                    paginationAction(pageNumber, pageSize, 'json','#gridProduct');
                }
            });
        },
        onLoadError:function(error){
            console.log("error loading grid data " + error);
        }
    });
    
    $("#product_category").keypress(function(event) {
        if ( event.which == 13 ) {
            event.preventDefault();
            findCategory();
        }
    });
}

function newProduct(){
    $('#dlgFormProduct').dialog('open').dialog('setTitle','New Product');  
    $('#formMasterProduct').form('clear');
    
    $.ajax({
        type: 'GET',
        url: 'code',
        contentType: 'application/json',
        success: function(data){
            $("#product_productCode").val(data.code);
        },
        error: function(errorResp){
            $.messager.show({
                title: 'Get ProductCode Error',
                msg: errorResp.responseText
            });
        },
        dataType: 'json'
    });
    
    urlProduct = "json";
    methodProduct = "POST";
    category = null;
}

function saveProduct(){
    var data = $('#formMasterProduct').serializeJSON();
    data.category = category;
    data.productCode = $("#product_productCode").val();
    delete data.pictures;
    $.ajax({
        type: methodProduct,
        url: urlProduct,
        data: JSON.stringify(data),
        contentType: 'application/json',
        success: function(data){
            $('#dlgFormProduct').dialog('close');
            createDatagridProduct();
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

function editProduct(){
    var row = $('#gridProduct').datagrid('getSelected');
    if (row){
        $('#dlgFormProduct').dialog('open').dialog('setTitle','Edit Product');
        $('#formMasterProduct').form('load',row);
        urlProduct = 'json/' + row.productCode;
        methodProduct = 'PUT';
        category = row.category;
        $("#product_category").val(row.category.categoryCode);
        $("#categoryName").val(row.category.description);
        $.ajax({
            type: 'GET',
            url: 'setPicSession/' + row.productCode,
            contentType: 'application/json',
            dataType: 'json'
        });
    }
}

function removeProduct(){
    var row = $('#gridProduct').datagrid('getSelected');
    if (row){
        $.messager.confirm('Confirm','Are you sure you want to delete this product?',function(r){
            if (r){
                $.ajax({
                    type: 'DELETE',
                    url: 'json/' + row.productCode,
                    success: function(data){
                        createDatagridProduct();
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

function findCategory(){
    if($("#product_category").validatebox('isValid')) {
        var idcat = $("#product_category").val();
        $.ajax({
            type: 'GET',
            url: 'cat/' + idcat,
            success: function(data){
                category = data;
                $("#categoryName").val(data.description);
            },
            error: function(errorResp){
                alert(errorResp.responseText);
            }
        });
    }
}

function addPicture(comp){
    var obj = $(comp);
    var parent = obj.parent().parent();
    
    var inc = comp.substring(comp.length - 1);
    var newId = 'product_picture' + ++inc;
    
    var html = '<div class="fitem">';
    html += '       <label>&nbsp;</label>';
    html += '       <input onChange="uploadPicture(\'#'+newId+'\')" type="file" name="picture" id="'+newId+'" style="width: 180px">';
    html += '       <a href="javascript:void(0)" onclick="addPicture(\'#'+newId+'\')">add</a>';
    html += '       <a href="javascript:void(0)" onclick="deletePicture(\'#'+newId+'\')">delete</a>';
    html += '   </div>'
    
    parent.append(html);
}

function deletePicture(comp){
    var obj = $(comp);
    var value = obj.val();
    $.ajax({
        type: 'GET',
        url: 'picdel?name=' + value,
        success: function(data){
            obj.parent().remove();
        },
        error: function(errorResp){
            alert(errorResp.responseText);
        }
    });
}

function uploadPicture(comp){
    $(comp).upload('upload', function(res) {
        if(res.msg != undefined){
            alert(res.msg);
            $(comp).val('');
        }
    }, 'json');
}
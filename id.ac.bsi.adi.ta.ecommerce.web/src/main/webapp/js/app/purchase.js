var urlPurchase = "json";
var methodPurchase = "POST";
var methodPurchaseDetail = "POST";
var urlPurchaseDetail = "/detail/json";
var supplierSelected = null;
var productSelected = null;

function createDatagridPurchase(){
    var sdate = $("#pur_sdate").datebox('getValue');
    var edate = $("#pur_edate").datebox('getValue');
    if(!sdate || !edate) return;
    
    $('#gridPurchase').datagrid({
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
            'page.size':10,
            'startDate': sdate,
            'endDate': edate
        },
        columns:[[
        {
            field:'purchaseNumber', 
            title:'Purchase No.', 
            width:110
        },
        {
            field:'purchaseDate', 
            title: 'Tanggal', 
            width:110
        },
        {
            field:'supplier', 
            title: 'Supplier', 
            width:110,
            formatter: function(value,row,index){
                if (row.supplier){
                    return row.supplier.name;
                } else {
                    return value;
                }
            }
        },
        
        ]],
        onLoadSuccess:function(row,data){
            var pager = $('#gridPurchase').datagrid('getPager');
            pager.pagination({
                onSelectPage: function(pageNumber, pageSize){
                    paginationActionByDate(pageNumber, pageSize, sdate, edate, 'json', '#gridPurchase');
                }
            });
        },
        onLoadError:function(error){
            console.log("error loading grid data " + error);
        }
    });
}

function createDatagridPurchaseDetail(){
    $('#gridPurchaseDetail').datagrid({
        style:'width:300px; height:150px',
        method:'get',
        url:'detail/json',
        idField:'id',
        fitColumns:'true',
        nowrap: false,
        singleSelect: true,
        columns:[[
        {
            field:'product', 
            title: 'Product', 
            width:80,
            formatter: function(value,row,index){
                if (row.product){
                    return row.product.productName;
                } else {
                    return value;
                }
            }
        },
        {
            field:'quantity', 
            title:'Qty', 
            width:30,
            align: 'center',
            formatter: function(value,row,index){
                return formatNumber(value);
            }
        },
        {
            field:'price', 
            title: 'Price', 
            width:70,
            align: 'right',
            formatter: function(value,row,index){
                return formatNumber(value);
            }
        },
        {
            field:'totalPrice', 
            title: 'Total', 
            width:70,
            align: 'right',
            formatter: function(value,row,index){
                return formatNumber(value);
            }
        }
        
        ]],
        onLoadSuccess:function(row,data){
            console.log("data load success");
        },
        onLoadError:function(error){
            console.log("error loading grid data " + error);
        }
    });
}

function newPurchase(){
    createDatagridPurchaseDetail();
    $('#dlgFormPurchase').dialog('open').dialog('setTitle','New Purchase');  
    $('#formPurchase').form('clear');  
    urlPurchase = "json";
    methodPurchase = "POST";
}

function newPurchaseDetail(){
    $('#dlgFormPurchaseDetail').dialog('open').dialog('setTitle','Add Detail');  
    $('#formPurchaseDetail').form('clear');  
    urlPurchaseDetail = "detail/save";
    methodPurchaseDetail = "POST";
}

function getObjectSupplier(){
    var idr = $('#purchase_supplier').combobox('getValue');
    console.log("call method getObjectSupplier : ", idr);
    $.ajax({
        type: 'GET',
        url: '../supplier/json/'+ idr,
        contentType: 'application/json',
        success: function(data){
            supplierSelected = data;
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
                    title: 'Get Supplier Error',
                    msg: errorResp.responseText
                });
            }
        },
        dataType: 'json'
    });
}

function savePurchaseDetail(){
    var data = $('#formPurchaseDetail').serializeJSON();
    data.product = productSelected;
    data.totalPrice = $('#purchaseDetail_totalPrice').val();
    
    $.ajax({
        type: methodPurchaseDetail,
        url: urlPurchaseDetail,
        data: JSON.stringify(data),
        contentType: 'application/json',
        success: function(data){
            $('#dlgFormPurchaseDetail').dialog('close');
            createDatagridPurchaseDetail();
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
function savePurchase(){
    var data = $('#formPurchase').serializeJSON();
    data.supplier = supplierSelected;
    
    $.ajax({
        type: methodPurchase,
        url: urlPurchase,
        data: JSON.stringify(data),
        contentType: 'application/json',
        success: function(data){
            $('#dlgFormPurchase').dialog('close');
            createDatagridPurchase();
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

function getObjectProduct(){
    var idr = $('#purchaseDetail_product').combobox('getValue');
    console.log("call method getObjectProduct : ", idr);
    $.ajax({
        type: 'GET',
        url: '../product/json/'+ idr,
        contentType: 'application/json',
        success: function(data){
            productSelected = data;
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
                    title: 'Get Product Error',
                    msg: errorResp.responseText
                });
            }
        },
        dataType: 'json'
    });
}

function calculatePurchaseDetail(){
    console.log("calculate Total");
    var qty = 0;
    var price = 0;
    
    if($('#purchaseDetail_quantity').val().length > 0){
        qty = $('#purchaseDetail_quantity').val();
    }
    
    if($('#purchaseDetail_price').val().length > 0){
        price = $('#purchaseDetail_price').val();
    }
    
    var total = qty * price;
    
    $('#purchaseDetail_totalPrice').val(total);
}

function cancelPurchase(){
    $.get('cancel', function(data) {
        $('#dlgFormPurchase').dialog('close');
        console.log("cancel success");
    });
}
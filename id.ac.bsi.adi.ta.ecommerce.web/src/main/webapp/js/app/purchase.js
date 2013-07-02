var urlPurchase = "json";
var methodPurchase = "POST";

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

function newPurchase(){
    $('#dlgFormCategory').dialog('open').dialog('setTitle','New Category');  
    $('#formMasterCategory').form('clear');  
    urlCategory = "json";
    methodCategory = "POST";
}
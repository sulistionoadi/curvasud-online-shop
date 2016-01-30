function createDatagridStok(){
    var tanggal = $("#tanggal").datebox('getValue');
    if(!tanggal) return;
    
    $('#gridStok').datagrid({
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
            'tanggal': tanggal
        },
        columns:[[
        {
            field:'product', 
            title: 'Produk', 
            width:200,
            formatter: function(value,row,index){
                if (row.product){
                    return row.product.productName;
                } else {
                    return value;
                }
            }
        },
        {
            field:'initialStock', 
            title:'Stok Awal', 
            width:60
        },
        {
            field:'stockDebet', 
            title: 'Debit', 
            width:60
        },
        {
            field:'stockCredit', 
            title: 'Kredit', 
            width:60
        },
        {
            field:'finalStock', 
            title: 'Stok Akhir', 
            width:60
        }
        
        ]],
        onLoadSuccess:function(row,data){
            var pager = $('#gridStok').datagrid('getPager');
            pager.pagination({
                onSelectPage: function(pageNumber, pageSize){
                    paginationDataStock(pageNumber, pageSize, tanggal, 'json', '#gridStok');
                }
            });
        },
        onLoadError:function(error){
            console.log("error loading grid data " + error);
        }
    });
}
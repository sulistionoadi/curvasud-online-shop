var urlCategory = "json";
var methodCategory = "POST";

function createGridApproval() {
    $('#gridApproval').datagrid({
        style: 'width:700px; height:400px',
        method: 'get',
        url: 'json',
        idField: 'id',
        fitColumns: 'true',
        nowrap: false,
        pagination: true,
        rownumbers: true,
        singleSelect: true,
        pageSize: 10,
        queryParams: {
            'page.page': 1,
            'page.size': 10
        },
        columns: [[
                {
                    field: 'paymentCode',
                    title: 'Code',
                    width: 110
                },
                {
                    field: 'accountName',
                    title: 'Account Name',
                    width: 110
                },
                {
                    field: 'action',
                    title: 'Action',
                    width: 110,
                    align: 'right',
                    formatter: function(value, row, index) {
                        var col;
                        if (row.id != null) {
                            col = '<a href="do-approve/'+row.id+'">Approve</a>';
                        }
                        return col;
                    }
                }
            ]],
        onLoadSuccess: function(row, data) {
            var pager = $('#gridApproval').datagrid('getPager');
            pager.pagination({
                onSelectPage: function(pageNumber, pageSize) {
                    paginationAction(pageNumber, pageSize, 'json', '#gridApproval');
                }
            });
        },
        onLoadError: function(error) {
            console.log("error loading grid data " + error);
        }
    });
}
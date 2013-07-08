var urlCategory = "json";
var methodCategory = "POST";

function createGridInvoice() {
    $('#gridInvoice').datagrid({
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
                    field: 'invoiceNumber',
                    title: 'Invoice Number',
                    width: 110
                },
                {
                    field: 'kodebooking',
                    title: 'Kode Booking',
                    width: 110,
                    formatter: function(value, row, index) {
                        if (row.booking) {
                            return row.booking.bookingCode;
                        } else {
                            return value;
                        }
                    }
                },
                {
                    field: 'transactionDate',
                    title: 'Tanggal Transaksi',
                    width: 120,
                    formatter: function(value) {
                        var result = "";
                        if (value) {
                            result = $.format.date(value, "dd-MM-yyyy hh:mm");
                        }
                        return result;
                    }
                },
                {
                    field: 'action',
                    title: 'Action',
                    width: 50,
                    align: 'right',
                    formatter: function(value, row, index) {
                        var col;
                        if (row.id != null) {
                            col = '<a href="cetak?id=' + row.id + '">Cetak</a>';
                        }
                        return col;
                    }
                }
            ]],
        onLoadSuccess: function(row, data) {
            var pager = $('#gridInvoice').datagrid('getPager');
            pager.pagination({
                onSelectPage: function(pageNumber, pageSize) {
                    paginationAction(pageNumber, pageSize, 'json', '#gridInvoice');
                }
            });
        },
        onLoadError: function(error) {
            console.log("error loading grid data " + error);
        }
    });
}
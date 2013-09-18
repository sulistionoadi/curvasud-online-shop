function getListCategoryMenu(){
    $.ajax({
        method: 'GET',
        url: '../search/categories',
        data: {},
        contentType: 'application/json',
        success: function(data){
            var shtml = "<ul>";
            
            shtml = shtml + "<li><a href=\"../search/panel\">Semua</a></li>";
            for(var i=0; i<data.length; i++){
                shtml = shtml + "<li><a href=\"../search/panel?cat="+data[i].categoryCode+"\">"+data[i].description+"</a></li>";
            }
            shtml = shtml + "</ul>";

            $('div#listCategoryMenu').html(shtml);
        },
        dataType: 'json'
    });
}

function refreshTotalSearchp(){
    $("#search_pagination").pagination('options').total = $('#txtTotalSearchp').val();
    $("#search_pagination").pagination('refresh');
}

function refreshTotalComments(){
    $("#comment_pagination").pagination('options').total = $('#txtTotalCommentList').val();
    $("#comment_pagination").pagination('refresh');
}

function cari(){
    
}

/**
 * Created by trevorBye on 9/23/16.
 */
$(document).on('click', '#btn-submit', function() {

        $('#feedback').hide();
        searchViaAjax();

});

function searchViaAjax() {

    var search = {}
    search["wish"] = $("#wish").val();

    $.ajax({
        type : "POST",
        contentType : "application/json",
        url : "/addwish",
        data : JSON.stringify(search),
        dataType : 'json',
        timeout : 100000,
        success : function(data) {
            console.log("SUCCESS: ", data);
            display(data);
        },
        error : function(e) {
            console.log("ERROR: ", e);
            display(e);
        },
        done : function(e) {
            console.log("DONE");
            enableSearchButton(true);
        }
    });

}


function display(data) {
    var jsonString = JSON.stringify(data);
    var json = JSON.parse(jsonString);
    $('#feedback').html(json.msg);
    $('#feedback').show();
}

$('#feedback').hover(function(){
    $('#feedback').fadeOut(300, function(){
        $(this).hide();
    });
});



















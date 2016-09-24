/**
 * Created by trevorBye on 9/23/16.
 */
jQuery(document).ready(function($) {

    $("#wish-form").submit(function(event) {

        // Disble the search button
        enableSearchButton(false);

        // Prevent the form from submitting via the browser.
        event.preventDefault();

        searchViaAjax();

    });

});

function searchViaAjax() {

    var search = {}
    search["wish"] = $("#wish").val();

    $.ajax({
        type : "POST",
        contentType : "application/json",
        url : "${home}addvote",
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

function enableSearchButton(flag) {
    $("#btn-submit").prop("disabled", flag);
}

function display(data) {
    var json = "<h4>Ajax Response</h4><pre>"
        + JSON.stringify(data, null, 4) + "</pre>";
    $('#feedback').html(json);
}

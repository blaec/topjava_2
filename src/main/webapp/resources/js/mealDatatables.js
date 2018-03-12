var ajaxUrl = "ajax/profile/meals/";
var datatableApi;

$(function () {
    datatableApi = $("#datatable").DataTable({
        "paging": false,
        "info": true,
        "columns": [
            {
                "data": "dateTime"
            },
            {
                "data": "description"
            },
            {
                "data": "calories"
            },
            {
                "defaultContent": "Edit",
                "orderable": false
            },
            {
                "defaultContent": "Delete",
                "orderable": false
            }
        ],
        "order": [
            [
                0,
                "desc"
            ]
        ]
    });
    makeEditable();
});

function filter() {
    // $("#datatable").filter(function(){
    //     return $("#date_time", this) >= $("#startDate").val() && $("#date_time", this) <= $("#endDate").val();}).css("background-color", "yellow");

    // $.get(ajaxUrl, function (data) {
    //     datatableApi.clear().rows.add(data).draw();
    // });

    alert("Start date: " + $("#startDate").val() + "\n" +
        "Start time: " + $("#startTime").val() + "\n" +
        "End date: " + $("#endDate").val() + "\n" +
        "End time: " + $("#endTime").val());
}

function clearFilter() {
    // $("#filterForm").find(":input").val("");
    $("#filterForm")[0].reset();
}
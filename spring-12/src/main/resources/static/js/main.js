$(document).ready(function () {
    $("#authors_link").click(function () {
        $.get("/api/authors", function (data) {
            $("#popup_data").html("<h3>Список актеров:</h3>");
            for (let i = 0; i < data.length; i++) {
                $("#popup_data").append("<div>" +
                    "<span class='author_id'>" + data[i].id + "</span>" +
                    "<span>" + data[i].fullName + "</span>" +
                    "</div>");
            }
            $("#popup").show();
        });
    });

    $("#genres_link").click(function () {
        $.get("/api/genres", function (data){
            $("#popup_data").html("<h3>Список жанров:</h3>");
            for (let i = 0; i < data.length; i++) {
                $("#popup_data").append("<div>" +
                    "<span class='genre_id'>" + data[i].id + "</span>" +
                    "<span>" + data[i].name + "</span>" +
                    "</div>");
            }
            $("#popup").show();
        });
    });

    $("#popup_close_btn").click(function (e) {
        $("#popup").hide();
    });
});
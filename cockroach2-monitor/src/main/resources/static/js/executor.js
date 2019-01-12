console.log("hello cockroach");

$(function() {
    function getExecotorInfo() {
        $.ajax({
            type: "get",
            url: "/executor/infos",
            success: function(data) {
                var json = JSON.parse(data);
                $("#executorcount").html(json.executorcount);
                $("#executorrunning").html(json.executorrunning);
                $("#executorend").html(json.executorend);

                // var executorList = json.executorList;
                //
                // var resultHtml = "";
                // for (var key in executorList) {
                //     var length = 0;
                //     var state = executorList[key][1];
                //     if (state === "start") {
                //         length = 0;
                //     }else if (state === "execute") {
                //         length = 50;
                //     }else if (state === "stop") {
                //         length = 100;
                //     }
                //     resultHtml += "<li>" +
                //         "<div class=\"columns\">" +
                //         "<div class=\"column is-3\">"+executorList[key][0]+"</div>" +
                //         "<div class=\"column is-9\"><progress class=\"progress is-info\" value=\""+length+"\" max=\"100\"></progress></div>" +
                //         "</div></li>";
                // }
                // $("#executorList").html(resultHtml)
            }
        })
    }

    setInterval(function () {
        getExecotorInfo();
    },2000)
})
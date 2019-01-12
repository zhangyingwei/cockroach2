console.log("hello cockroach");

$(function() {
    function getTaskInfo() {
        $.ajax({
            type: "get",
            url: "/task/taskinfo",
            success: function(data) {
                var json = JSON.parse(data);
                $("#taskcount").html(json.taskcount);
                $("#taskrunning").html(json.taskrunning);
                $("#tasksuccess").html(json.tasksuccess);
                $("#taskfailed").html(json.taskfailed);

                var groupinfo = json.taskgroup;

                var resultHtml = "";
                for (var key in groupinfo) {
                    resultHtml += "<li>" +
                        "<div class=\"columns\">" +
                        "<div class=\"column is-8\">"+key+"</div>" +
                        "<div class=\"column is-4\">"+groupinfo[key]+"</div>" +
                        "</div>" +
                        "</li>";
                }

                $("#taskgroup").html(resultHtml);

                var taskurls = json.taskurls;
                var urlHtml = "";
                for (var url in taskurls) {
                    urlHtml+="<li>"+taskurls[url]+"</li>"
                }
                $("#taskurls").html(urlHtml);
            }
        })
    }

    setInterval(function () {
        getTaskInfo();
    },2000)
})
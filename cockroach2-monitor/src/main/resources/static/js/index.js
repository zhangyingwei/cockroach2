console.log("hello cockroach");

$(function() {
    function getTaskInfo() {
        $.ajax({
            type: "get",
            url: "/index/taskinfo",
            success: function(data) {
                var json = JSON.parse(data);
                $("#taskcount").html(json.taskcount);
                $("#taskrunning").html(json.taskrunning);
                $("#tasksuccess").html(json.tasksuccess);
                $("#taskfailed").html(json.taskfailed);
            }
        })
    }

    function getExecutorInfo() {
        $.ajax({
            type: "get",
            url: "/index/executorinfo",
            success: function(data) {
                var json = JSON.parse(data);
                $("#executorrunning").html(json.executorrunning);
                $("#executorend").html(json.executorend);
            }
        })
    }

    setInterval(function () {
        getTaskInfo();
        getExecutorInfo();
    }, 1000);
})
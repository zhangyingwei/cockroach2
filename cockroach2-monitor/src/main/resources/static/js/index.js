console.log("hello cockroach");

$(function () {
    var taskinfos = new Vue({
        el: '#taskinfos',
        data: {
            count: 1,
            running: 2,
            success: 3,
            failed: 4
        }
    });

    var executorinof = new Vue({
        el: "#executorinfo",
        data: {
            running: 5,
            end: 6
        }
    });

    function getTaskInfo() {
        $.ajax({
            type: "get",
            url: "/index/taskinfo",
            success: function (data) {
                var json = JSON.parse(data);
                taskinfos.count = json.taskcount;
                taskinfos.running = json.taskrunning;
                taskinfos.success = json.tasksuccess;
                taskinfos.failed = json.taskfailed;
            }
        })
    }

    function getExecutorInfo() {
        $.ajax({
            type: "get",
            url: "/index/executorinfo",
            success: function (data) {
                var json = JSON.parse(data);
                executorinof.running = json.executorrunning;
                executorinof.end = json.executorend;
            }
        })
    }

    setInterval(function () {
        getTaskInfo();
        getExecutorInfo();
    }, 1000);
});
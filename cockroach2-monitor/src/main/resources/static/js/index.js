$(function() {
    function getTaskInfo() {
        $.ajax("/task/info",function(res) {
            console.log(res)
        })
    }

    getTaskInfo()
})
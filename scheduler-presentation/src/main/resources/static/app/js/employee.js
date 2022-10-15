$(document).ready(function(){
    $("#btn-add-emp").click(function(){
        saveEmpOnClick()
    })
})

function saveEmpOnClick()
{
    $("#save-employee-modal").modal('show')
}
$(document).ready(function(){
    $(".btn-approve").click(function(){
        approveOnClick()
    })
})

function approveOnClick()
{
    $("#checkin-approval-modal").modal('show')
}
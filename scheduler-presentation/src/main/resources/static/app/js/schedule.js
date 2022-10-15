$(document).ready(function(){
    $(".availibility-icon").click(function(){
        resourceEditOnClick()
    })
})

function resourceEditOnClick()
{
    $("#add-shift-modal").modal('show')
}
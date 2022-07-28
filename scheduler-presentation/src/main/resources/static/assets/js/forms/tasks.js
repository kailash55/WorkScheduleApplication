var tasks = []
$(document).ready(function(){
	$("#btn-add-task").click(function(e){
		e.preventDefault()
		let note = $("#txt-note").val()
		tasks.push(note)
		renderTasks()
	})
	
	$("#btn-addnote").click(function(){
		$("#addAnnouncement").modal('show')
	})
})

function renderTasks()
{
	$("#lst-taks").html("")
	
	for(let i=0; i<tasks.length; i++)
	{
		let task = tasks[i]
		let row  = '<li class="message-item">'
        row += '<div class="row" style="min-width:350px">'
        row +=  '<div class="col-md-10">'
        row +=  '<p>'+task+'</p>'
        row +=  '</div>'
        row +=  '<div class="col-md-1">'
        row +=   '<button type="button" id="btn-done" class="btn btn-primary">'
        row +=    '<i class="bi bi-bookmark-check"></i>'
        row +=     '</button>'
        row +=    	'</div> </div></li>'
        row += '<li><hr class="dropdown-divider"></li>'  
             
        $("#lst-taks").append(row)  
        
        $("#txt-note").val("")
	}
}


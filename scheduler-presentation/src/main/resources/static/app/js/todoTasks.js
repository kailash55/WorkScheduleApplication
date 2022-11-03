$(document).ready(function(){
	loadToDoTasks()
	
	$("#btn-add-task").click(function(){
		let todo = $("#txt-todo").val()
		addTask(todo)
	})
	
})

function completeToDo(id)
{
	let token = Cookies.get('AUTH_TOKEN_COOKIE');
	$.ajax({
			url: window.serviceUrl + "todo-notes/"+id+"/complete",
			type:"PATCH",
			"headers": {"Authorization": "Bearer "+token},
			success: function(response)
			{
				loadToDoTasks()
			},
			error: function(a,b,c)
			{
				alert(a.responseText)
			}
	})
}



function addTask(todo)
{
	let formData = {
	    "task" : todo
	}
	
	let token = Cookies.get('AUTH_TOKEN_COOKIE');
	$.ajax({
			url: window.serviceUrl + "todo-notes",
			type:"POST",
			"headers": {"Authorization": "Bearer "+token},
			contentType: "application/json; charset=utf-8",
			data: JSON.stringify(formData),
			success: function(response)
			{
				
				let row=
				"<tr>"+
					"<td>"+todo+"</td>"+
					'<td><input type="button" data-id="'+response+'" class="btn-task-done" value="Done" /></td>'+
				"</tr>"
				
				$("#todo-body").append(row)
				$("#txt-todo").val("")
				$(".btn-task-done").click(function(e){
					completeToDo($(e.currentTarget).data("id"))
				})
			},
			error: function(a,b,c)
			{
				alert(a.responseText)
			}
	})
}

function loadToDoTasks()
{
	let token = Cookies.get('AUTH_TOKEN_COOKIE');
	$.ajax({
			url: window.serviceUrl + "todo-notes",
			type:"GET",
			"headers": {"Authorization": "Bearer "+token},
			contentType: "application/json; charset=utf-8",
			success: function(response)
			{
				renderToDoTasks(response)
			},
			error: function(a,b,c)
			{
				console.log(a)
			}
	})
}


function renderToDoTasks(tasks)
{
	$("#todo-body").html("")
	for(let i=0; i<tasks.length; i++)
	{
		let task = tasks[i]
		
		let row=
		"<tr>"+
			"<td>"+task.todoText+"</td>"+
			'<td><input type="button" data-id="'+task.id+'" class="btn-task-done" value="Done" /></td>'+
		"</tr>"
        
        $("#todo-body").append(row)
	}
	$(".btn-task-done").click(function(e){
		completeToDo($(e.currentTarget).data("id"))
	})
}
$(document).ready(function(){
    $("#btn-add-emp").click(function(){
        saveEmpOnClick()
    })
    
    $("#saveEmployee").click(function(){
		saveEmployee()
	})
    
    loadEmployees()
})

function resetForms()
{
	$('input[type=checkbox]').prop('checked', false);
	$('input[type=text], textarea').val('')
	$('input[type=numeric]').val(0)
	$('select').val('0');
}

function loadEmployees()
{
	$("#backdrop").show()
	let token = Cookies.get('AUTH_TOKEN_COOKIE');
	$.ajax({
			url: window.serviceUrl + "employees",
			type:"GET",
			"headers": {"Authorization": "Bearer "+token},
			contentType: "application/json; charset=utf-8",
			success: function(response)
			{
				$("#backdrop").hide()
				renderEmployeesList(response)
			},
			error: function(a,b,c)
			{
				$("#backdrop").hide()
				console.log(a)
			}
	})
}

function renderEmployeesList(employeesList)
{
	$("#employees-tbody").html("")
	for(let i=0; i<employeesList.length; i++)
	{
		let emp = employeesList[i]
		
		let row = 
		"<tr>"+
            "<td>"+emp.firstName +" "+ emp.lastName+"</td>"+
            "<td>"+emp.email+"</td>"+
            "<td>"+emp.mobileNumber+"</td>"+
            "<td>"+emp.weeklyWorkingHours+"</td>"+
            "<td>"+emp.positions[0].name+"</td>"+
        "</tr>"
        
        $("#employees-tbody").append(row)
	}
}

function saveEmployee()
{
	$("#backdrop").show()
	let positions = []
	positions.push(parseInt($("#select-role").val()))
	
	let formData = {
	    "firstName": $("#txt-first-name").val(),
	    "lastName": $("#txt-last-name").val(),
	    "email": $("#txt-email").val(),
	    "mobileNumber":$("#txt-phone").val(),
	    "username":$("#txt-username").val(),
	    "weeklyWorkingHours":parseInt($("#txt-working-hrs").val()),
	    "positionIds": positions
	}
	
	let token = Cookies.get('AUTH_TOKEN_COOKIE');
	$.ajax({
			url: window.serviceUrl + "employees",
			type:"POST",
			"headers": {"Authorization": "Bearer "+token},
			contentType: "application/json; charset=utf-8",
			data: JSON.stringify(formData),
			success: function(response)
			{
				$("#backdrop").hide()
				$("#save-employee-modal").modal('hide')
				resetForms()
				loadEmployees()
				resetForm()
			},
			error: function(a,b,c)
			{
				$("#backdrop").hide()
				alert(a.responseText)
			}
	})
}

function saveEmpOnClick()
{
	resetForms()
    $("#save-employee-modal").modal('show')
}
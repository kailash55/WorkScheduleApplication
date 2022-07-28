var empListData = []
$(document).ready(function(){
	loadEmployeesList()
	
	$("#btn-add-emp").click(function(){
		
	})
})



function loadEmployeesList()
{
	let token = Cookies.get('AUTH_TOKEN_COOKIE');
	$.ajax({
			url: "http://localhost:8085/employees",
			type:"GET",
			"headers": {"Authorization": "Bearer "+token},
			contentType: "application/json; charset=utf-8",
			success: function(response)
			{
				setEmployeesListData(response)
			},
			error: function(a,b,c)
			{
				console.log(a)
			}
	})
}

function setEmployeesListData(data)
{
	empListData = data
	renderEmpListTable()
}

function renderEmpListTable()
{
	for(let i=0;i<empListData.length;i++)
	{
		let emp = empListData[i]
		let empDepartementsConcat = ""
		//for(inj
		let rowHtml =
		"<tr>"+
		"<td>"+ emp.firstName + " " + emp.lastName + "</td>"+
		"<td>"+ emp.email + "</td>"+
		"<td>"+ emp.weeklyWorkingHours + "</td>"+
		"<td>"+ emp.mobileNumber + "</td>"+
		"<td>"+ "Front End" + "</td>";
		
		$("#emp-table-body").append(rowHtml)
	}
}
$(document).ready(function(){
	$("#btn-register").click(function(){
		submitRegistrationRequest()
	})
})

function convertFormToJSON(form) {
	return $(form)
	  .serializeArray()
	  .reduce(function (json, { name, value }) {
		json[name] = value;
		return json;
	  }, {});
}

function submitRegistrationRequest()
{
	//var formData = $("#frm-registration").serialize();
	let formData = convertFormToJSON("#frm-registration")
	
	$.ajax({
		url:window.serviceUrl + "employeeregistration",
		type: "POST",
		data: JSON.stringify(formData),
		dataType : "json",
		contentType: "application/json; charset=utf-8",
		success: function(response){
			window.location.href = "/"
		},
		error: function(a,b,c)
		{
			alert("error")
		}
	}) 
}



$(document).ready(function(){
	
})

function submitRegistrationRequest()
{
	var formData = $("#frm-registration").serialize();
	
	$.ajax({
		url:"/employerregistration",
		type: "POST",
		success: function(response){
			if(response.success)
			{
				
			}
		}
	}) 
}



$(document).ready(function(){
	$("#btn-login").click(function(){
		let loginData = {
			"username": $("#txt-username").val(),
			"password": $("#txt-password").val()
		}
		
		$.ajax({
			url: "http://localhost:8085/login",
			type:"POST",
			dataType : "json",
			contentType: "application/json; charset=utf-8",
			data: JSON.stringify(loginData),
			success: function(response)
			{
				Cookies.set('AUTH_TOKEN_COOKIE', response.token);
				window.location.href = "/schedule"
			},
			error: function(a,b,c)
			{
				console.log(a)
			}
		})
	})
})
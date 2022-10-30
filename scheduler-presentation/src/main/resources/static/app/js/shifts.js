$(document).ready(function(){
    $("#btn-add-shift").click(function(){
        openAddShiftModal()
    })
    
    $("#btn-save-shift").click(function(){
		saveShift()
	})
    
    loadShifts()
})

function resetFormsShift()
{
	$('input[type=checkbox]').prop('checked', false);
	$('.timepicker').val("")
	$('input[type=text], textarea').val('')
	$('input[type=numeric]').val(0)
	$('select').val('0');
}

function openAddShiftModal()
{
	 $("#save-shift-modal").modal('show')
}

function saveShift()
{
	$("#backdrop").show()
	let formData = {
	    "startTimeHour" : parseTimeString($("#txt-start-time").val())[0],
	    "startTimeMinute": parseTimeString($("#txt-start-time").val())[1],
	    "endTimeHour": parseTimeString($("#txt-end-time").val())[0],
	    "endTimeMinute": parseTimeString($("#txt-end-time").val())[1],
	    "positionId": parseInt($("#select-role").val())
	}
	
	let token = Cookies.get('AUTH_TOKEN_COOKIE');
	$.ajax({
			url: window.serviceUrl + "shifts",
			type:"POST",
			"headers": {"Authorization": "Bearer "+token},
			contentType: "application/json; charset=utf-8",
			data: JSON.stringify(formData),
			success: function(response)
			{
				$("#backdrop").hide()
				$("#save-shift-modal ").modal('hide')
				resetFormsShift()
				loadShifts()
			},
			error: function(a,b,c)
			{
				$("#backdrop").hide()
				alert(a.responseText)
			}
	})
}

function loadShifts()
{
	$("#backdrop").show()
	let token = Cookies.get('AUTH_TOKEN_COOKIE');
	$.ajax({
			url: window.serviceUrl + "shifts",
			type:"GET",
			"headers": {"Authorization": "Bearer "+token},
			contentType: "application/json; charset=utf-8",
			success: function(response)
			{
				$("#backdrop").hide()
				renderShiftsList(response)
			},
			error: function(a,b,c)
			{
				$("#backdrop").hide()
				console.log(a)
			}
	})
}

function renderShiftsList(shiftsList)
{
	$("#shift-tbody").html("")
	for(let i=0; i<shiftsList.length; i++)
	{
		let shift = shiftsList[i]
		
		let row = 
		"<tr>"+
            "<td>"
            + get12HrTime(shift.startTimeHour) + ":" +formatNumber(shift.startTimeMinute)
            + getDayPeriod(shift.startTimeHour)
			+ " - " 
			+ get12HrTime(shift.endTimeHour) + ":" + formatNumber(shift.endTimeMinute)
			+ getDayPeriod(shift.endTimeHour)
            +"</td>"+
            "<td>"+shift.positionName+"</td>"
        "</tr>"
        
        $("#shift-tbody").append(row)
	}
}
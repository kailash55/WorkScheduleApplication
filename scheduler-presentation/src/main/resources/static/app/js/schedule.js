$(document).ready(function(){
	
    $(".availibility-icon").click(function(){
		alert("clicked")
        resourceEditOnClick()
    })
    
    //loadResourceData()
})

function resourceEditOnClick()
{
    $("#add-shift-modal").modal('show')
}

function loadResourceData()
{
	let token = Cookies.get('AUTH_TOKEN_COOKIE');
	$.ajax({
			url: "http://localhost:8085/weeklyresources",
			type:"GET",
			"headers": {"Authorization": "Bearer "+token},
			contentType: "application/json; charset=utf-8",
			success: function(response)
			{
				setResourcesListData(response)
			},
			error: function(a,b,c)
			{
				console.log(a)
			}
	})
}

function setResourcesListData(resources)
{
	resourcesData = resources
	renderTable()
}

function renderTable()
{
	$("#new-calendar-body").html("")
	for(let i=0; i<resourcesData.length; i++)
	{
		let resource = resourcesData[i]
		
		let row = "<tr>";
		
		row += "<td>"
        row += "<b>" + resource.empName + "</b>"
        row += '<i class="fa-solid fa-pencil availibility-icon"></i>'
        row += '<br>'
        row += '<span class="lbl-designation">' + 'Sr Developer' + '(' + resource.assignedHours +'/' + resource.totalWorkingHours + ')</span>'
        row += '</td>'
		
		for(let j=1; j<=7; j++)
		{
			let daySlots = resource.weeklyAvailibilitySlots[j]
			
			row += "<td>"
			if(daySlots.availableSlots.length > 0)
			{
				for(let k=0;k<daySlots.availableSlots.length; k++)
				{			
					row += '<div class="shift availibility-info">'
                            +'<div class="shift-name collapse-in">'
                            + daySlots.availableSlots[k].startTimeHour + ":" +daySlots.availableSlots[k].startTimeMinute
							+ "-" 
							+ daySlots.availableSlots[k].endTimeHour + ":" +daySlots.availableSlots[k].endTimeMinute
							+  '</div>'
                            + '<div class="shift-action-buttons collapse" style="color: antiquewhite;">'
                            + '<i class="bi bi-plus-circle btn-add-shift"></i>'
                            + '<i class="bi bi-pencil-square"></i>'
                            + '<i class="bi bi-archive"></i>'
                            + '</div>'
                          + '</div>'	
				}
				
			}
			
			
            
            for(let k=0; k<daySlots.assignedShifts.length; k++)
			{
				let shift = daySlots.assignedShifts[k]
				row += '<div class="shift shift-red">'
                row += '<div class="shift-name collapse-in">'+shift.startTimeHour+':'+shift.startTimeMinute+' - ' + shift.endTimeHour + ':' + shift.endTimeMinute +'</div>'
                row += '<div class="shift-action-buttons collapse" style="color: antiquewhite;">'
                row += '<i class="bi bi-plus-circle btn-add-shift"></i>'
                row += '<i class="bi bi-pencil-square"></i>'
                row += '<i class="bi bi-archive"></i>'
                row += '</div>'
                row += '</div>'
			}
			
			row += "</td>"
		}
		
		row += "</tr>"
		
		
		$("#calendar-tbody").append(row)
	}
	
	initEvents()
}
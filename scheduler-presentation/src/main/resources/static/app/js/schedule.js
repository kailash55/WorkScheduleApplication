var shifts = []
var startTimePicker = null
var resourcesData = []

var formTypeSaveFunctions = {
	"add-shift": ()=>saveShift(),
	"add-availability": ()=>saveAvailablity()
}

$(document).ready(function(){
    
    loadResourceData()
    loadPositionsDropdown()
    loadShifts()
    loadEmpListDropdown()
    
    $('#select-positions').on('change', function() {
  		populateShiftForPositions(this.value, "select-shift")
	});
	
	$('#open-shift-select-positions').on('change', function() {
  		populateShiftForPositions(this.value, "open-shift-select-shift")
	});
	
	$("#add-availability-form").hide()
	
	startTimePicker = $('.timepicker').timepicker({
	    timeFormat: 'h:mm p',
	    interval: 30,
	    dynamic: false,
	    dropdown: true,
	    scrollbar: true,
	    zindex: 9999999
	});
	
	$("#btn-save").click(function(){
		save()
	})
	
	$("#btn-save-open-shift").click(function(){
		saveOpenShift()
	})
	
	$("#btn-auto-schedule").click(function(){
		autoSchedule()
	})
})

function autoSchedule()
{
	$("#backdrop").show()
	
	let token = Cookies.get('AUTH_TOKEN_COOKIE');
	$.ajax({
			url: window.serviceUrl + "autoschedule",
			type:"POST",
			"headers": {"Authorization": "Bearer "+token},
			contentType: "application/json; charset=utf-8",
			success: function()
			{
				$("#backdrop").hide()
				loadResourceData()
			},
			error: function(a,b,c)
			{
				$("#backdrop").hide()
				console.log(a)
			}
	})
}

function resetForms()
{
	$('input[type=checkbox]').prop('checked', false);
	$('.timepicker').val("")
	$('input[type=text], textarea').val('')
	$('select').val('0');
}

function getRadioButtonGroupValue(name)
{
	let values = []
	$('input[name="'+name+'"]:checked').each(function() {
   		values.push(this.value)
	});
	return values[0]
}

function getSelectedCheckboxValues(name)
{
	let values = []
	$('input[name="'+name+'"]:checked').each(function() {
   		values.push(this.value)
	});
	return values
}

function save()
{
	let formType = getRadioButtonGroupValue("form-type")
	formTypeSaveFunctions[formType]()
}

function saveAvailablity()
{
	$("#backdrop").show()
	let formData = {
    "employeeId": parseInt($("#hdn-empId").val()),
    "startTimeHour" : parseTimeString($("#txt-start-time").val())[0],
    "startTimeMinute": parseTimeString($("#txt-start-time").val())[1],
    "endTimeHour": parseTimeString($("#txt-end-time").val())[0],
    "endTimeMinute": parseTimeString($("#txt-end-time").val())[1],
    "days": getSelectedCheckboxValues("avail-weekDay").map(x=> parseInt(x))
	}
	
	let token = Cookies.get('AUTH_TOKEN_COOKIE');
	$.ajax({
			url: window.serviceUrl + "employee-availablility",
			type:"POST",
			"headers": {"Authorization": "Bearer "+token},
			contentType: "application/json; charset=utf-8",
			data: JSON.stringify(formData),
			success: function(response)
			{
				$("#backdrop").hide()
				loadResourceData()
				resetForms()
			},
			error: function(a,b,c)
			{
				$("#backdrop").hide()
				alert(a.responseText)
			}
	})
}

function saveOpenShift()
{
	$("#backdrop").show()
	let formData = {
    "shiftId": parseInt($("#open-shift-select-shift").val()),
    "days": getSelectedCheckboxValues("open-shift-weekDay").map(x=> parseInt(x))
	}
	
	let token = Cookies.get('AUTH_TOKEN_COOKIE');
	$.ajax({
			url: window.serviceUrl + "open-shifts",
			type:"POST",
			"headers": {"Authorization": "Bearer "+token},
			contentType: "application/json; charset=utf-8",
			data: JSON.stringify(formData),
			success: function(response)
			{
				$("#backdrop").hide()
				resetForms()
				loadResourceData()
			},
			error: function(a,b,c)
			{
				$("#backdrop").hide()
				console.log(a)
			}
	})
}

function saveShift()
{
	$("#backdrop").show()
	let formData = {
    "employeeId": parseInt($("#hdn-empId").val()),
    "shiftId": parseInt($("#select-shift").val()),
    "days": getSelectedCheckboxValues("weekDay").map(x=> parseInt(x))
	}
	
	let token = Cookies.get('AUTH_TOKEN_COOKIE');
	$.ajax({
			url: window.serviceUrl + "assigned-shifts",
			type:"POST",
			"headers": {"Authorization": "Bearer "+token},
			contentType: "application/json; charset=utf-8",
			data: JSON.stringify(formData),
			success: function(response)
			{
				$("#backdrop").hide()
				resetForms()
				loadResourceData()
			},
			error: function(a,b,c)
			{
				$("#backdrop").hide()
				console.log(a.responseText)
				alert(a.responseText)
			}
	})
}

function parseTimeString(strTime)
{
	let split1 = strTime.split(" ")
	let timeSplit = split1[0].split(":")
	let hr = 0;
	if(split1[1] == "AM")
	{
		hr = parseInt(timeSplit[0])
	}
	else
	{
		hr = 12 + parseInt(timeSplit[0])
	}
	
	return [hr, parseInt(timeSplit[1])]
}
function toggleForm(type)
{
	$("#add-shift-form").hide()
	$("#add-availability-form").hide()
	if(type==1)
	{
		$("#add-shift-form").show()
	}
	else
	{
		$("#add-availability-form").show()
	}
}

function initEvents()
{
	$(".availibility-icon").click(function(e){        
        resourceEditOnClick(parseInt($(e.currentTarget).data("empid")))
    })
    
    $(".assign-open-shift").click(function(e){
		openAssignOpenShiftModal(parseInt($(e.currentTarget).data("id")), parseInt($(e.currentTarget).data("day")))
	})
}

function openAssignOpenShiftModal(openShiftId, day)
{
	let openShiftData = resourcesData
							.find(x=>x.empId==-1)
							.weeklyAvailibilitySlots[day]
							.assignedShifts
							.find(z=>z.id==openShiftId)
							
	$("#hdn-open-shift-id").val(openShiftId)
	$('select').val('0');
	$("#assign-open-shift-modal").modal('show')
	
}

function formatNumber(number)
{
	let formattedNumber = number.toLocaleString('en-US', {
	    minimumIntegerDigits: 2,
	    useGrouping: false
  	})
  	return formattedNumber
}

function getDayPeriod(hour)
{
	return hour>=12 ? "pm":"am"
}

function get12HrTime(hour)
{
	if(hour > 12)
		hour = hour % 12
	return formatNumber(hour)
}

function showOpenShiftModal()
{
	$("#add-open-shift-modal").modal('show')
}

function resourceEditOnClick(empId)
{
	if(empId==-1)
	{
		showOpenShiftModal()
		return
	}
	let resource = resourcesData.filter(x=> x.empId == empId)[0]
	$("#hdn-empId").val(resource.empId)
	$("#add-shift-empname-label").html(resource.empName)
    $("#add-shift-modal").modal('show')
}

function loadPositionsDropdown()
{
	let token = Cookies.get('AUTH_TOKEN_COOKIE');
	$.ajax({
			url: window.serviceUrl +"positions",
			type:"GET",
			"headers": {"Authorization": "Bearer "+token},
			contentType: "application/json; charset=utf-8",
			success: function(response)
			{
				populatePositionsDropdown(response)
			},
			error: function(a,b,c)
			{
				console.log(a)
			}
	})
}

function loadEmpListDropdown()
{
	let token = Cookies.get('AUTH_TOKEN_COOKIE');
	$.ajax({
			url: window.serviceUrl +"employees",
			type:"GET",
			"headers": {"Authorization": "Bearer "+token},
			contentType: "application/json; charset=utf-8",
			success: function(response)
			{
				populateEmployeesDropdown(response)
			},
			error: function(a,b,c)
			{
				console.log(a)
			}
	})
}

function populateShiftForPositions(positionId, selectId)
{
	let positionShifts = shifts.filter(x=> x.positionId == positionId)
	$("#"+selectId).html("")
	let shiftsHtml = '<option selected value="0">Select Time</option>'
	for(let i=0; i<positionShifts.length; i++)
	{
		let shiftRow = positionShifts[i]
		let shiftTime = + get12HrTime(shiftRow.startTimeHour) + ":" +formatNumber(shiftRow.startTimeMinute)
                            + getDayPeriod(shiftRow.startTimeHour)
							+ " - " 
							+ get12HrTime(shiftRow.endTimeHour) + ":" + formatNumber(shiftRow.endTimeMinute)
							+ getDayPeriod(shiftRow.endTimeHour)
		shiftsHtml += '<option value="'+shiftRow.id+'">'+ shiftTime +'</option>'
	}
	$("#"+selectId).append(shiftsHtml)
}

function loadShifts()
{
	let token = Cookies.get('AUTH_TOKEN_COOKIE');
	$.ajax({
			url: window.serviceUrl +"shifts",
			type:"GET",
			"headers": {"Authorization": "Bearer "+token},
			contentType: "application/json; charset=utf-8",
			success: function(response)
			{
				shifts = response
			},
			error: function(a,b,c)
			{
				console.log(a)
			}
	})
}

function populatePositionsDropdown(positions)
{
	for(let i=0; i<positions.length; i++)
	{
		let position = positions[i]
		let positionHtml = '<option value="'+ position.id +'">' + position.name +'</option>'
		$("#select-positions").append(positionHtml)
		$("#open-shift-select-positions").append(positionHtml)
	}
}

function populateEmployeesDropdown(employees)
{
	for(let i=0; i<employees.length; i++)
	{
		let employee = employees[i]
		let employeeRowHtml = '<option value="'+ employee.id +'">' + employee.firstName +" "+ employee.lastName +'</option>'
		$("#select-employee").append(employeeRowHtml)
	}
}

function loadResourceData()
{
	$("#backdrop").show()
	let token = Cookies.get('AUTH_TOKEN_COOKIE');
	$.ajax({
			url: window.serviceUrl + "weeklyresources",
			type:"GET",
			"headers": {"Authorization": "Bearer "+token},
			contentType: "application/json; charset=utf-8",
			success: function(response)
			{
				$("#backdrop").hide()
				setResourcesListData(response)
			},
			error: function(a,b,c)
			{
				$("#backdrop").hide()
				console.log(a)
			}
	})
}

function setResourcesListData(resources)
{
	resourcesData = resources
	renderTable()
}

function renderOpenShiftRow(resource)
{
	let row = "<tr>";
		
		row += "<td>"
        row += "<b>" + "Open Shifts" + "</b>"
        row += '<i class="fa-solid fa-pencil availibility-icon" data-empid="'+resource.empId+'"></i>'
        row += '</td>'
        
        for(let j=1; j<=7; j++)
		{
			let daySlots = resource.weeklyAvailibilitySlots[j]
			
			row += "<td>"
            
            for(let k=0; k<daySlots.assignedShifts.length; k++)
			{
				let shift = daySlots.assignedShifts[k]
                
                row += '<div class="shift assigned-shift-info assign-open-shift" data-id="'+shift.id+'" data-day="'+j+'">'
	            			+ get12HrTime(shift.startTimeHour) + ":" +formatNumber(shift.startTimeMinute)
                            + getDayPeriod(shift.startTimeHour)
							+ " - " 
							+ get12HrTime(shift.endTimeHour) + ":" + formatNumber(shift.endTimeMinute)
							+ getDayPeriod(shift.endTimeHour)
							+ '</div>'
	                        + '<div class="shift-action-buttons collapse" style="color: antiquewhite;">'
	                        + '<i class="bi bi-plus-circle btn-add-shift"></i>'
	                        + '<i class="bi bi-pencil-square"></i>'
	                        + '<i class="bi bi-archive"></i>'
	                        + '</div>'
	              			+ '</div>'
			}
			
			row += "</td>"
		}
		$("#calendar-tbody").prepend(row)
}

function renderTable()
{
	$("#calendar-tbody").html("")
	for(let i=0; i<resourcesData.length; i++)
	{
		
		let resource = resourcesData[i]
		
		if(resource.empId == -1)
		{
			renderOpenShiftRow(resource)
			continue;
		}
		
		let row = "<tr>";
		
		row += "<td>"
        row += "<b>" + resource.empName + "</b>"
        row += '<i class="fa-solid fa-pencil availibility-icon" data-empid="'+resource.empId+'"></i>'
        row += '<br>'
        row += '<span class="lbl-designation">' + resource.designation + '(' + resource.assignedHours +'/' + resource.totalWorkingHours + ')</span>'
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
                            + get12HrTime(daySlots.availableSlots[k].startTimeHour) + ":" +formatNumber(daySlots.availableSlots[k].startTimeMinute)
                            + getDayPeriod(daySlots.availableSlots[k].startTimeHour)
							+ " - " 
							+ get12HrTime(daySlots.availableSlots[k].endTimeHour) + ":" + formatNumber(daySlots.availableSlots[k].endTimeMinute)
							+ getDayPeriod(daySlots.availableSlots[k].endTimeHour)
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
                
                row += '<div class="shift assigned-shift-info">'
	            			+ get12HrTime(shift.startTimeHour) + ":" +formatNumber(shift.startTimeMinute)
                            + getDayPeriod(shift.startTimeHour)
							+ " - " 
							+ get12HrTime(shift.endTimeHour) + ":" + formatNumber(shift.endTimeMinute)
							+ getDayPeriod(shift.endTimeHour)
							+ '</div>'
	                        + '<div class="shift-action-buttons collapse" style="color: antiquewhite;">'
	                        + '<i class="bi bi-plus-circle btn-add-shift"></i>'
	                        + '<i class="bi bi-pencil-square"></i>'
	                        + '<i class="bi bi-archive"></i>'
	                        + '</div>'
	              			+ '</div>'
			}
			
			row += "</td>"
		}
		
		row += "</tr>"
		
		
		$("#calendar-tbody").append(row)
	}
	
	initEvents()
}
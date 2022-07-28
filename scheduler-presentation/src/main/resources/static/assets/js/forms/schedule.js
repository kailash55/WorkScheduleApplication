var resourcesData = {
	
}
var shifts = [
	{id:1 , startTimeHour:9, startTimeMinute:30, endTimeHour:1, endTimeMinute:45},
	{id:2 , startTimeHour:3, startTimeMinute:00, endTimeHour:5, endTimeMinute:45},
	{id:3 , startTimeHour:3, startTimeMinute:00, endTimeHour:4, endTimeMinute:45},
	{id:4 , startTimeHour:9, startTimeMinute:30, endTimeHour:6, endTimeMinute:30},
]


$(document).ready(function(){
	
	//createTableFromData();
	loadResourceData()

    $( ".shift" ).hover(
    function(e) {
        debugger;
        var elShiftName = $(e.currentTarget).find(".shift-name")
        var elShiftAct = $(e.currentTarget).find(".shift-action-buttons")
        $(elShiftName).removeClass("collapse-in")
        $(elShiftName).addClass("collapse")
        $(elShiftAct).removeClass("collapse")
        $(elShiftAct).addClass("collapse-in")
    },
    function(e) {
        var elShiftName = $(e.currentTarget).find(".shift-name")
        var elShiftAct = $(e.currentTarget).find(".shift-action-buttons")
        $(elShiftName).removeClass("collapse-in")
        $(elShiftName).removeClass("collapse")
        $(elShiftName).addClass("collapse-in")
        $(elShiftAct).removeClass("collapse-in")
        $(elShiftAct).addClass("collapse")
    });  
    
    
    $("#btn-assignShift").click(function(e){
		let day = $("#hdn-day").val()
		let shiftDetails = {
			empId: $("#hdn-empId").val(),
			empName: $("#txt-empName").val(),
			shift:shifts.find(x=> x.id==parseInt($("#select-shift").val())),
			
		}
		
		let resource = resourcebsData.find(x=>x.empId == parseInt($("#hdn-empId").val()))
		let avail = resource.weeklyAvailibilitySlots[day]
		avail.assignedShifts.push(shiftDetails)
		
		renderTable()
	})
	
	$("#btn-auto-schedule").click(function(){
		autoSchedule()
	})
    
})



function initEvents()
{
	$(".btn-add-shift").click(function(e){
		let element = $(e.target)
		let empId = element.data("empid")
		let empName = element.data("empname")
		let day = element.data("day")
		$("#hdn-empId").val(empId)
		$("#hdn-day").val(day)
		$("#txt-empName").val(empName)
        $("#assignShiftModal").modal('show')
        
    })
    
    

    $(".btn-add-open-shift").click(function(){
        $("#assignOpenShiftModal").modal('show')
    })
}

function createTableFromData()
{
	addOpenShiftRow()
	//addResourcesRows()
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
	$("#calendar-tbody").html("")
	for(let i=0; i<resourcesData.length; i++)
	{
		let resource = resourcesData[i]
		
		let row = "<tr>";
		
		row += "<td>" 
		row += "<div>"
		row += resource.empName
		row += "<br>"
		row += "<span>"
		row += resource.assignedHours
		row += "/"
		row += resource.totalWorkingHours
		row += "</span>"
		row += "</div>"
		row += "</td>"
		
		for(let j=1; j<=7; j++)
		{
			let daySlots = resource.weeklyAvailibilitySlots[j]
			
			row += "<td>"
			if(daySlots.availableSlots.length > 0)
			{
				row += '<div class="shift-availibility">';
				for(let k=0;k<daySlots.availableSlots.length; k++)
				{
				
					row += '<div class="shift-name collapse-in">'
							+ daySlots.availableSlots[k].startTimeHour + ":" +daySlots.availableSlots[k].startTimeMinute
							+ "-" 
							+ daySlots.availableSlots[k].endTimeHour + ":" +daySlots.availableSlots[k].endTimeMinute
							+  "</div>"
					
					
				}
				row += "</div>"
				
			}
			
			let out = resource.totalWorkingHours - resource.assignedHours
			
			if(out <= 0)
			{
				row += '<div class="empty-slot">'
            	row += '<i style="color:red" class="bi bi-plus-circle btn-add-shift" data-day="'+j+'" data-empName="'+resource.empName+'" data-empId="'+resource.empId+'" ></i>'
      
            	row += '</div>'
			}
			else
			{
				row += '<div class="empty-slot">'
            row += '<i class="bi bi-plus-circle btn-add-shift" data-day="'+j+'" data-empName="'+resource.empName+'" data-empId="'+resource.empId+'" ></i>'
      
            row += '</div>'
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

function autoSchedule()
{
	
}


function addOpenShiftRow()
{
	let row = "<tr>";
	
	let rowHeading = 
		"<td>"+
	      "<b>Open Shifts New</b>"+
	      "<br>"+
	      "<span class=\"lbl-designation\">Open Shift (8/40)</span>"+
	    "</td>"
	    
	row = row + rowHeading;
	
	for(let i=1; i<=7; i++)
	{
		let dayShifts = getShiftForDay(i)
		let cell =
		 "<td>";
		
		for(let j=0; j<dayShifts.length; j++)
		{
			let openShift = getShiftFromShiftId(dayShifts[j].shiftId)
			
			let shiftHtml = 
			'<div class="shift shift-blue">'+
	            '<div class="shift-name collapse-in">9am-5pm</div>'+
	            '<div class="shift-action-buttons collapse" style="color: antiquewhite;">'+
	              '<i class="bi bi-plus-circle btn-add-shift"></i>'+
	              '<i class="bi bi-pencil-square"></i>'+
	              '<i class="bi bi-archive"></i>'+
	            '</div>'+
            '</div>'
            
            cell = cell + shiftHtml
		}
		
		cell = cell + "</td>"
		
	}
	
	$("#calendar-tbody").append(row)
}

function getShiftFromShiftId(shiftId)
{
	let shift = data.shifts.find(x=>x.shiftId==shiftId)
	return shift
}

function getShiftForDay(dayId)
{
	let shifts = data.openShifts.filter(x=>x.day == dayId)
	return shifts
}


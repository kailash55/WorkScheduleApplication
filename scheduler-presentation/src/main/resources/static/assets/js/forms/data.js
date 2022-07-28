var data = {
    "departments":[
        {"id":1, "name": "Front Desk"},
        {"id":2, "name": "Accountant"},
        {"id":3, "name": "Labour"},
        {"id":4, "name": "Chef"}
    ],
    "shifts":[
        {
            "id":1,
            "departmentId":1,
            "startTime":"9am",
            "endTime":"5pm",
            "color":"#311b92"
        },
        {
            "id":2,
            "departmentId":2,
            "startTime":"8am",
            "endTime":"11am",
            "color":"#311b92"
        },
        {
            "id":3,
            "departmentId":2,
            "startTime":"1am",
            "endTime":"5am",
            "color":"#1b5e20"
        },
        {
            "id":4,
            "departmentId":3,
            "startTime":"4am",
            "endTime":"8am",
            "color":"#311b92"
        }
    ],
    "unscheduledShifts":[
        {"shiftId":1, "count":5},
        {"shiftId":2, "count":3}
    ],
    "openShifts":[
        {"shiftId":1, "day":5},
        {"shiftId":2, "day":5}
    ],
    "assignedShifts":[
        {
            "day":1,
            "resourceId":1,
            "shiftId":1
        }
    ],
    "resources":[
        {
            "id":1,
            "name":"Kailash Desiti",
            "department": "Front End",
            "totalWorkingHrs":"",
            "assignedWorkHrs":""
        },
        {
            "id":2,
            "name":"Rahul Yadav",
            "department": "Management",
            "totalWorkingHrs":"",
            "assignedWorkHrs":""
        },
        {
            "id":3,
            "name":"Kailash Desiti",
            "department": "Accounts",
            "totalWorkingHrs":"",
            "assignedWorkHrs":""
        },
        {
            "id":4,
            "name":"Kailash Desiti",
            "department": "Kitchen",
            "totalWorkingHrs":"",
            "assignedWorkHrs":""
        }
    ]

}
#### To create company:
###### Request Type: POST
https://time-clock-service.herokuapp.com/api/companies

Content-Type: application/json
```json
{
  "name": "{company name}"
}
```

#### To get all companies:
###### Request Type: GET
https://time-clock-service.herokuapp.com/api/companies

#### To search company by name:
###### Request Type: GET
https://time-clock-service.herokuapp.com/api/companies/search/findByName?name={companyName}

#### To update company:
###### Request Type: PUT or PATCH
https://time-clock-service.herokuapp.com/api/companies/{id}

Content-Type: application/json
```json
{
  "name": "{company name}"
}
```

#### To delete company:
###### Request Type: DELETE
https://time-clock-service.herokuapp.com/api/companies/{id}

#### To create employee:
###### Request Type: POST
https://time-clock-service.herokuapp.com/api/employees

Content-Type: application/json
```json
{
  "id": "{id}",
  "fullName": "{fullName}",
  "fingerprintId": "{fingerprintId}",
  "pin": "{pin}",
  "company": "https://time-clock-service.herokuapp.com/api/companies/{id}"
}
```

#### To associate company to employee:
###### Request Type: PUT
https://time-clock-service.herokuapp.com/api/employees/{employeeId}/company

Content-Type: text/uri-list
```
http://time-clock-service.herokuapp.com/api/companies/{companyId}
```

#### To get all employees of the company:
###### Request Type: GET
https://time-clock-service.herokuapp.com/api/companies/{companyId}/employees


#### To search employee by name:
###### Request Type: GET
https://time-clock-service.herokuapp.com/api/employees/search/findByName?name={employeeName}

#### To update employee:
###### Request Type: PATCH
https://time-clock-service.herokuapp.com/api/employees/{id}

Content-Type: application/json
```json
{
  "fingerprintId": "fingerprint",
  "pin": "1234"
}
```

#### To delete employee:
###### Request Type: DELETE
https://time-clock-service.herokuapp.com/api/employees/{id}

#### Employee Time-in
###### Request Type: POST
https://time-clock-service.herokuapp.com/api/timeIn

Content-Type: application/json
```json
{
  "empId": "{employee id}"
}
```

#### Employee Time-out
###### Request Type: POST
https://time-clock-service.herokuapp.com/api/timeOut

Content-Type: application/json
```json
{
  "empId": "{employee id}"
}
```

### Who Is In
###### Request Type: GET
https://time-clock-service.herokuapp.com/api/whoIsIn

Sample Response:
```json
{
    "_embedded": {
        "whoIsIns": [
            {
                "name": "{employee name}",
                "status": "{IN | OUT}",
                "date": "{current date}",
                "time": "{timeIn | timeOut}"
            }
        ]
    }
}
```

#### To update employee time record:
###### Request Type: PATCH or PUT
https://time-clock-service.herokuapp.com/api/employeeTimes/{id}

Content-Type: application/json
```json
{
  "timeIn": "{yyyy-MM-ddThh:mm:ss}",
  "timeOut": "{yyyy-MM-ddThh:mm:ss}",
  "notes": "{notes}"
}
```
Note: Not all fields are required.

#### To delete time record:
###### Request Type: DELETE
https://time-clock-service.herokuapp.com/api/employeeTimes/{id} -> not employee id

#### List View
###### Request Type: GET
https://time-clock-service.herokuapp.com/api/employeeTimes/search/listView{?employeeName,dateFrom,dateTo}



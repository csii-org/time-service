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
  "createdDate": "{date}"
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
Content-Type: application/json
```json
{
  "empId": "{employee id}",
  "time": "{unix timestamp}"
}
```




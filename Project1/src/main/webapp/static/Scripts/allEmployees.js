let token = sessionStorage.getItem("token");

let tokenArr;
if (!token) {
	localStorage.clear();
	window.location.href = "http://localhost:8080/Project1/login";
} else {
	tokenArr = token.split(":");
	if (tokenArr.length === 4) {
		let baseUrl = "http://localhost:8080/Project1/api/employees?";
		sendAjaxGet(baseUrl, displayEmployees);
	} else {
		window.location.href = "http://localhost:8080/Project1/login";
	}

}

function displayEmployees(xhr) {
	let employees = JSON.parse(xhr.response);
	let empTable = document.getElementById("empTable");
	let tableBody = document.createElement('tbody');
	for(const emp of employees){
		let row = document.createElement("tr");
		let col1 = document.createElement("td");
		let col2 = document.createElement("td");
		let col3 = document.createElement("td");
		let col4 = document.createElement("td");
		let col5 = document.createElement("td");
		col1.appendChild(document.createTextNode(emp.eID));
		col2.appendChild(document.createTextNode(emp.fName));
		col3.appendChild(document.createTextNode(emp.lName));
		col4.appendChild(document.createTextNode(emp.email));
		col5.appendChild(document.createTextNode(emp.uname));
		row.appendChild(col1);
		row.appendChild(col2);
		row.appendChild(col3);
		row.appendChild(col4);
		row.appendChild(col5);
		tableBody.appendChild(row);
	}
	empTable.appendChild(tableBody);
}
let token = sessionStorage.getItem("token");
if (!token) {
	window.location.href = "http://localhost:8080/Project1/login";
} else {
	let tokenArr = token.split(":");
	if (tokenArr.length === 3 || tokenArr.length ===4) {
		let baseUrl = "http://localhost:8080/Project1/api/employees?id=";
		sendAjaxGet(baseUrl + tokenArr[0], displayInfo);
	} else {
		window.location.href = "http://localhost:8080/Project1/login";
	}
}

function displayInfo(xhr) {
	let employee = JSON.parse(xhr.response);
	let pic = document.getElementById("profImg");
	let profPic = window.localStorage.getItem("profPic");
	pic.src = profPic;
	document.getElementById("emp-fname").innerHTML = employee.fName;
	document.getElementById("emp-lname").innerHTML = employee.lName
	document.getElementById("emp-email").innerHTML = employee.email;
	document.getElementById("emp-uname").innerHTML = employee.uname;
}

function editP() {
	window.location.href = "http://localhost:8080/Project1/editProf";
}

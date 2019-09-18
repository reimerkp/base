let token = sessionStorage.getItem("token");
let tokenArr;
if (!token) {
	window.location.href = "http://localhost:8080/Project1/login";
} else {
	tokenArr = token.split(":");
	// console.log(tokenArr);
	if (tokenArr.length === 3 || tokenArr.length ===4) {
		let baseUrl = "http://localhost:8080/Project1/api/employees?id=";
		sendAjaxGet(baseUrl + tokenArr[0], setPlaceholders);
	} else {
		window.location.href = "http://localhost:8080/Project1/login";
	}
}
let pic = document.getElementById("profImg");
var employee = null;
function setPlaceholders(xhr) {
	employee = JSON.parse(xhr.response);
	console.log(employee);

	let profPic = window.localStorage.getItem("profPic");
	pic.src = profPic;
	document.getElementById("fName").value = employee.fName;
	document.getElementById("lName").value = employee.lName;
	document.getElementById("email").value = employee.email;

	document.getElementById("empId").value = employee.eID;
}

let getImg = function(input) {
	if (input.files && input.files[0]) {
		var reader = new FileReader();
		reader.onload = function(e) {
			document.getElementById("profImg").src = e.target.result;
		};
		console.log(input.files[0]);
		reader.readAsDataURL(input.files[0]);
	}

}

let checkPass = function() {
	console.log("in checkPass");
	let submitBtn = document.getElementById("sbmit");
	let passMsg = document.getElementById("pwdMsg");
	let oldPass = document.getElementById("oldP").value;
	let newPass1 = document.getElementById("newP1").value;
	let newPass2 = document.getElementById("newP2").value;
	if (oldPass.length == 0) {
		if (newPass1.length > 0 || newPass2.length > 0) {
			submitBtn.disabled = true;
			passMsg.innerText = "Must confrim old password";
		} else {
			submitBtn.disabled = false;
			passMsg.innerText = "";
		}
	} else if (oldPass !== employee.pwd) {
		submitBtn.disabled = true;
		passMsg.innerText = "Incorrect Password";
	} else if (newPass1 != newPass2) {
		submitBtn.disabled = true;
		passMsg.innerText = "New passwords must match";
	} else {
		submitBtn.disabled = false;
		passMsg.innerText = "";
	}
}

let submitChange = function() {
	let url = "http://localhost:8080/Project1/update";
	let xhr = new XMLHttpRequest();
	xhr.open("POST", url)
	xhr.onreadystatechange = function() {
		if (this.readyState === 4 && this.status === 202) {
			let imageId = xhr.response;
			let newToken = tokenArr[0] + ":" + tokenArr[1] + ":" + imageId;
			console.log(newToken);
			sessionStorage.setItem('token', newToken);
			goHome();
		}
	}

	let fullPath = document.getElementById("pic").files[0];
	if (fullPath != null) {
		window.localStorage.removeItem('profPic');
	}
	let fName = document.getElementById("fName").value;
	let lName = document.getElementById("lName").value;
	let email = document.getElementById("email").value;
	let pwd = document.getElementById("newP2").value;
	let eId = document.getElementById("empId").value;

	// xhr.setRequestHeader("Content-Type",
	// "application/x-www-form-urlencoded");
	// let requestBody =
	// `fName=${fName}&lName=${lName}&email=${email}&pwd=${pwd}&empId=${eId}&picPath=${fullPath}`;
	let formData = new FormData();
	formData.append("Picture", fullPath);
	formData.append("First Name", fName);
	formData.append("Last Name", lName);
	formData.append("Email", email);
	formData.append("password", pwd);
	formData.append("Employee Id", eId);
	xhr.send(formData);
}

function goHome(){
	window.location.href = "http://localhost:8080/Project1/home";
}
document.getElementById("sbmit").addEventListener("click", submitChange);
document.getElementById("cancel").addEventListener("click", goHome);


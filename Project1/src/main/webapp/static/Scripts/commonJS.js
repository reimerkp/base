let cToken = sessionStorage.getItem("token");

let cTokenArr = cToken.split(":");
let reqLink = document.getElementById("allReq");
let empLink = document.getElementById("allEmp");
if (cTokenArr.length === 3) {
	reqLink.style.display = "none";
	empLink.style.display = "none";
}

function logOut() {
	sessionStorage.clear();
	location.reload();
}

function sendAjaxGet(url, callback) {
	let xhr = new XMLHttpRequest();
	xhr.open("GET", url);
	xhr.onreadystatechange = function() {
		if (this.readyState === 4 && this.status === 200) {
			callback(this);
		}
	}
	xhr.setRequestHeader("Authorization", token);
	xhr.send();
}

document.getElementById("log-out").addEventListener("click", logOut);
let token = sessionStorage.getItem("token");
let tokenArr;
let pic = document.getElementById("profImg");

if (!token) {
	window.location.href = "http://localhost:8080/Project1/login";
} else {
	tokenArr = token.split(":");
	// console.log(tokenArr);
	if (tokenArr.length === 3|| tokenArr.length ===4) {
		let profPic = window.localStorage.getItem("profPic");
		pic.src = profPic;
	} else {
		window.location.href = "http://localhost:8080/Project1/login";
	}
}

function checkAmount() {
	let amount = document.getElementById("amt").value;
	let submitBtn = document.getElementById("sbmit");
	submitBtn.disabled = true;
	var regex = /^[0-9]+(\.[0-9]{1,2})?$/;
	console.log("in checkamount");
	if (!regex.test(amount)) {
		submitBtn.disabled = true;
	} else {
		submitBtn.disabled = false;
	}
}

function submitReinbursement() {
	let reasonText = document.getElementById("reason").value;
	let amount = document.getElementById("amt").value;
	let url = "http://localhost:8080/Project1/reinburse";
	let eId = tokenArr[0];
	let requestBody = `reason=${reasonText}&amount=${amount}&eId=${eId}`;
	sendPost(url,requestBody,goHome);


}
document.getElementById("sbmit").addEventListener("click", submitReinbursement);

function sendPost(url,requestBody,callback) {
	let xhr = new XMLHttpRequest();
	xhr.open("POST", url)
	xhr.onreadystatechange = function() {
		if (this.readyState === 4 && this.status === 202) {
			callback();
		}

	}
	xhr.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
	xhr.send(requestBody);
}

function goHome() {
	window.location.href = "http://localhost:8080/Project1/home";
}
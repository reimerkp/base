let token = sessionStorage.getItem("token");

let tokenArr;
if (!token) {
	localStorage.clear();
	window.location.href = "http://localhost:8080/Project1/login";
} else {
	tokenArr = token.split(":");
	if (tokenArr.length === 4) {
		let baseUrl = "http://localhost:8080/Project1/api/requests?";
		sendAjaxGet(baseUrl , displayRequests);
	}
}

function displayRequests(xhr){
	let requests = JSON.parse(xhr.response);
	let reviewedR = [];
	let pendingR = [];
	for(const req of requests){
		if(req.mID == -1){
			pendingR.push(req);
		}else{
			reviewedR.push(req);
		}
	}
	displayPending(pendingR);
	displayReviewed(reviewedR);
}
function displayReviewed(reviewedR){
	let resolvedTable = document.getElementById("resolvedR");
	let tableBody = document.createElement('tbody');
	console.log(reviewedR[0]);
	for (let i = 0; i < reviewedR.length; i++) {
		let row = document.createElement("tr");
		let col1 = document.createElement("td");
		let col2 = document.createElement("td");
		let col3 = document.createElement("td");
		let col4 = document.createElement("td");
		let col5 = document.createElement("td");
		let col6 = document.createElement("td");
		col6.appendChild(document.createTextNode(reviewedR[i].eID));
		col1.appendChild(document.createTextNode(reviewedR[i].rID));
		col2.appendChild(document.createTextNode(reviewedR[i].reason));
		col3.appendChild(document.createTextNode(reviewedR[i].amount));
		col4.appendChild(document.createTextNode(reviewedR[i].mID));
		if (reviewedR[i].approved) {
			col5.appendChild(document.createTextNode("Approved"));
		} else {
			col5.appendChild(document.createTextNode("Denied"));
		}
		row.appendChild(col6);
		row.appendChild(col1);
		row.appendChild(col2);
		row.appendChild(col3);
		row.appendChild(col4);
		row.appendChild(col5);
		tableBody.appendChild(row);
	}
	resolvedTable.appendChild(tableBody);
}
function displayPending(pendingR){
	let pendingTable = document.getElementById("pendingR");
	let tableBody = document.createElement('tbody');
	for(const req of pendingR){
		let row = document.createElement("tr");
		let col1 = document.createElement("td");
		let col2 = document.createElement("td");
		let col3 = document.createElement("td");
		let col4 = document.createElement("td");
		let col5 = document.createElement("td");
		let col6 = document.createElement("td");
		col1.appendChild(document.createTextNode(req.eID));
		col2.appendChild(document.createTextNode(req.rID));
		col3.appendChild(document.createTextNode(req.reason));
		col4.appendChild(document.createTextNode(req.amount));
		let approvedButton = document.createElement('input');
		approvedButton.type = "button";
		approvedButton.className ="btn btn-success";
		approvedButton.value = "Approved";
		approvedButton.onclick = (
				function(entry){
					let requestId = entry.path[2].children[1].firstChild.textContent;
			
					approveRequest(requestId);
				});
		let deniedButton = document.createElement('input');
		deniedButton.type = "button";
		deniedButton.className = "btn btn-danger";
		deniedButton.value = "Denied";
		deniedButton.onclick = (
				function(entry){
					let requestId = entry.path[2].children[1].firstChild.textContent;
			
					denyRequest(requestId);
				});
		col5.appendChild(approvedButton);
		col6.appendChild(deniedButton);
		row.appendChild(col1);
		row.appendChild(col2);
		row.appendChild(col3);
		row.appendChild(col4);
		row.appendChild(col5);
		row.appendChild(col6);
		tableBody.appendChild(row);		
	}
	pendingTable.appendChild(tableBody);
}

function denyRequest(requestId){
	let reqUrl = "http://localhost:8080/Project1/updateReq";
	let requestBody = `rId=${requestId}&mId=${tokenArr[0]}&status=${false}`;
	sendPost(reqUrl,requestBody,reload);
}
function approveRequest(requestId){
	let reqUrl = "http://localhost:8080/Project1/updateReq";
	let requestBody = `rId=${requestId}&mId=${tokenArr[0]}&status=${true}`;
	sendPost(reqUrl,requestBody,reload);
}
function reload(){
	location.reload();
}
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


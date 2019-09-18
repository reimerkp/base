let token = sessionStorage.getItem("token");

let tokenArr;
if (!token) {
	localStorage.clear();
	window.location.href = "http://localhost:8080/Project1/login";
} else {
	tokenArr = token.split(":");
	console.log(tokenArr);
	if (tokenArr.length === 3 || tokenArr.length === 4) {
		let baseUrl = "http://localhost:8080/Project1/api/employees?";
		sendAjaxGet(baseUrl + "id=" + tokenArr[0], displayName);
	} else {
		window.location.href = "http://localhost:8080/Project1/login";
	}
}
let pic = document.getElementById("profImg");
function displayName(xhr) {
	let employee = JSON.parse(xhr.response);
	document.getElementById("emp-name").innerHTML = employee.fName + " "
			+ employee.lName;

	let imgUrl = "http://localhost:8080/Project1/api/img?";
	let profPic = window.localStorage.getItem("profPic");
	if (profPic == null) {
		if (tokenArr[2] == 0) {
			localStorage
					.setItem("profPic",
							"https://www.sackettwaconia.com/wp-content/uploads/default-profile.png");
			pic.src = localStorage.getItem("profPic");
		} else {
			sendAjaxGet(
					imgUrl + "id=" + tokenArr[2] + "&userId=" + tokenArr[0],
					setImgStorage);
			savePic();
		}
	} else {
		let pic = document.getElementById("profImg");
		pic.src = profPic;
	}
	getTables(employee);
}

function setImgStorage(xhr) {
	console.log("in setImgStorage");
	let image = JSON.parse(xhr.response);
	setPic(image.data);
}
function setPic(data) {
	pic.src = "data:image/png;base64," + data;

}

function getTables(employee) {
	requestUrl = "http://localhost:8080/Project1/api/requests?eId="
			+ tokenArr[0];
	sendAjaxGet(requestUrl, buildTables);
}
function buildTables(xhr) {
	let requests = JSON.parse(xhr.response);
	let pendRequests = [];
	let approvedRequests = [];
	for (let x = 0; x < requests.length; x++) {
		if (requests[x].mID == -1) {
			pendRequests.push(requests[x]);
		} else {
			approvedRequests.push(requests[x]);
		}
	}
	buildPending(pendRequests);
	buildApproved(approvedRequests);
}

function buildApproved(approvedRequests) {
	console.log(approvedRequests);
	let approvedTable = document.getElementById("resolvedR");
	let tableBody = document.createElement('tbody');
	for (let i = 0; i < approvedRequests.length; i++) {
		let row = document.createElement("tr");
		let col1 = document.createElement("td");
		let col2 = document.createElement("td");
		let col3 = document.createElement("td");
		let col4 = document.createElement("td");
		let col5 = document.createElement("td");
		col1.appendChild(document.createTextNode(approvedRequests[i].rID));
		col2.appendChild(document.createTextNode(approvedRequests[i].reason));
		col3.appendChild(document.createTextNode(approvedRequests[i].amount));
		col4.appendChild(document.createTextNode(approvedRequests[i].mID));
		if (approvedRequests[i].approved) {
			col5.appendChild(document.createTextNode("Approved"));
		} else {
			col5.appendChild(document.createTextNode("Denied"));
		}

		row.appendChild(col1);
		row.appendChild(col2);
		row.appendChild(col3);
		row.appendChild(col4);
		row.appendChild(col5);
		tableBody.appendChild(row);
	}
	approvedTable.appendChild(tableBody);
}
function buildPending(pendRequests) {
	let pendingTable = document.getElementById("pendingR");
	let tableBody = document.createElement('tbody');
	for (let i = 0; i < pendRequests.length; i++) {
		let row = document.createElement("tr");
		let col1 = document.createElement("td");
		let col2 = document.createElement("td");
		let col3 = document.createElement("td");
		col1.appendChild(document.createTextNode(pendRequests[i].rID));
		col2.appendChild(document.createTextNode(pendRequests[i].reason));
		col3.appendChild(document.createTextNode(pendRequests[i].amount));
		row.appendChild(col1);
		row.appendChild(col2);
		row.appendChild(col3);
		tableBody.appendChild(row);
	}
	pendingTable.appendChild(tableBody);
}
function savePic() {
	console.log("In savePic")
	let pic = document.getElementById("profImg");

	// Take action when the image has loaded
	pic
			.addEventListener(
					"load",
					function() {
						var imgCanvas = document.createElement("canvas"), imgContext = imgCanvas
								.getContext("2d");

						// Make sure canvas is as big as the picture
						imgCanvas.width = pic.width;
						imgCanvas.height = pic.height;

						// Draw image into canvas element
						imgContext.drawImage(pic, 0, 0, pic.width, pic.height);

						// Get canvas contents as a data URL
						var imgAsDataURL = imgCanvas.toDataURL("image/png");

						// Save image into localStorage
						try {
							localStorage.setItem("profPic", imgAsDataURL);
						} catch (e) {
							console.log("Storage failed: " + e);
						}
					}, false);

}

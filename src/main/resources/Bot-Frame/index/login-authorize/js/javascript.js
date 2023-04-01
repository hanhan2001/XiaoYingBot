var websocket = new WebSocket("ws://localhost:6501");
websocket.onopen = function() {
	websocket.send("connected");
}
websocket.onmessage = function(e) {
	console.log(e);
	with (e) {
		if (e.data.startsWith("url ")) {
			setUrl(e.data.replace("url ", ""));
		}
	}
}

websocket.onclose = function(e) {
　　websocket = new WebSocket("ws://localhost:6501");
}

websocket.onerror = function(e) {
　　console.log(error);
}

function sendMessage(text) {
	websocket.send(text);
}

function confirmed() {
	var ticket = document.querySelector(".content .display .input input").value;
	var message = "confirm " + ticket;
	sendMessage(message);
}

function setUrl(url) {
	document.querySelector(".content .display .show input").value = url;
}
var chatClass = ".content .content_chat .content_chat_box_chat";
var titleClass = ".content .content_chat .content_chat_box_title";

var chatEntity = document.querySelector(chatClass);
var titleEntity = document.querySelector(titleClass);

window.onload = function() {
	chatEntity = document.querySelector(chatClass);
	titleEntity = document.querySelector(titleClass);


　　$(document).keyup(function(event){ 
　　　　if (event.keyCode === 13){
			sendMessage();
　　　　} 
　　}); 
	// window.addEventListener("resize", function() {
	// 	let displayBox = document.querySelector(".content .content_chat .content_chat_box .content_chat_box_chat .display");
	// 	displayBox.scrollTop = displayBox.scrollHeight;
	// });
}

function showChat() {
	chatEntity.classList.contains("chat_on") ? chatEntity.classList.remove("chat_on") : chatEntity.classList.add("chat_on");
}

function sendMessage() {
	let content = document.querySelector(".content .content_chat .content_chat_box .content_chat_box_chat .chat input");
	let chatbox = "<div class='self'><p>%text%</p><div class='self_img'></div></div>".replace("%text%", content.value);
	
	content.value = null;
	let displayBox = document.querySelector(".content .content_chat .content_chat_box .content_chat_box_chat .display");
	displayBox.insertAdjacentHTML("beforeend", chatbox);
	let second = 0.2;
	let distance = displayBox.scrollHeight - displayBox.scrollTop;
	displayBox.scrollTop = displayBox.scrollHeight;
}
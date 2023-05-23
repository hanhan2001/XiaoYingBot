var chatClass = ".content .content_chat .content_chat_box_chat";
var titleClass = ".content .content_chat .content_chat_box_title";

var chatEntity = document.querySelector(chatClass);
var titleEntity = document.querySelector(titleClass);

var chatboxType = "into";

window.onload = function() {
	chatEntity = document.querySelector(chatClass);
	titleEntity = document.querySelector(titleClass);
}

// function showChat() {
// 	chatEntity.classList.add("chat_on");
// }
// function showTitle() {
// }

// // 用户界面
// function setChatBox(user, type, display) {

// }

function showChat() {
	chatEntity.classList.contains("chat_on") ? chatEntity.classList.remove("chat_on") : chatEntity.classList.add("chat_on");
}
// // 用户界面
// function setChatBox(user, type, display) {

// }

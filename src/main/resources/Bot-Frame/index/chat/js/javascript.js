var chatClass = ".content .content_chat .content_chat_box_chat";
var titleClass = ".content .content_chat .content_chat_box_title";

var chatEntity = document.querySelector(chatClass);
var titleEntity = document.querySelector(titleClass);

var chatboxType = "into";

window.onload = function() {
	chatEntity = document.querySelector(chatClass);
	titleEntity = document.querySelector(titleClass);

	// ChatBox事件
	chatEntity.addEventListener("animationend", () => {
		// 当元素为进入时
		if (chatEntity.style.display == "block" && chatboxType == "into") {
			return;
		}
		titleEntity.style.display = "flex";
		chatEntity.style.display = "none";
	});
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
	chatboxType = "into";
	chatEntity.style.animationDirection = "normal";
	chatEntity.style.animationIterationCount = 1;

	chatEntity.style.display = "block";
}
function showTitle() {
	chatboxType = "out";
	chatEntity.style.animationDirection = "reverse";
	chatEntity.style.animationIterationCount = 3;
}

// // 用户界面
// function setChatBox(user, type, display) {

// }
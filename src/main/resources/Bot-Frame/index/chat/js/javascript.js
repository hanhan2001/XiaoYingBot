let openBox = null;
let openUser = null;
let openType = null;

let users = new Array();
let groups = new Array();

window.onload = function() {
　　$(document).keyup(function(event){
		if (event.keyCode != 13 || openBox == null) {
			return;
		}
		if (openType == "user")
			sendMessage(openUser);
		else
			sendGroupMessage(openUser);
　　});
}

/**
 * 显示聊天界面
 * 
 * @param id 联系人id
 * */
function showChat(id) {
	if (!users.includes(id))
		throw new Error("Cannt find this user");

	if (openBox != null && openUser != id)
		closeChat();

	let userChatBox = document.querySelector(".content .content_chat .content_chat_box .user_" + id);
	userChatBox.classList.add("chat_on");
	openUser = id;
	openBox = userChatBox;
	openType = "user";
	document.querySelector(".content .content_chat .content_chat_title .content_chat_title_display").innerHTML = document.querySelector(".content .content_list .content_list_list .user_" + id + " .username").textContent;
}

/**
 * 显示聊天界面
 * 
 * @param id 联系人id
 * */
function showGroupChat(id) {
	if (!groups.includes(id))
		throw new Error("Cannt find this group");

	if (openBox != null && openUser != id)
		closeChat();

	let userChatBox = document.querySelector(".content .content_chat .content_chat_box .group_" + id);
	userChatBox.classList.add("chat_on");
	openUser = id;
	openBox = userChatBox;
	openType = "group";
	document.querySelector(".content .content_chat .content_chat_title .content_chat_title_display").innerHTML = document.querySelector(".content .content_list .content_list_list .group_" + id + " .username").textContent;
}

/**
 * 关闭聊天界面
 * */
function closeChat() {
	if (openBox == null)
		return;

	openBox.classList.remove("chat_on");
	document.querySelector(".content .content_chat .content_chat_title .content_chat_title_display").innerHTML = "主界面"

	openBox = null;
	openUser = null;
	openType = null;
}

/**
 * 创建联系人
 * 
 * @param name 名称
 * @param id 联系人id
 * */
function newGroupChat(name, id) {
	if (groups.includes(id))
		throw new Error("Already has this group, please change id value");

	groups.push(id);

	// 用户列表
	let userList = document.querySelector(".content .content_list .content_list_list");
	let time = new Date();
	let minutes = time.getMinutes();
	if (minutes.length == 1)
		minutes = "0" + minutes;
	let userEntity =
		"<div class='content_list_list_user group_" + id + "' onclick='showGroupChat(" + id + ")'>" +
			"<div class='content_list_list_user_picture' style='background-image: url(https://p.qlogo.cn/gh/" + id + "/640/); background-size: cover;'></div>" +
			"<p class='username'>" + name + "</p>" +
			"<p class='usercontent'></p>" +
			"<p class='usertime'>" + time.getHours() + ":" + minutes  + "</p>" +
		"</div>";

	userList.insertAdjacentHTML("beforeend", userEntity);

	// 聊天框
	let chatBox = document.querySelector(".content .content_chat .content_chat_box");
	let chatEntity = 
		"<div class='content_chat_box_chat group_" + id + "'>" + 
			"<div class='display'></div>" +
			"<div class='chat'>" +
				"<div></div>" +
				"<input type='' name='' placeholder='输入内容'>" + 
				"<div onclick='sendMessage(" + id + ")'>发送</div>" +
			"</div>"+
		"</div>";

	chatBox.insertAdjacentHTML("beforeend", chatEntity);
}

/**
 * 创建联系人
 * 
 * @param name 名称
 * @param id 联系人id
 * */
function newChat(name, id) {
	if (users.includes(id))
		throw new Error("Already has this user, please change id value");

	users.push(id);

	// 用户列表
	let userList = document.querySelector(".content .content_list .content_list_list");
	let time = new Date();
	let minutes = time.getMinutes();
	if (minutes.length == 1)
		minutes = "0" + minutes;
	let userEntity =
		"<div class='content_list_list_user user_" + id + "' onclick='showChat(" + id + ")'>" +
			"<div class='content_list_list_user_picture' style='background-image: url(https://q.qlogo.cn/g?b=qq&nk=" + id + "&s=100); background-size: cover;'></div>" +
			"<p class='username'>" + name + "</p>" +
			"<p class='usercontent'></p>" +
			"<p class='usertime'>" + time.getHours() + ":" + minutes  + "</p>" +
		"</div>";

	userList.insertAdjacentHTML("beforeend", userEntity);

	// 聊天框
	let chatBox = document.querySelector(".content .content_chat .content_chat_box");
	let chatEntity = 
		"<div class='content_chat_box_chat user_" + id + "'>" + 
			"<div class='display'></div>" +
			"<div class='chat'>" +
				"<div></div>" +
				"<input type='' name='' placeholder='输入内容'>" + 
				"<div onclick='sendMessage(" + id + ")'>发送</div>" +
			"</div>"+
		"</div>";

	chatBox.insertAdjacentHTML("beforeend", chatEntity);
}

/** 移除联系人
 * 
 * @param id 联系人id
 * */
function removeChat(id) {
	if (!users.includes(id))
		throw new Error("Cannt find this user");

	// 用户列表
	let userList = document.querySelector(".content .content_list .content_list_list");
	userList.removeChild(userList.querySelector(".user_" + id));

	// 聊天框
	let chatBox = document.querySelector(".content .content_chat .content_chat_box");
	chatBox.removeChild(chatBox.querySelector(".user_" + id));

	removeArrayListValue(users, id);
}

/** 移除联系人
 * 
 * @param id 联系人id
 * */
function removeGroupChat(id) {
	if (!users.includes(id))
		throw new Error("Cannt find this group");

	// 用户列表
	let userList = document.querySelector(".content .content_list .content_list_list");
	userList.removeChild(userList.querySelector(".group_" + id));

	// 聊天框
	let chatBox = document.querySelector(".content .content_chat .content_chat_box");
	chatBox.removeChild(chatBox.querySelector(".group_" + id));

	removeArrayListValue(groups, id);
}

/** 
 * 发送消息
 * 
 * @param id 联系人id
 * @param message 内容
 * */
function sendMessage(id) {
	if (!users.includes(id))
		throw new Error("Cannt find this user");

	let time = new Date();

	let content = document.querySelector(".content .content_chat .content_chat_box .user_" + id + " .chat input");
	let chatbox = 
		"<div class='self'>" +
			"<p>" + content.value + "</p>" +
			"<div class='self_img'></div>" +
		"</div>";

	let displayBox = document.querySelector(".content .content_chat .content_chat_box .user_" + id + " .display");
	displayBox.insertAdjacentHTML("beforeend", chatbox);
	displayBox.scrollTop = displayBox.scrollHeight;

	document.querySelector(".content .content_list .content_list_list .user_" + id + " .usertime").textContent = time.getHours() + ":" + time.getMinutes();
	document.querySelector(".content .content_list .content_list_list .user_" + id + " .usercontent").textContent = content.value;
	content.value = null;
}

/** 
 * 发送消息
 * 
 * @param id 联系人id
 * @param message 内容
 * */
function sendGroupMessage(id) {
	if (!groups.includes(id))
		throw new Error("Cannt find this user");

	let time = new Date();

	let content = document.querySelector(".content .content_chat .content_chat_box .group_" + id + " .chat input");
	let chatbox = 
		"<div class='self'>" +
			"<p>" + content.value + "</p>" +
			"<div class='self_img'></div>" +
		"</div>";

	let displayBox = document.querySelector(".content .content_chat .content_chat_box .group_" + id + " .display");
	displayBox.insertAdjacentHTML("beforeend", chatbox);
	displayBox.scrollTop = displayBox.scrollHeight;

	document.querySelector(".content .content_list .content_list_list .group_" + id + " .usertime").textContent = time.getHours() + ":" + time.getMinutes();
	document.querySelector(".content .content_list .content_list_list .group_" + id + " .usercontent").textContent = content.value;
	content.value = null;
}

/** 
 * 获取消息
 * 
 * @param id 联系人id
 * @param message 内容
 * */
function getMessage(id, message) {
	if (!users.includes(id))
		throw new Error("Cannt find this user");

	let time = new Date();
	let chatbox = 
		"<div class='other'>" +
			"<div class='other_img' style='background-image: url(https://q.qlogo.cn/g?b=qq&nk=" + id + "&s=100); background-size: cover;'></div>" +
			"<p>" + message + "</p>" +
		"</div>";
	message = message.replace("<br>", "");
	let displayBox = document.querySelector(".content .content_chat .content_chat_box .user_" + id + " .display");
	displayBox.insertAdjacentHTML("beforeend", chatbox);
	displayBox.scrollTop = displayBox.scrollHeight;

	document.querySelector(".content .content_list .content_list_list .user_" + id + " .usercontent").textContent = message.replaceAll("<br>", "");
	document.querySelector(".content .content_list .content_list_list .user_" + id + " .usertime").textContent = time.getHours() + ":" + time.getMinutes();
}

/** 
 * 获取消息
 * 
 * @param id 联系人id
 * @param message 内容
 * */
function getGroupMessage(id, user, username, message) {
	if (!groups.includes(id))
		throw new Error("Cannt find this user");

	let time = new Date();
	let chatbox = 
		"<div class='other'>" +
			"<div class='other_img' style='background-image: url(https://q.qlogo.cn/g?b=qq&nk=" + user + "&s=100); background-size: cover;'></div>" +
			"<lable class='name'>" + username + "</lable>" +
			"<p>" + message + "</p>" +
		"</div>";
	message = message.replace("<br>", "");
	let displayBox = document.querySelector(".content .content_chat .content_chat_box .group_" + id + " .display");
	displayBox.insertAdjacentHTML("beforeend", chatbox);
	displayBox.scrollTop = displayBox.scrollHeight;

	document.querySelector(".content .content_list .content_list_list .group_" + id + " .usercontent").textContent = username + ":" + message.replaceAll("<br>", "");
	document.querySelector(".content .content_list .content_list_list .group_" + id + " .usertime").textContent = time.getHours() + ":" + time.getMinutes();
}

function accept() {
	document.querySelector(".jump_title").style.display = "none";
}

function disaccept() {
	window.close();
}

/**
 * 根据值移除 ArrayList 中的元素
 * 
 * @param list 列表
 * @param value 值
 * */
function removeArrayListValue(list, value) {
	if (!users.includes(id))
		throw new Error("Cannt find this value on this list");

	for (let i = 0; i < list.length; i++) {
		if (list[i] != value)
			continue;

		list.splice(i, 1);
	}
}
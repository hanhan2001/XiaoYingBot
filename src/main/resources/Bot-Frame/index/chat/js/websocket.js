var ws = new WebSocket("ws://localhost:6501"); 

ws.onopen = function(){
}
ws.onmessage = function(e){
　　//当客户端收到服务端发来的消息时，触发onmessage事件，参数e.data包含server传递过来的数据
   console.log(e.data);

   let json;
   try {
      json = JSON.parse(e.data);
   } catch (e) {
      return;
   }

   let type = json.type;
   if (type == "Self") {
      let id = json.id;
      let name = json.name;
      let image = json.selfImage;
      setSelfImage(image);
   }
   if (type == "FriendMessage") {
      let message = json.message;
      let user = Number(json.user);
      let username = json.userName;
      if (!users.includes(user))
         newChat(username, user);
      getMessage(user, message);
   }
   if (type == "GroupMessage") {
      let group = Number(json.group);
      let groupname = json.groupName;
      let user = json.user;
      let username = json.userName;
      let message = json.message;
      if (!groups.includes(group))
         newGroupChat(groupname, group);
      getGroupMessage(group, user, username, message);
   }
}

ws.onclose = function(e){
　　console.log("close");
}

ws.onerror = function(e){
　　console.log(error);
}
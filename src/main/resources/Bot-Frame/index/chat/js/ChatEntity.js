class ChatEntity {
	constructor(type, id, message) {
		this.type = type;
		this.id = id;
		this.message = message;
	}

	getMessage() {
		return "{" + 
			"\"type\": \"" + this.type + "\"," +
			"\"id\": \"" + this.id + "\"," +
			"\"message\": \"" + this.message + "\"" +
			"}";
	}
}
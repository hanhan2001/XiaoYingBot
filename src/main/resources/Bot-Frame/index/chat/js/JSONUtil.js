function isJson(message) {
	JSON.parse(message);
	try {
		if (typeof JSON.parse("\"" + message + "\"") == "Object")
			return true; 
	} catch (e) {
		return false;
	}
}
package me.xiaoying.bot.plugin;

public class IllegalCommandException extends Throwable {
    private static final long serialVersionUID = -8242141640709409545L;

    public IllegalCommandException(Throwable cause) {
        super(cause);
    }

    public IllegalCommandException() {
    }

    public IllegalCommandException(String message, Throwable cause) {
        super(message, cause);
    }

    public IllegalCommandException(String message) {
        super(message);
    }
}
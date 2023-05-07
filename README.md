# XiaoYingBot
A little testability framework. This is my pratice framework, I wanna get more program thinking.

I use for reference to Bukkit(Minecraft plugin framework), so this framework look like Bukkit.

This framework's working principle:

```mermaid
graph LR
	first[Start-Up Springboot] --> second{start}
	second{start} --> Server[BotServer]
	second{start} --> BotLogin
	BotLogin --> LoginBot{LoginBot} --> BOT
	Server[BotServer] --> init{init}
	init{init} --> PluginManager
	init{init} --> PluginCommand
	PluginManager --> loadPlugin{loadPlugin} --> Plugin
	first[Start-Up Springboot] --> stop{stop}
```


# b4dis
[JDA](https://github.com/DV8FromTheWorld/JDA) [Brigadier](https://github.com/Mojang/brigadier) Parser Plugin.

```
We want to thank the Concordia University Part-time Faculty Association for the Professional Development Grant that allows us to develop/contribute to the open source community. 
```

Following is the typical ping pong example for the Discord API. Command registration works essentially the same way as on Minecraft Forge (which is why this package was developed).

## Ping Pong Example

```java
DiscordBrigadier discordBrigadier = new DiscordBrigadier();

discordBrigadier.register(
    DiscordBrigadier
        .literal("ping")
        .executes(context -> {
            context
                .getSource()
                .getTextChannel()
                .sendMessage("pong")
                .queue();
            return 1;
            }
        )
    );

try {
    JDA jda = JDABuilder.createDefault("Discord_Bot_Token_Here").build();

    jda.addEventListener(discordBrigadier);

    jda.awaitReady();
} catch(Exception e) {}
```
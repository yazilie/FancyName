![FancyName Header](https://cdn.modrinth.com/data/alaQKApY/images/3b03b1d69b52b4a072242e7434a60c44a8e78b2b.png)
---
Nick yourself—or stylize your existing username. Everyone using the mod will be able to see your nickname on any server.

## Usage
In-game, type `/fancy set <nickname>`

It is recommended to keep your original username text the same, as some people have the 'Style Changes Only' option enabled.

## Styling
FancyName uses [MiniMessage](https://docs.papermc.io/adventure/minimessage/) for styling. Full MiniMessage format options can be found [here](https://docs.papermc.io/adventure/minimessage/format/).

It may be more convient for you to use a GUI to create MiniMessage styles. Here are a few MiniMessage GUI options:
- [Birdflop](https://www.birdflop.com/resources/rgb/) - Generate gradient names with ease. Make sure to set 'Color Format' to 'MiniMessage'
- [MiniMessage Web Editor](https://webui.advntr.dev/) - Full MiniMessage editor.

## Examples
```/fancy set <gradient:#FF76C1:#FFFFFF>yazilie</gradient>```

```/fancy set <b><red>NOT</red></b> <gradient:red:white>yazilie</gradient>```

![FancyName Demo](https://cdn.modrinth.com/data/alaQKApY/images/53b5acd847a9cf26fa1888062cc451f1ca613ca8.png)

## Config Options
- Enable/Disable the mod
- Only show nicknames that do not change the username, while allowing custom styles
- Disable nicknames in the player list
- Disable nicknames in game

Access the config via [Mod Menu](https://modrinth.com/mod/modmenu), or turn it on/off in-game with `/fancy <on|off>`

## Dependencies
FancyName requires [Fabric API](https://modrinth.com/mod/fabric-api), [adventure-platform-mod](https://modrinth.com/mod/adventure-platform-mod), [YetAnotherConfigLib (YACL)](https://modrinth.com/mod/yacl) and [Fabric Language Kotlin](https://modrinth.com/mod/fabric-language-kotlin).
[Mod Menu](https://modrinth.com/mod/modmenu) is optionally required for accessing the config screen.

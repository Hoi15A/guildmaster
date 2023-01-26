import command.*
import dev.kord.core.Kord
import dev.kord.core.event.gateway.ReadyEvent
import dev.kord.core.event.interaction.GuildChatInputCommandInteractionCreateEvent
import dev.kord.core.on

suspend fun main() {
    val kord = Kord(System.getenv("DISCORD_TOKEN"))
    val guildCommands = mutableListOf(
        Echo(),
        Info(),
        Splatoon()
    )

    for (gc in guildCommands) {
        println("Registering guild command: ${gc.name}")
        gc.registerCommand(kord)
    }

    kord.on<GuildChatInputCommandInteractionCreateEvent> {
        for (gc in guildCommands) {
            if (interaction.command.rootName == gc.name) {
                gc.event(interaction)
                return@on
            }
        }
    }

    kord.on<ReadyEvent> {
        println("\nLogged in with ${self.tag}")
    }

    kord.login()
}

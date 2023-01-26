package command

import dev.kord.core.Kord
import dev.kord.core.behavior.interaction.response.respond
import dev.kord.core.entity.interaction.GuildChatInputCommandInteraction
import dev.kord.rest.builder.interaction.string

class Echo : GuildCommand {
    override val name = "echo"
    override val activeGuilds = listOf(
        Guilds.ORIGIN_MASTER.snowflake,
        Guilds.TBD.snowflake
    )

    override suspend fun registerCommand(kord: Kord) {
        for (snowflake in activeGuilds) {
            kord.createGuildChatInputCommand(
                snowflake,
                name,
                "Return what was sent"
            ) {
                string("text", "Text to be sent") {
                    required = true
                }
            }
        }
    }

    override suspend fun event(interaction: GuildChatInputCommandInteraction) {
        val response = interaction.deferPublicResponse()
        val command = interaction.command

        val text = command.strings["text"]

        response.respond {
            content = text
        }
    }
}

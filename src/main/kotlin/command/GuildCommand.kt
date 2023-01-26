package command

import dev.kord.common.entity.Snowflake
import dev.kord.core.Kord
import dev.kord.core.entity.interaction.GuildChatInputCommandInteraction

interface GuildCommand {
    val name: String
    val activeGuilds: List<Snowflake>
    suspend fun registerCommand(kord: Kord)
    suspend fun event(interaction: GuildChatInputCommandInteraction)
}

package command

import dev.kord.common.Color
import dev.kord.common.DiscordTimestampStyle
import dev.kord.common.toMessageFormat
import dev.kord.core.Kord
import dev.kord.core.behavior.interaction.response.respond
import dev.kord.core.entity.interaction.GuildChatInputCommandInteraction
import dev.kord.rest.builder.message.modify.embed
import dev.kord.x.emoji.Emojis
import kotlinx.coroutines.flow.count

class Info : GuildCommand {
    override val name = "info"
    override val activeGuilds = listOf(
        Guilds.ORIGIN_MASTER.snowflake,
        Guilds.TBD.snowflake
    )

    override suspend fun registerCommand(kord: Kord) {
        for (snowflake in activeGuilds) {
            kord.createGuildChatInputCommand(
                snowflake,
                name,
                "Get general bot, guild and user info"
            )
        }
    }

    override suspend fun event(interaction: GuildChatInputCommandInteraction) {
        val response = interaction.deferPublicResponse()

        response.respond {
            embed {
                val self = interaction.kord.getSelf()
                val guild = interaction.guild.asGuild()
                color = Color(248, 152, 192) // wigglytuff pink
                author {
                    name = self.username
                    icon = self.avatar?.url ?: self.defaultAvatar.url
                }
                field {
                    name = "${Emojis.robot} Bot info"
                    value = "**User:** ${self.mention}\n" +
                            "**Created:** ${self.id.timestamp.toMessageFormat(DiscordTimestampStyle.RelativeTime)}\n" +
                            "**Guild count:** ${interaction.kord.guilds.count()}"
                    inline = true
                }
                field {
                    val member = interaction.user
                    name = "${Emojis.smiley} You"
                    value = "**User:** ${member.mention}\n" +
                            "**Joined Discord:** ${member.id.timestamp.toMessageFormat(DiscordTimestampStyle.RelativeTime)}\n" +
                            "**Joined ${guild.name}:** ${member.joinedAt.toMessageFormat(DiscordTimestampStyle.RelativeTime)}"
                    inline = true
                }
                field {
                    name = "${Emojis.houseBuildings} Guild info"
                    value = "**Name:** ${guild.name}\n" +
                            "**Created:** ${guild.id.timestamp.toMessageFormat(DiscordTimestampStyle.RelativeTime)}\n" +
                            "**Member count:** ${guild.memberCount}\n" +
                            "**Owner:** ${guild.owner.mention}"
                }
            }
        }
    }
}

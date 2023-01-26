package command

import dev.kord.common.Color
import dev.kord.common.DiscordTimestampStyle
import dev.kord.common.toMessageFormat
import dev.kord.core.Kord
import dev.kord.core.behavior.interaction.response.DeferredPublicMessageInteractionResponseBehavior
import dev.kord.core.behavior.interaction.response.respond
import dev.kord.core.entity.interaction.GuildChatInputCommandInteraction
import dev.kord.core.entity.interaction.SubCommand
import dev.kord.rest.builder.interaction.string
import dev.kord.rest.builder.interaction.subCommand
import dev.kord.rest.builder.message.modify.embed
import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import util.splatoon.Splatoon3

class Splatoon : GuildCommand {
    enum class GameModes(val displayName: String, val color: Color, val icon: String) {
        TURF_WAR("Turf War", Color(209, 213, 40), "https://gamepedia.cursecdn.com/splatoon_gamepedia/thumb/a/a2/Turf-wars-icon.png/350px-Turf-wars-icon.png"),
        ANARCHY_OPEN("Anarchy Battles (Open)", Color(0, 0, 0), "https://gamepedia.cursecdn.com/splatoon_gamepedia/f/f2/Ranked-battle-icon.png"),
        ANARCHY_SERIES("Anarchy Battles (Series)", Color(0, 0, 0), "https://gamepedia.cursecdn.com/splatoon_gamepedia/f/f2/Ranked-battle-icon.png"),
        X_BATTLE("X Battles", Color(0, 0, 0), "https://i.imgur.com/uSxa2hz.png")
    }

    override val name = "splatoon"
    override val activeGuilds = listOf(
        Guilds.ORIGIN_MASTER.snowflake,
        Guilds.TBD.snowflake,
        Guilds.SWITCH_VIBES.snowflake
    )

    override suspend fun registerCommand(kord: Kord) {
        for (snowflake in activeGuilds) {
            kord.createGuildChatInputCommand(
                snowflake,
                name,
                "Splatoon related commands"
            ) {
                subCommand("schedule", "Get a schedule for a gamemode") {
                    string("mode", "A splatoon gamemode") {
                        for (mode in GameModes.values()) {
                            choice(mode.displayName, mode.name)
                        }
                    }
                }
            }
        }
    }


    override suspend fun event(interaction: GuildChatInputCommandInteraction) {
        val response = interaction.deferPublicResponse()

        when((interaction.command as SubCommand).name) {
            "schedule" -> schedule(interaction, response)
            else -> throw IllegalArgumentException("Splatoon command called without subcommand. Should never happen lol.")
        }
    }

    private val splat = Splatoon3()
    private suspend fun schedule(
        interaction: GuildChatInputCommandInteraction,
        response: DeferredPublicMessageInteractionResponseBehavior
    ) {
        val schedules = splat.getSchedules()

        response.respond {
            when(GameModes.values().first { it.name == interaction.command.strings["mode"] }) {
                GameModes.TURF_WAR -> embed {
                    val upcoming = schedules.data.regularSchedules.nodes.take(3)

                    field {
                        name = "Now | ${Instant.parse(upcoming[0].startTime).toMessageFormat(DiscordTimestampStyle.ShortTime)} - ${Instant.parse(upcoming[0].endTime).toMessageFormat(DiscordTimestampStyle.ShortTime)}"
                        value = upcoming[0].regularMatchSetting.vsStages.joinToString(", ") { it.name }
                    }

                    field {
                        name = "Up Next | ${Instant.parse(upcoming[1].startTime).toMessageFormat(DiscordTimestampStyle.ShortTime)} - ${Instant.parse(upcoming[1].endTime).toMessageFormat(DiscordTimestampStyle.ShortTime)}"
                        value = upcoming[1].regularMatchSetting.vsStages.joinToString(", ") { it.name }
                    }

                    field {
                        name = "Soon | ${Instant.parse(upcoming[2].startTime).toMessageFormat(DiscordTimestampStyle.ShortTime)} - ${Instant.parse(upcoming[2].endTime).toMessageFormat(DiscordTimestampStyle.ShortTime)}"
                        value = upcoming[2].regularMatchSetting.vsStages.joinToString(", ") { it.name }
                    }

                    title = GameModes.TURF_WAR.displayName
                    description = "The following maps are up next"
                    color = GameModes.TURF_WAR.color
                    val randStage = upcoming[0].regularMatchSetting.vsStages.random()
                    image = randStage.image.url
                    footer {
                        text = "Image: ${randStage.name}"
                    }
                    timestamp = Clock.System.now()
                    thumbnail {
                        url = GameModes.TURF_WAR.icon
                    }
                }
                GameModes.ANARCHY_OPEN -> TODO()
                GameModes.ANARCHY_SERIES -> TODO()
                GameModes.X_BATTLE -> TODO()
            }
        }
    }
}

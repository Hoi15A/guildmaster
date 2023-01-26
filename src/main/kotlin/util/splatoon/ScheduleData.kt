package util.splatoon

import kotlinx.serialization.Serializable

/* GENERIC */

@Serializable
data class Schedules(val data: ScheduleData)

@Serializable
data class ScheduleData(val regularSchedules: TurfSchedule, val bankaraSchedules: AnarchySchedule, val xSchedules: XSchedule, val coopGroupingSchedule: SalmonSchedule)

@Serializable
data class Image(val url: String)

@Serializable
data class VSStage(val name: String, val image: Image)

@Serializable
data class VSRule(val name: String, val rule: String)

@Serializable
data class MatchSettings(val vsStages: List<VSStage>, val vsRule: VSRule)

/* TURF WARS */

@Serializable
data class TurfSchedule(val nodes: List<TurfScheduleData>)

@Serializable
data class TurfScheduleData(val startTime: String, val endTime: String, val regularMatchSetting: MatchSettings)

/* ANARCHY BATTLES */

@Serializable
data class AnarchySchedule(val nodes: List<AnarchyScheduleData>)

@Serializable
data class AnarchyScheduleData(val startTime: String, val endTime: String, val bankaraMatchSettings: List<AnarchyMatchSettings>)

@Serializable
data class AnarchyMatchSettings(val vsStages: List<VSStage>, val vsRule: VSRule, val mode: String)


/* X BATTLES */

@Serializable
data class XSchedule(val nodes: List<XScheduleData>)

@Serializable
data class XScheduleData(val startTime: String, val endTime: String, val xMatchSetting: MatchSettings)


/* SALMON RUN */

@Serializable
data class SalmonSchedule(val regularSchedules: SalmonRegularSchedules, val bigRunSchedules: SalmonBigRunSchedules)

@Serializable
data class SalmonRegularSchedules(val nodes: List<SalmonRegularScheduleData>)

@Serializable
data class SalmonRegularScheduleData(val startTime: String, val endTime: String, val setting: SalmonSetting)

@Serializable
data class SalmonSetting(val coopStage: CoopStage, val weapons: List<Weapon>)

@Serializable
data class CoopStage(val name: String, val image: Image, val thumbnailImage: Image)

@Serializable
data class Weapon(val name: String, val image: Image)

// Complete guess, did not have example data
@Serializable
data class SalmonBigRunSchedules(val nodes: List<SalmonRegularScheduleData>)

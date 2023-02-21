package util.splatoon

import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.engine.cio.*
import io.ktor.client.plugins.*
import io.ktor.client.plugins.cache.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.json.Json

val client = HttpClient(CIO) {
    install(UserAgent) {
        agent = "Ktor http-client/Guildmaster Discord Bot - Austin#8741"
    }
    install(HttpCache)
    install(ContentNegotiation) {
        json(Json {
            ignoreUnknownKeys = true
        })
    }
    defaultRequest {
        host = "splatoon3.ink"
        url {
            protocol = URLProtocol.HTTPS
        }
    }
}

class Splatoon3 {
    fun getSchedules(): Schedules? {
        var data: Schedules? = null
        runBlocking {
            val resp = client.get("data/schedules.json")
            try {
                data = resp.body()
            } catch (e: Exception) {
                println(e)
            }
        }
        return data
    }
}

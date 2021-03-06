package jp.ne.naokiur.api

import com.google.gson.GsonBuilder
import io.ktor.application.Application
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpMethod
import io.ktor.http.HttpStatusCode
import io.ktor.server.testing.handleRequest
import io.ktor.server.testing.setBody
import io.ktor.server.testing.withTestApplication
import jp.ne.naokiur.api.domain.ApiRes
import jp.ne.naokiur.domain.infra.user.UserRepository
import jp.ne.naokiur.domain.models.users.FullName
import jp.ne.naokiur.domain.models.users.InMemoryUserFactory
import jp.ne.naokiur.domain.models.users.UserName
import kotlin.test.Test
import kotlin.test.assertEquals

class ExecutorTest {
    private val repository = UserRepository()
    private val factory = InMemoryUserFactory()

    @Test
    fun testHello() = withTestApplication(Application::api) {
        handleRequest(HttpMethod.Get, "/").run {
            assertEquals(HttpStatusCode.OK, response.status())
            assertEquals("Hello, world!", response.content)
        }
    }

    @Test
    fun testShow() = withTestApplication(Application::api) {
        val gson = GsonBuilder().setPrettyPrinting().create()

//        handleRequest(HttpMethod.Get, "/show").run {
//            val expectedContent = gson.toJson(repository.findAll())
//
//            assertEquals(HttpStatusCode.OK, response.status())
//            assertEquals(expectedContent, response.content)
//        }
    }

    @Test
    fun testCreate() = withTestApplication(Application::api) {

        val gson = GsonBuilder().setPrettyPrinting().create()
        val param = factory.createUser(UserName("test"), FullName("test", "hoge"))

        handleRequest(HttpMethod.Post, "/create") {

            addHeader(HttpHeaders.Accept, ContentType.Text.Plain.toString())
            addHeader(HttpHeaders.ContentType, ContentType.Application.Json.toString())
            setBody(gson.toJson(param))

        }.run {

            val expectedContent = gson.toJson(ApiRes("Success"))
            assertEquals(HttpStatusCode.OK, response.status())
            assertEquals(expectedContent, response.content)
        }
    }

    @Test
    fun testConnect() = withTestApplication(Application::api) {
        handleRequest(HttpMethod.Get, "/connect").run {
            println("connect test.")
        }
    }
}
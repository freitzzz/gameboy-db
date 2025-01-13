package com.github.freitzzz.gameboydb.data.http

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.IOException
import java.net.HttpURLConnection
import java.net.SocketTimeoutException
import java.net.URL
import java.security.KeyManagementException
import java.security.NoSuchAlgorithmException
import java.security.SecureRandom
import java.security.cert.X509Certificate
import javax.net.ssl.HttpsURLConnection
import javax.net.ssl.SSLContext
import javax.net.ssl.TrustManager
import javax.net.ssl.X509TrustManager

const val DEFAULT_TIMEOUT_DURATION: Int = 5 * 60 * 1000 // 5 minutes in milliseconds

open class NetworkingClient(
    private val baseUrl: String,
    private val interceptors: List<Interceptor> = emptyList()
) {
    suspend fun get(
        endpoint: String,
        headers: Map<String, String>? = null,
        queryParameters: Map<String, String>? = null
    ): Either<RequestError, Response> {
        val uri = resolveUri(baseUrl, endpoint, queryParameters)
        return send(Request(HttpVerb.GET, uri, headers = headers))
    }

    suspend fun post(
        endpoint: String,
        contentType: String = "application/json",
        data: String? = null,
        headers: Map<String, String>? = null,
        queryParameters: Map<String, String>? = null
    ): Either<RequestError, Response> {
        val uri = resolveUri(baseUrl, endpoint, queryParameters)
        return send(Request(HttpVerb.POST, uri, contentType, data, headers))
    }

    suspend fun put(
        endpoint: String,
        contentType: String = "application/json",
        data: String? = null,
        headers: Map<String, String>? = null,
        queryParameters: Map<String, String>? = null
    ): Either<RequestError, Response> {
        val uri = resolveUri(baseUrl, endpoint, queryParameters)
        return send(Request(HttpVerb.PUT, uri, contentType, data, headers))
    }

    suspend fun delete(
        endpoint: String,
        headers: Map<String, String>? = null,
        queryParameters: Map<String, String>? = null
    ): Either<RequestError, Response> {
        val uri = resolveUri(baseUrl, endpoint, queryParameters)
        return send(Request(HttpVerb.DELETE, uri, headers = headers))
    }

    suspend fun download(
        url: String,
        headers: Map<String, String>? = null,
    ): Either<RequestError, Response> {
        return send(Request(HttpVerb.GET, url, headers = headers))
    }

    private suspend fun send(request: Request): Either<RequestError, Response> {
        var connection: HttpURLConnection? = null
        return try {
            val url = URL(request.uri)
            connection = (withContext(Dispatchers.IO) {
                url.openConnection()
            } as HttpURLConnection).apply {
                requestMethod = request.verb.name
                connectTimeout = DEFAULT_TIMEOUT_DURATION
                readTimeout = DEFAULT_TIMEOUT_DURATION
                request.headers?.forEach { (key, value) ->
                    setRequestProperty(key, value)
                }
                request.contentType?.let { setRequestProperty("Content-Type", it) }
                request.data?.let {
                    doOutput = true
                    outputStream.write(it.toByteArray())
                }
            }

            val responseCode = connection.responseCode
            val responseStream = if (responseCode in 200..299) {
                connection.inputStream
            } else {
                connection.errorStream
            }

            val responseBytes = responseStream.readBytes()
            val response = Response(
                statusCode = responseCode,
                body = responseBytes,
                headers = connection.headerFields.filterKeys { it != null }
                    .mapValues { it.value.joinToString() }
            )
            Right(response)
        } catch (e: SocketTimeoutException) {
            Left(RequestError.TimeoutError("Request timed out"))
        } catch (e: IOException) {
            e.printStackTrace()

            Left(RequestError.NoInternetConnectionError("No internet connection"))
        } catch (e: Exception) {
            e.printStackTrace()
            Left(RequestError.UnknownError(e.message ?: "Unknown error"))
        } finally {
            connection?.disconnect()
        }
    }

    private fun resolveUri(
        baseUrl: String,
        endpoint: String,
        queryParameters: Map<String, String>?
    ): String {
        val urlBuilder = StringBuilder(baseUrl)
        if (!baseUrl.endsWith("/")) {
            urlBuilder.append("/")
        }
        if (endpoint.startsWith("/")) {
            urlBuilder.append(endpoint.substring(1))
        } else {
            urlBuilder.append(endpoint)
        }
        queryParameters?.let {
            urlBuilder.append("?")
            urlBuilder.append(it.entries.joinToString("&") { (key, value) -> "$key=$value" })
        }
        return urlBuilder.toString()
    }
}

// Supporting classes and enums
sealed class Either<out L, out R>
class Left<out L>(val value: L) : Either<L, Nothing>()
class Right<out R>(val value: R) : Either<Nothing, R>()

enum class HttpVerb {
    GET, POST, PUT, DELETE
}

data class Request(
    val verb: HttpVerb,
    val uri: String,
    val contentType: String? = null,
    val data: String? = null,
    val headers: Map<String, String>? = null
)

data class Response(
    val statusCode: Int,
    val body: ByteArray,
    val headers: Map<String, String>
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Response

        if (statusCode != other.statusCode) return false
        if (!body.contentEquals(other.body)) return false
        if (headers != other.headers) return false

        return true
    }

    override fun hashCode(): Int {
        var result = statusCode
        result = 31 * result + body.contentHashCode()
        result = 31 * result + headers.hashCode()
        return result
    }
}

sealed class RequestError(val message: String) {
    class TimeoutError(message: String) : RequestError(message)
    class NoInternetConnectionError(message: String) : RequestError(message)
    class UnknownError(message: String) : RequestError(message)
}

interface Interceptor {
    fun onRequest(request: Request): Request
    fun onResponse(response: Response)
    fun onError(error: RequestError)
}

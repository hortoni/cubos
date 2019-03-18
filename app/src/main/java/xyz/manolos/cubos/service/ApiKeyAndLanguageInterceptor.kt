package xyz.manolos.cubos.service

import okhttp3.Interceptor
import okhttp3.Response

private const val API_KEY = "d71ff64de15d4ed68bd780ce30e5b24c"
private const val LANGUAGE = "pt-BR"

class ApiKeyAndLanguageInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        var request = chain.request()
        val url = chain.request()
            .url()
            .newBuilder()
            .addQueryParameter("api_key", API_KEY)
            .addQueryParameter("language", LANGUAGE)
            .build()
        request = request.newBuilder().url(url).build()
        return chain.proceed(request)
    }

}

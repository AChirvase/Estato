package com.estato.core.exceptions

import java.io.IOException

class NetworkException(message: String, cause: Throwable? = null) : IOException(message, cause)

class ApiException(message: String, cause: Throwable? = null) : Exception(message, cause)

class CacheException(message: String, cause: Throwable? = null) : Exception(message, cause)

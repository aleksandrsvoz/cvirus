package com.alexvoz.cvirus.repository.base_network.interceptor

import java.io.IOException

class NoNetworkException(message: String? = null) : IOException(message) {
}
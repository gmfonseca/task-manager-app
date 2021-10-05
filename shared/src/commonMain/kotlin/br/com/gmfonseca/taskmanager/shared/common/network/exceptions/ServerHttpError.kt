package br.com.gmfonseca.taskmanager.shared.common.network.exceptions

import br.com.gmfonseca.taskmanager.shared.common.network.HttpCodes

class ServerHttpError(code: Int) : HttpError(code) {
    init {
        require(code in HttpCodes.SERVER_RANGE)
    }
}

package br.com.gmfonseca.taskmanager.shared.common.network.exceptions

import br.com.gmfonseca.taskmanager.shared.common.network.HttpCodes

class ClientHttpError(code: Int) : HttpError(code) {
    init {
        require(code in HttpCodes.CLIENT_RANGE)
    }
}

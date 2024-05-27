package hs.kr.equus.application.global.feign.config

import feign.FeignException
import feign.Response
import feign.codec.ErrorDecoder
import hs.kr.equus.application.global.feign.exception.FeignExceptions

class FeignClientErrorDecoder : ErrorDecoder {
    override fun decode(
        methodKey: String?,
        response: Response,
    ): Exception? {
        if (response.status() >= 400) {
            throw FeignExceptions.FeignServerErrorException()
        }
        return FeignException.errorStatus(methodKey, response)
    }
}

package hs.kr.equus.application.domain.status.spi

import hs.kr.equus.application.domain.application.spi.ApplicationCommandStatusPort
import hs.kr.equus.application.domain.application.spi.ApplicationQueryStatusPort

interface StatusPort : ApplicationQueryStatusPort, ApplicationCommandStatusPort
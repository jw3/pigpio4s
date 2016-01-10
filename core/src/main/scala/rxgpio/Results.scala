package rxgpio

import rxgpio.pigpio.{PigpioLibrary => lib}


sealed trait WaveCreateResult {
    def value: Int
}
case class WaveCreateOK(value: Int) extends WaveCreateResult
case class UnknownWaveCreateFailure(value: Int) extends RuntimeException with SerialReadResult

sealed trait SerialReadResult
case class ReadOK(value: Int) extends SerialReadResult
case class UnknownReadFailure(value: Int) extends RuntimeException with SerialReadResult

sealed trait InitResult
case class InitOK private[rxgpio](ver: Int) extends InitResult
case object InitFailed extends InitResult
case object UnknownInitFailure extends InitResult

object InitResult {
    def apply(code: Int) = code match {
        case lib.PI_INIT_FAILED => InitFailed
        case ver: Int => InitOK(ver)
    }
}

sealed trait WaveAddResult

sealed trait GpioResult
case object OK extends GpioResult with WaveAddResult
sealed trait GpioFailure extends RuntimeException with GpioResult
case class UnknownFailure() extends GpioFailure with SerialReadResult

sealed trait BadGpio extends GpioFailure with SerialReadResult
case class BadUserGpio() extends BadGpio with WaveAddResult
case class BadExGpio() extends BadGpio

case class NotServoGpio() extends GpioFailure
case class BadMode() extends GpioFailure
case class BadLevel() extends GpioFailure
case class BadDutyCycle() extends GpioFailure
case class BadDutyCycleRange() extends GpioFailure
case class BadPull() extends GpioFailure
case class BadPulseWidth() extends GpioFailure

case class BadBaudRate() extends GpioFailure
case class BadWaveBaud() extends GpioFailure with WaveAddResult
case class BadDataBits() extends GpioFailure with WaveAddResult
case class GpioIsInUse() extends GpioFailure
case class NotSerialGpio() extends GpioFailure with SerialReadResult

case class EmptyWaveform() extends GpioFailure with WaveCreateResult {val value = lib.PI_EMPTY_WAVEFORM}
case class TooManyCbs() extends GpioFailure with WaveCreateResult {val value = lib.PI_TOO_MANY_CBS}
case class TooManyOol() extends GpioFailure with WaveCreateResult {val value = lib.PI_TOO_MANY_OOL}
case class NoWaveformId() extends GpioFailure with WaveCreateResult {val value = lib.PI_NO_WAVEFORM_ID}

sealed trait WaveResult
sealed trait WaveSendResult extends WaveResult
sealed trait WaveDeleteResult extends WaveResult

case class BadWaveMode() extends GpioFailure with WaveSendResult
case class BadWaveId() extends GpioFailure with WaveSendResult with WaveDeleteResult

case class BadStopBits() extends GpioFailure with WaveAddResult
case class TooManyChars() extends GpioFailure with WaveAddResult
case class BadSerialOffset() extends GpioFailure with WaveAddResult
case class TooManyPulses() extends GpioFailure with WaveAddResult

object GpioResult {
    def apply(code: Int) = code match {
        case 0 => OK
        case lib.PI_BAD_USER_GPIO => throw BadUserGpio()
        case lib.PI_BAD_GPIO => throw BadExGpio()
        case lib.PI_NOT_SERVO_GPIO => throw NotServoGpio()
        case lib.PI_BAD_MODE => throw BadMode()
        case lib.PI_BAD_DUTYCYCLE => throw BadDutyCycle()
        case lib.PI_BAD_PUD => throw BadPull()
        case lib.PI_BAD_PULSEWIDTH => throw BadPulseWidth()
        case lib.PI_BAD_WAVE_BAUD => throw BadWaveBaud()
        case lib.PI_BAD_DATABITS => throw BadDataBits()
        case lib.PI_GPIO_IN_USE => throw GpioIsInUse()
        case lib.PI_NOT_SERIAL_GPIO => throw NotSerialGpio()
        case lib.PI_EMPTY_WAVEFORM => throw EmptyWaveform()
        case lib.PI_TOO_MANY_CBS => throw TooManyCbs()
        case lib.PI_TOO_MANY_OOL => throw TooManyOol()
        case lib.PI_NO_WAVEFORM_ID => throw NoWaveformId()
        case lib.PI_BAD_WAVE_ID => throw BadWaveId()
        case lib.PI_BAD_WAVE_MODE => throw BadWaveMode()
        case lib.PI_BAD_STOPBITS => throw BadStopBits()
        case lib.PI_TOO_MANY_CHARS => throw TooManyChars()
        case lib.PI_BAD_SER_OFFSET => throw BadSerialOffset()
        case lib.PI_TOO_MANY_PULSES => throw TooManyPulses()
        case _ => throw UnknownFailure()
    }
}

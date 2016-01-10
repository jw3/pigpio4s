package rxgpio

import java.nio.ByteBuffer

import rxgpio.pigpio.PigpioLibrary

import scala.util.{Failure, Success, Try}

/**
 *
 */
trait WaveIO {
    def gpioWaveClear()(implicit pigpio: PigpioLibrary): Try[GpioResult]
    def gpioWaveAddSerial(user_gpio: UserGpio, baud: BaudRate, data_bits: DataBits, stop_bits: StopBits, str: String)(implicit pigpio: PigpioLibrary): Try[GpioResult]
    def gpioWaveCreate()(implicit pigpio: PigpioLibrary): Try[WaveCreateResult]
    def gpioWaveDelete(wave_id: WaveId)(implicit pigpio: PigpioLibrary): Try[GpioResult]
    def gpioWaveTxSend(wave_id: WaveId, wave_mode: WaveMode)(implicit pigpio: PigpioLibrary): Try[GpioResult]
    def gpioWaveTxBusy()(implicit pigpio: PigpioLibrary): Try[Boolean]
    def gpioWaveTxStop()(implicit pigpio: PigpioLibrary): Try[GpioResult]
}

object DefaultWaveIO extends DefaultWaveIO

trait DefaultWaveIO extends WaveIO {
    def gpioWaveClear()(implicit pigpio: PigpioLibrary): Try[GpioResult] = {
        gpioResultFunction(pigpio.gpioWaveClear)
    }
    def gpioWaveAddSerial(user_gpio: UserGpio, baud: BaudRate, data_bits: DataBits, stop_bits: StopBits, str: String)(implicit pigpio: PigpioLibrary): Try[GpioResult] = {
        gpioResultFunction(pigpio.gpioWaveAddSerial(user_gpio.value, baud.value, data_bits.value, stop_bits.value, 0, str.length, ByteBuffer.wrap(str.getBytes())))
    }
    def gpioWaveCreate()(implicit pigpio: PigpioLibrary): Try[WaveCreateResult] = {
        pigpio.gpioWaveCreate match {
            case wid if wid >= 0 => Success(WaveCreateOK(wid))
            case f => Failure(UnknownWaveCreateFailure(f))
        }
    }
    def gpioWaveDelete(wave_id: WaveId)(implicit pigpio: PigpioLibrary): Try[GpioResult] = {
        gpioResultFunction(pigpio.gpioWaveDelete(wave_id.value))
    }

    def gpioWaveTxBusy()(implicit pigpio: PigpioLibrary): Try[Boolean] = {
        pigpio.gpioWaveTxBusy match {
            case 0 => Success(false)
            case 1 => Success(true)
            case f => Failure(new RuntimeException("wave busy failure"))
        }
    }

    def gpioWaveTxSend(wave_id: WaveId, wave_mode: WaveMode)(implicit pigpio: PigpioLibrary): Try[GpioResult] = {
        pigpio.gpioWaveTxSend(wave_id.value, wave_mode.value) match {
            case r if r >= 0 => Success(OK)
            case r => Failure(new RuntimeException("wave send failure"))
        }
    }

    def gpioWaveTxStop()(implicit pigpio: PigpioLibrary): Try[GpioResult] = {
        gpioResultFunction(pigpio.gpioWaveTxStop)
    }
}
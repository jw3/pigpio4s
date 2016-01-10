package rxgpio

import rxgpio.pigpio.PigpioLibrary

case class WaveId(value: Int)

trait WaveMode {
    def value: Int
}
object WaveModes {
    val once = new WaveMode {val value = PigpioLibrary.PI_WAVE_MODE_ONE_SHOT}
    val repeat = new WaveMode {val value = PigpioLibrary.PI_WAVE_MODE_REPEAT}
}
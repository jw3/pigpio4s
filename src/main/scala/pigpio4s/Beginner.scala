package pigpio4s

import pigpio4s.PigpioLibrary.{gpioAlertFunc_t, gpioTimerFunc_t}

/**
 * members of the BEGINNER section as described in pigpio.h
 * https://github.com/joan2937/pigpio/blob/master/pigpio.h
 */
trait Beginner {
    def gpioSetMode(gpio: Int, mode: Int): Int
    def gpioGetMode(gpio: Int): Int

    def gpioSetPullUpDown(gpio: Int, pud: Int): Int
    def gpioRead(gpio: Int): Int
    def gpioWrite(gpio: Int, level: Int): Int
    def gpioPWM(user_gpio: Int, dutycycle: Int): Int

    def gpioGetPWMdutycycle(user_gpio: Int): Int
    def gpioServo(user_gpio: Int, pulsewidth: Int): Int
    def gpioGetServoPulsewidth(user_gpio: Int): Int

    def gpioSetAlertFunc(user_gpio: Int, f: gpioAlertFunc_t): Int
    def gpioSetTimerFunc(timer: Int, millis: Int, f: gpioTimerFunc_t): Int
}


trait DefaultBeginner extends Beginner {
    private val pigpio = PigpioLibrary.INSTANCE

    def gpioSetMode(gpio: Int, mode: Int): Int = pigpio.gpioSetMode(gpio, mode)
    def gpioSetPullUpDown(gpio: Int, pud: Int): Int = pigpio.gpioSetPullUpDown(gpio, pud)
    def gpioServo(user_gpio: Int, pulsewidth: Int): Int = pigpio.gpioServo(user_gpio, pulsewidth)
    def gpioPWM(user_gpio: Int, dutycycle: Int): Int = pigpio.gpioPWM(user_gpio, dutycycle)
    def gpioSetTimerFunc(timer: Int, millis: Int, f: gpioTimerFunc_t): Int = pigpio.gpioSetTimerFunc(timer, millis, f)
    def gpioGetPWMdutycycle(user_gpio: Int): Int = pigpio.gpioGetPWMdutycycle(user_gpio)
    def gpioGetServoPulsewidth(user_gpio: Int): Int = pigpio.gpioGetServoPulsewidth(user_gpio)
    def gpioWrite(gpio: Int, level: Int): Int = pigpio.gpioWrite(gpio, level)
    def gpioGetMode(gpio: Int): Int = pigpio.gpioGetMode(gpio)
    def gpioRead(gpio: Int): Int = pigpio.gpioRead(gpio)
    def gpioSetAlertFunc(user_gpio: Int, f: gpioAlertFunc_t): Int = pigpio.gpioSetAlertFunc(user_gpio, f)
}
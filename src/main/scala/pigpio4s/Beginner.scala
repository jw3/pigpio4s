package pigpio4s

/**
 * members of the BEGINNER section as described in pigpio.h
 * https://github.com/joan2937/pigpio/blob/master/pigpio.h
 */
trait Beginner {
    def gpioSetMode(gpio: Int, mode: PinMode): Int
    def gpioGetMode(gpio: Int): PinMode

    def gpioSetPullUpDown(gpio: Int, pud: GpioPull): Int
    def gpioRead(gpio: Int): Int
    def gpioWrite(gpio: Int, level: Int): Int
    def gpioPWM(user_gpio: Int, dutycycle: DutyCycle): Int
    def gpioGetPWMdutycycle(user_gpio: Int): DutyCycle

    def gpioServo(user_gpio: Int, pulsewidth: ServoPulse): Int
    def gpioGetServoPulsewidth(user_gpio: Int): ServoPulse

    def gpioSetAlertFunc(user_gpio: Int, f: GpioWatcher): Int
    def gpioSetTimerFunc(timer: Int, millis: Int, f: TimerWatcher): Int
}


trait DefaultBeginner extends Beginner {
    private val pigpio = PigpioLibrary.INSTANCE

    def gpioGetMode(gpio: Int): PinMode = PinMode(pigpio.gpioGetMode(gpio))
    def gpioSetMode(gpio: Int, mode: PinMode): Int = pigpio.gpioSetMode(gpio, mode.value)

    def gpioSetPullUpDown(gpio: Int, pud: GpioPull): Int = pigpio.gpioSetPullUpDown(gpio, pud.value)


    def gpioRead(gpio: Int): Int = pigpio.gpioRead(gpio)
    def gpioWrite(gpio: Int, level: Int): Int = pigpio.gpioWrite(gpio, level)


    def gpioPWM(user_gpio: Int, dutycycle: DutyCycle): Int = pigpio.gpioPWM(user_gpio, dutycycle.value)
    def gpioGetPWMdutycycle(user_gpio: Int): DutyCycle = DutyCycle(pigpio.gpioGetPWMdutycycle(user_gpio))

    def gpioServo(user_gpio: Int, pulsewidth: ServoPulse): Int = pigpio.gpioServo(user_gpio, pulsewidth.value)
    def gpioGetServoPulsewidth(user_gpio: Int): ServoPulse = ServoPulse(pigpio.gpioGetServoPulsewidth(user_gpio))

    def gpioSetAlertFunc(user_gpio: Int, f: GpioWatcher): Int = pigpio.gpioSetAlertFunc(user_gpio, f)
    def gpioSetTimerFunc(timer: Int, millis: Int, f: TimerWatcher): Int = pigpio.gpioSetTimerFunc(timer, millis, f)
}
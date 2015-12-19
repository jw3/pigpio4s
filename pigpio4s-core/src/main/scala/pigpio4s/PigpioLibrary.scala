package pigpio4s

import java.nio.{ByteBuffer, IntBuffer}

import com.ochafik.lang.jnaerator.runtime.NativeSize
import com.sun.jna._
import pigpio4s.PigpioLibrary._

trait PigpioLibrary extends Library {
    def gpioInitialise(): Int
    def gpioTerminate(): Unit
    def gpioSetMode(gpio: Int, mode: Int): Int
    def gpioGetMode(gpio: Int): Int
    def gpioSetPullUpDown(gpio: Int, pud: Int): Int
    def gpioRead(gpio: Int): Int
    def gpioWrite(gpio: Int, level: Int): Int
    def gpioPWM(user_gpio: Int, dutycycle: Int): Int
    def gpioGetPWMdutycycle(user_gpio: Int): Int
    def gpioSetPWMrange(user_gpio: Int, range: Int): Int
    def gpioGetPWMrange(user_gpio: Int): Int
    def gpioGetPWMrealRange(user_gpio: Int): Int
    def gpioSetPWMfrequency(user_gpio: Int, frequency: Int): Int
    def gpioGetPWMfrequency(user_gpio: Int): Int
    def gpioServo(user_gpio: Int, pulsewidth: Int): Int
    def gpioGetServoPulsewidth(user_gpio: Int): Int
    def gpioSetAlertFunc(user_gpio: Int, f: gpioAlertFunc_t): Int
    def gpioSetAlertFuncEx(user_gpio: Int, f: gpioAlertFuncEx_t, userdata: Pointer): Int
    def gpioSetISRFunc(user_gpio: Int, edge: Int, timeout: Int, f: gpioISRFunc_t): Int
    def gpioSetISRFuncEx(user_gpio: Int, edge: Int, timeout: Int, f: gpioISRFuncEx_t, userdata: Pointer): Int
    def gpioNotifyOpen: Int
    def gpioNotifyBegin(handle: Int, bits: Int): Int
    def gpioNotifyPause(handle: Int): Int
    def gpioNotifyClose(handle: Int): Int
    def gpioWaveClear: Int
    def gpioWaveAddNew: Int
    def gpioWaveAddGeneric(numPulses: Int, pulses: gpioPulse_t): Int
    def gpioWaveAddSerial(user_gpio: Int, baud: Int, data_bits: Int, stop_bits: Int, offset: Int, numBytes: Int, str: ByteBuffer): Int
    def gpioWaveCreate: Int
    def gpioWaveDelete(wave_id: Int): Int
    def gpioWaveTxSend(wave_id: Int, wave_mode: Int): Int
    def gpioWaveChain(buf: ByteBuffer, bufSize: Int): Int
    def gpioWaveTxBusy: Int
    def gpioWaveTxStop: Int
    def gpioWaveGetMicros: Int
    def gpioWaveGetHighMicros: Int
    def gpioWaveGetMaxMicros: Int
    def gpioWaveGetPulses: Int
    def gpioWaveGetHighPulses: Int
    def gpioWaveGetMaxPulses: Int
    def gpioWaveGetCbs: Int
    def gpioWaveGetHighCbs: Int
    def gpioWaveGetMaxCbs: Int
    def gpioSerialReadOpen(user_gpio: Int, baud: Int, data_bits: Int): Int
    def gpioSerialReadInvert(user_gpio: Int, invert: Int): Int
    def gpioSerialRead(user_gpio: Int, buf: Pointer, bufSize: NativeSize): Int
    def gpioSerialReadClose(user_gpio: Int): Int
    def i2cOpen(i2cBus: Int, i2cAddr: Int, i2cFlags: Int): Int
    def i2cClose(handle: Int): Int
    def i2cWriteQuick(handle: Int, bit: Int): Int
    def i2cWriteByte(handle: Int, bVal: Int): Int
    def i2cReadByte(handle: Int): Int
    def i2cWriteByteData(handle: Int, i2cReg: Int, bVal: Int): Int
    def i2cWriteWordData(handle: Int, i2cReg: Int, wVal: Int): Int
    def i2cReadByteData(handle: Int, i2cReg: Int): Int
    def i2cReadWordData(handle: Int, i2cReg: Int): Int
    def i2cProcessCall(handle: Int, i2cReg: Int, wVal: Int): Int
    def i2cWriteBlockData(handle: Int, i2cReg: Int, buf: ByteBuffer, count: Int): Int
    def i2cReadBlockData(handle: Int, i2cReg: Int, buf: ByteBuffer): Int
    def i2cBlockProcessCall(handle: Int, i2cReg: Int, buf: ByteBuffer, count: Int): Int
    def i2cReadI2CBlockData(handle: Int, i2cReg: Int, buf: ByteBuffer, count: Int): Int
    def i2cWriteI2CBlockData(handle: Int, i2cReg: Int, buf: ByteBuffer, count: Int): Int
    def i2cReadDevice(handle: Int, buf: ByteBuffer, count: Int): Int
    def i2cWriteDevice(handle: Int, buf: ByteBuffer, count: Int): Int
    def i2cSwitchCombined(setting: Int)
    def i2cSegments(handle: Int, segs: pi_i2c_msg_t, numSegs: Int): Int
    def i2cZip(handle: Int, inBuf: ByteBuffer, inLen: Int, outBuf: ByteBuffer, outLen: Int): Int
    def bbI2COpen(SDA: Int, SCL: Int, baud: Int): Int
    def bbI2CClose(SDA: Int): Int
    def bbI2CZip(SDA: Int, inBuf: ByteBuffer, inLen: Int, outBuf: ByteBuffer, outLen: Int): Int
    def spiOpen(spiChan: Int, baud: Int, spiFlags: Int): Int
    def spiClose(handle: Int): Int
    def spiRead(handle: Int, buf: ByteBuffer, count: Int): Int
    def spiWrite(handle: Int, buf: ByteBuffer, count: Int): Int
    def spiXfer(handle: Int, txBuf: ByteBuffer, rxBuf: ByteBuffer, count: Int): Int
    def serOpen(sertty: ByteBuffer, baud: Int, serFlags: Int): Int
    def serClose(handle: Int): Int
    def serWriteByte(handle: Int, bVal: Int): Int
    def serReadByte(handle: Int): Int
    def serWrite(handle: Int, buf: ByteBuffer, count: Int): Int
    def serRead(handle: Int, buf: ByteBuffer, count: Int): Int
    def serDataAvailable(handle: Int): Int
    def gpioTrigger(user_gpio: Int, pulseLen: Int, level: Int): Int
    def gpioSetWatchdog(user_gpio: Int, timeout: Int): Int
    def gpioNoiseFilter(user_gpio: Int, steady: Int, active: Int): Int
    def gpioGlitchFilter(user_gpio: Int, steady: Int): Int
    def gpioSetGetSamplesFunc(f: gpioGetSamplesFunc_t, bits: Int): Int
    def gpioSetGetSamplesFuncEx(f: gpioGetSamplesFuncEx_t, bits: Int, userdata: Pointer): Int
    def gpioSetTimerFunc(timer: Int, millis: Int, f: gpioTimerFunc_t): Int
    def gpioSetTimerFuncEx(timer: Int, millis: Int, f: gpioTimerFuncEx_t, userdata: Pointer): Int
    def gpioStartThread(f: gpioThreadFunc_t, userdata: Pointer): pthread_t
    def gpioStopThread(pth: pthread_t)
    def gpioStoreScript(script: ByteBuffer): Int
    def gpioRunScript(script_id: Int, numPar: Int, param: IntBuffer): Int
    def gpioScriptStatus(script_id: Int, param: IntBuffer): Int
    def gpioStopScript(script_id: Int): Int
    def gpioDeleteScript(script_id: Int): Int
    def gpioSetSignalFunc(signum: Int, f: gpioSignalFunc_t): Int
    def gpioSetSignalFuncEx(signum: Int, f: gpioSignalFuncEx_t, userdata: Pointer): Int
    def gpioReadBits031: Int
    def gpioReadBits3253: Int
    def gpioWriteBits031Clear(bits: Int): Int
    def gpioWriteBits3253Clear(bits: Int): Int
    def gpioWriteBits031Set(bits: Int): Int
    def gpioWriteBits3253Set(bits: Int): Int
    def gpioHardwareClock(gpio: Int, clkfreq: Int): Int
    def gpioHardwarePWM(gpio: Int, PWMfreq: Int, PWMduty: Int): Int
    def gpioTime(timetype: Int, seconds: IntBuffer, micros: IntBuffer): Int
    def gpioSleep(timetype: Int, seconds: Int, micros: Int): Int
    def gpioDelay(micros: Int): Int
    def gpioTick: Int /*UINT32*/
    def gpioHardwareRevision: Int
    def gpioVersion: Int
    def gpioCfgBufferSize(cfgMillis: Int): Int
    def gpioCfgClock(cfgMicros: Int, cfgPeripheral: Int, cfgSource: Int): Int
    def gpioCfgDMAchannel(DMAchannel: Int): Int
    def gpioCfgDMAchannels(primaryChannel: Int, secondaryChannel: Int): Int
    def gpioCfgPermissions(updateMask: Long): Int
    def gpioCfgSocketPort(port: Int): Int
    def gpioCfgInterfaces(ifFlags: Int): Int
    def gpioCfgMemAlloc(memAllocMode: Int): Int
    def gpioCfgInternals(cfgWhat: Int, cfgVal: Int): Int
    def gpioCfgGetInternals: Int
    def gpioCfgSetInternals(cfgVal: Int): Int
    def gpioCustom1(arg1: Int, arg2: Int, argx: ByteBuffer, argc: Int): Int
    def gpioCustom2(arg1: Int, argx: ByteBuffer, argc: Int, retBuf: ByteBuffer, retMax: Int): Int
    def rawWaveAddSPI(spi: rawSPI_t, offset: Int, spiSS: Int, buf: ByteBuffer, spiTxBits: Int, spiBitFirst: Int, spiBitLast: Int, spiBits: Int): Int
    def rawWaveAddGeneric(numPulses: Int, pulses: rawWave_t): Int
    def rawWaveCB: Int
    def rawWaveCBAdr(cbNum: Int): rawCbs_t
    def rawWaveGetOut(pos: Int): Int
    def rawWaveSetOut(pos: Int, lVal: Int)
    def rawWaveGetIn(pos: Int): Int
    def rawWaveSetIn(pos: Int, lVal: Int)
    def rawWaveInfo(wave_id: Int): rawWaveInfo_t.ByValue
    def getBitInBytes(bitPos: Int, buf: ByteBuffer, numBits: Int): Int
    def putBitInBytes(bitPos: Int, buf: ByteBuffer, bit: Int)
    def timeTime: Double
    def timeSleep(seconds: Double)
    def rawDumpWave(): Unit
    def rawDumpScript(script_id: Int)
}

object PigpioLibrary {
    lazy val Instance = Native.loadLibrary("pigpio", classOf[PigpioLibrary]).asInstanceOf[PigpioLibrary]

    trait gpioAlertFunc_t extends Callback {
        def callback(gpio: Int, level: Int, tick: Int /*UINT32*/)
    }
    trait gpioAlertFuncEx_t extends Callback {
        def apply(gpio: Int, level: Int, tick: Int /*UINT32*/, userdata: Pointer)
    }
    trait gpioISRFunc_t extends Callback {
        def apply(gpio: Int, level: Int, tick: Int /*UINT32*/)
    }
    trait gpioISRFuncEx_t extends Callback {
        def apply(gpio: Int, level: Int, tick: Int /*UINT32*/, userdata: Pointer)
    }
    trait gpioTimerFunc_t extends Callback {
        def apply()
    }
    trait gpioTimerFuncEx_t extends Callback {
        def apply(userdata: Pointer)
    }
    trait gpioSignalFunc_t extends Callback {
        def apply(signum: Int)
    }
    trait gpioSignalFuncEx_t extends Callback {
        def apply(signum: Int, userdata: Pointer)
    }
    trait gpioGetSamplesFunc_t extends Callback {
        def apply(samples: gpioSample_t, numSamples: Int)
    }
    trait gpioGetSamplesFuncEx_t extends Callback {
        def apply(samples: gpioSample_t, numSamples: Int, userdata: Pointer)
    }

    /** Pointer to unknown (opaque) type */
    class gpioThreadFunc_t(address: Pointer) extends PointerType(address)
    /** Pointer to unknown (opaque) type */
    class pthread_t(address: Pointer) extends PointerType(address)

    val PI_CMD_PARSE: Int = 48
    val PI_BAD_HPWM_DUTY: Int = -97
    val PIGPIO_VERSION: Int = 40
    val PI_TOO_MANY_OOL: Int = -68
    val PI_SCRIPT_INITING: Int = 0
    val PI_CMD_JMP: Int = 813
    val PI_CMD_TAG: Int = 817
    val PI_BAD_PULSELEN: Int = -46
    val PI_I2C_WRITE: Int = 7
    val PI_MAX_WAVES: Int = 250
    val PI_WAVE_MAX_BAUD: Int = 1000000
    val PI_INIT_FAILED: Int = -1
    val PI_CMD_WVTXR: Int = 52
    val PI_CMD_RR: Int = 832
    val PI_BAD_DUTYRANGE: Int = -21
    val PI_BAD_SECO_CHANNEL: Int = -30
    val PI_MAX_I2C_DEVICE_COUNT: Int = 1 << 16
    val PI_BAD_SCRIPT_ID: Int = -48
    val PI_CMD_JNZ: Int = 814
    val PI_CMD_RL: Int = 830
    val PI_PUD_OFF: Int = 0
    val PI_SET: Int = 1
    val PI_CFG_ALERT_FREQ: Int = 4
    val PI_BAD_USER_GPIO: Int = -2
    val PI_NOT_SERVO_GPIO: Int = -93
    val PI_CHAIN_LOOP_CNT: Int = -113
    val PI_CLOCK_PCM: Int = 1
    val PI_I2C_READ: Int = 6
    val PI_DEFAULT_CFG_INTERNALS: Int = 0
    val PI_CMD_SERVO: Int = 8
    val PI_TIME_ABSOLUTE: Int = 1
    val PI_CMD_DCR: Int = 806
    val PI_DEFAULT_DMA_SECONDARY_CHANNEL: Int = 5
    val PI_BAD_CLK_PERIPH: Int = -17
    val PI_WAVE_MAX_CHARS: Int = 4 * 300
    val PI_CMD_SCRIPT: Int = 800
    val PI_BAD_HPWM_FREQ: Int = -96
    val PI_CMD_WVBSY: Int = 32
    val PI_CMD_SERWB: Int = 79
    val PI_CMD_INR: Int = 810
    val PI_MAX_WAVE_DELAY: Int = 65535
    val PI_WAVE_COUNT_PAGES: Int = 10
    val PI_MAX_MICS_DELAY: Int = 1000000
    val PI_BAD_CHAIN_LOOP: Int = -114
    val PI_SPI_MIN_BAUD: Int = 1
    val PI_CMD_GDC: Int = 83
    val PI_BAD_WDOG_TIMEOUT: Int = -15
    val PI_CMD_WVSC: Int = 36
    val PI_CMD_OR: Int = 824
    val PI_CMD_WDOG: Int = 9
    val PI_CMD_WVCRE: Int = 49
    val PI_NOT_SERIAL_GPIO: Int = -38
    val PI_CMD_MODEG: Int = 1
    val PI_SOCK_WRIT_FAILED: Int = -60
    val PI_INPFIFO: String = "/dev/pigpio"
    val PI_CMD_MODES: Int = 0
    val PI_BAD_SPI_COUNT: Int = -84
    val PI_BAD_POINTER: Int = -90
    val PI_I2C_FLAGS: Int = 5
    val PI_MIN_DUTYCYCLE_RANGE: Int = 25
    val PI_SPI_XFER_FAILED: Int = -89
    val PI_CMD_PWM: Int = 5
    val PI_DEPRECATED: Int = -120
    val PI_MAX_DMA_CHANNEL: Int = 14
    val PI_CMD_NO: Int = 18
    val PI_CMD_NP: Int = 20
    val PI_CMD_NC: Int = 21
    val PI_BAD_I2C_WLEN: Int = -109
    val PI_BAD_STOPBITS: Int = -102
    val PI_BAD_WAVE_BAUD: Int = -35
    val PI_BB_SER_MAX_BAUD: Int = 250000
    val PI_CMD_WVNEW: Int = 53
    val PI_TOO_MANY_CHARS: Int = -37
    val PI_MEM_ALLOC_MAILBOX: Int = 2
    val PI_TOO_MANY_SEGS: Int = -105
    val PI_CMD_SERRB: Int = 78
    val PI_SER_READ_NO_DATA: Int = -87
    val PI_BAD_DATABITS: Int = -101
    val PI_MAX_SCRIPT_TAGS: Int = 50
    val PI_MAX_WAVE_CYCLES: Int = 65535
    val PI_DEFAULT_DMA_CHANNEL: Int = 14
    val PI_MIN_TIMER: Int = 0
    val PI_MIN_SERVO_PULSEWIDTH: Int = 500
    val PI_HPWM_ILLEGAL: Int = -100
    val PI_BAD_EDGE: Int = -122
    val PI_DEFAULT_SOCKET_ADDR_STR: String = "127.0.0.1"
    val PI_BAD_HCLK_PASS: Int = -99
    val PI_MAX_ACTIVE: Int = 1000000
    val PI_DEFAULT_SOCKET_PORT: Int = 8888
    val PI_NO_HANDLE: Int = -24
    val PI_DEFAULT_IF_FLAGS: Int = 0
    val PI_BAD_MODE: Int = -4
    val PI_SER_SLOTS: Int = 8
    val PI_CMD_ADD: Int = 800
    val PI_CMD_RET: Int = 829
    val PI_BAD_SER_SPEED: Int = -80
    val PI_DEFAULT_MEM_ALLOC_MODE: Int = 0
    val PI_BAD_HCLK_FREQ: Int = -98
    val PI_CMD_DIV: Int = 808
    val PI_DUP_TAG: Int = -53
    val PI_BAD_PULSEWIDTH: Int = -7
    val PI_DISABLE_FIFO_IF: Int = 1
    val PI_HW_PWM_MIN_FREQ: Int = 1
    val PI_SCRIPT_HALTED: Int = 1
    val PI_NOT_PWM_GPIO: Int = -92
    val PI_BAD_SER_INVERT: Int = -121
    val PI_BAD_CLK_SOURCE: Int = -18
    val PI_TIMER_FAILED: Int = -14
    val PI_CMD_GPW: Int = 84
    val PI_MAX_MILS_DELAY: Int = 60000
    val PI_CMD_TICK: Int = 16
    val PI_TOO_MANY_PULSES: Int = -36
    val PI_NOT_PERMITTED: Int = -41
    val PI_CMD_READ: Int = 3
    val PI_CMD_WRITE: Int = 4
    val PI_CMD_WVTX: Int = 51
    val PI_BAD_SPI_SPEED: Int = -78
    val PI_WAVE_BLOCKS: Int = 4
    val PI_MIN_SOCKET_PORT: Int = 1024
    val PI_I2C_COMBINED: String = "/sys/module/i2c_bcm2708/parameters/combined"
    val RISING_EDGE: Int = 0
    val PI_BAD_SPI_CHANNEL: Int = -76
    val PI_CMD_X: Int = 839
    val PI_I2C_WRITE_FAILED: Int = -82
    val PI_MAX_STEADY: Int = 300000
    val PI_BAD_PARAM: Int = -81
    val PI_HW_PWM_RANGE: Int = 1000000
    val PI_BAD_MICROS: Int = -13
    val PI_BAD_I2C_BAUD: Int = -112
    val PI_MAX_DUTYCYCLE_RANGE: Int = 40000
    val PI_CMD_WAIT: Int = 838
    val PI_ON: Int = 1
    val PI_NOT_HALTED: Int = -62
    val PI_CMD_WVSM: Int = 34
    val PI_CMD_WVSP: Int = 35
    val PI_CMD_I2CWW: Int = 64
    val PI_NTFY_FLAGS_WDOG: Int = 1 << 5
    val PI_MAX_SERVO_PULSEWIDTH: Int = 2500
    val PI_CFG_ILLEGAL_VAL: Int = 1 << 10
    val PI_CMD_I2CWS: Int = 60
    val PI_NO_ALERT_FUNC: Int = -16
    val PI_CMD_I2CWQ: Int = 58
    val PI_BAD_WAVE_ID: Int = -66
    val PI_CMD_I2CWK: Int = 66
    val PI_CMD_I2CWI: Int = 68
    val PI_BAD_BUF_MILLIS: Int = -20
    val PI_CMD_I2CWD: Int = 57
    val PI_CMD_I2CWB: Int = 62
    val PI_MIN_WDOG_TIMEOUT: Int = 0
    val PI_CMD_SLRO: Int = 42
    val PI_CMD_CALL: Int = 802
    val PI_MAX_SPI_DEVICE_COUNT: Int = 1 << 16
    val PI_CMD_XA: Int = 840
    val PI_CLEAR: Int = 0
    val PI_CUSTOM_ERR_999: Int = -3999
    val PI_I2C_ADDR: Int = 4
    val PI_ENVPORT: String = "PIGPIO_PORT"
    val PI_NOT_INITIALISED: Int = -31
    val PI_CMD_PIGPV: Int = 26
    val PI_CMD_STAB: Int = 835
    val PI_NUM_AUX_SPI_CHANNEL: Int = 3
    val PI_SER_WRITE_FAILED: Int = -85
    val PI_BAD_PATHNAME: Int = -23
    val PI_SCRIPT_RUNNING: Int = 2
    val PI_DEFAULT_DUTYCYCLE_RANGE: Int = 255
    val PI_CFG_DBG_LEVEL: Int = 0
    val PI_CMD_BC2: Int = 13
    val PI_CMD_BC1: Int = 12
    val PI_CMD_SLRI: Int = 94
    val PI_MAX_I2C_ADDR: Int = 0x7F
    val PI_BAD_CLK_MICROS: Int = -19
    val PI_CMD_SLRC: Int = 44
    val PI_BAD_WVSP_COMMND: Int = -45
    val PI_MAX_SCRIPT_PARAMS: Int = 10
    val PI_TOO_MANY_PARAM: Int = -61
    val PI_I2C_START: Int = 2
    val PI_HIGH: Int = 1
    val PI_CMD_XOR: Int = 841
    val PI_CMD_CF2: Int = 88
    val PI_CMD_CF1: Int = 87
    val PI_DEFAULT_BUFFER_MILLIS: Int = 120
    val PI_CFG_STATS: Int = 1 << 9
    val PI_CMD_PROCR: Int = 40
    val PI_CMD_PROCS: Int = 41
    val PI_BAD_SMBUS_CMD: Int = -107
    val PI_MIN_GPIO: Int = 0
    val PI_DISABLE_SOCK_IF: Int = 2
    val PI_MIN_WAVE_HALFSTOPBITS: Int = 2
    val PI_CMD_HWVER: Int = 17
    val PI_BB_I2C_MAX_BAUD: Int = 500000
    val PI_CMD_CGI: Int = 95
    val PI_BB_SER_INVERT: Int = 1
    val PI_CMD_PROCD: Int = 39
    val PI_BAD_I2C_SEG: Int = -106
    val PI_I2C_M_WR: Int = 0x0000
    val PI_MAX_SIGNUM: Int = 63
    val PI_CMD_INRA: Int = 811
    val PI_CMD_PROCP: Int = 45
    val PI_I2C_STOP: Int = 3
    val PI_I2C_M_RD: Int = 0x0001
    val PI_GPIO_IN_USE: Int = -50
    val WAVE_FLAG_READ: Int = 1
    val PI_CMD_SERDA: Int = 82
    val PI_BAD_PUD: Int = -6
    val PI_NOTIFY_SLOTS: Int = 32
    val PI_CMD_I2CRW: Int = 63
    val PI_BAD_PARAM_NUM: Int = -52
    val WAVE_FLAG_TICK: Int = 2
    val PI_CMD_I2CRS: Int = 59
    val PI_SCRIPT_FAILED: Int = 4
    val PI_CMD_I2CRK: Int = 65
    val PI_CMD_DCRA: Int = 807
    val PI_CMD_I2CRI: Int = 67
    val PI_BAD_IF_FLAGS: Int = -26
    val PI_CMD_I2CRD: Int = 56
    val PI_CMD_I2CRB: Int = 61
    val PI_NOT_HPWM_GPIO: Int = -95
    val PI_BAD_SER_OFFSET: Int = -49
    val PI_SER_OPEN_FAILED: Int = -72
    val PI_MAX_WDOG_TIMEOUT: Int = 60000
    val PI_CFG_RT_PRIORITY: Int = 1 << 8
    val PI_BAD_SCRIPT_CMD: Int = -55
    val PI_BAD_TIMETYPE: Int = -11
    val PI_MAX_PRIMARY_CHANNEL: Int = 14
    val FALLING_EDGE: Int = 1
    val PI_MIN_WAVE_DATABITS: Int = 1
    val PI_CMD_NOIB: Int = 99
    val PI_I2C_READ_FAILED: Int = -83
    val PI_DEFAULT_CLK_PERIPHERAL: Int = 1
    val PI_BAD_MILS_DELAY: Int = -65
    val PI_CMD_LDA: Int = 819
    val PI_WAVE_MIN_BAUD: Int = 50
    val PI_I2C_M_IGNORE_NAK: Int = 0x1000
    val PI_MIN_MS: Int = 10
    val PI_CMD_PUSHA: Int = 828
    val PI_CMD_WVDEL: Int = 50
    val PI_CMD_I2CPK: Int = 70
    val PI_BAD_HANDLE: Int = -25
    val PI_BAD_TIMER: Int = -9
    val PI_BAD_I2C_ADDR: Int = -75
    val PI_NOT_I2C_GPIO: Int = -108
    val PI_SOME_PERMITTED: Int = -42
    val PI_PUD_UP: Int = 2
    val PI_CMD_AND: Int = 801
    val PI_CMD_I2CPC: Int = 69
    val PI_INITIALISED: Int = -32
    val PI_CMD_BR2: Int = 11
    val PI_I2C_M_TEN: Int = 0x0010
    val PI_CMD_BR1: Int = 10
    val PI_CMD_CMDR: Int = 803
    val PI_BUF_MILLIS_MAX: Int = 10000
    val PI_BAD_SIGNUM: Int = -22
    val PI_NO_WAVEFORM_ID: Int = -70
    val PI_CMD_CMDW: Int = 804
    val PI_BAD_VAR_NUM: Int = -56
    val PI_NO_MEMORY: Int = -58
    val PI_I2C_ESC: Int = 1
    val PI_CMD_CSI: Int = 96
    val PI_WAVE_MODE_REPEAT: Int = 1
    val PI_NUM_I2C_BUS: Int = 2
    val PI_SOCK_READ_FAILED: Int = -59
    val PI_CMD_PROC: Int = 38
    val PI_BAD_PRIM_CHANNEL: Int = -27
    val PI_SER_READ_FAILED: Int = -86
    val PI_DEFAULT_CLK_MICROS: Int = 5
    val PI_DEFAULT_UPDATE_MASK_COMPUTE: Long = 0x00FFFFFFFFFFFFL
    val PI_MEM_ALLOC_AUTO: Int = 0
    val PI_DEFAULT_UPDATE_MASK_R0: Long = 0xFFFFFFFFL
    val PI_TOO_MANY_CBS: Int = -67
    val PI_BAD_SECONDS: Int = -12
    val PI_TIME_RELATIVE: Int = 0
    val PI_SERVO_OFF: Int = 0
    val PI_MEM_ALLOC_PAGEMAP: Int = 1
    val PI_CMD_I2CO: Int = 54
    val PI_BAD_LEVEL: Int = -5
    val PI_CMD_PFS: Int = 7
    val PI_CMD_I2CZ: Int = 92
    val PI_MAX_MS: Int = 60000
    val PI_CMD_CMP: Int = 805
    val PI_UNKNOWN_COMMAND: Int = -88
    val PI_CMD_HELP: Int = 25
    val PI_CMD_PFG: Int = 23
    val PI_CMD_WVHLT: Int = 33
    val PI_CMD_WVAS: Int = 29
    val PI_CMD_SLR: Int = 43
    val PI_CMD_WVAG: Int = 28
    val PI_CHAIN_NESTING: Int = -118
    val PI_OFF: Int = 0
    val PI_DEFAULT_DMA_PRIMARY_CHANNEL: Int = 14
    val PI_BAD_SER_DEVICE: Int = -79
    val PI_I2C_M_REV_DIR_ADDR: Int = 0x2000
    val PI_MAX_SECONDARY_CHANNEL: Int = 6
    val PI_BAD_SERIAL_BUF: Int = -40
    val PI_CMD_PUSH: Int = 827
    val PI_HW_CLK_MAX_FREQ: Int = 250000000
    val PI_MAX_SCRIPT_VARS: Int = 150
    val PI_CMD_RLA: Int = 831
    val PI_I2C_END: Int = 0
    val PI_MAX_BUSY_DELAY: Int = 100
    val PI_BAD_I2C_CMD: Int = -111
    val PI_BAD_MICS_DELAY: Int = -64
    val PI_CMD_I2CC: Int = 55
    val PI_BAD_GPIO: Int = -3
    val PI_BAD_I2C_RLEN: Int = -110
    val PI_BAD_FIFO_COMMAND: Int = -29
    val PI_CLOCK_PWM: Int = 0
    val PI_LOCKFILE: String = "/var/run/pigpio.pid"
    val PI_BAD_MALLOC_MODE: Int = -104
    val PI_MAX_SOCKET_PORT: Int = 32000
    val PI_I2C_M_RECV_LEN: Int = 0x0400
    val PI_BAD_SCRIPT: Int = -47
    val PI_HW_CLK_MIN_FREQ: Int = 4689
    val PI_DEFAULT_UPDATE_MASK_R3: Long = 0x0080480FFFFFFCL
    val PI_ERRFIFO: String = "/dev/pigerr"
    val PI_WAVE_MODE_ONE_SHOT: Int = 0
    val PI_BAD_TAG: Int = -63
    val PI_DEFAULT_UPDATE_MASK_R1: Int = 0x03E7CF93
    val PI_DEFAULT_UPDATE_MASK_R2: Int = 0xFBC7CF9C
    val PI_ENVADDR: String = "PIGPIO_ADDR"
    val PI_CMD_MICS: Int = 46
    val PI_NO_AUX_SPI: Int = -91
    val PI_BB_SER_MIN_BAUD: Int = 50
    val PI_CUSTOM_ERR_0: Int = -3000
    val PI_SCRIPT_WAITING: Int = 3
    val PI_PIGIF_ERR_0: Int = -2000
    val PI_BB_SER_NORMAL: Int = 0
    val PI_CMD_PRG: Int = 22
    val PI_I2C_M_NO_RD_ACK: Int = 0x0800
    val PI_BAD_SOCKET_PORT: Int = -28
    val PI_MAX_SCRIPTS: Int = 32
    val PI_BAD_MS: Int = -10
    val PI_CMD_JZ: Int = 816
    val PI_CMD_PRRG: Int = 24
    val PI_NTFY_FLAGS_ALIVE: Int = 1 << 6
    val PI_MAX_USER_GPIO: Int = 31
    val PI_CMD_JP: Int = 815
    val PI_MIN_SIGNUM: Int = 0
    val PI_CMD_PRS: Int = 6
    val PI_CMD_JM: Int = 812
    val PI_HW_PWM_MAX_FREQ: Int = 125000000
    val PI_CMD_MLT: Int = 821
    val PI_CMD_LDAB: Int = 820
    val EITHER_EDGE: Int = 2
    val PI_CMD_NB: Int = 19
    val PI_I2C_OPEN_FAILED: Int = -71
    val PI_SPI_OPEN_FAILED: Int = -73
    val PI_CMD_WVCLR: Int = 27
    val PI_CMD_NOP: Int = 823
    val PI_BAD_CHAIN_DELAY: Int = -117
    val PI_LOW: Int = 0
    val PI_CMD_SYS: Int = 837
    val PI_CMD_MOD: Int = 822
    val PI_CMD_PUD: Int = 2
    val PI_WAVE_MAX_PULSES: Int = 4 * 3000
    val PI_NUM_STD_SPI_CHANNEL: Int = 2
    val PI_MAX_TIMER: Int = 9
    val PI_NO_SCRIPT_ROOM: Int = -57
    val PI_OUTFIFO: String = "/dev/pigout"
    val PI_DEFAULT_SOCKET_PORT_STR: String = "8888"
    val PI_MIN_DMA_CHANNEL: Int = 0
    val PI_BUF_MILLIS_MIN: Int = 100
    val PI_CMD_LD: Int = 818
    val PI_TIMEOUT: Int = 2
    val PI_EMPTY_WAVEFORM: Int = -69
    val PI_BAD_WVSC_COMMND: Int = -43
    val PI_CHAIN_COUNTER: Int = -115
    val PI_BAD_ISR_INIT: Int = -123
    val PI_CMD_BS2: Int = 15
    val PI_CMD_RRA: Int = 833
    val PI_CMD_BS1: Int = 14
    val PI_PUD_DOWN: Int = 1
    val PI_SPI_MAX_BAUD: Int = 500000000
    val PI_BAD_CHANNEL: Int = -27
    val PI_ALT4: Int = 3
    val PI_ALT3: Int = 7
    val PI_CMD_STA: Int = 834
    val PI_ALT5: Int = 2
    val PI_ALT0: Int = 4
    val PI_ALT2: Int = 6
    val PI_ALT1: Int = 5
    val PI_BAD_DUTY_RANGE: Int = -21
    val PI_MAX_WAVE_HALFSTOPBITS: Int = 8
    val PI_BAD_DUTYCYCLE: Int = -8
    val PI_BAD_I2C_BUS: Int = -74
    val PI_CMD_SPIO: Int = 71
    val PI_I2C_SLOTS: Int = 32
    val PI_MAX_WAVE_DATABITS: Int = 32
    val PI_MSG_TOOBIG: Int = -103
    val PI_CMD_FN: Int = 98
    val PI_BAD_SERIAL_STRUC: Int = -39
    val PI_TOO_MANY_TAGS: Int = -54
    val PI_CMD_SPIC: Int = 72
    val PI_OUTPUT: Int = 1
    val PI_CMD_SUB: Int = 836
    val PI_INPUT: Int = 0
    val PI_CMD_HALT: Int = 809
    val PI_I2C_COMBINED_OFF: Int = 3
    val PI_BAD_WAVE_MODE: Int = -33
    val PI_I2C_M_NOSTART: Int = 0x4000
    val PI_CMD_FG: Int = 97
    val PI_I2C_COMBINED_ON: Int = 2
    val PI_CMD_SPIR: Int = 73
    val PI_BAD_FILTER: Int = -125
    val PI_CMD_SPIW: Int = 74
    val PI_CMD_SPIX: Int = 75
    val PI_NOT_HCLK_GPIO: Int = -94
    val PI_CMD_SERW: Int = 81
    val PI_CMD_WVCHA: Int = 93
    val PI_BAD_FLAGS: Int = -77
    val PI_CMD_MILS: Int = 47
    val PI_I2C_RDRW_IOCTL_MAX_MSGS: Int = 42
    val PI_CMD_TRIG: Int = 37
    val PI_BAD_CHAIN_CMD: Int = -116
    val PI_CMD_POP: Int = 825
    val PI_BAD_CFG_INTERNAL: Int = -34
    val PI_BB_I2C_MIN_BAUD: Int = 50
    val PI_CMD_WVGOR: Int = 31
    val PI_CMD_POPA: Int = 826
    val PI_SPI_SLOTS: Int = 16
    val PI_PIGIF_ERR_99: Int = -2099
    val PI_CMD_BI2CC: Int = 89
    val PI_CMD_SERC: Int = 77
    val PI_CMD_HP: Int = 86
    val PI_CMD_BI2CO: Int = 90
    val PI_WAVE_MAX_MICROS: Int = 30 * 60 * 1000000
    val PI_CMD_BI2CZ: Int = 91
    val PI_CHAIN_TOO_BIG: Int = -119
    val PI_MAX_GPIO: Int = 53
    val PI_CMD_SERR: Int = 80
    val PI_CMD_WVGO: Int = 30
    val PI_CMD_HC: Int = 85
    val PI_CMD_SERO: Int = 76
    val PI_BAD_WVSM_COMMND: Int = -44
    val PI_BAD_SERIAL_COUNT: Int = -51
    val PI_BAD_FOREVER: Int = -124
}
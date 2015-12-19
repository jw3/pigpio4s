package pigpio4s


object Ticks {
    private val sbit = 0x80000000L

    /**
     * convert an unsigned int to a signed long
     */
    def asUint(ival: Int): Long = {
        val msbSet = (sbit & ival) != 0
        val clval = if (msbSet) ival & 0x7FFFFFFF else ival
        if (msbSet) clval | sbit else clval
    }
}
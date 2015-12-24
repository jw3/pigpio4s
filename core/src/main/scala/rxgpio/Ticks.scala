package rxgpio


object Ticks {
    private val isignbit = 0x80000000L

    /**
     * convert an unsigned int to a signed long
     */
    def asUint(utick: Int): Long = {
        val sbset = (isignbit & utick) != 0
        val clval = if (sbset) utick & 0x7FFFFFFF else utick
        if (sbset) clval | isignbit else clval
    }
}
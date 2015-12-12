package pigpio4s

import org.scalamock.scalatest.MockFactory
import org.scalatest.{Matchers, WordSpecLike}

import scala.util.Success

/**
 *
 */
class SerialSpec extends WordSpecLike with Matchers with MockFactory {
    "mocking inputs" should {
        "work" in {
            val lib = mock[PigpioLibrary]
            (lib.gpioSerialRead _).expects(1, null, null).returning(1)
            MockSerialIO(lib).gpioSerialRead(UserGpio(1), null, null) shouldBe Success(ReadOK(1))
        }
    }
}

case class MockSerialIO(override val pigpio: PigpioLibrary) extends DefaultSerialIO
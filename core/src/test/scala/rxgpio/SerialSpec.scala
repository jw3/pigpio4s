package rxgpio

import com.sun.jna.Pointer
import org.scalamock.scalatest.MockFactory
import org.scalatest.{Matchers, WordSpecLike}

import scala.concurrent.Await
import scala.concurrent.duration.Duration

/**
 *
 */
class SerialSpec extends WordSpecLike with Matchers with MockFactory {
    "mocking inputs" should {
        "work" in {
            implicit val lib = mock[PigpioLibrary]
            val x = (lib.gpioSerialRead _) expects(*, *, *)

            // revisit;; ideally the param to the onCall impl would be a tuple, however that
            // overridden fn is not resolving the Product using method is the only one that seems usable
            x onCall { xx =>
                xx.productElement(1).asInstanceOf[Pointer].setString(0, "!")
                1
            }

            val callback = mockFunction[String, Unit]
            callback.expects("!")

            Await.result(DefaultSerialIO.gpioSerialRead(UserGpio(1))(callback), Duration.Inf) shouldBe ReadOK(1)
        }
    }
}

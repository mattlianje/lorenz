package lorenz
import cats.instances.all._
import monocle.macros.Lenses
import cats.monocle.syntax._
import cats.instances.all._
import scala.util.Random

 @Lenses case class Wheel(
                // pin_settings: Indices of which pins are flipped on the wheel to register as a binary '1'.
                pin_settings: List[Int],
                // wheel_type: As per Bill Tutte's nomenclature, you had χ, ψ, and μ wheels - to be implemented as chi, psi and mu.
                wheel_type: String,
                number_of_pins: Int,
                // position: Current pin the wheel is at.
                position: Int
                ) {

  val random = new Random
  def getRandomPins (numPins: Int, curr: List[Int]): List[Int] = {
    val possiblePinSettings = Seq(1, 0)
    val randomPin = possiblePinSettings(random.nextInt(possiblePinSettings.length))
    val newList = curr ++ List(randomPin)
    if (newList.length == numPins) {
      newList
    }
    else {
      getRandomPins(numPins, newList)
    }
  }

  def getValue: Int = {
    pin_settings(this.position)
  }

  def turnWheel: Wheel = {
    // Return to position 0 if turning on the final position.
    if (this.position + 1 == number_of_pins) {
      this.copy(position = 0)
    }
    else {
      val incrementPosition = Wheel.position += 1
      val turnedState = for {
        newPosition <- incrementPosition
      } yield (newPosition)

      val (turnedWheel, (newPos)) = turnedState.run(this).value
      turnedWheel
    }
  }
}

// https://cs.stanford.edu/people/eroberts/courses/soco/projects/2008-09/colossus/lorenzmachine.html
// Some fixed pin settings for testing purposes

object Wheels {
  def chi_I (p: Int) = Wheel(
    pin_settings = List(1,0,1,0,0,1,0,1,0,0,1,0,1,0,0,1,0,1,0,0,1,0,1,0,0,1,0,1,0,0,1,0,1,0,0,1,0,1,0,0,1),
    wheel_type = "χ_I",
    number_of_pins = 41,
    position = p
  )
  def chi_II (p: Int) = Wheel(
    pin_settings = List(1,0,1,0,0,1,0,1,0,0,1,0,1,0,0,1,0,1,0,0,1,0,1,0,0,1,0,1,0,0,1),
    wheel_type = "χ_II",
    number_of_pins = 31,
    position = p
  )
  def chi_III (p: Int) = Wheel(
    pin_settings = List(0,0,1,0,0,1,0,1,0,0,1,0,1,0,0,1,0,1,0,0,1,0,1,0,0,1,0,1,0),
    wheel_type = "χ_III",
    number_of_pins = 29,
    position = p
  )
  def chi_IV (p: Int) = Wheel(
    pin_settings = List(1,0,1,0,0,1,0,1,0,0,1,0,1,0,0,1,0,1,0,0,1,0,1,0,0,1),
    wheel_type = "χ_IV",
    number_of_pins = 26,
    position = p
  )
  def chi_V (p: Int) = Wheel(
    pin_settings = List(0,0,1,0,0,1,0,1,0,0,1,0,1,0,0,1,0,1,0,0,1,0,1),
    wheel_type = "χ_V",
    number_of_pins = 23,
    position = p
  )
  def psi_I (p: Int) = Wheel(
    pin_settings = List(1,0,1,0,0,1,0,1,0,0,1,0,1,0,0,1,0,1,0,0,1,0,1,0,0,1,0,1,0,0,1,0,1,0,0,1,0,1,0,0,1,0,1),
    wheel_type = "ψ_I",
    number_of_pins = 43,
    position = p
  )
  def psi_II (p: Int) = Wheel(
    pin_settings = List(1,0,1,0,0,1,0,1,0,0,1,0,1,0,0,1,0,1,0,0,1,0,1,0,0,1,0,1,0,0,1,0,1,0,0,1,0,1,0,0,1,0,1,0,0,1,0),
    wheel_type = "ψ_II",
    number_of_pins = 47,
    position = p
  )
  def psi_III (p: Int) = Wheel(
    pin_settings = List(1,0,1,0,0,1,0,1,0,0,1,0,1,0,0,1,0,1,0,0,1,0,1,0,0,1,0,1,0,0,1,0,1,0,0,1,0,1,0,0,1,0,1,0,0,1,0,0,0,0,1),
    wheel_type = "ψ_III",
    number_of_pins = 51,
    position = p
  )
  def psi_IV (p: Int) = Wheel(
    pin_settings = List(0,0,1,0,0,1,0,1,0,0,1,0,1,0,0,1,0,1,0,0,1,0,1,0,0,1,0,1,0,0,1,0,1,0,0,1,0,1,0,0,1,0,1,0,0,1,0,0,0,0,1,0,0),
    wheel_type = "ψ_IV",
    number_of_pins = 53,
    position = p
  )
  def psi_V (p: Int) = Wheel(
    pin_settings = List(1,0,1,0,0,1,0,1,0,0,1,0,1,0,0,1,0,1,0,0,1,0,1,0,0,1,0,1,0,0,1,0,1,0,0,1,0,1,0,0,1,0,1,0,0,1,0,0,0,0,1,0,0,1,0,0,0,0,1),
    wheel_type = "ψ_V",
    number_of_pins = 59,
    position = p
  )
  def mu_I (p: Int) = Wheel(
    pin_settings = List(1,0,0,0,1,1,0,1,0,0,1,0,1,0,0,1,0,1,0,0,1,0,1,0,0,1,0,1,0,0,1,0,1,0,0,1,0,1,0,0,1,0,1,0,0,1,0,0,0,0,1,0,0,1,0,0,0,0,1,0,0),
    wheel_type = "μ_I",
    number_of_pins = 61,
    position = p
  )
  def mu_II (p: Int) = Wheel(
    pin_settings = List(1,0,1,0,0,1,0,1,0,0,1,0,1,0,0,1,0,1,0,0,1,0,1,0,0,1,0,1,0,0,1,0,1,0,0,1,0),
    wheel_type = "μ_II",
    number_of_pins = 37,
    position = p
  )
}

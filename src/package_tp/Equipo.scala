package package_tp
import scala.collection.immutable.Set
import scala.collection.immutable.HashSet
import scala.collection.immutable.Nil
import scala.util.Try
import scala.util.Failure
import scala.util.Success

case class Equipo(val nombreDeEquipo: String, val pozoComun: Int = 0, val heroes: Set[Heroe] = Set()) {

  def mejorHeroeSegun(criterio: (Heroe => Int)): Option[Heroe] = {

    val mejor = heroes.maxBy { heroe => criterio(heroe) }
    if (mejor == null) { None }
    else { Some(mejor) }
  }

  def lider(): Option[Heroe] = mejorHeroeSegun((heroe: Heroe) => heroe.getMainStatValue())

  def expulsarMiembro(unHeroe: Heroe): Equipo = this.copy(heroes = heroes.-(unHeroe))

  def agregarMiembro(unHeroe: Heroe): Equipo = this.copy(heroes = heroes.+(unHeroe))

  def reemplazarMiembro(unHeroe: Heroe, reemplazo: Heroe): Equipo = this.expulsarMiembro(unHeroe).agregarMiembro(reemplazo)

  def obtenerUnItem(unItem: Item): Equipo = {

    val seLoLleva = mejorHeroeSegun { heroe => heroe.equipar(unItem).getMainStatValue - heroe.getMainStatValue }

    if (seLoLleva.isEmpty || (seLoLleva.get.equipar(unItem).getMainStatValue() - seLoLleva.get.getMainStatValue()) == 0) { this.copy(pozoComun = pozoComun + unItem.precio) }
    else { this.reemplazarMiembro(seLoLleva.get, seLoLleva.get.equipar(unItem)) }
  }

  def realizarTarea(unaTarea: Tarea): Equipo = {
    if (unaTarea.puedeSerRealizadaPorEquipo(this)) {
      val mejor = mejorHeroeSegun((heroe: Heroe) => unaTarea.facilidad(heroe, this))
      if (mejor.isEmpty) { throw new TareaFallidaException(unaTarea, this) }
      else { this.reemplazarMiembro(mejor.get, unaTarea.efecto(mejor.get)) }
    } else {
      throw new TareaFallidaException(unaTarea, this)
    }
  }

  def realizarMision(unaMision: Mision): Equipo = {
    val equipoFinal = unaMision.tareas.foldLeft(this)((unEquipo: Equipo, unaTarea: Tarea) => unEquipo.realizarTarea(unaTarea))
    unaMision.recompensa(equipoFinal)
  }

  type criterioMejorMision = (Equipo, Equipo) => Boolean
  def elegirMision(criterio: criterioMejorMision, mision1: Mision, mision2: Mision): Mision = {
    val equipoConMision1: Try[Equipo] = Try(this.realizarMision(mision1))
    val equipoConMision2: Try[Equipo] = Try(this.realizarMision(mision2))
    val tupla = (equipoConMision1, equipoConMision2)
    tupla match {
      case (Failure(_), Success(rdo)) => mision2
      case (Success(rdo), Failure(_)) => mision1
      case (Success(rdo1), Success(rdo2)) =>
        {
          if (criterio(rdo1, rdo2)) { mision1 }
          else { mision2 }
        }
      case (Failure(exception), Failure(_)) => throw exception
    }
  }

  def mejorMision(criterio: criterioMejorMision, mision1: Mision, mision2: Mision): Boolean = {

    var rdo1, rdo2 = this

    try {
      rdo1 = this.realizarMision(mision1)
    } catch {
      case e: TareaFallidaException => return false
    }

    try {
      rdo2 = this.realizarMision(mision2)
    } catch {
      case e: TareaFallidaException => return true
    }

    return criterio(rdo1, rdo2)
  }

  /*def entrenar(misiones: List[Mision], criterio: criterioMejorMision): Equipo = {
    misiones match {
      case x :: xs  => this.realizarMision(misiones.fold(x)((mis1, mis2) => elegirMision(criterio, mis1, mis2))).entrenar(xs, criterio)
      case x :: Nil => this.realizarMision(x)
      case _        => this
    }
  }*/

  def entrenar(misiones: List[Mision], criterio: criterioMejorMision): Equipo = {
    misiones.sortWith((mision1: Mision, mision2: Mision) => mejorMision(criterio, mision1, mision2)).
      foldLeft(this)((equipo: Equipo, mision: Mision) => equipo.realizarMision(mision))
  }

}  
  

package package_tp
import scala.collection.immutable.Set
import scala.collection.immutable.HashSet
import scala.collection.immutable.Nil
import scala.util.Try

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
  val equipoConMision1:Try[Equipo]=Try(this.realizarMision(mision1))
  val equipoConMision2:Try[Equipo]=Try(this.realizarMision(mision2))
  val tupla=(equipoConMision1,equipoConMision2)
  if (criterio(this.realizarMision(mision1), this.realizarMision(mision2))) { mision1 }
    else { mision2 }
  
  
  }

  def entrenar(misiones: List[Mision], criterio: criterioMejorMision): Equipo = {
    misiones match {
      case x :: xs => this.realizarMision(misiones.fold(x)((mis1, mis2) => elegirMision(criterio, mis1, mis2))).
        entrenar(xs, criterio)
      case x :: Nil => this.realizarMision(x)

    }
  }
}
  
  

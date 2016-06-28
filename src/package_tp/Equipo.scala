package package_tp
import scala.collection.immutable.Set
import scala.collection.immutable.HashSet
import scala.collection.immutable.Nil
import scala.util.Try
import scala.util.Failure
import scala.util.Success

case class Equipo(val nombreDeEquipo: String, val pozoComun: Int = 0, val heroes: Set[Heroe] = Set()) {

  //mejorHeroeSegun ya no contempla heroes que dan error al aplicar el criterio
  def mejorHeroeSegun(criterio: (Heroe => Int)): Option[Heroe] = {

    val mejor = heroes.filter { heroe => Try(criterio(heroe)).isSuccess}.maxBy { heroe => 
      criterio(heroe)
      }
    if (mejor == null) { None }
    else { Some(mejor) }
  }

  def lider(): Option[Heroe] = mejorHeroeSegun((heroe: Heroe) => heroe.getMainStatValue())

  def expulsarMiembro(unHeroe: Heroe): Equipo = this.copy(heroes = heroes.-(unHeroe))

  def agregarMiembro(unHeroe: Heroe): Equipo = this.copy(heroes = heroes.+(unHeroe))

  def reemplazarMiembro(unHeroe: Heroe, reemplazo: Heroe): Equipo = this.expulsarMiembro(unHeroe).agregarMiembro(reemplazo)

  def obtenerUnItem(unItem: Item): Equipo = {

    val seLoLleva = mejorHeroeSegun { heroe => heroe.equipar(unItem).get.getMainStatValue-heroe.getMainStatValue }

    seLoLleva.fold(this.copy(pozoComun = pozoComun + unItem.precio))(heroe=>
      if((heroe.equipar(unItem).get.getMainStatValue-heroe.getMainStatValue)<0){
        this.copy(pozoComun = pozoComun + unItem.precio)
      }
      else{
        this.reemplazarMiembro(seLoLleva.get, seLoLleva.get.equipar(unItem).get)
      }
   )
    
  }
  //crear una nueva estructura para manejar el resultado de una tarea
  //Se devuelve un rdo: si la tarea salio bien (con el equipo final y un None en tarea fallida)
  //Si la tarea salio mal (el estado inicial y un Some con la tarea en la que fallo)
  def realizarTarea(unaTarea: Tarea): RdoDeRealizarTarea = {
      val mejor = mejorHeroeSegun((heroe: Heroe) => unaTarea.facilidad(this).get(heroe))
      mejor.fold(RdoDeRealizarTarea(this,Some(unaTarea)))(mejor=>RdoDeRealizarTarea(this.
          reemplazarMiembro(mejor, unaTarea.efecto(mejor)),None))
  }

  //Hace un fold de todas las tareas. Si el rdo final del fold es un rdo fallido devuelve ese rdo
  //Si el rdo final es un rdo que salio bien devuelve un rdo con el equipo (despues de la recompensa)
  //y un None en tarea fallida
  def realizarMision(unaMision: Mision): RdoDeRealizarTarea = {
   val equipoFinal=unaMision.tareas.foldLeft(RdoDeRealizarTarea(this,None))((rdo,tarea)=>
     rdo.realizarTarea(tarea))
     equipoFinal.tareaFallida.fold(RdoDeRealizarTarea(unaMision.recompensa(equipoFinal.equipo),None))(tarea=>
       equipoFinal)
  
    
  }

  type criterioMejorMision = (Equipo, Equipo) => Boolean
  def elegirMision(criterio: criterioMejorMision, mision1: Mision, mision2: Mision): Mision = {
    if(mejorMision(criterio)(mision1, mision2)){
      mision1
    }else{
      mision2
    }
  }
  
  def mejorMision(criterio: criterioMejorMision)(mision1: Mision, mision2: Mision): Boolean = {

    var rdo1, rdo2 = RdoDeRealizarTarea(this,None)

     rdo1.better(rdo2,criterio)
  }


  //Si la lista tiene mas de una mision. Obtiene la mejor, la hace
  //y manda el equipo resultante a entrenar nuevamente (con las miisones que quedan)
  //si la lista tiene una sola mision. la hace. 
  //devuelve un resultado (cuando falla burbujea, manteniendo el mismo resultado con el equipo y la 
  //mision en la que fallo)
  def entrenar(misiones: List[Mision], criterio: criterioMejorMision): RdoDeRealizarTarea = {
    RdoDeRealizarTarea(this,None).entrenar(misiones, criterio)
    }
  
}
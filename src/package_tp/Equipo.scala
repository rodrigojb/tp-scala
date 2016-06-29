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

  def realizarTarea(unaTarea: Tarea): ResultadoTarea = {
    var facilidadDeEquipo = unaTarea.facilidad(this)
    val mejor = mejorHeroeSegun((heroe: Heroe) => facilidadDeEquipo(heroe))
    mejor.fold[ResultadoTarea](TareaFallida(unaTarea,this))(mejor=>
      TareaExitosa(unaTarea,this.reemplazarMiembro(mejor,unaTarea.efecto(mejor))))
    
  }

  def realizarMision(unaMision: Mision): ResultadoTarea = {
    val tareaFinal = unaMision.tareas.foldLeft[ResultadoTarea](SinTarea(this)) {
      (tareaAnterior, nuevaTarea)=>
    tareaAnterior.map(equipo=>equipo.realizarTarea(nuevaTarea))
    }
    tareaFinal match{
      //esto es para que devuelva el equipo al estado original
      case TareaFallida(tarea,equipo) => TareaFallida(tarea,this)
      case TareaExitosa(tarea,equipo) => TareaExitosa(tarea,unaMision.recompensa(this))
      //se asume que nunca viene aca un SinTarea, una mision siempre deberia tener al menos una
      case otro => otro
    }

  }
       
  type criterioMejorMision = (Equipo, Equipo) => Boolean
  def elegirMision(criterio: criterioMejorMision)(mision1: Mision, mision2: Mision): Mision = {
    if(mejorMision(criterio)(mision1, mision2)){
      mision1
    }else{
      mision2
    }
  }
  
  def mejorMision(criterio: criterioMejorMision)(mision1: Mision, mision2: Mision): Boolean = {
  
    var rdo1 = realizarMision(mision1)
    var rdo2 = realizarMision(mision2)
    rdo1.isBetterThan(criterio)(rdo2)
  }

  def entrenar(misiones: List[Mision], criterio: criterioMejorMision): ResultadoTarea = {
    val criterioMision = elegirMision(criterio)(_,_)
    var tareaUltimaMision:ResultadoTarea = SinTarea(this)
    do{
     tareaUltimaMision match{
       case TareaFallida(tarea,equipo) => return TareaFallida(tarea,equipo)
     }
     val mejorMision = misiones.reduceLeft(criterioMision)
     tareaUltimaMision = tareaUltimaMision.equipo.realizarMision(mejorMision)
    }while(!misiones.isEmpty)
    tareaUltimaMision
    }
  
}
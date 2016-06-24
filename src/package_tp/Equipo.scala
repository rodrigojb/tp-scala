package package_tp
import scala.collection.mutable.Set
import scala.collection.mutable.HashSet
import scala.collection.immutable.Nil


case class Equipo (val nombreDeEquipo:String, val pozoComun:Int=0,val heroes: Set[Heroe]=Set()) {
 

  def mejorHeroeSegun(criterio:(Heroe=>Int)):Option[Heroe]={
    val mejor=heroes.maxBy { x => criterio(x) }
    if(mejor==null){None}
    else{Some(mejor)}
  }
  
  def lider():Option[Heroe]=mejorHeroeSegun((heroe:Heroe)=>heroe.mainStat())
  
      def expulsarMiembro(unHeroe:Heroe):Equipo=this.copy(heroes=heroes.-(unHeroe))       
 
       def obtenerMiembro(unHeroe:Heroe):Equipo=this.copy(heroes=heroes.+(unHeroe))  
      
       
    def reemplazarMiembro(unHeroe:Heroe,reemplazo:Heroe):Equipo=this.expulsarMiembro(unHeroe).obtenerMiembro(reemplazo)
      
      def obtenerUnItem(unItem:Item):Equipo={
        
        val seLoLleva=mejorHeroeSegun { heroe => heroe.Equipar(unItem).mainStat() }
        if(seLoLleva.isEmpty){this.reemplazarMiembro(seLoLleva.get, seLoLleva.get.Equipar(unItem))}
        else{this.copy(pozoComun=pozoComun+unItem.precio)}
   
      }
  def realizarTarea(unaTarea:Tarea):Equipo={
    val mejor=mejorHeroeSegun((heroe:Heroe)=>unaTarea.facilidad(heroe,this))
    if(mejor.isEmpty){throw new TareaFallidaException(unaTarea,this) }
    else {this.reemplazarMiembro(mejor.get,unaTarea.efecto(mejor.get))}
  }
  def realizarMision(unaMision:Mision):Equipo={
    val equipoFinal=unaMision.tareas.foldLeft(this)((unEquipo:Equipo,unaTarea:Tarea)=>unEquipo.realizarTarea(unaTarea))
    unaMision.recompensa(equipoFinal)
  }
  
  type criterioMejorMision=(Equipo,Equipo)=>Boolean
  def elegirMision(criterio:criterioMejorMision,mision1:Mision,mision2:Mision):Mision={
    if(criterio(this.realizarMision(mision1),this.realizarMision(mision2))){mision1}
    else{mision2}
    }
  
    def entrenar(misiones:List[Mision],criterio:criterioMejorMision):Equipo={
      misiones match{
        case x::xs=> this.realizarMision(misiones.fold(x)((mis1,mis2)=>elegirMision(criterio,mis1,mis2))).
        entrenar(xs,criterio)
          case x::Nil=> this.realizarMision(x)
        
      }
    }
}
  
  

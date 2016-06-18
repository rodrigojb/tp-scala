package package_tp
import collection.mutable.HashMap

class Heroe (val hp:Int, val fuerza:Int, val velocidad:Int, val inteligencia:Int) {
  
  val stats = new HashMap[Stat.Value,Int] ()
  stats += (Stat.fuerza -> fuerza, Stat.hp -> hp, Stat.velocidad -> velocidad, Stat.inteligencia -> inteligencia)
 
  var inventario = new HashMap[Slot.Value, Option[Item]]
  
  var trabajo:Option[Trabajo]=_
  
  def modificarStat (funcion:(Int => Int), stat:Stat.Value): Heroe = {
    
    var statFinal = funcion(stats.get(stat).get)
    
    if (statFinal < 1) {
      statFinal = 1
    }    
    
    stats.update(stat, statFinal)
    return this  
  }

  def intentarEquiparItem (unItem:Item):Heroe= {
    unItem.equipar(this)
  }
  
  
  def equipaEnElSlot(unSlot: Slot.Value, unItem : Item):Heroe ={
    inventario.update(unSlot,Some(unItem))    
    return this      
  }
  
 
  
  
  
  
  
  
  
}
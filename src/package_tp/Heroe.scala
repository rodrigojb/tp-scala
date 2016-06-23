package package_tp
import collection.immutable.HashMap

case class Heroe (val stats:Stats, val trabajo:Option[Trabajo], val inventario:HashMap[Slot.Value, Option[Item]]) {
  
  //val stats = new Stats(hp,fuerza,velocidad,inteligencia) 
  
 // val inventario = new HashMap[Slot.Value, Option[Item]]

  
  /*def modificarStat (funcion:(Int => Int), stat:Stat.Value): Heroe = {
    
    var statFinal = funcion(stats.getStat(stat))
    
    if (statFinal < 1) {
      statFinal = 1
    }    
    
    stats.stats.update(stat, statFinal)
    return this  
  }*/
  

  def intentarEquipar(unItem:Item):Heroe= {
    if(unItem.puedeEquipar(this)){
      return this.ocuparSlot(unItem)
    }
    return this
  }
  
  /*
  def equipaEnElSlot(unSlot: Slot.Value, unItem : Item):Heroe ={
    inventario.update(unSlot,Some(unItem))    
    return this      
  }
  */
  
  
  //Option.fold(valorSiNoHayNada)(valorSiTieneUnObjeto)
  def getStat(stat:Stat.Value):Int = {
    
    
    var valorStat :Int = stats.getStat(stat) + trabajo.fold(0)((trabajo:Trabajo)=>trabajo.getStatModifier(stat))
    var statFinal :Int = inventario.foldLeft(valorStat)((param1,param2)=>(param2._2.fold(valorStat)(_.getStat(this, stat, param1))))
    
    return statFinal.max(1)
    
  }
  
  def setStat(stat:Stat.Value, cantStat:Int) :Heroe ={
    val statsCambiados = this.stats.setStat(stat,cantStat)
    val heroeCambiado = this.copy(stats = statsCambiados)
    heroeCambiado
  }
 
  private def ocuparSlot(item:Item):Heroe={
    val slot = item.slot
    val inventarioNuevo = inventario.updated(slot, Some(item))
    val nuevoHeroe = this.copy(inventario=inventarioNuevo)
    return nuevoHeroe
  }
  
  
  
  
  
  
  
  
  
}
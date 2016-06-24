package package_tp
import collection.immutable.HashMap

case class Heroe (val hp:Int, val fuerza:Int, val velocidad:Int, val inteligencia:Int, val trabajo:Option[Trabajo]=None, val inventario:Inventario=new Inventario) {
  
  val stats :scala.collection.immutable.HashMap[Stat.Value,Int]= HashMap((Stat.hp,hp),(Stat.fuerza,fuerza),(Stat.inteligencia,inteligencia),
      (Stat.velocidad,velocidad))

     
  def Equipar(unItem:Item):Heroe= {
      if(unItem.puedeEquipar(this)){
        unItem match{
    case casco:Casco=>this.copy(inventario=inventario.copy( cabeza=Some(casco)))
    case pecho:Pecho=>this.copy(inventario=inventario.copy( torso=Some(pecho)))
    case manoDoble:ManoDoble=>this.copy(inventario=inventario.copy( manoIzq=Some(manoDoble),manoDer=Some(manoDoble)))
    case unaMano:UnaMano=>equiparEnManoPro(unaMano)
    case talisman:Talisman=>this.copy(inventario=inventario.copy(talismanes=inventario.talismanes.+:(Some(talisman)))) 
        }
      }
      else{throw new NoEquipableException(unItem)}
  }
  
  def equiparEnManoPro(unItem:Item):Heroe={
    val equipadoEnDer=this.copy(inventario=inventario.copy(manoDer=Some(unItem)))
    val equipadoEnIzq=this.copy(inventario=inventario.copy(manoIzq=Some(unItem)))
    if(equipadoEnDer.mainStat>equipadoEnIzq.mainStat){ equipadoEnDer}
    else{equipadoEnIzq}
  }
 def Trabajar(unTrabajo:Trabajo):Heroe= this.copy(trabajo=Some(unTrabajo))
 
  
  
  //Option.fold(valorSiNoHayNada)(valorSiTieneUnObjeto)
  def getStat(stat:Stat.Value):Int = {
    val equipado=inventario.aplicate(this)
    val estadoFinal=trabajo.fold(equipado)(trabajo=>trabajo.funcion(equipado))
    val statResultante=estadoFinal.stats.get(stat).get
 if(statResultante<1) {1}
 else{statResultante}
 }
 def mainStat():Int={
   if(trabajo.isEmpty){1}
   else{this.getStat(trabajo.get.statPrincipal)}
 }
 
} 
  

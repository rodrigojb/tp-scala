package package_tp
import collection.immutable.HashMap

case class Heroe (val hp:Int, val fuerza:Int, val velocidad:Int, val inteligencia:Int, val trabajo:Option[Trabajo]=None, val inventario:Inventario=new Inventario) {
  
  lazy val stats :scala.collection.immutable.HashMap[Stat.Value,Int]= HashMap((Stat.hp,hp),(Stat.fuerza,fuerza),(Stat.inteligencia,inteligencia),
      (Stat.velocidad,velocidad))

     //devolver un Option (Y)
  def equipar(unItem:Item):Option[Heroe]= {
      if(unItem.puedeEquipar(this)){
        unItem match{
    case casco:Casco=>Some(this.copy(inventario=inventario.copy( cabeza=Some(casco))))
    case pecho:Pecho=>Some(this.copy(inventario=inventario.copy( torso=Some(pecho))))
    case manoDoble:ManoDoble=>Some(this.copy(inventario=inventario.copy( manoIzq=Some(manoDoble),manoDer=None)))
    case unaMano:UnaMano=>Some(equiparEnManoPro(unaMano))
    case talisman:Talisman=>Some(this.copy(inventario=inventario.copy(talismanes=inventario.talismanes.+:(Some(talisman)))))
        }
      }
      else{Some(this
          )}
  }
  
  def equiparEnManoPro(unItem:Item):Heroe={
    val manoIzq=inventario.manoIzq
    
    manoIzq.get match{
      case manoDoble:ManoDoble=>this.copy(inventario=inventario.copy(manoIzq=Some(unItem)))  
      case otherwise=>{
      val equipadoEnDer=this.copy(inventario=inventario.copy(manoDer=Some(unItem)))
    val equipadoEnIzq=this.copy(inventario=inventario.copy(manoIzq=Some(unItem)))
    if(equipadoEnDer.getMainStatValue>equipadoEnIzq.getMainStatValue){ equipadoEnDer}
    else{equipadoEnIzq}}
    }
    }
 def trabajar(unTrabajo:Trabajo):Heroe= this.copy(trabajo=Some(unTrabajo))
 
  
  
  //Option.fold(valorSiNoHayNada)(valorSiTieneUnObjeto)
  def getStat(stat:Stat.Value):Int = {
   val heroeConTrabajo=trabajo.fold(this)(trabajo=>trabajo.efectoSobreHeroe(this))
   val equipado=inventario.aplicate(heroeConTrabajo) 
    val statResultante=equipado.stats.get(stat).get
   statResultante.max(1)
 }
 def getMainStatValue():Int={
   
   trabajo.fold(0)(trabajo=>this.getStat(trabajo.statPrincipal))
   //usar un fold (Y)
 }
 
} 
  

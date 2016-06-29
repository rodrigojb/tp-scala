package package_tp
import collection.mutable.HashMap
import java.lang.reflect.Field


case class Inventario(val cabeza:  Option[Item]=None, val torso :
    Option[Item]=None, val manoIzq:  Option[Item]=None,val manoDer : Option[Item]=None, 
    val talismanes :List[Option[Talisman]]=List() ) {
//El aplicate aplica todo el inventario al heroe. Devuelve un heroe aplicado
  def aplicate(heroe:Heroe):Heroe={
    val items=cabeza::torso::manoIzq::manoDer::talismanes
    items.foldLeft(heroe)((unHeroe:Heroe,unItem:Option[Item])=>unItem.fold(unHeroe)
        ((x:Item)=>x.efectoSobreHeroe(heroe))) 
  }
}
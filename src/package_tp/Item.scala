package package_tp

//La funcion devuevle un heroe con los stats modificados por el item
 class Item (val puedeEquipar:(Heroe=>Boolean), val precio:Int,val funcion :(Heroe=>Heroe)) {  
          
  
  
  
}
case class ManoDoble(override val puedeEquipar:(Heroe=>Boolean),override val precio:Int,override val funcion :(Heroe=>Heroe)) extends Item (puedeEquipar,precio,funcion){
  
}
case  class Casco(override val puedeEquipar:(Heroe=>Boolean),override val precio:Int,override  val funcion :(Heroe=>Heroe)) extends Item (puedeEquipar,precio,funcion){
  
}
case  class Pecho( override val puedeEquipar:(Heroe=>Boolean), override  val precio:Int,override  val funcion :(Heroe=>Heroe)) extends Item (puedeEquipar,precio,funcion){
  
}
case  class UnaMano( override val puedeEquipar:(Heroe=>Boolean), override  val precio:Int, override val funcion :(Heroe=>Heroe)) extends Item (puedeEquipar,precio,funcion){
  
}   
case  class Talisman( override val puedeEquipar:(Heroe=>Boolean), override val precio:Int, override val funcion :(Heroe=>Heroe)) extends Item (puedeEquipar,precio,funcion){
  
}   

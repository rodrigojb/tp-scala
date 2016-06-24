package package_tp

//La funcion devuevle un heroe con los stats modificados por el item
 class Item (val puedeEquipar:(Heroe=>Boolean), val precio:Int,val efectoSobreHeroe :(Heroe=>Heroe)) {  
          
  
  
  
}
case class ManoDoble(override val puedeEquipar:(Heroe=>Boolean),override val precio:Int,override val efectoSobreHeroe :(Heroe=>Heroe)) extends Item (puedeEquipar,precio,efectoSobreHeroe){
  
}
case  class Casco(override val puedeEquipar:(Heroe=>Boolean),override val precio:Int,override  val efectoSobreHeroe :(Heroe=>Heroe)) extends Item (puedeEquipar,precio,efectoSobreHeroe){
  
}
case  class Pecho( override val puedeEquipar:(Heroe=>Boolean), override  val precio:Int,override  val efectoSobreHeroe :(Heroe=>Heroe)) extends Item (puedeEquipar,precio,efectoSobreHeroe){
  
}
case  class UnaMano( override val puedeEquipar:(Heroe=>Boolean), override  val precio:Int, override val efectoSobreHeroe :(Heroe=>Heroe)) extends Item (puedeEquipar,precio,efectoSobreHeroe){
  
}   
case  class Talisman( override val puedeEquipar:(Heroe=>Boolean), override val precio:Int, override val efectoSobreHeroe :(Heroe=>Heroe)) extends Item (puedeEquipar,precio,efectoSobreHeroe){
  
}   

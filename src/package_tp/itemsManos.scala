package package_tp

class itemsManos (val slot:Slot.Value, val puedeEquipar:(Heroe=>Boolean),val precio:Int, val stats:Stats)  
      extends Item(slot,puedeEquipar,precio,stats){
  
   val itemManoIzquierda, itemManoDerecha : Item = null
   
   
  
}
package package_tp

//Slot donde equipar, condicion de equipamiento, funcion de equipar
class Item (val slot:Slot.Value, val puedeEquipar:(Heroe=>Boolean), val modificarStat:(Heroe=>Heroe),val precio:Int ) {  
  

  def ocuparInventario(unHeroe:Heroe):Heroe=
  {
    unHeroe.equipaEnElSlot(slot, this)     
  }
  
  
  def equipar(unHeroe: Heroe):Heroe={

    if (puedeEquipar(unHeroe)){    
       ocuparInventario(modificarStat(unHeroe))      
    }else{
      unHeroe
    }
  } 
 
}
package package_tp

//Slot donde equipar, condicion de equipamiento, funcion de equipar
class Item (val slot:Slot.Value, val puedeEquipar:(Heroe=>Boolean), val precio:Int,val stats:Stats, val getStat:((Heroe,Stat.Value,Int)=>Int)) {  
          
  
  /*
  def ocuparInventario(unHeroe:Heroe):Heroe=
  {
    unHeroe.equipaEnElSlot(slot, this)     
  }
  */
  /*
  def equipar(unHeroe: Heroe):Heroe={

    if (puedeEquipar(unHeroe)){    
        unHeroe.equipar(this)
        //ocuparInventario(modificarStat(unHeroe))      
    }else{
      unHeroe
    }
  }*/
  
}
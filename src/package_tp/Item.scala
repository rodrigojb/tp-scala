package package_tp

//Slot donde equipar, condicion de equipamiento, funcion de equipar
class Item (val puedeEquipar:(Heroe=>Boolean), val modificarStat:(Heroe=>Heroe) ) {  
  

  def ocuparInventario(unHeroe:Heroe):Heroe=
  {
    
    
  }
  
  def equipar(unHeroe: Heroe):Heroe={
    if (puedeEquipar(unHeroe)){
      modificarStat(ocuparInventario(unHeroe))
      
    }else{
      unHeroe
    }
  }
  
  

 
}
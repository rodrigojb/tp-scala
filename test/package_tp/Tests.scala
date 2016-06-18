package package_tp

import org.junit.Test
import org.junit.Assert._
import org.junit.Before

class Tests  {
  
  var cascoVikingo:Item=_
  var unHeroe:Heroe=_
  
  @Before
  def setup(){
    
   cascoVikingo = new Item(Slot.cabeza,((x:Heroe) => x.fuerza>30),
          ((x:Heroe) => x.modificarStat(((x:Int) => x+10),Stat.hp)),5)
    
   unHeroe = new Heroe(10,40,30,40)
    
    
    
  }
  @Test
  def `puede_equipar_un_heroe_casco_vikingo`= {
    assertTrue(cascoVikingo.puedeEquipar(unHeroe))
  }  
  
  @Test
  def `modificarHeroe`= {
    var heroeEquipado = unHeroe.intentarEquiparItem(cascoVikingo)
    
    assertTrue(heroeEquipado.inventario.get(Slot.cabeza).contains(Some(cascoVikingo)))
  }
   
  
}
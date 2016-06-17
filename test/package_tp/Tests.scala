package package_tp

import org.junit.Test
import org.junit.Assert._
import org.junit.Before

class Tests {
  
  var cascoVikingo:Item
  var unHeroe:Heroe
  
  @Before
  def setup(){
    
   cascoVikingo = new Item(Slot.cabeza,((x:Heroe) => x.fuerza>30),
          ((x:Heroe) => x.modificarStat(((x:Int) => x+10),Stat.hp)))
    
   unHeroe = new Heroe(10,20,30,40)
    
    
    
  }
  
  @Test
  def modificarHeroe():Unit = {
    var heroeEquipado = unHeroe.equiparItem(cascoVikingo,Slot.cabeza)
    
    assertEquals(20, unHeroe.hp)
  }
  
}
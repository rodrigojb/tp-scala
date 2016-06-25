package package_tp

import org.junit.Test
import org.junit.Before
import org.junit.Assert._
import com.sun.xml.internal.bind.v2.runtime.unmarshaller.Intercepter

class Tests {

  var cascoVikingo: Item = _
  var unHeroe: Heroe = _
  var heroePt: Heroe = _
  @Before
  def setup() {

    cascoVikingo = new Casco(((x: Heroe) => x.fuerza > 30), 5,
      ((heroe: Heroe) => heroe.copy(hp = heroe.hp + 10)))

    unHeroe = new Heroe(10, 40, 30, 40)
    heroePt = new Heroe(1, 1, 1, 1)
    
    
    

  }

  @Test
  def `puede_equipar_un_heroe_casco_vikingo` = {
    assertTrue(cascoVikingo.puedeEquipar(unHeroe))
  }

  @Test
  def `equipar_un_heroe_con_casco_vikingo` = {
    var heroeEquipado = unHeroe.equipar(cascoVikingo)

    assertEquals(heroeEquipado.inventario.cabeza, Some(cascoVikingo))
    assertEquals(heroeEquipado.getStat(Stat.hp), unHeroe.getStat(Stat.hp) + 10)
  }

  @Test(expected = classOf[NoEquipableException])
  def `equipar_un_heroe_pt_con_casco_vikingo_y_no_se_equipa_por_no_cumplir` = {
    var heroeEquipado = heroePt.equipar(cascoVikingo)
  }
  
  
  
  
  

}
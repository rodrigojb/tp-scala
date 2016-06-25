package package_tp

import org.junit.Test
import org.junit.Before
import org.junit.Assert._
import com.sun.xml.internal.bind.v2.runtime.unmarshaller.Intercepter

class Tests {

  var cascoVikingo: Item = _
  var unHeroe: Heroe = _
  var heroePt: Heroe = _

  var unGuerrero: Heroe = _
  var unMago: Heroe = _
  var unLadron: Heroe = _
  var unEquipo: Equipo = _
  var setEquipo: Set[Heroe] = _
  var unItem: Item = _
  var unInventario: Inventario = _
  var subirEscalera: Tarea = _
  var vencerDragon: Tarea = _
  var rescatarPrincesa: Mision = _
  var tareas : List[Tarea] = _
  var unTablon : Tablon = _
  var misiones : List[Mision] = _

  
  @Before
  def setup() {

    cascoVikingo = new Casco(((x: Heroe) => x.fuerza > 30), 5,
      ((heroe: Heroe) => heroe.copy(hp = heroe.hp + 10)))

    unHeroe = new Heroe(10, 40, 30, 40)
    heroePt = new Heroe(1, 1, 1, 1)

    unGuerrero = new Heroe(2, 3, 4, 5, Some(Guerrero))
    unMago = new Heroe(5, 4, 3, 2, Some(Mago))
    unLadron = new Heroe(1, 1, 1, 3, Some(Ladron))

    setEquipo.+(unGuerrero).+(unMago).+(unLadron)

    unEquipo = new Equipo("losSuperAmigos", 120, setEquipo)

    def forzudo(unHeroe: Heroe): Boolean = unHeroe.fuerza > 10

    def subirHP(unHeroe: Heroe): Heroe = {
      unHeroe.hp.+(20)
      return unHeroe
    }

    unItem = new Item(forzudo, 15, subirHP)

    unInventario = new Inventario

    def matarDragon(unHeroe: Heroe, unEquipo: Equipo): Int = {
      if (unHeroe.fuerza > 80) {
        return 10
      }
      
      return 0
    }
    
    def subirEscalon(unHeroe: Heroe, unEquipo: Equipo): Int = {
      if (unHeroe.hp > 100) {
        return 5
      }
      
      return 2
    }   
    
    def efectoDragon(unHeroe:Heroe):Heroe = {
      unHeroe.inteligencia.+(100)
      return unHeroe
    }
    
    def efectoEscalera(unHeroe:Heroe):Heroe = {
      unHeroe.hp.-(20)
      return unHeroe
    }
    
    def puedeVencerDragon(unEquipo:Equipo):Boolean = true
    def puedeSubirEscalera(unEquipo:Equipo):Boolean = true
    
    vencerDragon = new Tarea(matarDragon, efectoDragon, puedeVencerDragon)
    subirEscalera = new Tarea(subirEscalon, efectoEscalera, puedeSubirEscalera)
    
    def recompensa(unEquipo:Equipo):Equipo = {
      unEquipo.pozoComun.+(2000)
      return unEquipo
    }
    
    tareas = vencerDragon :: subirEscalera :: Nil   
    rescatarPrincesa = new Mision(tareas,recompensa)
    
    misiones = rescatarPrincesa :: Nil       
    unTablon = new Tablon(misiones)
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
  
  @Test
  def `liderDeEquipo` = {
    assertEquals(unEquipo.lider(),Some(unMago))
  }


}
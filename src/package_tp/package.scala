//Definimos los objetos que vamos a crear y a la mierda.


package object package_tp {
  
  object Stat extends Enumeration{
    type Stat = Value
    val hp, fuerza, velocidad, inteligencia = Value    
  }  
  
  /*object Slot extends Enumeration{
    type Slot = Value
    val cabeza, torso, manos, talismanes = Value
  }
*/
  object Guerrero extends Trabajo(Stat.fuerza,
      ((unHeroe:Heroe) => unHeroe.copy(hp=unHeroe.hp+10, fuerza=unHeroe.fuerza+15,inteligencia=unHeroe.inteligencia-10) ))

  object Mago extends Trabajo(Stat.inteligencia,
      ((unHeroe:Heroe) => unHeroe.copy(fuerza=unHeroe.fuerza-20,inteligencia=unHeroe.inteligencia+20) ))

  object Ladron extends Trabajo(Stat.velocidad,
      ((unHeroe:Heroe) => unHeroe.copy(hp=unHeroe.hp-5,velocidad=unHeroe.velocidad+10) ))
}

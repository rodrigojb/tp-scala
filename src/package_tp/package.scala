//Definimos los objetos que vamos a crear y a la mierda.


package object package_tp {
  
  object Stat extends Enumeration{
    type Stat = Value
    val hp, fuerza, velocidad, inteligencia = Value    
  }  
  
  object Slot extends Enumeration{
    type Slot = Value
    val cabeza, torso, manos, talismanes = Value
  }

  /*
  object Guerrero extends Trabajo(Stat.fuerza,
      (unHeroe:Heroe) => new Heroe 
        (unHeroe.hp, unHeroe.fuerza, unHeroe.velocidad, unHeroe.inteligencia)
        .modificarStat(( x :Int)  =>  x +10, Stat.hp)
        .modificarStat(( x :Int)  =>  x +15, Stat.fuerza)
        .modificarStat(( x :Int)  =>  x -10, Stat.inteligencia)){}
      
 */
  //val statPrincipal:Stat.Value, val hp:Int, val fuerza:Int, val velocidad:Int, val inteligencia:Int
  
  object Guerrero extends Trabajo(Stat.fuerza,new Stats(10,15,0,-10)){}
        
  object Mago extends Trabajo(Stat.inteligencia,new Stats(0,-20,0,20)){}

  object Ladron extends Trabajo(Stat.velocidad,new Stats(-5,0,10,0)){}
  
}
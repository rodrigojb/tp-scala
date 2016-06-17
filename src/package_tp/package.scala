//Definimos los objetos que vamos a crear y a la mierda.


package object package_tp {
  
  object Stat extends Enumeration{
    type Stat = Value
    val hp, fuerza, velocidad, inteligencia = Value    
  }  
  
  object Slot extends Enumeration{
    type Slot = Value
    val cabeza, torso, manoIzq, manoDer, talismanes = Value
  }

  
  object Guerrero extends Trabajo(Stat.fuerza,
      (unHeroe:Heroe) => new Heroe 
        (unHeroe.hp, unHeroe.fuerza, unHeroe.velocidad, unHeroe.inteligencia)
        .modificarStat(( x :Int)  =>  x +10, Stat.hp)
        .modificarStat(( x :Int)  =>  x +15, Stat.fuerza)
        .modificarStat(( x :Int)  =>  x -10, Stat.inteligencia)){}
      
    
  
  
  object Mago extends Trabajo(Stat.inteligencia,
      (unHeroe:Heroe) => new Heroe
      (unHeroe.hp, unHeroe.fuerza , unHeroe.velocidad, unHeroe.inteligencia)
      .modificarStat((x:Int) => x -20, Stat.fuerza)
      .modificarStat((x:Int) => x +20, Stat.inteligencia)){
    
  }

  object Ladron extends Trabajo(Stat.velocidad,
      (unHeroe:Heroe) => new Heroe
      (unHeroe.hp, unHeroe.fuerza, unHeroe.velocidad, unHeroe.inteligencia)
      .modificarStat((x:Int) => x - 5, Stat.hp)
      .modificarStat((x:Int) => x + 10, Stat.velocidad)){
    
  }
  
}
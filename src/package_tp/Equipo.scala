package package_tp
import scala.collection.mutable.Set
import scala.collection.mutable.HashSet


class Equipo (val nombreDeEquipo:String) {
 
  var pozoComun: Int = 0
  var listaDeHeroes: Set[Heroe] = new HashSet[Heroe]  
    
}
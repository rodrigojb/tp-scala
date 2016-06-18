package package_tp
import scala.collection.mutable.Set
import scala.collection.mutable.HashSet


class Equipo (val nombreDeEquipo:String) {
 
  var pozoComun: Int = 0
  var listaDeHeroes: Set[Option[Heroe]] = new HashSet[Option[Heroe]]  
    
  def mejorHeroeSegun(criterio:(Heroe=>Int)):Option[Heroe]={
    listaDeHeroes.fold(None)((x:Option[Heroe],y:Option[Heroe])=> 
      if(x.fold(0)((x:Heroe)=>criterio(x))>y.fold(0)((y:Heroe)=>criterio(y))){ x}
      else{ y})
  }
  
  def lider(){
    mejorHeroeSegun((x:Heroe)=>x.stats.get(x.trabajo.get.statPrincipal).get)
  }
      def expulsarMiembro(unHeroe:Option[Heroe]){
        listaDeHeroes.-=(unHeroe)
       
      }
       def obtenerMiembro(unHeroe:Option[Heroe]){
        listaDeHeroes.+=(unHeroe)
      }
       
      def reemplazarMiembro(unHeroe:Option[Heroe],reemplazo:Option[Heroe]){
          expulsarMiembro(unHeroe)
          obtenerMiembro(reemplazo)
      }
      def obtenerUnItem(unItem:Item):Equipo={
        
        val diferencia=((_unHeroe:Heroe,otroHeroe:Heroe,stat:Stat.Value)=>
          _unHeroe.stats.get(stat).get-otroHeroe.stats.get(stat).get
        )
        
        val mejorHeroe=mejorHeroeSegun((unHeroe:Heroe)=>
             diferencia(unHeroe.trabajo.get.aplicarTrabajo(unHeroe),unHeroe,
                unHeroe.trabajo.get.statPrincipal)
      )
        val equipo=new Equipo(nombreDeEquipo)
          
      if(mejorHeroe.get.stats.get(mejorHeroe.get.trabajo.get.statPrincipal).get<0){
          equipo.reemplazarMiembro(mejorHeroe, Some(mejorHeroe.get.intentarEquiparItem(unItem)))
          equipo
      }
      else{equipo.pozoComun=equipo.pozoComun+unItem.precio
        equipo}
      }
      
  
}
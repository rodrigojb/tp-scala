package package_tp

case class RdoDeRealizarTarea(equipo : Equipo,tareaFallida:Option[Tarea]) {
  
//Si es un resultado fallido se devuelve a si mismo (no hace nada)
  //Si es un resultado ok crea un nuevo resultado realizando la tarea
  def realizarTarea(unaTarea:Tarea):RdoDeRealizarTarea=
    tareaFallida.fold(equipo.realizarTarea(unaTarea))(tarea=>this)
   
  def realizarMision(unaMision:Mision):RdoDeRealizarTarea=
    tareaFallida.fold(equipo.realizarMision(unaMision))(tarea=>this)

    def entrenar(misiones: List[Mision], criterio: ((Equipo, Equipo) => Boolean)): RdoDeRealizarTarea={
    misiones match{ 
    case x::xs::nil=> {val mision=misiones.sortWith(equipo.mejorMision(criterio)).head
        this.realizarMision(mision).entrenar(misiones.dropWhile { x => x==mision }, criterio)
      
      }
      case x::nil=>this.realizarMision(x)
      }
  }
//si es un rdo OK y se compara con otro rdo OK. Se comparan los eqipos resultantes de aplicar el criterio
  //Si alguno de los 2 es un rdo fallido el otro es mejor
def better(rdo:RdoDeRealizarTarea,criterio:(Equipo,Equipo)=>Boolean):Boolean={
  
 var tupla=(tareaFallida,rdo.tareaFallida)
 tupla match{
   case (None,None)=>criterio(rdo.equipo,equipo)
   case (_,None)=>true
   case otherwise=>false
 }
  
}
}

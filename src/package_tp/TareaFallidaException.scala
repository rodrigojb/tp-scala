package package_tp

class TareaFallidaException(val tarea:Tarea,val equipo:Equipo) extends Exception("el equipo fallo en:" + 
    tarea.toString()+". El estado final es:"+ equipo.toString()) {
  
}
package package_tp

trait ResultadoTarea extends Object with FuncionesTypes{
  val equipo :Equipo
  def map(f:(Equipo=>ResultadoTarea)):ResultadoTarea
  def isBetterThan(criterio: CriterioMejorMision)(comparado :ResultadoTarea) : Boolean

}

case class TareaExitosa(val tarea:Tarea,val equipo: Equipo) extends ResultadoTarea{
  def map(f:(Equipo=>ResultadoTarea)) :ResultadoTarea = f(equipo)
  
  def isBetterThan(criterio: CriterioMejorMision)(comparado :ResultadoTarea) = {
    comparado match{
      case TareaExitosa(_, equipoAComparar) => criterio(equipo, equipoAComparar) 
      case otro => true
    }
  }
  

}

case class TareaFallida(val tarea:Tarea,val equipo: Equipo) extends ResultadoTarea{
  def map(f:(Equipo=>ResultadoTarea)) :ResultadoTarea = this
  
  def isBetterThan(criterio: CriterioMejorMision)(comparado :ResultadoTarea) = false
  

}

case class SinTarea(val equipo: Equipo) extends ResultadoTarea{
  def map(f:(Equipo=>ResultadoTarea)) :ResultadoTarea = f(equipo)
  def isBetterThan(criterio: CriterioMejorMision)(comparado :ResultadoTarea) = false

}


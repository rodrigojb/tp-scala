package package_tp
import collection.mutable.HashMap

case class Stats (val hp:Int, val fuerza:Int, val velocidad:Int, val inteligencia:Int) {
  
  val stats = new HashMap[Stat.Value,Int]()
  
  def getStat(stat:Stat.Value):Int = {
    return stats.get(stat).fold(0)((valorStat:Int) => valorStat)
  }
  
  def setStat(stat:Stat.Value, cantStat:Int):Stats = {
    stat match{
      case hp => this.copy(hp=cantStat)
      case fuerza => this.copy(fuerza=cantStat)
      case velocidad => this.copy(velocidad=cantStat)
      case inteligencia => this.copy(inteligencia=cantStat)
    }
    
  }
  
}
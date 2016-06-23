package package_tp


//El trabajo recibe un StatPrincipal y una funcion que recibe un Heroe y devuelve el mismo Heroe con dicho Trabajo
// sin modificar el stat, solamente con el nuevo trabajo.
class Trabajo(val statPrincipal:Stat.Value, val stats : Stats) {

  
  def getStatModifier(unStat:Stat.Value) = {
    stats.getStat(unStat) 
  }
  
}

/*
stats = (8,9,7,8)
casco  (KISS)

*/
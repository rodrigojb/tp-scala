package package_tp


//la funcion devuelve un heroe con los stats modificados por el trabajo
class Trabajo(val statPrincipal:Stat.Value, val funcion:(Heroe=>Heroe)) {

  def aplicate(heroe:Heroe):Heroe= funcion(heroe)

}


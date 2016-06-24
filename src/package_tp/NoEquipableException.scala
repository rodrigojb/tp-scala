package package_tp

class NoEquipableException(val item:Item) extends Exception("No se puede equipar" + item.toString()) {
  
}
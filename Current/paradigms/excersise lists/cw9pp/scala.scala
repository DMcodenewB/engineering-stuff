class Time (private var h:Int) {    
  if (h<0) h=0      
  def hour:Int=h                              
  
  def hour_=(newHour:Int){
    if(newHour<0) h=0              
    else h=newHour
  }
}

val t = new Time(5)
t.hour
                      //tu sobie sprawdzałem to po prostu
t.hour = -12
t.hour

//zadanie 2

class Time (private var h:Int) {     
  if (h<0) h=0                            
  def hour:Int=h                              
  
  def hour_=(newHour:Int){
    if(newHour<0) h=0              
    else h=newHour
  }
}
object Time {
  def apply (hour:Int) = new Time(hour)
  
  def main(args:Array[String]){
    var time1 = apply(5)
  }
}                                          //klasę i obiekt trzeba zadeklarować jednocześnie

var time2 = Time.apply(5);                 //tutaj nie jestem pewien, 
                                           //ale chyba time2 dostaje wartość lokalnej zmiennej time1
time2.hour                                 //w sensie sam to napisałem ale nie jestem pewien jak działa xD


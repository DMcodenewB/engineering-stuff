//zadanie 2
//funkcja 2a

def fib (x:Int):Int =
  if (x == 0) 0
  else if (x == 1) 1
  else fib(x-2) + fib(x-1)
  
val a = fib(12)
  
//funkcja 2b

def fibTail (x:Int):Int = {
  def fibTailRec (acc:Int, help1:Int, help2:Int):Int =
    acc match {
    case 0 => help1
    case 1 => help2
    case _ => fibTailRec(acc-1, help2, help1+help2)
  }
fibTailRec(x, 0, 1)
}

val b = fibTail(12)

//zadanie 3



//zadanie 4
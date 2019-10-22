(*zadanie 2

funkcja 2a*)

let rec fib x =
	if x==0 then 0
	else if x==1 then 1
	else fib(x-1) + fib(x-2);;
	
	fib 15;;

(*funkcja 2b*)

let fibTail x =
	let rec fibTailRec(acc, help1, help2) =
		match acc with 
		| 0 -> help1
		| 1 -> help2
		| _ -> fibTailRec(acc-1, help2, help1+help2)
	in fibTailRec(x, 0, 1)
	
	fibTail 20;;

(*zadanie 3*)

(* let findCubeRoot (x, sigma) =     *)
(* 	let rec findCubeRootTail(acc, ) *)

(*zadanie 4*)

let patternMatch1(xs, x) =
	match xs with
	| [_;_;x;_;_] -> true
	| _ -> false
	
let patternMatch2 (xs, x) =
	match xs with
	| [(_,_);(x,_)] -> true
	| _ -> false


let list1 = [-2;-1;0;1;2]
let list2 = [(1,2);(0,1)]

patternMatch1 (list1, 20)
patternMatch2 (list2, 1)

(*kod z zad4 nie działa, za każdym razem daje true*)

(*zadanie 5*)

let rec initSegment(seg, xs) =
	match (seg, xs) with
	| ([], _) -> true
	| (_, []) -> false
	| _ -> if List.hd seg == List.hd xs then initSegment(List.tl seg, List.tl xs)
	 			 else false;;

let segment1 = [2;3;4;5]
let segment2 = [2;3;4;6]
let segment3 = [4;5]
let newlist = [1;2;3;4;5;6]

initSegment(segment1, newlist)
initSegment(segment2, newlist)
initSegment(segment3, newlist)
					






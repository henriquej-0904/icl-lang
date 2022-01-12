def 
  f = fun g:(int)int -> g (10) end
in
  def x = f(fun y:int -> y*2 end)
  in
   println (x+2)
  end
end;;


def comp:((int)int,(int)int)(int)int = fun f:(int)int,g:(int)int-> (fun x:int -> f(g(x))end) end in
        def inc:(int)int = fun x:int -> x + 1 end in
                def dup:(int)int = comp(inc,inc) in
                        dup(2)  end 
                        end
                end;;



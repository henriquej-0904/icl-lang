def comp = fun f:(int)int,g:(int)int-> (fun x:int -> f(g(x))end) end in
        def inc = fun x:int -> x + 1 end in
                def dup = comp(inc,inc) in
                        dup(2)  end 
                        end
                end;;

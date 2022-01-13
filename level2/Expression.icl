def x:ref_int = new 100 f:(int)int = fun x:int -> x+1 end in
        while (!x > 0) do

                if( !x != 3) then        
                        x := f(!x) - 1;
                        if (!x == 0) then
                                1+1;
                                if (1 == 0) then
                                        1
                                end
                        end
                end;
                println ((x := !x - 1))
        end
end;;
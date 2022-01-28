def 
    createCounter = fun x:int -> 
        def x = new x in
            {
                inc = fun -> x := !x + 1 end,

                dec = fun -> x := !x - 1 end,

                double = fun -> x := !x * 2 end,

                add = fun y:int -> x := !x + y end,

                get = fun -> !x end
            }
        end
    end
    
in
    def
        multiplesOf = fun x:int, range1:int, range2:int -> 
            def
                mod = fun dividend:int, divisor:int ->
                    dividend - divisor * (dividend / divisor) end
                
                initCounter = fun ->
                    if range1 <= x then
                        createCounter(x)
                    else
                        def counter = createCounter(range1)
                            found = new (false)
                        in
                            while ( ~(!found) ) do
                                found := counter.inc() > range2 || mod(counter.get(), x) == 0
                            end;
                            counter
                        end
                    end
                end

                counter = initCounter()
            in
                {
                    hasNext = fun -> counter.get() <= range2 end,

                    get = fun -> def toReturn = counter.get() in
                                    counter.add(x);
                                    toReturn
                                end
                          end  
                }
            end
        end
    in
        def
            x = 5
            range1 = 12
            range2 = 51
            multiples5 = multiplesOf(x, range1, range2)
        in
            printf("Multiples of %d between %d and %d:\n", x, range1, range2);

            while (multiples5.hasNext()) do
                println (multiples5.get())
            end;

            println("Finished!")
        end
    end
end;;
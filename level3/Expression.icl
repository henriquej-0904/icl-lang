def 
   o = def x = new 0 in
           [ inc = fun -> x := !x + 1 end,
             get = fun -> !x end
          ]
        end
  in
    o.inc(); o.inc();
     o.get()
end;;

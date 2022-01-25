def
    toString = fun person:record(name:string, age:int) ->
        "Person with name: " + person.name + " and age: " + person.age end
in
    def
        person1 = { name = "Henrique", age = 21 }
        person2 = { name = "José", age = 21 }
    in
        println( toString(person1) );
        println( toString(person2) )
    end
end;;
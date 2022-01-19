# Projeto ICL

Aulas práticas de Interpretação e Compilação de Linguagens.

O projeto foi desenvolvido em diferentes fases, sendo que a versão mais completa é a linguagem de nível 3, disponível na pasta [level3](https://github.com/henriquej-0904/icl-aulas/tree/main/level3).

Trabalho realizado por:

- Henrique Campos Ferreira - 55065
- José Dias - 55015


## Intruções para correr o programa

O programa foi desenvolvido com base no docker, pois permitiu instalarmos todas as ferramentas que precisámos, ex: jasmin, javacc, variáveis de ambiente, etc.

- Compilar o programa ([build.sh](https://github.com/henriquej-0904/icl-aulas/blob/main/level3/build.sh))
- Executar o container com o programa compilado ([run.sh](https://github.com/henriquej-0904/icl-aulas/blob/main/level3/run.sh))

Para correr o programa em modo debug (apresentar os ficheiros \*.j gerados, stack trace dos erros, etc) deve-se executar o script [debug.sh](https://github.com/henriquej-0904/icl-aulas/blob/main/level3/debug.sh). Os ficheiros jasmin gerados a partir de uma compilação estão acessíveis numa pasta [level3](https://github.com/henriquej-0904/icl-aulas/blob/main/level3)/jFiles.
  
## Correr o programa em modo interpretador

Para correr o programa em modo interpretador pode-se utilizar a shell ou fornecer um ficheiro com um programa com o nome [Expression.icl](https://github.com/henriquej-0904/icl-aulas/blob/main/level3/Expression.icl) (tem de ter este nome para estar acessível no container e permite alterações no mesmo sem ser necessário criar um novo container).
  
Exemplo:
```bash
java -jar MathExpression.jar -e Expression.icl

```
## Correr o programa em modo compilador
  
Neste modo, é necessário o mesmo ficheiro [Expression.icl](https://github.com/henriquej-0904/icl-aulas/blob/main/level3/Expression.icl).

Exemplo:
```bash
java -jar MathExpression.jar -c Expression.icl

```

É obrigatório fornecer os tipos dos argumentos de funções (recursivas ou não), mas não é necessário declarar os tipos dos binds numa expressão DEF. Caso sejam declarados os tipos dos binds, o typechecker verifica se o tipo declarado corresponde ao tipo inferido. No exemplo que se segue, são criados 3 binds "func", "x" e "y" em que não são declarados os tipos e um bind "z" em que é verificado se o tipo declarado (bool) corresponde ao tipo inferido (tipo de 'true' -> bool).


```bash
def
  func = fun x:int, y:int -> x+y end
  x = 1
  y = "ola"
  z:bool = true
in
  println (func(1,2))
end

```
  
Ao contrário do interpretador, é necessário indicar se uma função é recursiva, sendo utilizada a keyword **rec**. Além disso, é necessário fornecer o tipo da função, por exemplo:
  
```bash
def
  rec fact:(int)int = fun x:int -> if x == 0 then 1 else x*fact(x-1) end end
in
  println (fact(5))
end

```

Para criar Records deve ser utilizada a seguinte sintaxe: [ id_1:tipo_1 = valor_1, id_1:tipo_1 = valor_1 ]. Tal como nos DEF, o typechecker funciona da mesma forma, ou seja, não é obrigatório fornecer os tipos, exceto na definição de funções recursivas. Um exemplo de utilização de Records é o seguinte:

```bash
def
    toString = fun person:record(name:string, age:int) ->
        "Person with name: " + person.name + " and age: " + person.age end
in
    def
        person1 = [name = "Henrique", age = 21]
        person2 = [name = "José", age = 21]
    in
        println( toString(person1) );
        println( toString(person2) )
    end
end;;

```

## Otimizações do compilador

O programa otimiza a compilação de expressões booleanas quando estas são utilizadas em contruções do tipo IF ou WHILE (Short Circuit).

## Funções de biblioteca

As funções de biblioteca, tal como todas as outras, necessitam de parênteses "(...)" para se passar os argumentos.

- println -> Função que recebe um tipo primitivo e imprime a sua representação em texto.

## Tipos da linguagem

- Integer -> int
- Boolean -> bool
- String -> string
- Funções -> (tipo_arg1, tipo_arg2, ...)tipo_resultado
- Records -> record(id_1:tipo_1, id_2:tipo_2, ...), em que os id's correspondem aos nomes dos campos de um Record.
  
  
  
  
  
  
  
  
  
  
  
  
  

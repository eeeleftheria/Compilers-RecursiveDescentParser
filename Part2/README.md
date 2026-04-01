## To run use Makefile:
```
make compile
make execute1 # takes as input example #1 of 
make execute2
make execute3
make clean
```
execute1, 2 and 3 use as input the example #1, #2 and #3 accordingly.
The result is printed to the Output.java file. My final Java code
is not fully correct, so I redirect the output to a file instead of
printing to stdout, so you can test it by adding manually the "String" 
types in front of the identifiers inside declarations.

## Initial grammar

```
start_list -> definitions calls

identifier -> a-z Tail 
    | A-Z Tail
    | _ Tail

Tail -> a-z Tail
    | A-Z Tail
    | _ Tail
    | 0-9 Tail
    | ε

definitions -> identifier ( parameters ) { exp } definitions

// parameters for a function declaration
parameters -> identifier ParTail

ParTail -> , parameters 
    | ε

exp -> exp + exp
    | str
    | call
    | identifier
    | ifExp 

calls -> identifier ( args_list ) calls

// arguments that we pass when call a function
args_list -> exp 
    | args_list, exp
    | ε


ifExp -> if ( ifCond ) exp else exp

ifCond -> exp prefix exp
    | exp suffix exp

```

While writing the code, obviously a lot of changes were made, especially
regarding the seperation of "outer" and "inner" operations. Specifically, 
in the case of inner calls e.g foo("str", bar()) it was necessary to add
an extra non terminal. This way, when translating into Java code only
the outer foo() call prints the "System.out.println()" needed to print
the output of the call, while bar() is just passed as an identifier
producing a String output. Similarly, I seperated the if conditions
so as only the outer one prints the whole instruction 
cond ? true_case : false_case;

## Conflicts
The main conflict i faced was:
```
Warning : *** Reduce/Reduce conflict found in state #49
  between parameters ::= IDENTIFIER (*) 
  and     exp ::= IDENTIFIER (*) 
  under symbols: {COMMA}
  Resolved in favor of the first production.
```

From my understading, this occurs when the parser has just read   
an identifier and the lookahead is a comma (e.g foo(x, )). In
this case, it does not know where the Identifier came from
(rule arg -> exp -> Identifier inside of a function call or from
parameters -> Identifier inside of a function definition),
thus it does not know which rule to reduce. Since, I did not
manage to fix this issue i finally used the non-terminal args
for both calls and definitions so it can compile. However, this 
way I was also not able to print the proper types before each 
identifier in the final Java code of a function's definition (or 
at least that's what I thought).


## Test cases
In order to test the cases with the "slightly" wrong Java code,
you can add the type String in front of every identifier of every
function declaration.
I used the test cases of https://piazza.com/class/mlr5w0vw9i12lp/post/44 

ToDo/Fix:
    - input8.txt: test with no declerations and no calls
    - input9.txt: test with strings outside of a function body
    - input10.txt: test with no calls

  
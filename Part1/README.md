
[LL(1)-GRAMMAR]

1. exp -> term expTail 
2. expTail -> / exp
3.        | ε
4. term -> factor termTail
5. termTail -> ** factor termTail
6.      | ε
7. factor -> str
8.     | (exp)
9. str -> char strTail 
10. strTail -> str
11.        | ε
12. char -> a-z 
13.     | A-Z

In order to transform the initial grammar to a LL(1) I applied 4 
transformations:
- precendences:  based on the lectures' slides (specifically the 
transformation from slide 9 to slide 19 of the syntax analysis unit), I
created a non-terminal <term> which produces ** deeper in the grammar,
thus with a higher priority. Basically, <term> is evaluated first as part of
the evaluation of <exp> and only after that is the operation '/' 
evaluated.  

1. exp -> term 
2.     | exp / term 
3. term -> term ** factor
4.     | factor
5. factor -> str
6.     | (exp)
7. str -> char 
8.     | char str
9. char -> a-z 
10.     | A-Z

- associativity: ** is already left associative since in can be produced 
through left recursion, while the recursion of rule #2 should be swapped 
since we need <term> to appear on the right side as a right associative so 
the right operations are evaluated first.

1. exp -> term 
2.     | term / exp 
3. term -> term ** factor
4.     | factor
5. factor -> str
6.     | (exp)
7. str -> char 
8.     | char str
9. char -> a-z 
10.     | A-Z

- left factoring: 
FIST(#1) = FIRST(#2) and FIRST(#7) = FIRST(#8), so the first part which is 
similar in more than one rule should be shared in a single rule and then 
the suffix should be determined by the other rules.

- elimination of left recursion:
left recursion appears only in rule #4, since it is of type A->*Aa.

1. exp -> term expTail 
2. expTail -> / exp
3.        | ε
4. term -> factor termTail
5. termTail -> ** factor termTail
6.      | ε
7. factor -> str
8.     | (exp)
9. str -> char strTail 
10. strTail -> str
11.        | ε
12. char -> a-z 
13.     | A-Z


[FIRST/FOLLOW-SETS]

After making our grammar a LL(1) grammar, I calculated the 
FIRST AND FOLLOW sets of each rule and finally, the FIRST+ sets. The 
detailed process can be viewed in the <firstFollowSets.txt> file. 

[LOOKUP-TABLE]

The lookup table can be viewd in the <lookupTable.txt> file.

[EVALUATOR]

My solution for the parser was built upon the TernaryGrammar example we were
given and the lectures' slides. Initially, i started by returning true or
false for every function corresponding to a non-terminal. This way, I only
checked if the given input is part of the grammar or not. I continued by
printing the correct input, which revealed some mistakes with consuming 
characters in the wrong place. 

After doing that, I started the evaluation
process by implementing operator '/'. In order to do that, I wrote a 
<EvalExcl(String s1, String s2)> function that takes two strings and
checks if s2 is a suffix of s1. If so, it keeps the substring of s1 
excluding the suffix and returns it. This function is called inside 
the non-terminal <Exp()> after the recursive calls of <Term()> and
<expTail()>. If <expTail()> has returned a non-empty string it means
that an operation '/' should be applied between the output of <Term()>
(left part of operation) and the output of <expTail()>. This implementation
is valid with the right associativity of the operator, since the outer '/'
is calculated only after the inner ones have been calculated.

Regarding the operator '**', I created a <evalDoubleStar(String s1, Strings2)> 
appending the s2 string twice to the s1. Initially, I tried calling
the function inside <Term()> however this did not work with the left
associativity of the operator. Instead, I put it inside <TermTail()>
where it performs the operation with the factor as the right part
and the string passed by <Term()> as the left part. The result is then
passed as the left side of the operation to the next recursive call of
<TermTail()>. If there is no other character following (meaning 
lookahead is either eof, \n, etc) <TermTail()> return the string as it is.


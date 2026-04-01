all: compile

compile:
	truncate -s 0 Output.java
	./jflex-1.9.1/bin/jflex scanner.flex
	java -jar java-cup-11b.jar -interface -parser Parser parser.cup
	javac -cp java-cup-11b-runtime.jar *.java

execute1:
	java -cp java-cup-11b-runtime.jar:. Main < inputs/input1.txt > Output.java

execute2:
	java -cp java-cup-11b-runtime.jar:. Main < inputs/input2.txt > Output.java
execute3:
	java -cp java-cup-11b-runtime.jar:. Main < inputs/input3.txt > Output.java
execute4:
	java -cp java-cup-11b-runtime.jar:. Main < inputs/input14.txt > Output.java

clean:
	rm -f *.class *~

all: compile

compile:
	./jflex-1.8.2/bin/jflex scanner.flex
	java -jar java-cup-11b.jar -interface -parser Parser parser.cup
	javac -cp java-cup-11b-runtime.jar *.java

execute 1:
	java -cp java-cup-11b-runtime.jar:. Main < inputs/input1.txt

execute 2:
	java -cp java-cup-11b-runtime.jar:. Main < inputs/input2.txt
execute 3:
	java -cp java-cup-11b-runtime.jar:. Main < inputs/input3.txt

clean:
	rm -f *.class *~

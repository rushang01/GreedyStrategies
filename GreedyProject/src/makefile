JAVAC = javac
JAVA = java
SOURCE = GreedyStrategies.java
TARGET = GreedyStrategies.class

default: $(TARGET)

$(TARGET): $(SOURCE)
	$(JAVAC) $(SOURCE)

run1: $(TARGET)
	$(JAVA) GreedyStrategies 1

run2: $(TARGET)
	$(JAVA) GreedyStrategies 2

run3: $(TARGET)
	$(JAVA) GreedyStrategies 3

run4: $(TARGET)
	$(JAVA) GreedyStrategies 4

clean:
	rm -f *.class
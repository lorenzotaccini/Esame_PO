#!/bin/sh

javac -proc:none -cp "lib/*:lib/poi-bin-5.2.3/*:lib/poi-bin-5.2.3/auxiliary/*:lib/poi-bin-5.2.3/lib/*:lib/poi-bin-5.2.3/ooxml-lib/*" -sourcepath src/ -d . src/Main.java

java -cp "lib/*:lib/poi-bin-5.2.3/*:lib/poi-bin-5.2.3/auxiliary/*:lib/poi-bin-5.2.3/lib/*:lib/poi-bin-5.2.3/ooxml-lib/*:." Main

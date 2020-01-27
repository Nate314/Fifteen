:: create bin directory if it does not exist
if not exist "Fifteen/Fifteen/bin" mkdir "Fifteen/Fifteen/bin"
:: compile .java files
javac Fifteen/Fifteen/src/com/nathangawith/umkc/*.java -d Fifteen/Fifteen/bin
:: run java .class files
java -Dfile.encoding=UTF-8 -cp Fifteen/Fifteen/bin com.nathangawith.umkc.Fifteen %1 %2 %3 %4 %5 %6 %7 %8 %9

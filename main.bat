:: ---------------------------------------------------------------------
:: JAP COURSE - SCRIPT - Bang Bang Simulation
:: ASSIGNMENTS - CST8221 - Spring 2023
:: ---------------------------------------------------------------------
:: Begin of Script (Assignments – S23)
:: ---------------------------------------------------------------------
CLS
:: LOCAL VARIABLES .................................................... 
SET JAVAFXDIR=/SOFT/copy/dev/java/javafx/lib 
SET SRCDIR=src 
SET BINDIR=bin
SET BINOUT=game_javac.out
SET BINERR=game_javac.err
SET JARNAME=Game.jar
SET JAROUT=game_jar.out
SET JARERR=game_jar.err
SET DOCDIR=doc
SET DOCPACK=game
SET DOCOUT=game_javadoc.out
SET DOCERR=game_javadoc.err
SET MAINCLASSSRC=src/Main.java
SET MAINCLASSBIN=Main
SET MODULELIST=javafx.controls,javafx.fxml

@echo off 
ECHO " 																	   " 
ECHO "@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@" 
ECHO "@ 																  @" 
ECHO "@ 				  #       @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@  @" 
ECHO "@ 				 ## 	  @ A L G O N Q U I N C O L L E G E    @  @" 
ECHO "@ 				## #      @   JAVA APPLICATION PROGRAMMING     @  @" 
ECHO "@ 			 ###   ##     @       S P R I N G - 2 0 2 3        @  @" 
ECHO "@ 		  ###   ## 		  @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@  @" 
ECHO "@ 		###   ## 												  @" 
ECHO "@ 		## 	 ### 			    ### 							  @" 
ECHO "@ 		 ##   ### 				### 							  @" 
ECHO "@ 		   ## 	## 				###    #####  ##     ##  ##### 	  @" 
ECHO "@ 		 ( 	  ( 	 ((((() 	### 	   ## ### 	### 	 ##   @" 
ECHO "@ 	 (((( 	 (((((((( 	  () 	###    ######  ###  ## 	 ######   @" 
ECHO "@ 		(( 				 () 	###   ##   ## 	## ## 	## 	 ##   @" 
ECHO "@ 		 ((((((((((( ((() 		###    ###### 	 ### 	 ######   @" 
ECHO "@ 		 (( 		(( 		   ### 								  @" 
ECHO "@ 		  ((((((((((( 											  @" 
ECHO "@    ((( 					   (( 		  /-----------------\ 		  @" 
ECHO "@ 	((((((((((((((((((((() )) 		  | BATTLESHIP GAME | 		  @" 
ECHO "@ 		 ((((((((((((((((() 		  \-----------------/ 		  @" 
ECHO "@ 																  @" 
ECHO "@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@" 
ECHO " 																	   " 

ECHO "1. Compiling ......................" 
javac -Xlint -cp ".;%SRCDIR%;%JAVAFXDIR%" %MAINCLASSSRC% -d %BINDIR% > %BINOUT% 2> %BINERR% 

:: ECHO "Running ........................." \
:: start java -cp ".;%BINDIR%;%JAVAFXDIR%/*" %MAINCLASSBIN% 

ECHO "2. Creating Jar ..................." 
cd bin 
jar cvfe %JARNAME% %MAINCLASSBIN% . > %JAROUT% 2> %JARERR% 

ECHO "3. Creating Javadoc ..............." 
cd .. 
javadoc -cp ".;%BINDIR%;%JAVAFXDIR%" --module-path "%JAVAFXDIR%" --add-modules %MODULELIST% -d %DOCDIR% -sourcepath %SRCDIR% -subpackages %DOCPACK% > %DOCOUT% 2> %DOCERR% 
cd bin 

ECHO "4. Running Jar ...................." 
start java --module-path "%JAVAFXDIR%" --add-modules %MODULELIST% -jar %JARNAME% 
cd .. 

ECHO "[END OF SCRIPT -------------------]" 
ECHO " " 

@echo on 
:: --------------------------------------------------------------------- 
:: End of Script (Assignments - S23) 
:: ---------------------------------------------------------------------
pause
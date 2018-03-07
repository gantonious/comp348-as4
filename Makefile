ifeq ($(OS), Windows_NT)
	CLASS_PATH_SEPERATOR = ;
else
	CLASS_PATH_SEPERATOR = :
endif

PACKAGE_NAME = comp346_as4_3364768
PACKAGE_CONTENTS = Makefile *.md $(SOURCE_PATH) $(DOCS_PATH) $(LIBS_PATH)

OUTPUT_PATH = ./out/production/as4
SOURCE_PATH = ./src
DOCS_PATH = ./docs
LIBS_PATH = ./libs
JAVA_MAIL_JAR = $(LIBS_PATH)/javax.mail.jar
CLASS_PATH = $(JAVA_MAIL_JAR)$(CLASS_PATH_SEPERATOR)$(OUTPUT_PATH)

PART1_MAIN = ./src/part1/Main.java
PART2_MAIN = ./src/part2/Main.java
PART3_MAIN = ./src/part3/Main.java

# PROGRAM ARGUMENTS
EMAIL_FILE =
MAIL_SERVER =
EMAIL_ADDRESS =
PASSWORD =
EMAIL_SELECTION =

all: sources

sources: output
	@echo "[=====Building Sources=====]"
	javac -cp $(JAVA_MAIL_JAR) -sourcepath $(SOURCE_PATH) -d $(OUTPUT_PATH) $(PART1_MAIN) $(PART2_MAIN) $(PART3_MAIN)

output:
	@echo "[=====Preparing Output Directory=====]"
	mkdir -p $(OUTPUT_PATH)

runSendEmail: sources
	@echo "[=====Running Send Email=====]"
	java -cp $(CLASS_PATH) part1.Main $(EMAIL_FILE)

runSendEmailWithAttachment: sources
	@echo "[=====Running Send Email with Attachment=====]"
	java -cp $(CLASS_PATH) part2.Main $(EMAIL_FILE)

runEmailReader: sources
	@echo "[=====Running Email Reader=====]"
	java -cp $(CLASS_PATH) part3.Main $(MAIL_SERVER) $(EMAIL_ADDRESS) $(PASSWORD) $(EMAIL_SELECTION)

clean:
	@echo "[=====Cleaning Output=====]"
	rm -rf out

package:
	@mkdir -p $(PACKAGE_NAME)
	@cp -r $(PACKAGE_CONTENTS) $(PACKAGE_NAME)/
	@zip -r $(PACKAGE_NAME).zip $(PACKAGE_NAME)/
	@rm -rf $(PACKAGE_NAME)
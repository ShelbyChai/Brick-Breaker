# Brick_Destroy
This report provides a summary on the major refactoring activities and additions made to the Brick Destroyer Game provided for the Software Maintenance Coursework.

# Major Refactoring Activities
- Extracted Crack class from abstract Brick class
- Created a Levels class by extracting level related content out from Wall class
- Created Brick Factory to replace the switch statement in Levels
- Combined the makeSingleTypeLevel and makeChessboardLevel in Levels class

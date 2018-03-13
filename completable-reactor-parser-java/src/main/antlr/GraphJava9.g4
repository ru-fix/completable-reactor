grammar GraphJava9;


sourceFile
	:	(graphBlock | ignoredToken)+?
	;


graphBlock
    :   payloadTransitionBlock
    |   vertexTransitionBlock
    |   vertexInitializationBlock
    |   vertexAssignmentBlock
    |   vertexCloningBlock
    |   coordinatesBlock
    |   graphDeclarationBlock
    ;

graphDeclarationBlock
    :   classAnnotation* ('public' | 'private' | 'static')? 'class' graphClass 'extends' 'Graph' '<' payloadType '>'
    ;

classAnnotation
    :   '@' Identifier (LPAREN anythingBeforeRParen RPAREN)?
    ;

graphClass
    :   Identifier
    ;

payloadType
    :   Identifier
    ;

vertexAssignmentBlock
    :   'Vertex' vertexName ASSIGN vertexBuilder SEMI
    ;

vertexCloningBlock
    :   'Vertex' vertexName ASSIGN vertexName DOT CLONE LPAREN RPAREN SEMI
    ;

vertexName
    :   anyGraphKeyword
    |   Identifier
    ;

vertexInitializationBlock
    :   'Vertex' Identifier ASSIGN NEW 'Vertex' LPAREN RPAREN vertexInitializationStaticSection SEMI
    ;

vertexInitializationStaticSection
    :   '{' ~'{'* '{' vertexBuilder SEMI '}' '}'
    ;

vertexBuilder
    :   builderHandler
    |   builderSubgraph
    |   builderRouter
    ;

builderSubgraph
    :   SUBGRAPH LPAREN subgraphPayloadClass DOT 'class' anythingBeforeRParen RPAREN DOT builderMerger
    ;

subgraphPayloadClass
    :   Identifier (DOT Identifier)*
    ;

builderRouter
    :   ROUTER LPAREN anythingBeforeRParen RPAREN
    ;

builderHandler
    :   (handler LPAREN anythingBeforeRParen RPAREN DOT builderMerger)
    ;

handler
    :   HANDLER
    ;

builderMerger
    :   builderWithMerger | builderWithoutMerger
    ;

builderWithMerger
    :   ('withMerger' LPAREN anythingBeforeRParen RPAREN)
    ;

builderWithoutMerger
    :   ('withoutMerger' LPAREN RPAREN)
    ;

anythingBeforeRParen
    :   (~RPAREN | ignoreBracesBlock | ignoreParenthesesBlock)*
    ;

anythingBeforeRBrace
    :   (~RBRACE | ignoreBracesBlock | ignoreParenthesesBlock)*
    ;

ignoreBracesBlock
    :   LBRACE (~RBRACE | ignoreBracesBlock)* RBRACE
    ;

ignoreParenthesesBlock
    :   LPAREN (~RPAREN | ignoreParenthesesBlock)* RPAREN
    ;



payloadTransitionBlock
    :	PAYLOAD LPAREN RPAREN handleBy+ SEMI
    ;

handleBy
    :	(DOT 'handleBy' LPAREN vertexName RPAREN)
    ;


vertexTransitionBlock
    :	vertexName vertexTransition+ SEMI
    ;

vertexTransition
    :   vertexTransitionOn
    |   vertexTransitionOnAny
    ;

vertexTransitionOn
    :   DOT 'on' LPAREN transitionCondition RPAREN transitionAction
    ;
vertexTransitionOnAny
    :   DOT 'onAny' LPAREN RPAREN transitionAction
    ;

transitionAction
    :   transitionActionComplete
    |   transitionActionMergeBy
    |   transitionActionHandleBy
    ;

transitionActionComplete
    :   DOT COMPLETE LPAREN RPAREN
    ;

transitionActionMergeBy
    :   DOT 'mergeBy' LPAREN vertexName RPAREN
    ;

transitionActionHandleBy
    :   DOT 'handleBy' LPAREN vertexName RPAREN
    ;

coordinatesBlock
    :   'coordinates' LPAREN RPAREN coordinate* SEMI
    ;

coordinate
    :   coordinatePayload
    |   coordinateHandler
    |   coordinateSubgraph
    |   coordinateRouter
    |   coordinateMerger
    |   coordinateComplete
    ;

coordinatePayload : DOT PAYLOAD LPAREN Coordinate COMMA Coordinate RPAREN;
coordinateHandler : DOT HANDLER LPAREN Identifier COMMA Coordinate COMMA Coordinate RPAREN;
coordinateSubgraph : DOT SUBGRAPH LPAREN Identifier COMMA Coordinate COMMA Coordinate RPAREN;
coordinateRouter : DOT ROUTER LPAREN Identifier COMMA Coordinate COMMA Coordinate RPAREN;
coordinateMerger : DOT MERGER LPAREN Identifier COMMA Coordinate COMMA Coordinate RPAREN;
coordinateComplete : DOT COMPLETE LPAREN Identifier COMMA Coordinate COMMA Coordinate RPAREN;

Coordinate: '-'? DecimalIntegerLiteral;

transitionCondition
    :   Identifier (DOT Identifier)*
    ;

ignoredToken
    :   .
    ;


SUBGRAPH : 'subgraph';
MERGER : 'merger';
HANDLER : 'handler';
PAYLOAD : 'payload';
COMPLETE : 'complete';
ROUTER : 'router';
CLONE : 'clone';


anyGraphKeyword
    :   SUBGRAPH
    |   MERGER
    |   HANDLER
    |   PAYLOAD
    |   COMPLETE
    |   ROUTER
    |   CLONE
    ;




// §3.10.1 Integer Literals

IntegerLiteral
	:	DecimalIntegerLiteral
	|	HexIntegerLiteral
	|	OctalIntegerLiteral
	|	BinaryIntegerLiteral
	;

fragment
DecimalIntegerLiteral
	:	DecimalNumeral IntegerTypeSuffix?
	;

fragment
HexIntegerLiteral
	:	HexNumeral IntegerTypeSuffix?
	;

fragment
OctalIntegerLiteral
	:	OctalNumeral IntegerTypeSuffix?
	;

fragment
BinaryIntegerLiteral
	:	BinaryNumeral IntegerTypeSuffix?
	;

fragment
IntegerTypeSuffix
	:	[lL]
	;

fragment
DecimalNumeral
	:	'0'
	|	NonZeroDigit (Digits? | Underscores Digits)
	;

fragment
Digits
	:	Digit (DigitsAndUnderscores? Digit)?
	;

fragment
Digit
	:	'0'
	|	NonZeroDigit
	;

fragment
NonZeroDigit
	:	[1-9]
	;

fragment
DigitsAndUnderscores
	:	DigitOrUnderscore+
	;

fragment
DigitOrUnderscore
	:	Digit
	|	'_'
	;

fragment
Underscores
	:	'_'+
	;

fragment
HexNumeral
	:	'0' [xX] HexDigits
	;

fragment
HexDigits
	:	HexDigit (HexDigitsAndUnderscores? HexDigit)?
	;

fragment
HexDigit
	:	[0-9a-fA-F]
	;

fragment
HexDigitsAndUnderscores
	:	HexDigitOrUnderscore+
	;

fragment
HexDigitOrUnderscore
	:	HexDigit
	|	'_'
	;

fragment
OctalNumeral
	:	'0' Underscores? OctalDigits
	;

fragment
OctalDigits
	:	OctalDigit (OctalDigitsAndUnderscores? OctalDigit)?
	;

fragment
OctalDigit
	:	[0-7]
	;

fragment
OctalDigitsAndUnderscores
	:	OctalDigitOrUnderscore+
	;

fragment
OctalDigitOrUnderscore
	:	OctalDigit
	|	'_'
	;

fragment
BinaryNumeral
	:	'0' [bB] BinaryDigits
	;

fragment
BinaryDigits
	:	BinaryDigit (BinaryDigitsAndUnderscores? BinaryDigit)?
	;

fragment
BinaryDigit
	:	[01]
	;

fragment
BinaryDigitsAndUnderscores
	:	BinaryDigitOrUnderscore+
	;

fragment
BinaryDigitOrUnderscore
	:	BinaryDigit
	|	'_'
	;

// §3.10.2 Floating-Point Literals

FloatingPointLiteral
	:	DecimalFloatingPointLiteral
	|	HexadecimalFloatingPointLiteral
	;

fragment
DecimalFloatingPointLiteral
	:	Digits '.' Digits? ExponentPart? FloatTypeSuffix?
	|	'.' Digits ExponentPart? FloatTypeSuffix?
	|	Digits ExponentPart FloatTypeSuffix?
	|	Digits FloatTypeSuffix
	;

fragment
ExponentPart
	:	ExponentIndicator SignedInteger
	;

fragment
ExponentIndicator
	:	[eE]
	;

fragment
SignedInteger
	:	Sign? Digits
	;

fragment
Sign
	:	[+-]
	;

fragment
FloatTypeSuffix
	:	[fFdD]
	;

fragment
HexadecimalFloatingPointLiteral
	:	HexSignificand BinaryExponent FloatTypeSuffix?
	;

fragment
HexSignificand
	:	HexNumeral '.'?
	|	'0' [xX] HexDigits? '.' HexDigits
	;

fragment
BinaryExponent
	:	BinaryExponentIndicator SignedInteger
	;

fragment
BinaryExponentIndicator
	:	[pP]
	;

// §3.10.3 Boolean Literals

BooleanLiteral
	:	'true'
	|	'false'
	;

// §3.10.4 Character Literals

CharacterLiteral
	:	'\'' SingleCharacter '\''
	|	'\'' EscapeSequence '\''
	;

fragment
SingleCharacter
	:	~['\\\r\n]
	;

// §3.10.5 String Literals

StringLiteral
	:	'"' StringCharacters? '"'
	;

fragment
StringCharacters
	:	StringCharacter+
	;

fragment
StringCharacter
	:	~["\\\r\n]
	|	EscapeSequence
	;

// §3.10.6 Escape Sequences for Character and String Literals

fragment
EscapeSequence
	:	'\\' [btnfr"'\\]
	|	OctalEscape
    |   UnicodeEscape // This is not in the spec but prevents having to preprocess the input
	;

fragment
OctalEscape
	:	'\\' OctalDigit
	|	'\\' OctalDigit OctalDigit
	|	'\\' ZeroToThree OctalDigit OctalDigit
	;

fragment
ZeroToThree
	:	[0-3]
	;

// This is not in the spec but prevents having to preprocess the input
fragment
UnicodeEscape
    :   '\\' 'u'+ HexDigit HexDigit HexDigit HexDigit
    ;

// §3.10.7 The Null Literal

NullLiteral
	:	'null'
	;

// §3.11 Separators

LPAREN : '(';
RPAREN : ')';
LBRACE : '{';
RBRACE : '}';
LBRACK : '[';
RBRACK : ']';
SEMI : ';';
COMMA : ',';
DOT : '.';
ELLIPSIS : '...';
AT : '@';
COLONCOLON : '::';


// §3.12 Operators

ASSIGN : '=';
GT : '>';
LT : '<';
BANG : '!';
TILDE : '~';
QUESTION : '?';
COLON : ':';
ARROW : '->';
EQUAL : '==';
LE : '<=';
GE : '>=';
NOTEQUAL : '!=';
AND : '&&';
OR : '||';
INC : '++';
DEC : '--';
ADD : '+';
SUB : '-';
MUL : '*';
DIV : '/';
BITAND : '&';
BITOR : '|';
CARET : '^';
MOD : '%';
//LSHIFT : '<<';
//RSHIFT : '>>';
//URSHIFT : '>>>';

ADD_ASSIGN : '+=';
SUB_ASSIGN : '-=';
MUL_ASSIGN : '*=';
DIV_ASSIGN : '/=';
AND_ASSIGN : '&=';
OR_ASSIGN : '|=';
XOR_ASSIGN : '^=';
MOD_ASSIGN : '%=';
LSHIFT_ASSIGN : '<<=';
RSHIFT_ASSIGN : '>>=';
URSHIFT_ASSIGN : '>>>=';

// §3.8 Identifiers (must appear after all keywords in the grammar)

Identifier
	:	JavaLetter JavaLetterOrDigit*
	;

fragment
JavaLetter
	:	[a-zA-Z$_] // these are the "java letters" below 0x7F
	|	// covers all characters above 0x7F which are not a surrogate
		~[\u0000-\u007F\uD800-\uDBFF]
		{Character.isJavaIdentifierStart(_input.LA(-1))}?
	|	// covers UTF-16 surrogate pairs encodings for U+10000 to U+10FFFF
		[\uD800-\uDBFF] [\uDC00-\uDFFF]
		{Character.isJavaIdentifierStart(Character.toCodePoint((char)_input.LA(-2), (char)_input.LA(-1)))}?
	;

fragment
JavaLetterOrDigit
	:	[a-zA-Z0-9$_] // these are the "java letters or digits" below 0x7F
	|	// covers all characters above 0x7F which are not a surrogate
		~[\u0000-\u007F\uD800-\uDBFF]
		{Character.isJavaIdentifierPart(_input.LA(-1))}?
	|	// covers UTF-16 surrogate pairs encodings for U+10000 to U+10FFFF
		[\uD800-\uDBFF] [\uDC00-\uDFFF]
		{Character.isJavaIdentifierPart(Character.toCodePoint((char)_input.LA(-2), (char)_input.LA(-1)))}?
	;

//
// Whitespace and comments
//

WS  :  [ \t\r\n\u000C]+ -> skip
    ;

COMMENT
    :   '/*' .*? '*/' -> channel(HIDDEN)
    ;

LINE_COMMENT
    :   '//' ~[\r\n]* -> channel(HIDDEN)
    ;

OTHER_SYMBOL : .;
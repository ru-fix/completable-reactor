grammar GraphJava9;


sourceFile
	:	(graphBlock | ignoredToken)+?
	;


graphBlock
    :   payloadTransitionBlock
    |   vertexTransitionBlock
    |   vertexAssignmentBlock
    |   vertexCloningBlock
    |   coordinatesBlock
    |   graphDeclarationBlock
    ;

graphDeclarationBlock
    :   classAnnotation*
        ('public' | 'private' | 'static' | 'open' | 'sealed')*
        'class' graphClass ('extends' | ':') 'Graph' '<' payloadType '>'
    ;

classAnnotation
    :   AT Identifier (LPAREN anythingBeforeRParen RPAREN)?
    ;

graphClass
    :   Identifier
    ;

payloadType
    :   Identifier
    ;

vertexAssignmentBlock
    :   (VERTEX | 'val') vertexName ASSIGN vertexBuilder SEMI?
    ;

vertexCloningBlock
    :   (VERTEX | 'val') vertexName ASSIGN vertexName DOT CLONE LPAREN RPAREN SEMI?
    ;

vertexName
    :   anyGraphKeyword
    |   Identifier
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
    :   handler
        (LPAREN anythingBeforeRParen RPAREN DOT builderMerger)
    |   (LBRACE anythingBeforeRBrace RBRACE DOT builderMerger)
    ;

handler
    :   HANDLER
    ;

builderMerger
    :   builderWithMerger | builderWithoutMerger
    ;

builderWithMerger
    :   'withMerger' (LPAREN anythingBeforeRParen RPAREN) | (LBRACE anythingBeforeRBrace RBRACE)
    ;

builderWithoutMerger
    :   'withoutMerger' LPAREN RPAREN
    ;

anythingBeforeRParen
    :   (ignoreParenthesesBlock | ignoreBracesBlock | ~(LPAREN|RPAREN))*
    ;

anythingBeforeRBrace
    :   (ignoreBracesBlock | ignoreBracesBlock | ~(LPAREN|RPAREN))*
    ;

ignoreBracesBlock
    :   LBRACE ( ~(LBRACE|RBRACE) | ignoreBracesBlock )* RBRACE
    ;

ignoreParenthesesBlock
    :   LPAREN ( ~(LPAREN|RPAREN) | ignoreParenthesesBlock )* RPAREN
    ;



payloadTransitionBlock
    :	PAYLOAD LPAREN RPAREN handleBy+ SEMI?
    ;

handleBy
    :	(DOT 'handleBy' LPAREN vertexName RPAREN)
    ;


vertexTransitionBlock
    :	vertexName vertexTransition+ SEMI?
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
    :   'coordinates' LPAREN RPAREN coordinate* SEMI?
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

Coordinate: '-'? DecimalNumeral;

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
VERTEX : 'Vertex';

anyGraphKeyword
    :   SUBGRAPH
    |   MERGER
    |   HANDLER
    |   PAYLOAD
    |   COMPLETE
    |   ROUTER
    |   CLONE
    ;

DecimalNumeral : [0-9_]+;

LPAREN : '(';
RPAREN : ')';
LBRACE : '{';
RBRACE : '}';

SEMI : ';';
COMMA : ',';
DOT : '.';
AT : '@';
ASSIGN : '=';
NEW : 'new';


Identifier
	:	(JavaLetter JavaLetterOrDigit*)
	|   '`' ~('`')+ '`'
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
grammar Graph;


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
    |   vertexBuilderFunctionDefinition
    ;

graphDeclarationBlock
    :   classAnnotation* classModifier* 'class' graphClass
        (classModifier | classAnnotation)* 'constructor'? (LPAREN anythingBeforeRParen RPAREN)?
        ('extends' | ':') 'Graph' '<' payloadType '>'
    ;

classModifier
    :   'public'
    |   'protected'
    |   'private'
    |   'abstract'
    |   'static'
    |   'final'
    |   'open'
    |   'sealed'
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
    :   (VERTEX | 'val' | 'var') vertexName ASSIGN (vertexBuilder | vertexBuilderFunctionInvocation) SEMI?
    ;

vertexCloningBlock
    :   (VERTEX | 'val' | 'var') vertexName ASSIGN vertexName DOT CLONE LPAREN RPAREN SEMI?
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
    :   SUBGRAPH LPAREN subgraphPayloadClass ((DOT 'class')|(COLONCOLON 'class')) anythingBeforeRParen RPAREN
        (LBRACE anythingBeforeRBrace RBRACE)?
        DOT builderMerger
    ;

subgraphPayloadClass
    :   Identifier (DOT Identifier)*
    ;

builderRouter
    :   (ROUTER | MUTATOR)
        ((LPAREN anythingBeforeRParen RPAREN) | (LBRACE anythingBeforeRBrace RBRACE))
    ;

vertexBuilderFunctionInvocation
    :   Identifier LPAREN anythingBeforeRParen RPAREN
    ;

vertexBuilderFunctionDefinition
    :   (VERTEX | 'fun')? Identifier LPAREN anythingBeforeRParen RPAREN
        vertexBuilderFuncitonDefinitionBody
    ;

vertexBuilderFuncitonDefinitionBody
    :   LBRACE
        ( vertexBuilder | vertexBuilderFuncitonDefinitionBody | ~RBRACE )*
        RBRACE
    ;

builderHandler
    :   handler
        ((LPAREN anythingBeforeRParen RPAREN) | (LBRACE anythingBeforeRBrace RBRACE))
        DOT builderMerger
    ;

handler
    :   HANDLER
    |   SUSPEND_HANDLER
   ;

builderMerger
    :   builderWithMerger | builderWithoutMerger | builderWithEmptyMerger
    ;

builderWithMerger
    :   ('withMerger' | 'withRoutingMerger')
        ((LPAREN anythingBeforeRParen RPAREN) | (LBRACE anythingBeforeRBrace RBRACE))
    ;

builderWithoutMerger
    :   'withoutMerger' LPAREN RPAREN
    ;

builderWithEmptyMerger
    :   'withEmptyMerger' LPAREN RPAREN
    ;

anythingBeforeRParen
    :   (ignoreParenthesesBlock | ignoreBracesBlock | ~(LPAREN|RPAREN))*
    ;

anythingBeforeRBrace
    :   (ignoreBracesBlock | ignoreParenthesesBlock | ~(LBRACE|RBRACE))*
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
    |   coordinateVertex
    |   coordinateComplete
    ;

coordinatePayload : DOT (PAYLOAD | 'pd') LPAREN Coordinate COMMA Coordinate RPAREN;
coordinateHandler : DOT HANDLER LPAREN Identifier COMMA Coordinate COMMA Coordinate RPAREN;
coordinateSubgraph : DOT SUBGRAPH LPAREN Identifier COMMA Coordinate COMMA Coordinate RPAREN;
coordinateRouter : DOT ROUTER LPAREN Identifier COMMA Coordinate COMMA Coordinate RPAREN;
coordinateMerger : DOT MERGER LPAREN Identifier COMMA Coordinate COMMA Coordinate RPAREN;
coordinateVertex : DOT 'vx' LPAREN Identifier COMMA Coordinate COMMA Coordinate (COMMA Coordinate COMMA Coordinate)? RPAREN;
coordinateComplete : DOT (COMPLETE | 'ct') LPAREN Identifier COMMA Coordinate COMMA Coordinate RPAREN;

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
SUSPEND_HANDLER : 'suspendHandler';
PAYLOAD : 'payload';
COMPLETE : 'complete';
ROUTER : 'router';
MUTATOR : 'mutator';
CLONE : 'clone';
VERTEX : 'Vertex';

anyGraphKeyword
    :   SUBGRAPH
    |   MERGER
    |   HANDLER
    |   PAYLOAD
    |   COMPLETE
    |   'pd'
    |   'vx'
    |   'ct'
    |   ROUTER
    |   MUTATOR
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
COLONCOLON : '::';


StringLiteral
	:	('"' StringCharacter* '"')
	|   ('"""' .*? '"""')
	;

fragment
StringCharacter
	:	~["]
	|	'\\"'
	;


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
parser grammar GraphKotlinParser;

options { tokenVocab = GraphKotlinLexer; }


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
    :   classAnnotation* (PUBLIC | PRIVATE | OPEN)* CLASS graphClass COLON GRAPH LANGLE payloadType RANGLE LPAREN RPAREN
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
    :   VERTEX vertexName ASSIGN vertexBuilder SEMI
    ;

vertexCloningBlock
    :   VERTEX vertexName ASSIGN vertexName DOT CLONE LPAREN RPAREN SEMI
    ;

vertexName
    :   anyGraphKeyword
    |   Identifier
    ;

vertexInitializationBlock
    :   VERTEX Identifier ASSIGN NEW VERTEX LPAREN RPAREN vertexInitializationStaticSection SEMI
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
    :   SUBGRAPH LPAREN subgraphPayloadClass DOT CLASS anythingBeforeRParen RPAREN DOT builderMerger
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
    :   (WITH_MERGER LPAREN anythingBeforeRParen RPAREN)
    ;

builderWithoutMerger
    :   (WITHOUT_MERGER LPAREN RPAREN)
    ;

anythingBeforeRParen
    :   (~RPAREN | ignoreBracesBlock | ignoreParenthesesBlock)+
    ;

anythingBeforeRBrace
    :   (~RBRACE | ignoreBracesBlock | ignoreParenthesesBlock)+
    ;

ignoreBracesBlock
    :   LBRACE (~RBRACE | ignoreBracesBlock)* RBRACE
    ;

ignoreParenthesesBlock
    :   LPAREN (~RPAREN | ignoreParenthesesBlock)* RPAREN
    ;



payloadTransitionBlock
    :	PAYLOAD LPAREN RPAREN handleBy+ SEMI?
    ;

handleBy
    :	(DOT HANDLE_BY LPAREN vertexName RPAREN)
    ;


vertexTransitionBlock
    :	vertexName vertexTransition+ SEMI?
    ;

vertexTransition
    :   vertexTransitionOn
    |   vertexTransitionOnAny
    ;

vertexTransitionOn
    :   DOT ON LPAREN transitionCondition RPAREN transitionAction
    ;
vertexTransitionOnAny
    :   DOT ON_ANY LPAREN RPAREN transitionAction
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
    :   DOT MERGE_BY LPAREN vertexName RPAREN
    ;

transitionActionHandleBy
    :   DOT HANDLE_BY LPAREN vertexName RPAREN
    ;

coordinatesBlock
    :   COORDINATES LPAREN RPAREN coordinate* SEMI
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

transitionCondition
    :   Identifier (DOT Identifier)*
    ;

ignoredToken
    :   .
    ;

anyGraphKeyword
    :   SUBGRAPH
    |   MERGER
    |   HANDLER
    |   PAYLOAD
    |   COMPLETE
    |   ROUTER
    |   CLONE
    ;



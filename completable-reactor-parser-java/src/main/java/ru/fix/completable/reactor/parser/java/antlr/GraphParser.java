// Generated from Graph.g4 by ANTLR 4.7.1
package ru.fix.completable.reactor.parser.java.antlr;
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.misc.*;
import org.antlr.v4.runtime.tree.*;
import java.util.List;
import java.util.Iterator;
import java.util.ArrayList;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class GraphParser extends Parser {
	static { RuntimeMetaData.checkVersion("4.7.1", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		T__0=1, T__1=2, T__2=3, T__3=4, T__4=5, T__5=6, T__6=7, T__7=8, T__8=9, 
		T__9=10, T__10=11, T__11=12, T__12=13, T__13=14, T__14=15, T__15=16, T__16=17, 
		T__17=18, T__18=19, T__19=20, T__20=21, Coordinate=22, SUBGRAPH=23, MERGER=24, 
		HANDLER=25, SUSPEND_HANDLER=26, PAYLOAD=27, COMPLETE=28, ROUTER=29, CLONE=30, 
		VERTEX=31, DecimalNumeral=32, LPAREN=33, RPAREN=34, LBRACE=35, RBRACE=36, 
		SEMI=37, COMMA=38, DOT=39, AT=40, ASSIGN=41, NEW=42, COLONCOLON=43, StringLiteral=44, 
		Identifier=45, WS=46, COMMENT=47, LINE_COMMENT=48, OTHER_SYMBOL=49;
	public static final int
		RULE_sourceFile = 0, RULE_graphBlock = 1, RULE_graphDeclarationBlock = 2, 
		RULE_classAnnotation = 3, RULE_graphClass = 4, RULE_payloadType = 5, RULE_vertexAssignmentBlock = 6, 
		RULE_vertexCloningBlock = 7, RULE_vertexName = 8, RULE_vertexBuilder = 9, 
		RULE_builderSubgraph = 10, RULE_subgraphPayloadClass = 11, RULE_builderRouter = 12, 
		RULE_builderHandler = 13, RULE_handler = 14, RULE_builderMerger = 15, 
		RULE_builderWithMerger = 16, RULE_builderWithoutMerger = 17, RULE_builderWithEmptyMerger = 18, 
		RULE_anythingBeforeRParen = 19, RULE_anythingBeforeRBrace = 20, RULE_ignoreBracesBlock = 21, 
		RULE_ignoreParenthesesBlock = 22, RULE_payloadTransitionBlock = 23, RULE_handleBy = 24, 
		RULE_vertexTransitionBlock = 25, RULE_vertexTransition = 26, RULE_vertexTransitionOn = 27, 
		RULE_vertexTransitionOnAny = 28, RULE_transitionAction = 29, RULE_transitionActionComplete = 30, 
		RULE_transitionActionMergeBy = 31, RULE_transitionActionHandleBy = 32, 
		RULE_coordinatesBlock = 33, RULE_coordinate = 34, RULE_coordinatePayload = 35, 
		RULE_coordinateHandler = 36, RULE_coordinateSubgraph = 37, RULE_coordinateRouter = 38, 
		RULE_coordinateMerger = 39, RULE_coordinateComplete = 40, RULE_transitionCondition = 41, 
		RULE_ignoredToken = 42, RULE_anyGraphKeyword = 43;
	public static final String[] ruleNames = {
		"sourceFile", "graphBlock", "graphDeclarationBlock", "classAnnotation", 
		"graphClass", "payloadType", "vertexAssignmentBlock", "vertexCloningBlock", 
		"vertexName", "vertexBuilder", "builderSubgraph", "subgraphPayloadClass", 
		"builderRouter", "builderHandler", "handler", "builderMerger", "builderWithMerger", 
		"builderWithoutMerger", "builderWithEmptyMerger", "anythingBeforeRParen", 
		"anythingBeforeRBrace", "ignoreBracesBlock", "ignoreParenthesesBlock", 
		"payloadTransitionBlock", "handleBy", "vertexTransitionBlock", "vertexTransition", 
		"vertexTransitionOn", "vertexTransitionOnAny", "transitionAction", "transitionActionComplete", 
		"transitionActionMergeBy", "transitionActionHandleBy", "coordinatesBlock", 
		"coordinate", "coordinatePayload", "coordinateHandler", "coordinateSubgraph", 
		"coordinateRouter", "coordinateMerger", "coordinateComplete", "transitionCondition", 
		"ignoredToken", "anyGraphKeyword"
	};

	private static final String[] _LITERAL_NAMES = {
		null, "'public'", "'private'", "'static'", "'open'", "'sealed'", "'class'", 
		"'extends'", "':'", "'Graph'", "'<'", "'>'", "'val'", "'var'", "'withMerger'", 
		"'withoutMerger'", "'withEmptyMerger'", "'handleBy'", "'on'", "'onAny'", 
		"'mergeBy'", "'coordinates'", null, "'subgraph'", "'merger'", "'handler'", 
		"'suspendHandler'", "'payload'", "'complete'", "'router'", "'clone'", 
		"'Vertex'", null, "'('", "')'", "'{'", "'}'", "';'", "','", "'.'", "'@'", 
		"'='", "'new'", "'::'"
	};
	private static final String[] _SYMBOLIC_NAMES = {
		null, null, null, null, null, null, null, null, null, null, null, null, 
		null, null, null, null, null, null, null, null, null, null, "Coordinate", 
		"SUBGRAPH", "MERGER", "HANDLER", "SUSPEND_HANDLER", "PAYLOAD", "COMPLETE", 
		"ROUTER", "CLONE", "VERTEX", "DecimalNumeral", "LPAREN", "RPAREN", "LBRACE", 
		"RBRACE", "SEMI", "COMMA", "DOT", "AT", "ASSIGN", "NEW", "COLONCOLON", 
		"StringLiteral", "Identifier", "WS", "COMMENT", "LINE_COMMENT", "OTHER_SYMBOL"
	};
	public static final Vocabulary VOCABULARY = new VocabularyImpl(_LITERAL_NAMES, _SYMBOLIC_NAMES);

	/**
	 * @deprecated Use {@link #VOCABULARY} instead.
	 */
	@Deprecated
	public static final String[] tokenNames;
	static {
		tokenNames = new String[_SYMBOLIC_NAMES.length];
		for (int i = 0; i < tokenNames.length; i++) {
			tokenNames[i] = VOCABULARY.getLiteralName(i);
			if (tokenNames[i] == null) {
				tokenNames[i] = VOCABULARY.getSymbolicName(i);
			}

			if (tokenNames[i] == null) {
				tokenNames[i] = "<INVALID>";
			}
		}
	}

	@Override
	@Deprecated
	public String[] getTokenNames() {
		return tokenNames;
	}

	@Override

	public Vocabulary getVocabulary() {
		return VOCABULARY;
	}

	@Override
	public String getGrammarFileName() { return "Graph.g4"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public ATN getATN() { return _ATN; }

	public GraphParser(TokenStream input) {
		super(input);
		_interp = new ParserATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}
	public static class SourceFileContext extends ParserRuleContext {
		public List<GraphBlockContext> graphBlock() {
			return getRuleContexts(GraphBlockContext.class);
		}
		public GraphBlockContext graphBlock(int i) {
			return getRuleContext(GraphBlockContext.class,i);
		}
		public List<IgnoredTokenContext> ignoredToken() {
			return getRuleContexts(IgnoredTokenContext.class);
		}
		public IgnoredTokenContext ignoredToken(int i) {
			return getRuleContext(IgnoredTokenContext.class,i);
		}
		public SourceFileContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_sourceFile; }
	}

	public final SourceFileContext sourceFile() throws RecognitionException {
		SourceFileContext _localctx = new SourceFileContext(_ctx, getState());
		enterRule(_localctx, 0, RULE_sourceFile);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(90); 
			_errHandler.sync(this);
			_alt = 1+1;
			do {
				switch (_alt) {
				case 1+1:
					{
					setState(90);
					_errHandler.sync(this);
					switch ( getInterpreter().adaptivePredict(_input,0,_ctx) ) {
					case 1:
						{
						setState(88);
						graphBlock();
						}
						break;
					case 2:
						{
						setState(89);
						ignoredToken();
						}
						break;
					}
					}
					break;
				default:
					throw new NoViableAltException(this);
				}
				setState(92); 
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,1,_ctx);
			} while ( _alt!=1 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER );
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class GraphBlockContext extends ParserRuleContext {
		public PayloadTransitionBlockContext payloadTransitionBlock() {
			return getRuleContext(PayloadTransitionBlockContext.class,0);
		}
		public VertexTransitionBlockContext vertexTransitionBlock() {
			return getRuleContext(VertexTransitionBlockContext.class,0);
		}
		public VertexAssignmentBlockContext vertexAssignmentBlock() {
			return getRuleContext(VertexAssignmentBlockContext.class,0);
		}
		public VertexCloningBlockContext vertexCloningBlock() {
			return getRuleContext(VertexCloningBlockContext.class,0);
		}
		public CoordinatesBlockContext coordinatesBlock() {
			return getRuleContext(CoordinatesBlockContext.class,0);
		}
		public GraphDeclarationBlockContext graphDeclarationBlock() {
			return getRuleContext(GraphDeclarationBlockContext.class,0);
		}
		public GraphBlockContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_graphBlock; }
	}

	public final GraphBlockContext graphBlock() throws RecognitionException {
		GraphBlockContext _localctx = new GraphBlockContext(_ctx, getState());
		enterRule(_localctx, 2, RULE_graphBlock);
		try {
			setState(100);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,2,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(94);
				payloadTransitionBlock();
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(95);
				vertexTransitionBlock();
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(96);
				vertexAssignmentBlock();
				}
				break;
			case 4:
				enterOuterAlt(_localctx, 4);
				{
				setState(97);
				vertexCloningBlock();
				}
				break;
			case 5:
				enterOuterAlt(_localctx, 5);
				{
				setState(98);
				coordinatesBlock();
				}
				break;
			case 6:
				enterOuterAlt(_localctx, 6);
				{
				setState(99);
				graphDeclarationBlock();
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class GraphDeclarationBlockContext extends ParserRuleContext {
		public GraphClassContext graphClass() {
			return getRuleContext(GraphClassContext.class,0);
		}
		public PayloadTypeContext payloadType() {
			return getRuleContext(PayloadTypeContext.class,0);
		}
		public List<ClassAnnotationContext> classAnnotation() {
			return getRuleContexts(ClassAnnotationContext.class);
		}
		public ClassAnnotationContext classAnnotation(int i) {
			return getRuleContext(ClassAnnotationContext.class,i);
		}
		public GraphDeclarationBlockContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_graphDeclarationBlock; }
	}

	public final GraphDeclarationBlockContext graphDeclarationBlock() throws RecognitionException {
		GraphDeclarationBlockContext _localctx = new GraphDeclarationBlockContext(_ctx, getState());
		enterRule(_localctx, 4, RULE_graphDeclarationBlock);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(105);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==AT) {
				{
				{
				setState(102);
				classAnnotation();
				}
				}
				setState(107);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(111);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__0) | (1L << T__1) | (1L << T__2) | (1L << T__3) | (1L << T__4))) != 0)) {
				{
				{
				setState(108);
				_la = _input.LA(1);
				if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__0) | (1L << T__1) | (1L << T__2) | (1L << T__3) | (1L << T__4))) != 0)) ) {
				_errHandler.recoverInline(this);
				}
				else {
					if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
					_errHandler.reportMatch(this);
					consume();
				}
				}
				}
				setState(113);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(114);
			match(T__5);
			setState(115);
			graphClass();
			setState(116);
			_la = _input.LA(1);
			if ( !(_la==T__6 || _la==T__7) ) {
			_errHandler.recoverInline(this);
			}
			else {
				if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
				_errHandler.reportMatch(this);
				consume();
			}
			setState(117);
			match(T__8);
			setState(118);
			match(T__9);
			setState(119);
			payloadType();
			setState(120);
			match(T__10);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ClassAnnotationContext extends ParserRuleContext {
		public TerminalNode AT() { return getToken(GraphParser.AT, 0); }
		public TerminalNode Identifier() { return getToken(GraphParser.Identifier, 0); }
		public TerminalNode LPAREN() { return getToken(GraphParser.LPAREN, 0); }
		public AnythingBeforeRParenContext anythingBeforeRParen() {
			return getRuleContext(AnythingBeforeRParenContext.class,0);
		}
		public TerminalNode RPAREN() { return getToken(GraphParser.RPAREN, 0); }
		public ClassAnnotationContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_classAnnotation; }
	}

	public final ClassAnnotationContext classAnnotation() throws RecognitionException {
		ClassAnnotationContext _localctx = new ClassAnnotationContext(_ctx, getState());
		enterRule(_localctx, 6, RULE_classAnnotation);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(122);
			match(AT);
			setState(123);
			match(Identifier);
			setState(128);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==LPAREN) {
				{
				setState(124);
				match(LPAREN);
				setState(125);
				anythingBeforeRParen();
				setState(126);
				match(RPAREN);
				}
			}

			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class GraphClassContext extends ParserRuleContext {
		public TerminalNode Identifier() { return getToken(GraphParser.Identifier, 0); }
		public GraphClassContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_graphClass; }
	}

	public final GraphClassContext graphClass() throws RecognitionException {
		GraphClassContext _localctx = new GraphClassContext(_ctx, getState());
		enterRule(_localctx, 8, RULE_graphClass);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(130);
			match(Identifier);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class PayloadTypeContext extends ParserRuleContext {
		public TerminalNode Identifier() { return getToken(GraphParser.Identifier, 0); }
		public PayloadTypeContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_payloadType; }
	}

	public final PayloadTypeContext payloadType() throws RecognitionException {
		PayloadTypeContext _localctx = new PayloadTypeContext(_ctx, getState());
		enterRule(_localctx, 10, RULE_payloadType);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(132);
			match(Identifier);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class VertexAssignmentBlockContext extends ParserRuleContext {
		public VertexNameContext vertexName() {
			return getRuleContext(VertexNameContext.class,0);
		}
		public TerminalNode ASSIGN() { return getToken(GraphParser.ASSIGN, 0); }
		public VertexBuilderContext vertexBuilder() {
			return getRuleContext(VertexBuilderContext.class,0);
		}
		public TerminalNode VERTEX() { return getToken(GraphParser.VERTEX, 0); }
		public TerminalNode SEMI() { return getToken(GraphParser.SEMI, 0); }
		public VertexAssignmentBlockContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_vertexAssignmentBlock; }
	}

	public final VertexAssignmentBlockContext vertexAssignmentBlock() throws RecognitionException {
		VertexAssignmentBlockContext _localctx = new VertexAssignmentBlockContext(_ctx, getState());
		enterRule(_localctx, 12, RULE_vertexAssignmentBlock);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(134);
			_la = _input.LA(1);
			if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__11) | (1L << T__12) | (1L << VERTEX))) != 0)) ) {
			_errHandler.recoverInline(this);
			}
			else {
				if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
				_errHandler.reportMatch(this);
				consume();
			}
			setState(135);
			vertexName();
			setState(136);
			match(ASSIGN);
			setState(137);
			vertexBuilder();
			setState(139);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,6,_ctx) ) {
			case 1:
				{
				setState(138);
				match(SEMI);
				}
				break;
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class VertexCloningBlockContext extends ParserRuleContext {
		public List<VertexNameContext> vertexName() {
			return getRuleContexts(VertexNameContext.class);
		}
		public VertexNameContext vertexName(int i) {
			return getRuleContext(VertexNameContext.class,i);
		}
		public TerminalNode ASSIGN() { return getToken(GraphParser.ASSIGN, 0); }
		public TerminalNode DOT() { return getToken(GraphParser.DOT, 0); }
		public TerminalNode CLONE() { return getToken(GraphParser.CLONE, 0); }
		public TerminalNode LPAREN() { return getToken(GraphParser.LPAREN, 0); }
		public TerminalNode RPAREN() { return getToken(GraphParser.RPAREN, 0); }
		public TerminalNode VERTEX() { return getToken(GraphParser.VERTEX, 0); }
		public TerminalNode SEMI() { return getToken(GraphParser.SEMI, 0); }
		public VertexCloningBlockContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_vertexCloningBlock; }
	}

	public final VertexCloningBlockContext vertexCloningBlock() throws RecognitionException {
		VertexCloningBlockContext _localctx = new VertexCloningBlockContext(_ctx, getState());
		enterRule(_localctx, 14, RULE_vertexCloningBlock);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(141);
			_la = _input.LA(1);
			if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__11) | (1L << T__12) | (1L << VERTEX))) != 0)) ) {
			_errHandler.recoverInline(this);
			}
			else {
				if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
				_errHandler.reportMatch(this);
				consume();
			}
			setState(142);
			vertexName();
			setState(143);
			match(ASSIGN);
			setState(144);
			vertexName();
			setState(145);
			match(DOT);
			setState(146);
			match(CLONE);
			setState(147);
			match(LPAREN);
			setState(148);
			match(RPAREN);
			setState(150);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,7,_ctx) ) {
			case 1:
				{
				setState(149);
				match(SEMI);
				}
				break;
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class VertexNameContext extends ParserRuleContext {
		public AnyGraphKeywordContext anyGraphKeyword() {
			return getRuleContext(AnyGraphKeywordContext.class,0);
		}
		public TerminalNode Identifier() { return getToken(GraphParser.Identifier, 0); }
		public VertexNameContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_vertexName; }
	}

	public final VertexNameContext vertexName() throws RecognitionException {
		VertexNameContext _localctx = new VertexNameContext(_ctx, getState());
		enterRule(_localctx, 16, RULE_vertexName);
		try {
			setState(154);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case SUBGRAPH:
			case MERGER:
			case HANDLER:
			case PAYLOAD:
			case COMPLETE:
			case ROUTER:
			case CLONE:
				enterOuterAlt(_localctx, 1);
				{
				setState(152);
				anyGraphKeyword();
				}
				break;
			case Identifier:
				enterOuterAlt(_localctx, 2);
				{
				setState(153);
				match(Identifier);
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class VertexBuilderContext extends ParserRuleContext {
		public BuilderHandlerContext builderHandler() {
			return getRuleContext(BuilderHandlerContext.class,0);
		}
		public BuilderSubgraphContext builderSubgraph() {
			return getRuleContext(BuilderSubgraphContext.class,0);
		}
		public BuilderRouterContext builderRouter() {
			return getRuleContext(BuilderRouterContext.class,0);
		}
		public VertexBuilderContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_vertexBuilder; }
	}

	public final VertexBuilderContext vertexBuilder() throws RecognitionException {
		VertexBuilderContext _localctx = new VertexBuilderContext(_ctx, getState());
		enterRule(_localctx, 18, RULE_vertexBuilder);
		try {
			setState(159);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case HANDLER:
			case SUSPEND_HANDLER:
				enterOuterAlt(_localctx, 1);
				{
				setState(156);
				builderHandler();
				}
				break;
			case SUBGRAPH:
				enterOuterAlt(_localctx, 2);
				{
				setState(157);
				builderSubgraph();
				}
				break;
			case ROUTER:
				enterOuterAlt(_localctx, 3);
				{
				setState(158);
				builderRouter();
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class BuilderSubgraphContext extends ParserRuleContext {
		public TerminalNode SUBGRAPH() { return getToken(GraphParser.SUBGRAPH, 0); }
		public TerminalNode LPAREN() { return getToken(GraphParser.LPAREN, 0); }
		public SubgraphPayloadClassContext subgraphPayloadClass() {
			return getRuleContext(SubgraphPayloadClassContext.class,0);
		}
		public AnythingBeforeRParenContext anythingBeforeRParen() {
			return getRuleContext(AnythingBeforeRParenContext.class,0);
		}
		public TerminalNode RPAREN() { return getToken(GraphParser.RPAREN, 0); }
		public List<TerminalNode> DOT() { return getTokens(GraphParser.DOT); }
		public TerminalNode DOT(int i) {
			return getToken(GraphParser.DOT, i);
		}
		public BuilderMergerContext builderMerger() {
			return getRuleContext(BuilderMergerContext.class,0);
		}
		public TerminalNode LBRACE() { return getToken(GraphParser.LBRACE, 0); }
		public AnythingBeforeRBraceContext anythingBeforeRBrace() {
			return getRuleContext(AnythingBeforeRBraceContext.class,0);
		}
		public TerminalNode RBRACE() { return getToken(GraphParser.RBRACE, 0); }
		public TerminalNode COLONCOLON() { return getToken(GraphParser.COLONCOLON, 0); }
		public BuilderSubgraphContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_builderSubgraph; }
	}

	public final BuilderSubgraphContext builderSubgraph() throws RecognitionException {
		BuilderSubgraphContext _localctx = new BuilderSubgraphContext(_ctx, getState());
		enterRule(_localctx, 20, RULE_builderSubgraph);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(161);
			match(SUBGRAPH);
			setState(162);
			match(LPAREN);
			setState(163);
			subgraphPayloadClass();
			setState(168);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case DOT:
				{
				{
				setState(164);
				match(DOT);
				setState(165);
				match(T__5);
				}
				}
				break;
			case COLONCOLON:
				{
				{
				setState(166);
				match(COLONCOLON);
				setState(167);
				match(T__5);
				}
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
			setState(170);
			anythingBeforeRParen();
			setState(171);
			match(RPAREN);
			setState(176);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==LBRACE) {
				{
				setState(172);
				match(LBRACE);
				setState(173);
				anythingBeforeRBrace();
				setState(174);
				match(RBRACE);
				}
			}

			setState(178);
			match(DOT);
			setState(179);
			builderMerger();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class SubgraphPayloadClassContext extends ParserRuleContext {
		public List<TerminalNode> Identifier() { return getTokens(GraphParser.Identifier); }
		public TerminalNode Identifier(int i) {
			return getToken(GraphParser.Identifier, i);
		}
		public List<TerminalNode> DOT() { return getTokens(GraphParser.DOT); }
		public TerminalNode DOT(int i) {
			return getToken(GraphParser.DOT, i);
		}
		public SubgraphPayloadClassContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_subgraphPayloadClass; }
	}

	public final SubgraphPayloadClassContext subgraphPayloadClass() throws RecognitionException {
		SubgraphPayloadClassContext _localctx = new SubgraphPayloadClassContext(_ctx, getState());
		enterRule(_localctx, 22, RULE_subgraphPayloadClass);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(181);
			match(Identifier);
			setState(186);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,12,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					{
					{
					setState(182);
					match(DOT);
					setState(183);
					match(Identifier);
					}
					} 
				}
				setState(188);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,12,_ctx);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class BuilderRouterContext extends ParserRuleContext {
		public TerminalNode ROUTER() { return getToken(GraphParser.ROUTER, 0); }
		public TerminalNode LPAREN() { return getToken(GraphParser.LPAREN, 0); }
		public AnythingBeforeRParenContext anythingBeforeRParen() {
			return getRuleContext(AnythingBeforeRParenContext.class,0);
		}
		public TerminalNode RPAREN() { return getToken(GraphParser.RPAREN, 0); }
		public TerminalNode LBRACE() { return getToken(GraphParser.LBRACE, 0); }
		public AnythingBeforeRBraceContext anythingBeforeRBrace() {
			return getRuleContext(AnythingBeforeRBraceContext.class,0);
		}
		public TerminalNode RBRACE() { return getToken(GraphParser.RBRACE, 0); }
		public BuilderRouterContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_builderRouter; }
	}

	public final BuilderRouterContext builderRouter() throws RecognitionException {
		BuilderRouterContext _localctx = new BuilderRouterContext(_ctx, getState());
		enterRule(_localctx, 24, RULE_builderRouter);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(189);
			match(ROUTER);
			setState(198);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case LPAREN:
				{
				{
				setState(190);
				match(LPAREN);
				setState(191);
				anythingBeforeRParen();
				setState(192);
				match(RPAREN);
				}
				}
				break;
			case LBRACE:
				{
				{
				setState(194);
				match(LBRACE);
				setState(195);
				anythingBeforeRBrace();
				setState(196);
				match(RBRACE);
				}
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class BuilderHandlerContext extends ParserRuleContext {
		public HandlerContext handler() {
			return getRuleContext(HandlerContext.class,0);
		}
		public TerminalNode DOT() { return getToken(GraphParser.DOT, 0); }
		public BuilderMergerContext builderMerger() {
			return getRuleContext(BuilderMergerContext.class,0);
		}
		public TerminalNode LPAREN() { return getToken(GraphParser.LPAREN, 0); }
		public AnythingBeforeRParenContext anythingBeforeRParen() {
			return getRuleContext(AnythingBeforeRParenContext.class,0);
		}
		public TerminalNode RPAREN() { return getToken(GraphParser.RPAREN, 0); }
		public TerminalNode LBRACE() { return getToken(GraphParser.LBRACE, 0); }
		public AnythingBeforeRBraceContext anythingBeforeRBrace() {
			return getRuleContext(AnythingBeforeRBraceContext.class,0);
		}
		public TerminalNode RBRACE() { return getToken(GraphParser.RBRACE, 0); }
		public BuilderHandlerContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_builderHandler; }
	}

	public final BuilderHandlerContext builderHandler() throws RecognitionException {
		BuilderHandlerContext _localctx = new BuilderHandlerContext(_ctx, getState());
		enterRule(_localctx, 26, RULE_builderHandler);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(200);
			handler();
			setState(209);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case LPAREN:
				{
				{
				setState(201);
				match(LPAREN);
				setState(202);
				anythingBeforeRParen();
				setState(203);
				match(RPAREN);
				}
				}
				break;
			case LBRACE:
				{
				{
				setState(205);
				match(LBRACE);
				setState(206);
				anythingBeforeRBrace();
				setState(207);
				match(RBRACE);
				}
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
			setState(211);
			match(DOT);
			setState(212);
			builderMerger();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class HandlerContext extends ParserRuleContext {
		public TerminalNode HANDLER() { return getToken(GraphParser.HANDLER, 0); }
		public TerminalNode SUSPEND_HANDLER() { return getToken(GraphParser.SUSPEND_HANDLER, 0); }
		public HandlerContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_handler; }
	}

	public final HandlerContext handler() throws RecognitionException {
		HandlerContext _localctx = new HandlerContext(_ctx, getState());
		enterRule(_localctx, 28, RULE_handler);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(214);
			_la = _input.LA(1);
			if ( !(_la==HANDLER || _la==SUSPEND_HANDLER) ) {
			_errHandler.recoverInline(this);
			}
			else {
				if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
				_errHandler.reportMatch(this);
				consume();
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class BuilderMergerContext extends ParserRuleContext {
		public BuilderWithMergerContext builderWithMerger() {
			return getRuleContext(BuilderWithMergerContext.class,0);
		}
		public BuilderWithoutMergerContext builderWithoutMerger() {
			return getRuleContext(BuilderWithoutMergerContext.class,0);
		}
		public BuilderWithEmptyMergerContext builderWithEmptyMerger() {
			return getRuleContext(BuilderWithEmptyMergerContext.class,0);
		}
		public BuilderMergerContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_builderMerger; }
	}

	public final BuilderMergerContext builderMerger() throws RecognitionException {
		BuilderMergerContext _localctx = new BuilderMergerContext(_ctx, getState());
		enterRule(_localctx, 30, RULE_builderMerger);
		try {
			setState(219);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case T__13:
				enterOuterAlt(_localctx, 1);
				{
				setState(216);
				builderWithMerger();
				}
				break;
			case T__14:
				enterOuterAlt(_localctx, 2);
				{
				setState(217);
				builderWithoutMerger();
				}
				break;
			case T__15:
				enterOuterAlt(_localctx, 3);
				{
				setState(218);
				builderWithEmptyMerger();
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class BuilderWithMergerContext extends ParserRuleContext {
		public TerminalNode LPAREN() { return getToken(GraphParser.LPAREN, 0); }
		public AnythingBeforeRParenContext anythingBeforeRParen() {
			return getRuleContext(AnythingBeforeRParenContext.class,0);
		}
		public TerminalNode RPAREN() { return getToken(GraphParser.RPAREN, 0); }
		public TerminalNode LBRACE() { return getToken(GraphParser.LBRACE, 0); }
		public AnythingBeforeRBraceContext anythingBeforeRBrace() {
			return getRuleContext(AnythingBeforeRBraceContext.class,0);
		}
		public TerminalNode RBRACE() { return getToken(GraphParser.RBRACE, 0); }
		public BuilderWithMergerContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_builderWithMerger; }
	}

	public final BuilderWithMergerContext builderWithMerger() throws RecognitionException {
		BuilderWithMergerContext _localctx = new BuilderWithMergerContext(_ctx, getState());
		enterRule(_localctx, 32, RULE_builderWithMerger);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(221);
			match(T__13);
			setState(230);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case LPAREN:
				{
				{
				setState(222);
				match(LPAREN);
				setState(223);
				anythingBeforeRParen();
				setState(224);
				match(RPAREN);
				}
				}
				break;
			case LBRACE:
				{
				{
				setState(226);
				match(LBRACE);
				setState(227);
				anythingBeforeRBrace();
				setState(228);
				match(RBRACE);
				}
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class BuilderWithoutMergerContext extends ParserRuleContext {
		public TerminalNode LPAREN() { return getToken(GraphParser.LPAREN, 0); }
		public TerminalNode RPAREN() { return getToken(GraphParser.RPAREN, 0); }
		public BuilderWithoutMergerContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_builderWithoutMerger; }
	}

	public final BuilderWithoutMergerContext builderWithoutMerger() throws RecognitionException {
		BuilderWithoutMergerContext _localctx = new BuilderWithoutMergerContext(_ctx, getState());
		enterRule(_localctx, 34, RULE_builderWithoutMerger);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(232);
			match(T__14);
			setState(233);
			match(LPAREN);
			setState(234);
			match(RPAREN);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class BuilderWithEmptyMergerContext extends ParserRuleContext {
		public TerminalNode LPAREN() { return getToken(GraphParser.LPAREN, 0); }
		public TerminalNode RPAREN() { return getToken(GraphParser.RPAREN, 0); }
		public BuilderWithEmptyMergerContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_builderWithEmptyMerger; }
	}

	public final BuilderWithEmptyMergerContext builderWithEmptyMerger() throws RecognitionException {
		BuilderWithEmptyMergerContext _localctx = new BuilderWithEmptyMergerContext(_ctx, getState());
		enterRule(_localctx, 36, RULE_builderWithEmptyMerger);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(236);
			match(T__15);
			setState(237);
			match(LPAREN);
			setState(238);
			match(RPAREN);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class AnythingBeforeRParenContext extends ParserRuleContext {
		public List<IgnoreParenthesesBlockContext> ignoreParenthesesBlock() {
			return getRuleContexts(IgnoreParenthesesBlockContext.class);
		}
		public IgnoreParenthesesBlockContext ignoreParenthesesBlock(int i) {
			return getRuleContext(IgnoreParenthesesBlockContext.class,i);
		}
		public List<IgnoreBracesBlockContext> ignoreBracesBlock() {
			return getRuleContexts(IgnoreBracesBlockContext.class);
		}
		public IgnoreBracesBlockContext ignoreBracesBlock(int i) {
			return getRuleContext(IgnoreBracesBlockContext.class,i);
		}
		public List<TerminalNode> LPAREN() { return getTokens(GraphParser.LPAREN); }
		public TerminalNode LPAREN(int i) {
			return getToken(GraphParser.LPAREN, i);
		}
		public List<TerminalNode> RPAREN() { return getTokens(GraphParser.RPAREN); }
		public TerminalNode RPAREN(int i) {
			return getToken(GraphParser.RPAREN, i);
		}
		public AnythingBeforeRParenContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_anythingBeforeRParen; }
	}

	public final AnythingBeforeRParenContext anythingBeforeRParen() throws RecognitionException {
		AnythingBeforeRParenContext _localctx = new AnythingBeforeRParenContext(_ctx, getState());
		enterRule(_localctx, 38, RULE_anythingBeforeRParen);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(245);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__0) | (1L << T__1) | (1L << T__2) | (1L << T__3) | (1L << T__4) | (1L << T__5) | (1L << T__6) | (1L << T__7) | (1L << T__8) | (1L << T__9) | (1L << T__10) | (1L << T__11) | (1L << T__12) | (1L << T__13) | (1L << T__14) | (1L << T__15) | (1L << T__16) | (1L << T__17) | (1L << T__18) | (1L << T__19) | (1L << T__20) | (1L << Coordinate) | (1L << SUBGRAPH) | (1L << MERGER) | (1L << HANDLER) | (1L << SUSPEND_HANDLER) | (1L << PAYLOAD) | (1L << COMPLETE) | (1L << ROUTER) | (1L << CLONE) | (1L << VERTEX) | (1L << DecimalNumeral) | (1L << LPAREN) | (1L << LBRACE) | (1L << RBRACE) | (1L << SEMI) | (1L << COMMA) | (1L << DOT) | (1L << AT) | (1L << ASSIGN) | (1L << NEW) | (1L << COLONCOLON) | (1L << StringLiteral) | (1L << Identifier) | (1L << WS) | (1L << COMMENT) | (1L << LINE_COMMENT) | (1L << OTHER_SYMBOL))) != 0)) {
				{
				setState(243);
				_errHandler.sync(this);
				switch ( getInterpreter().adaptivePredict(_input,17,_ctx) ) {
				case 1:
					{
					setState(240);
					ignoreParenthesesBlock();
					}
					break;
				case 2:
					{
					setState(241);
					ignoreBracesBlock();
					}
					break;
				case 3:
					{
					setState(242);
					_la = _input.LA(1);
					if ( _la <= 0 || (_la==LPAREN || _la==RPAREN) ) {
					_errHandler.recoverInline(this);
					}
					else {
						if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
						_errHandler.reportMatch(this);
						consume();
					}
					}
					break;
				}
				}
				setState(247);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class AnythingBeforeRBraceContext extends ParserRuleContext {
		public List<IgnoreBracesBlockContext> ignoreBracesBlock() {
			return getRuleContexts(IgnoreBracesBlockContext.class);
		}
		public IgnoreBracesBlockContext ignoreBracesBlock(int i) {
			return getRuleContext(IgnoreBracesBlockContext.class,i);
		}
		public List<TerminalNode> LBRACE() { return getTokens(GraphParser.LBRACE); }
		public TerminalNode LBRACE(int i) {
			return getToken(GraphParser.LBRACE, i);
		}
		public List<TerminalNode> RBRACE() { return getTokens(GraphParser.RBRACE); }
		public TerminalNode RBRACE(int i) {
			return getToken(GraphParser.RBRACE, i);
		}
		public AnythingBeforeRBraceContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_anythingBeforeRBrace; }
	}

	public final AnythingBeforeRBraceContext anythingBeforeRBrace() throws RecognitionException {
		AnythingBeforeRBraceContext _localctx = new AnythingBeforeRBraceContext(_ctx, getState());
		enterRule(_localctx, 40, RULE_anythingBeforeRBrace);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(253);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__0) | (1L << T__1) | (1L << T__2) | (1L << T__3) | (1L << T__4) | (1L << T__5) | (1L << T__6) | (1L << T__7) | (1L << T__8) | (1L << T__9) | (1L << T__10) | (1L << T__11) | (1L << T__12) | (1L << T__13) | (1L << T__14) | (1L << T__15) | (1L << T__16) | (1L << T__17) | (1L << T__18) | (1L << T__19) | (1L << T__20) | (1L << Coordinate) | (1L << SUBGRAPH) | (1L << MERGER) | (1L << HANDLER) | (1L << SUSPEND_HANDLER) | (1L << PAYLOAD) | (1L << COMPLETE) | (1L << ROUTER) | (1L << CLONE) | (1L << VERTEX) | (1L << DecimalNumeral) | (1L << LPAREN) | (1L << RPAREN) | (1L << LBRACE) | (1L << SEMI) | (1L << COMMA) | (1L << DOT) | (1L << AT) | (1L << ASSIGN) | (1L << NEW) | (1L << COLONCOLON) | (1L << StringLiteral) | (1L << Identifier) | (1L << WS) | (1L << COMMENT) | (1L << LINE_COMMENT) | (1L << OTHER_SYMBOL))) != 0)) {
				{
				setState(251);
				_errHandler.sync(this);
				switch ( getInterpreter().adaptivePredict(_input,19,_ctx) ) {
				case 1:
					{
					setState(248);
					ignoreBracesBlock();
					}
					break;
				case 2:
					{
					setState(249);
					ignoreBracesBlock();
					}
					break;
				case 3:
					{
					setState(250);
					_la = _input.LA(1);
					if ( _la <= 0 || (_la==LBRACE || _la==RBRACE) ) {
					_errHandler.recoverInline(this);
					}
					else {
						if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
						_errHandler.reportMatch(this);
						consume();
					}
					}
					break;
				}
				}
				setState(255);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class IgnoreBracesBlockContext extends ParserRuleContext {
		public List<TerminalNode> LBRACE() { return getTokens(GraphParser.LBRACE); }
		public TerminalNode LBRACE(int i) {
			return getToken(GraphParser.LBRACE, i);
		}
		public List<TerminalNode> RBRACE() { return getTokens(GraphParser.RBRACE); }
		public TerminalNode RBRACE(int i) {
			return getToken(GraphParser.RBRACE, i);
		}
		public List<IgnoreBracesBlockContext> ignoreBracesBlock() {
			return getRuleContexts(IgnoreBracesBlockContext.class);
		}
		public IgnoreBracesBlockContext ignoreBracesBlock(int i) {
			return getRuleContext(IgnoreBracesBlockContext.class,i);
		}
		public IgnoreBracesBlockContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_ignoreBracesBlock; }
	}

	public final IgnoreBracesBlockContext ignoreBracesBlock() throws RecognitionException {
		IgnoreBracesBlockContext _localctx = new IgnoreBracesBlockContext(_ctx, getState());
		enterRule(_localctx, 42, RULE_ignoreBracesBlock);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(256);
			match(LBRACE);
			setState(261);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__0) | (1L << T__1) | (1L << T__2) | (1L << T__3) | (1L << T__4) | (1L << T__5) | (1L << T__6) | (1L << T__7) | (1L << T__8) | (1L << T__9) | (1L << T__10) | (1L << T__11) | (1L << T__12) | (1L << T__13) | (1L << T__14) | (1L << T__15) | (1L << T__16) | (1L << T__17) | (1L << T__18) | (1L << T__19) | (1L << T__20) | (1L << Coordinate) | (1L << SUBGRAPH) | (1L << MERGER) | (1L << HANDLER) | (1L << SUSPEND_HANDLER) | (1L << PAYLOAD) | (1L << COMPLETE) | (1L << ROUTER) | (1L << CLONE) | (1L << VERTEX) | (1L << DecimalNumeral) | (1L << LPAREN) | (1L << RPAREN) | (1L << LBRACE) | (1L << SEMI) | (1L << COMMA) | (1L << DOT) | (1L << AT) | (1L << ASSIGN) | (1L << NEW) | (1L << COLONCOLON) | (1L << StringLiteral) | (1L << Identifier) | (1L << WS) | (1L << COMMENT) | (1L << LINE_COMMENT) | (1L << OTHER_SYMBOL))) != 0)) {
				{
				setState(259);
				_errHandler.sync(this);
				switch (_input.LA(1)) {
				case T__0:
				case T__1:
				case T__2:
				case T__3:
				case T__4:
				case T__5:
				case T__6:
				case T__7:
				case T__8:
				case T__9:
				case T__10:
				case T__11:
				case T__12:
				case T__13:
				case T__14:
				case T__15:
				case T__16:
				case T__17:
				case T__18:
				case T__19:
				case T__20:
				case Coordinate:
				case SUBGRAPH:
				case MERGER:
				case HANDLER:
				case SUSPEND_HANDLER:
				case PAYLOAD:
				case COMPLETE:
				case ROUTER:
				case CLONE:
				case VERTEX:
				case DecimalNumeral:
				case LPAREN:
				case RPAREN:
				case SEMI:
				case COMMA:
				case DOT:
				case AT:
				case ASSIGN:
				case NEW:
				case COLONCOLON:
				case StringLiteral:
				case Identifier:
				case WS:
				case COMMENT:
				case LINE_COMMENT:
				case OTHER_SYMBOL:
					{
					setState(257);
					_la = _input.LA(1);
					if ( _la <= 0 || (_la==LBRACE || _la==RBRACE) ) {
					_errHandler.recoverInline(this);
					}
					else {
						if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
						_errHandler.reportMatch(this);
						consume();
					}
					}
					break;
				case LBRACE:
					{
					setState(258);
					ignoreBracesBlock();
					}
					break;
				default:
					throw new NoViableAltException(this);
				}
				}
				setState(263);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(264);
			match(RBRACE);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class IgnoreParenthesesBlockContext extends ParserRuleContext {
		public List<TerminalNode> LPAREN() { return getTokens(GraphParser.LPAREN); }
		public TerminalNode LPAREN(int i) {
			return getToken(GraphParser.LPAREN, i);
		}
		public List<TerminalNode> RPAREN() { return getTokens(GraphParser.RPAREN); }
		public TerminalNode RPAREN(int i) {
			return getToken(GraphParser.RPAREN, i);
		}
		public List<IgnoreParenthesesBlockContext> ignoreParenthesesBlock() {
			return getRuleContexts(IgnoreParenthesesBlockContext.class);
		}
		public IgnoreParenthesesBlockContext ignoreParenthesesBlock(int i) {
			return getRuleContext(IgnoreParenthesesBlockContext.class,i);
		}
		public IgnoreParenthesesBlockContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_ignoreParenthesesBlock; }
	}

	public final IgnoreParenthesesBlockContext ignoreParenthesesBlock() throws RecognitionException {
		IgnoreParenthesesBlockContext _localctx = new IgnoreParenthesesBlockContext(_ctx, getState());
		enterRule(_localctx, 44, RULE_ignoreParenthesesBlock);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(266);
			match(LPAREN);
			setState(271);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__0) | (1L << T__1) | (1L << T__2) | (1L << T__3) | (1L << T__4) | (1L << T__5) | (1L << T__6) | (1L << T__7) | (1L << T__8) | (1L << T__9) | (1L << T__10) | (1L << T__11) | (1L << T__12) | (1L << T__13) | (1L << T__14) | (1L << T__15) | (1L << T__16) | (1L << T__17) | (1L << T__18) | (1L << T__19) | (1L << T__20) | (1L << Coordinate) | (1L << SUBGRAPH) | (1L << MERGER) | (1L << HANDLER) | (1L << SUSPEND_HANDLER) | (1L << PAYLOAD) | (1L << COMPLETE) | (1L << ROUTER) | (1L << CLONE) | (1L << VERTEX) | (1L << DecimalNumeral) | (1L << LPAREN) | (1L << LBRACE) | (1L << RBRACE) | (1L << SEMI) | (1L << COMMA) | (1L << DOT) | (1L << AT) | (1L << ASSIGN) | (1L << NEW) | (1L << COLONCOLON) | (1L << StringLiteral) | (1L << Identifier) | (1L << WS) | (1L << COMMENT) | (1L << LINE_COMMENT) | (1L << OTHER_SYMBOL))) != 0)) {
				{
				setState(269);
				_errHandler.sync(this);
				switch (_input.LA(1)) {
				case T__0:
				case T__1:
				case T__2:
				case T__3:
				case T__4:
				case T__5:
				case T__6:
				case T__7:
				case T__8:
				case T__9:
				case T__10:
				case T__11:
				case T__12:
				case T__13:
				case T__14:
				case T__15:
				case T__16:
				case T__17:
				case T__18:
				case T__19:
				case T__20:
				case Coordinate:
				case SUBGRAPH:
				case MERGER:
				case HANDLER:
				case SUSPEND_HANDLER:
				case PAYLOAD:
				case COMPLETE:
				case ROUTER:
				case CLONE:
				case VERTEX:
				case DecimalNumeral:
				case LBRACE:
				case RBRACE:
				case SEMI:
				case COMMA:
				case DOT:
				case AT:
				case ASSIGN:
				case NEW:
				case COLONCOLON:
				case StringLiteral:
				case Identifier:
				case WS:
				case COMMENT:
				case LINE_COMMENT:
				case OTHER_SYMBOL:
					{
					setState(267);
					_la = _input.LA(1);
					if ( _la <= 0 || (_la==LPAREN || _la==RPAREN) ) {
					_errHandler.recoverInline(this);
					}
					else {
						if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
						_errHandler.reportMatch(this);
						consume();
					}
					}
					break;
				case LPAREN:
					{
					setState(268);
					ignoreParenthesesBlock();
					}
					break;
				default:
					throw new NoViableAltException(this);
				}
				}
				setState(273);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(274);
			match(RPAREN);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class PayloadTransitionBlockContext extends ParserRuleContext {
		public TerminalNode PAYLOAD() { return getToken(GraphParser.PAYLOAD, 0); }
		public TerminalNode LPAREN() { return getToken(GraphParser.LPAREN, 0); }
		public TerminalNode RPAREN() { return getToken(GraphParser.RPAREN, 0); }
		public List<HandleByContext> handleBy() {
			return getRuleContexts(HandleByContext.class);
		}
		public HandleByContext handleBy(int i) {
			return getRuleContext(HandleByContext.class,i);
		}
		public TerminalNode SEMI() { return getToken(GraphParser.SEMI, 0); }
		public PayloadTransitionBlockContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_payloadTransitionBlock; }
	}

	public final PayloadTransitionBlockContext payloadTransitionBlock() throws RecognitionException {
		PayloadTransitionBlockContext _localctx = new PayloadTransitionBlockContext(_ctx, getState());
		enterRule(_localctx, 46, RULE_payloadTransitionBlock);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(276);
			match(PAYLOAD);
			setState(277);
			match(LPAREN);
			setState(278);
			match(RPAREN);
			setState(280); 
			_errHandler.sync(this);
			_alt = 1;
			do {
				switch (_alt) {
				case 1:
					{
					{
					setState(279);
					handleBy();
					}
					}
					break;
				default:
					throw new NoViableAltException(this);
				}
				setState(282); 
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,25,_ctx);
			} while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER );
			setState(285);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,26,_ctx) ) {
			case 1:
				{
				setState(284);
				match(SEMI);
				}
				break;
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class HandleByContext extends ParserRuleContext {
		public TerminalNode DOT() { return getToken(GraphParser.DOT, 0); }
		public TerminalNode LPAREN() { return getToken(GraphParser.LPAREN, 0); }
		public VertexNameContext vertexName() {
			return getRuleContext(VertexNameContext.class,0);
		}
		public TerminalNode RPAREN() { return getToken(GraphParser.RPAREN, 0); }
		public HandleByContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_handleBy; }
	}

	public final HandleByContext handleBy() throws RecognitionException {
		HandleByContext _localctx = new HandleByContext(_ctx, getState());
		enterRule(_localctx, 48, RULE_handleBy);
		try {
			enterOuterAlt(_localctx, 1);
			{
			{
			setState(287);
			match(DOT);
			setState(288);
			match(T__16);
			setState(289);
			match(LPAREN);
			setState(290);
			vertexName();
			setState(291);
			match(RPAREN);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class VertexTransitionBlockContext extends ParserRuleContext {
		public VertexNameContext vertexName() {
			return getRuleContext(VertexNameContext.class,0);
		}
		public List<VertexTransitionContext> vertexTransition() {
			return getRuleContexts(VertexTransitionContext.class);
		}
		public VertexTransitionContext vertexTransition(int i) {
			return getRuleContext(VertexTransitionContext.class,i);
		}
		public TerminalNode SEMI() { return getToken(GraphParser.SEMI, 0); }
		public VertexTransitionBlockContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_vertexTransitionBlock; }
	}

	public final VertexTransitionBlockContext vertexTransitionBlock() throws RecognitionException {
		VertexTransitionBlockContext _localctx = new VertexTransitionBlockContext(_ctx, getState());
		enterRule(_localctx, 50, RULE_vertexTransitionBlock);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(293);
			vertexName();
			setState(295); 
			_errHandler.sync(this);
			_alt = 1;
			do {
				switch (_alt) {
				case 1:
					{
					{
					setState(294);
					vertexTransition();
					}
					}
					break;
				default:
					throw new NoViableAltException(this);
				}
				setState(297); 
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,27,_ctx);
			} while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER );
			setState(300);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,28,_ctx) ) {
			case 1:
				{
				setState(299);
				match(SEMI);
				}
				break;
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class VertexTransitionContext extends ParserRuleContext {
		public VertexTransitionOnContext vertexTransitionOn() {
			return getRuleContext(VertexTransitionOnContext.class,0);
		}
		public VertexTransitionOnAnyContext vertexTransitionOnAny() {
			return getRuleContext(VertexTransitionOnAnyContext.class,0);
		}
		public VertexTransitionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_vertexTransition; }
	}

	public final VertexTransitionContext vertexTransition() throws RecognitionException {
		VertexTransitionContext _localctx = new VertexTransitionContext(_ctx, getState());
		enterRule(_localctx, 52, RULE_vertexTransition);
		try {
			setState(304);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,29,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(302);
				vertexTransitionOn();
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(303);
				vertexTransitionOnAny();
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class VertexTransitionOnContext extends ParserRuleContext {
		public TerminalNode DOT() { return getToken(GraphParser.DOT, 0); }
		public TerminalNode LPAREN() { return getToken(GraphParser.LPAREN, 0); }
		public TransitionConditionContext transitionCondition() {
			return getRuleContext(TransitionConditionContext.class,0);
		}
		public TerminalNode RPAREN() { return getToken(GraphParser.RPAREN, 0); }
		public TransitionActionContext transitionAction() {
			return getRuleContext(TransitionActionContext.class,0);
		}
		public VertexTransitionOnContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_vertexTransitionOn; }
	}

	public final VertexTransitionOnContext vertexTransitionOn() throws RecognitionException {
		VertexTransitionOnContext _localctx = new VertexTransitionOnContext(_ctx, getState());
		enterRule(_localctx, 54, RULE_vertexTransitionOn);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(306);
			match(DOT);
			setState(307);
			match(T__17);
			setState(308);
			match(LPAREN);
			setState(309);
			transitionCondition();
			setState(310);
			match(RPAREN);
			setState(311);
			transitionAction();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class VertexTransitionOnAnyContext extends ParserRuleContext {
		public TerminalNode DOT() { return getToken(GraphParser.DOT, 0); }
		public TerminalNode LPAREN() { return getToken(GraphParser.LPAREN, 0); }
		public TerminalNode RPAREN() { return getToken(GraphParser.RPAREN, 0); }
		public TransitionActionContext transitionAction() {
			return getRuleContext(TransitionActionContext.class,0);
		}
		public VertexTransitionOnAnyContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_vertexTransitionOnAny; }
	}

	public final VertexTransitionOnAnyContext vertexTransitionOnAny() throws RecognitionException {
		VertexTransitionOnAnyContext _localctx = new VertexTransitionOnAnyContext(_ctx, getState());
		enterRule(_localctx, 56, RULE_vertexTransitionOnAny);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(313);
			match(DOT);
			setState(314);
			match(T__18);
			setState(315);
			match(LPAREN);
			setState(316);
			match(RPAREN);
			setState(317);
			transitionAction();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class TransitionActionContext extends ParserRuleContext {
		public TransitionActionCompleteContext transitionActionComplete() {
			return getRuleContext(TransitionActionCompleteContext.class,0);
		}
		public TransitionActionMergeByContext transitionActionMergeBy() {
			return getRuleContext(TransitionActionMergeByContext.class,0);
		}
		public TransitionActionHandleByContext transitionActionHandleBy() {
			return getRuleContext(TransitionActionHandleByContext.class,0);
		}
		public TransitionActionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_transitionAction; }
	}

	public final TransitionActionContext transitionAction() throws RecognitionException {
		TransitionActionContext _localctx = new TransitionActionContext(_ctx, getState());
		enterRule(_localctx, 58, RULE_transitionAction);
		try {
			setState(322);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,30,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(319);
				transitionActionComplete();
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(320);
				transitionActionMergeBy();
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(321);
				transitionActionHandleBy();
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class TransitionActionCompleteContext extends ParserRuleContext {
		public TerminalNode DOT() { return getToken(GraphParser.DOT, 0); }
		public TerminalNode COMPLETE() { return getToken(GraphParser.COMPLETE, 0); }
		public TerminalNode LPAREN() { return getToken(GraphParser.LPAREN, 0); }
		public TerminalNode RPAREN() { return getToken(GraphParser.RPAREN, 0); }
		public TransitionActionCompleteContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_transitionActionComplete; }
	}

	public final TransitionActionCompleteContext transitionActionComplete() throws RecognitionException {
		TransitionActionCompleteContext _localctx = new TransitionActionCompleteContext(_ctx, getState());
		enterRule(_localctx, 60, RULE_transitionActionComplete);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(324);
			match(DOT);
			setState(325);
			match(COMPLETE);
			setState(326);
			match(LPAREN);
			setState(327);
			match(RPAREN);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class TransitionActionMergeByContext extends ParserRuleContext {
		public TerminalNode DOT() { return getToken(GraphParser.DOT, 0); }
		public TerminalNode LPAREN() { return getToken(GraphParser.LPAREN, 0); }
		public VertexNameContext vertexName() {
			return getRuleContext(VertexNameContext.class,0);
		}
		public TerminalNode RPAREN() { return getToken(GraphParser.RPAREN, 0); }
		public TransitionActionMergeByContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_transitionActionMergeBy; }
	}

	public final TransitionActionMergeByContext transitionActionMergeBy() throws RecognitionException {
		TransitionActionMergeByContext _localctx = new TransitionActionMergeByContext(_ctx, getState());
		enterRule(_localctx, 62, RULE_transitionActionMergeBy);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(329);
			match(DOT);
			setState(330);
			match(T__19);
			setState(331);
			match(LPAREN);
			setState(332);
			vertexName();
			setState(333);
			match(RPAREN);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class TransitionActionHandleByContext extends ParserRuleContext {
		public TerminalNode DOT() { return getToken(GraphParser.DOT, 0); }
		public TerminalNode LPAREN() { return getToken(GraphParser.LPAREN, 0); }
		public VertexNameContext vertexName() {
			return getRuleContext(VertexNameContext.class,0);
		}
		public TerminalNode RPAREN() { return getToken(GraphParser.RPAREN, 0); }
		public TransitionActionHandleByContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_transitionActionHandleBy; }
	}

	public final TransitionActionHandleByContext transitionActionHandleBy() throws RecognitionException {
		TransitionActionHandleByContext _localctx = new TransitionActionHandleByContext(_ctx, getState());
		enterRule(_localctx, 64, RULE_transitionActionHandleBy);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(335);
			match(DOT);
			setState(336);
			match(T__16);
			setState(337);
			match(LPAREN);
			setState(338);
			vertexName();
			setState(339);
			match(RPAREN);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class CoordinatesBlockContext extends ParserRuleContext {
		public TerminalNode LPAREN() { return getToken(GraphParser.LPAREN, 0); }
		public TerminalNode RPAREN() { return getToken(GraphParser.RPAREN, 0); }
		public List<CoordinateContext> coordinate() {
			return getRuleContexts(CoordinateContext.class);
		}
		public CoordinateContext coordinate(int i) {
			return getRuleContext(CoordinateContext.class,i);
		}
		public TerminalNode SEMI() { return getToken(GraphParser.SEMI, 0); }
		public CoordinatesBlockContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_coordinatesBlock; }
	}

	public final CoordinatesBlockContext coordinatesBlock() throws RecognitionException {
		CoordinatesBlockContext _localctx = new CoordinatesBlockContext(_ctx, getState());
		enterRule(_localctx, 66, RULE_coordinatesBlock);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(341);
			match(T__20);
			setState(342);
			match(LPAREN);
			setState(343);
			match(RPAREN);
			setState(347);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,31,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					{
					{
					setState(344);
					coordinate();
					}
					} 
				}
				setState(349);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,31,_ctx);
			}
			setState(351);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,32,_ctx) ) {
			case 1:
				{
				setState(350);
				match(SEMI);
				}
				break;
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class CoordinateContext extends ParserRuleContext {
		public CoordinatePayloadContext coordinatePayload() {
			return getRuleContext(CoordinatePayloadContext.class,0);
		}
		public CoordinateHandlerContext coordinateHandler() {
			return getRuleContext(CoordinateHandlerContext.class,0);
		}
		public CoordinateSubgraphContext coordinateSubgraph() {
			return getRuleContext(CoordinateSubgraphContext.class,0);
		}
		public CoordinateRouterContext coordinateRouter() {
			return getRuleContext(CoordinateRouterContext.class,0);
		}
		public CoordinateMergerContext coordinateMerger() {
			return getRuleContext(CoordinateMergerContext.class,0);
		}
		public CoordinateCompleteContext coordinateComplete() {
			return getRuleContext(CoordinateCompleteContext.class,0);
		}
		public CoordinateContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_coordinate; }
	}

	public final CoordinateContext coordinate() throws RecognitionException {
		CoordinateContext _localctx = new CoordinateContext(_ctx, getState());
		enterRule(_localctx, 68, RULE_coordinate);
		try {
			setState(359);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,33,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(353);
				coordinatePayload();
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(354);
				coordinateHandler();
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(355);
				coordinateSubgraph();
				}
				break;
			case 4:
				enterOuterAlt(_localctx, 4);
				{
				setState(356);
				coordinateRouter();
				}
				break;
			case 5:
				enterOuterAlt(_localctx, 5);
				{
				setState(357);
				coordinateMerger();
				}
				break;
			case 6:
				enterOuterAlt(_localctx, 6);
				{
				setState(358);
				coordinateComplete();
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class CoordinatePayloadContext extends ParserRuleContext {
		public TerminalNode DOT() { return getToken(GraphParser.DOT, 0); }
		public TerminalNode PAYLOAD() { return getToken(GraphParser.PAYLOAD, 0); }
		public TerminalNode LPAREN() { return getToken(GraphParser.LPAREN, 0); }
		public List<TerminalNode> Coordinate() { return getTokens(GraphParser.Coordinate); }
		public TerminalNode Coordinate(int i) {
			return getToken(GraphParser.Coordinate, i);
		}
		public TerminalNode COMMA() { return getToken(GraphParser.COMMA, 0); }
		public TerminalNode RPAREN() { return getToken(GraphParser.RPAREN, 0); }
		public CoordinatePayloadContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_coordinatePayload; }
	}

	public final CoordinatePayloadContext coordinatePayload() throws RecognitionException {
		CoordinatePayloadContext _localctx = new CoordinatePayloadContext(_ctx, getState());
		enterRule(_localctx, 70, RULE_coordinatePayload);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(361);
			match(DOT);
			setState(362);
			match(PAYLOAD);
			setState(363);
			match(LPAREN);
			setState(364);
			match(Coordinate);
			setState(365);
			match(COMMA);
			setState(366);
			match(Coordinate);
			setState(367);
			match(RPAREN);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class CoordinateHandlerContext extends ParserRuleContext {
		public TerminalNode DOT() { return getToken(GraphParser.DOT, 0); }
		public TerminalNode HANDLER() { return getToken(GraphParser.HANDLER, 0); }
		public TerminalNode LPAREN() { return getToken(GraphParser.LPAREN, 0); }
		public TerminalNode Identifier() { return getToken(GraphParser.Identifier, 0); }
		public List<TerminalNode> COMMA() { return getTokens(GraphParser.COMMA); }
		public TerminalNode COMMA(int i) {
			return getToken(GraphParser.COMMA, i);
		}
		public List<TerminalNode> Coordinate() { return getTokens(GraphParser.Coordinate); }
		public TerminalNode Coordinate(int i) {
			return getToken(GraphParser.Coordinate, i);
		}
		public TerminalNode RPAREN() { return getToken(GraphParser.RPAREN, 0); }
		public CoordinateHandlerContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_coordinateHandler; }
	}

	public final CoordinateHandlerContext coordinateHandler() throws RecognitionException {
		CoordinateHandlerContext _localctx = new CoordinateHandlerContext(_ctx, getState());
		enterRule(_localctx, 72, RULE_coordinateHandler);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(369);
			match(DOT);
			setState(370);
			match(HANDLER);
			setState(371);
			match(LPAREN);
			setState(372);
			match(Identifier);
			setState(373);
			match(COMMA);
			setState(374);
			match(Coordinate);
			setState(375);
			match(COMMA);
			setState(376);
			match(Coordinate);
			setState(377);
			match(RPAREN);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class CoordinateSubgraphContext extends ParserRuleContext {
		public TerminalNode DOT() { return getToken(GraphParser.DOT, 0); }
		public TerminalNode SUBGRAPH() { return getToken(GraphParser.SUBGRAPH, 0); }
		public TerminalNode LPAREN() { return getToken(GraphParser.LPAREN, 0); }
		public TerminalNode Identifier() { return getToken(GraphParser.Identifier, 0); }
		public List<TerminalNode> COMMA() { return getTokens(GraphParser.COMMA); }
		public TerminalNode COMMA(int i) {
			return getToken(GraphParser.COMMA, i);
		}
		public List<TerminalNode> Coordinate() { return getTokens(GraphParser.Coordinate); }
		public TerminalNode Coordinate(int i) {
			return getToken(GraphParser.Coordinate, i);
		}
		public TerminalNode RPAREN() { return getToken(GraphParser.RPAREN, 0); }
		public CoordinateSubgraphContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_coordinateSubgraph; }
	}

	public final CoordinateSubgraphContext coordinateSubgraph() throws RecognitionException {
		CoordinateSubgraphContext _localctx = new CoordinateSubgraphContext(_ctx, getState());
		enterRule(_localctx, 74, RULE_coordinateSubgraph);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(379);
			match(DOT);
			setState(380);
			match(SUBGRAPH);
			setState(381);
			match(LPAREN);
			setState(382);
			match(Identifier);
			setState(383);
			match(COMMA);
			setState(384);
			match(Coordinate);
			setState(385);
			match(COMMA);
			setState(386);
			match(Coordinate);
			setState(387);
			match(RPAREN);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class CoordinateRouterContext extends ParserRuleContext {
		public TerminalNode DOT() { return getToken(GraphParser.DOT, 0); }
		public TerminalNode ROUTER() { return getToken(GraphParser.ROUTER, 0); }
		public TerminalNode LPAREN() { return getToken(GraphParser.LPAREN, 0); }
		public TerminalNode Identifier() { return getToken(GraphParser.Identifier, 0); }
		public List<TerminalNode> COMMA() { return getTokens(GraphParser.COMMA); }
		public TerminalNode COMMA(int i) {
			return getToken(GraphParser.COMMA, i);
		}
		public List<TerminalNode> Coordinate() { return getTokens(GraphParser.Coordinate); }
		public TerminalNode Coordinate(int i) {
			return getToken(GraphParser.Coordinate, i);
		}
		public TerminalNode RPAREN() { return getToken(GraphParser.RPAREN, 0); }
		public CoordinateRouterContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_coordinateRouter; }
	}

	public final CoordinateRouterContext coordinateRouter() throws RecognitionException {
		CoordinateRouterContext _localctx = new CoordinateRouterContext(_ctx, getState());
		enterRule(_localctx, 76, RULE_coordinateRouter);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(389);
			match(DOT);
			setState(390);
			match(ROUTER);
			setState(391);
			match(LPAREN);
			setState(392);
			match(Identifier);
			setState(393);
			match(COMMA);
			setState(394);
			match(Coordinate);
			setState(395);
			match(COMMA);
			setState(396);
			match(Coordinate);
			setState(397);
			match(RPAREN);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class CoordinateMergerContext extends ParserRuleContext {
		public TerminalNode DOT() { return getToken(GraphParser.DOT, 0); }
		public TerminalNode MERGER() { return getToken(GraphParser.MERGER, 0); }
		public TerminalNode LPAREN() { return getToken(GraphParser.LPAREN, 0); }
		public TerminalNode Identifier() { return getToken(GraphParser.Identifier, 0); }
		public List<TerminalNode> COMMA() { return getTokens(GraphParser.COMMA); }
		public TerminalNode COMMA(int i) {
			return getToken(GraphParser.COMMA, i);
		}
		public List<TerminalNode> Coordinate() { return getTokens(GraphParser.Coordinate); }
		public TerminalNode Coordinate(int i) {
			return getToken(GraphParser.Coordinate, i);
		}
		public TerminalNode RPAREN() { return getToken(GraphParser.RPAREN, 0); }
		public CoordinateMergerContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_coordinateMerger; }
	}

	public final CoordinateMergerContext coordinateMerger() throws RecognitionException {
		CoordinateMergerContext _localctx = new CoordinateMergerContext(_ctx, getState());
		enterRule(_localctx, 78, RULE_coordinateMerger);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(399);
			match(DOT);
			setState(400);
			match(MERGER);
			setState(401);
			match(LPAREN);
			setState(402);
			match(Identifier);
			setState(403);
			match(COMMA);
			setState(404);
			match(Coordinate);
			setState(405);
			match(COMMA);
			setState(406);
			match(Coordinate);
			setState(407);
			match(RPAREN);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class CoordinateCompleteContext extends ParserRuleContext {
		public TerminalNode DOT() { return getToken(GraphParser.DOT, 0); }
		public TerminalNode COMPLETE() { return getToken(GraphParser.COMPLETE, 0); }
		public TerminalNode LPAREN() { return getToken(GraphParser.LPAREN, 0); }
		public TerminalNode Identifier() { return getToken(GraphParser.Identifier, 0); }
		public List<TerminalNode> COMMA() { return getTokens(GraphParser.COMMA); }
		public TerminalNode COMMA(int i) {
			return getToken(GraphParser.COMMA, i);
		}
		public List<TerminalNode> Coordinate() { return getTokens(GraphParser.Coordinate); }
		public TerminalNode Coordinate(int i) {
			return getToken(GraphParser.Coordinate, i);
		}
		public TerminalNode RPAREN() { return getToken(GraphParser.RPAREN, 0); }
		public CoordinateCompleteContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_coordinateComplete; }
	}

	public final CoordinateCompleteContext coordinateComplete() throws RecognitionException {
		CoordinateCompleteContext _localctx = new CoordinateCompleteContext(_ctx, getState());
		enterRule(_localctx, 80, RULE_coordinateComplete);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(409);
			match(DOT);
			setState(410);
			match(COMPLETE);
			setState(411);
			match(LPAREN);
			setState(412);
			match(Identifier);
			setState(413);
			match(COMMA);
			setState(414);
			match(Coordinate);
			setState(415);
			match(COMMA);
			setState(416);
			match(Coordinate);
			setState(417);
			match(RPAREN);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class TransitionConditionContext extends ParserRuleContext {
		public List<TerminalNode> Identifier() { return getTokens(GraphParser.Identifier); }
		public TerminalNode Identifier(int i) {
			return getToken(GraphParser.Identifier, i);
		}
		public List<TerminalNode> DOT() { return getTokens(GraphParser.DOT); }
		public TerminalNode DOT(int i) {
			return getToken(GraphParser.DOT, i);
		}
		public TransitionConditionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_transitionCondition; }
	}

	public final TransitionConditionContext transitionCondition() throws RecognitionException {
		TransitionConditionContext _localctx = new TransitionConditionContext(_ctx, getState());
		enterRule(_localctx, 82, RULE_transitionCondition);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(419);
			match(Identifier);
			setState(424);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==DOT) {
				{
				{
				setState(420);
				match(DOT);
				setState(421);
				match(Identifier);
				}
				}
				setState(426);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class IgnoredTokenContext extends ParserRuleContext {
		public IgnoredTokenContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_ignoredToken; }
	}

	public final IgnoredTokenContext ignoredToken() throws RecognitionException {
		IgnoredTokenContext _localctx = new IgnoredTokenContext(_ctx, getState());
		enterRule(_localctx, 84, RULE_ignoredToken);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(427);
			matchWildcard();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class AnyGraphKeywordContext extends ParserRuleContext {
		public TerminalNode SUBGRAPH() { return getToken(GraphParser.SUBGRAPH, 0); }
		public TerminalNode MERGER() { return getToken(GraphParser.MERGER, 0); }
		public TerminalNode HANDLER() { return getToken(GraphParser.HANDLER, 0); }
		public TerminalNode PAYLOAD() { return getToken(GraphParser.PAYLOAD, 0); }
		public TerminalNode COMPLETE() { return getToken(GraphParser.COMPLETE, 0); }
		public TerminalNode ROUTER() { return getToken(GraphParser.ROUTER, 0); }
		public TerminalNode CLONE() { return getToken(GraphParser.CLONE, 0); }
		public AnyGraphKeywordContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_anyGraphKeyword; }
	}

	public final AnyGraphKeywordContext anyGraphKeyword() throws RecognitionException {
		AnyGraphKeywordContext _localctx = new AnyGraphKeywordContext(_ctx, getState());
		enterRule(_localctx, 86, RULE_anyGraphKeyword);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(429);
			_la = _input.LA(1);
			if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << SUBGRAPH) | (1L << MERGER) | (1L << HANDLER) | (1L << PAYLOAD) | (1L << COMPLETE) | (1L << ROUTER) | (1L << CLONE))) != 0)) ) {
			_errHandler.recoverInline(this);
			}
			else {
				if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
				_errHandler.reportMatch(this);
				consume();
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static final String _serializedATN =
		"\3\u608b\ua72a\u8133\ub9ed\u417c\u3be7\u7786\u5964\3\63\u01b2\4\2\t\2"+
		"\4\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4\13"+
		"\t\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20\t\20\4\21\t\21\4\22\t\22"+
		"\4\23\t\23\4\24\t\24\4\25\t\25\4\26\t\26\4\27\t\27\4\30\t\30\4\31\t\31"+
		"\4\32\t\32\4\33\t\33\4\34\t\34\4\35\t\35\4\36\t\36\4\37\t\37\4 \t \4!"+
		"\t!\4\"\t\"\4#\t#\4$\t$\4%\t%\4&\t&\4\'\t\'\4(\t(\4)\t)\4*\t*\4+\t+\4"+
		",\t,\4-\t-\3\2\3\2\6\2]\n\2\r\2\16\2^\3\3\3\3\3\3\3\3\3\3\3\3\5\3g\n\3"+
		"\3\4\7\4j\n\4\f\4\16\4m\13\4\3\4\7\4p\n\4\f\4\16\4s\13\4\3\4\3\4\3\4\3"+
		"\4\3\4\3\4\3\4\3\4\3\5\3\5\3\5\3\5\3\5\3\5\5\5\u0083\n\5\3\6\3\6\3\7\3"+
		"\7\3\b\3\b\3\b\3\b\3\b\5\b\u008e\n\b\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3"+
		"\t\5\t\u0099\n\t\3\n\3\n\5\n\u009d\n\n\3\13\3\13\3\13\5\13\u00a2\n\13"+
		"\3\f\3\f\3\f\3\f\3\f\3\f\3\f\5\f\u00ab\n\f\3\f\3\f\3\f\3\f\3\f\3\f\5\f"+
		"\u00b3\n\f\3\f\3\f\3\f\3\r\3\r\3\r\7\r\u00bb\n\r\f\r\16\r\u00be\13\r\3"+
		"\16\3\16\3\16\3\16\3\16\3\16\3\16\3\16\3\16\5\16\u00c9\n\16\3\17\3\17"+
		"\3\17\3\17\3\17\3\17\3\17\3\17\3\17\5\17\u00d4\n\17\3\17\3\17\3\17\3\20"+
		"\3\20\3\21\3\21\3\21\5\21\u00de\n\21\3\22\3\22\3\22\3\22\3\22\3\22\3\22"+
		"\3\22\3\22\5\22\u00e9\n\22\3\23\3\23\3\23\3\23\3\24\3\24\3\24\3\24\3\25"+
		"\3\25\3\25\7\25\u00f6\n\25\f\25\16\25\u00f9\13\25\3\26\3\26\3\26\7\26"+
		"\u00fe\n\26\f\26\16\26\u0101\13\26\3\27\3\27\3\27\7\27\u0106\n\27\f\27"+
		"\16\27\u0109\13\27\3\27\3\27\3\30\3\30\3\30\7\30\u0110\n\30\f\30\16\30"+
		"\u0113\13\30\3\30\3\30\3\31\3\31\3\31\3\31\6\31\u011b\n\31\r\31\16\31"+
		"\u011c\3\31\5\31\u0120\n\31\3\32\3\32\3\32\3\32\3\32\3\32\3\33\3\33\6"+
		"\33\u012a\n\33\r\33\16\33\u012b\3\33\5\33\u012f\n\33\3\34\3\34\5\34\u0133"+
		"\n\34\3\35\3\35\3\35\3\35\3\35\3\35\3\35\3\36\3\36\3\36\3\36\3\36\3\36"+
		"\3\37\3\37\3\37\5\37\u0145\n\37\3 \3 \3 \3 \3 \3!\3!\3!\3!\3!\3!\3\"\3"+
		"\"\3\"\3\"\3\"\3\"\3#\3#\3#\3#\7#\u015c\n#\f#\16#\u015f\13#\3#\5#\u0162"+
		"\n#\3$\3$\3$\3$\3$\3$\5$\u016a\n$\3%\3%\3%\3%\3%\3%\3%\3%\3&\3&\3&\3&"+
		"\3&\3&\3&\3&\3&\3&\3\'\3\'\3\'\3\'\3\'\3\'\3\'\3\'\3\'\3\'\3(\3(\3(\3"+
		"(\3(\3(\3(\3(\3(\3(\3)\3)\3)\3)\3)\3)\3)\3)\3)\3)\3*\3*\3*\3*\3*\3*\3"+
		"*\3*\3*\3*\3+\3+\3+\7+\u01a9\n+\f+\16+\u01ac\13+\3,\3,\3-\3-\3-\3^\2."+
		"\2\4\6\b\n\f\16\20\22\24\26\30\32\34\36 \"$&(*,.\60\62\64\668:<>@BDFH"+
		"JLNPRTVX\2\t\3\2\3\7\3\2\t\n\4\2\16\17!!\3\2\33\34\3\2#$\3\2%&\4\2\31"+
		"\33\35 \2\u01b5\2\\\3\2\2\2\4f\3\2\2\2\6k\3\2\2\2\b|\3\2\2\2\n\u0084\3"+
		"\2\2\2\f\u0086\3\2\2\2\16\u0088\3\2\2\2\20\u008f\3\2\2\2\22\u009c\3\2"+
		"\2\2\24\u00a1\3\2\2\2\26\u00a3\3\2\2\2\30\u00b7\3\2\2\2\32\u00bf\3\2\2"+
		"\2\34\u00ca\3\2\2\2\36\u00d8\3\2\2\2 \u00dd\3\2\2\2\"\u00df\3\2\2\2$\u00ea"+
		"\3\2\2\2&\u00ee\3\2\2\2(\u00f7\3\2\2\2*\u00ff\3\2\2\2,\u0102\3\2\2\2."+
		"\u010c\3\2\2\2\60\u0116\3\2\2\2\62\u0121\3\2\2\2\64\u0127\3\2\2\2\66\u0132"+
		"\3\2\2\28\u0134\3\2\2\2:\u013b\3\2\2\2<\u0144\3\2\2\2>\u0146\3\2\2\2@"+
		"\u014b\3\2\2\2B\u0151\3\2\2\2D\u0157\3\2\2\2F\u0169\3\2\2\2H\u016b\3\2"+
		"\2\2J\u0173\3\2\2\2L\u017d\3\2\2\2N\u0187\3\2\2\2P\u0191\3\2\2\2R\u019b"+
		"\3\2\2\2T\u01a5\3\2\2\2V\u01ad\3\2\2\2X\u01af\3\2\2\2Z]\5\4\3\2[]\5V,"+
		"\2\\Z\3\2\2\2\\[\3\2\2\2]^\3\2\2\2^_\3\2\2\2^\\\3\2\2\2_\3\3\2\2\2`g\5"+
		"\60\31\2ag\5\64\33\2bg\5\16\b\2cg\5\20\t\2dg\5D#\2eg\5\6\4\2f`\3\2\2\2"+
		"fa\3\2\2\2fb\3\2\2\2fc\3\2\2\2fd\3\2\2\2fe\3\2\2\2g\5\3\2\2\2hj\5\b\5"+
		"\2ih\3\2\2\2jm\3\2\2\2ki\3\2\2\2kl\3\2\2\2lq\3\2\2\2mk\3\2\2\2np\t\2\2"+
		"\2on\3\2\2\2ps\3\2\2\2qo\3\2\2\2qr\3\2\2\2rt\3\2\2\2sq\3\2\2\2tu\7\b\2"+
		"\2uv\5\n\6\2vw\t\3\2\2wx\7\13\2\2xy\7\f\2\2yz\5\f\7\2z{\7\r\2\2{\7\3\2"+
		"\2\2|}\7*\2\2}\u0082\7/\2\2~\177\7#\2\2\177\u0080\5(\25\2\u0080\u0081"+
		"\7$\2\2\u0081\u0083\3\2\2\2\u0082~\3\2\2\2\u0082\u0083\3\2\2\2\u0083\t"+
		"\3\2\2\2\u0084\u0085\7/\2\2\u0085\13\3\2\2\2\u0086\u0087\7/\2\2\u0087"+
		"\r\3\2\2\2\u0088\u0089\t\4\2\2\u0089\u008a\5\22\n\2\u008a\u008b\7+\2\2"+
		"\u008b\u008d\5\24\13\2\u008c\u008e\7\'\2\2\u008d\u008c\3\2\2\2\u008d\u008e"+
		"\3\2\2\2\u008e\17\3\2\2\2\u008f\u0090\t\4\2\2\u0090\u0091\5\22\n\2\u0091"+
		"\u0092\7+\2\2\u0092\u0093\5\22\n\2\u0093\u0094\7)\2\2\u0094\u0095\7 \2"+
		"\2\u0095\u0096\7#\2\2\u0096\u0098\7$\2\2\u0097\u0099\7\'\2\2\u0098\u0097"+
		"\3\2\2\2\u0098\u0099\3\2\2\2\u0099\21\3\2\2\2\u009a\u009d\5X-\2\u009b"+
		"\u009d\7/\2\2\u009c\u009a\3\2\2\2\u009c\u009b\3\2\2\2\u009d\23\3\2\2\2"+
		"\u009e\u00a2\5\34\17\2\u009f\u00a2\5\26\f\2\u00a0\u00a2\5\32\16\2\u00a1"+
		"\u009e\3\2\2\2\u00a1\u009f\3\2\2\2\u00a1\u00a0\3\2\2\2\u00a2\25\3\2\2"+
		"\2\u00a3\u00a4\7\31\2\2\u00a4\u00a5\7#\2\2\u00a5\u00aa\5\30\r\2\u00a6"+
		"\u00a7\7)\2\2\u00a7\u00ab\7\b\2\2\u00a8\u00a9\7-\2\2\u00a9\u00ab\7\b\2"+
		"\2\u00aa\u00a6\3\2\2\2\u00aa\u00a8\3\2\2\2\u00ab\u00ac\3\2\2\2\u00ac\u00ad"+
		"\5(\25\2\u00ad\u00b2\7$\2\2\u00ae\u00af\7%\2\2\u00af\u00b0\5*\26\2\u00b0"+
		"\u00b1\7&\2\2\u00b1\u00b3\3\2\2\2\u00b2\u00ae\3\2\2\2\u00b2\u00b3\3\2"+
		"\2\2\u00b3\u00b4\3\2\2\2\u00b4\u00b5\7)\2\2\u00b5\u00b6\5 \21\2\u00b6"+
		"\27\3\2\2\2\u00b7\u00bc\7/\2\2\u00b8\u00b9\7)\2\2\u00b9\u00bb\7/\2\2\u00ba"+
		"\u00b8\3\2\2\2\u00bb\u00be\3\2\2\2\u00bc\u00ba\3\2\2\2\u00bc\u00bd\3\2"+
		"\2\2\u00bd\31\3\2\2\2\u00be\u00bc\3\2\2\2\u00bf\u00c8\7\37\2\2\u00c0\u00c1"+
		"\7#\2\2\u00c1\u00c2\5(\25\2\u00c2\u00c3\7$\2\2\u00c3\u00c9\3\2\2\2\u00c4"+
		"\u00c5\7%\2\2\u00c5\u00c6\5*\26\2\u00c6\u00c7\7&\2\2\u00c7\u00c9\3\2\2"+
		"\2\u00c8\u00c0\3\2\2\2\u00c8\u00c4\3\2\2\2\u00c9\33\3\2\2\2\u00ca\u00d3"+
		"\5\36\20\2\u00cb\u00cc\7#\2\2\u00cc\u00cd\5(\25\2\u00cd\u00ce\7$\2\2\u00ce"+
		"\u00d4\3\2\2\2\u00cf\u00d0\7%\2\2\u00d0\u00d1\5*\26\2\u00d1\u00d2\7&\2"+
		"\2\u00d2\u00d4\3\2\2\2\u00d3\u00cb\3\2\2\2\u00d3\u00cf\3\2\2\2\u00d4\u00d5"+
		"\3\2\2\2\u00d5\u00d6\7)\2\2\u00d6\u00d7\5 \21\2\u00d7\35\3\2\2\2\u00d8"+
		"\u00d9\t\5\2\2\u00d9\37\3\2\2\2\u00da\u00de\5\"\22\2\u00db\u00de\5$\23"+
		"\2\u00dc\u00de\5&\24\2\u00dd\u00da\3\2\2\2\u00dd\u00db\3\2\2\2\u00dd\u00dc"+
		"\3\2\2\2\u00de!\3\2\2\2\u00df\u00e8\7\20\2\2\u00e0\u00e1\7#\2\2\u00e1"+
		"\u00e2\5(\25\2\u00e2\u00e3\7$\2\2\u00e3\u00e9\3\2\2\2\u00e4\u00e5\7%\2"+
		"\2\u00e5\u00e6\5*\26\2\u00e6\u00e7\7&\2\2\u00e7\u00e9\3\2\2\2\u00e8\u00e0"+
		"\3\2\2\2\u00e8\u00e4\3\2\2\2\u00e9#\3\2\2\2\u00ea\u00eb\7\21\2\2\u00eb"+
		"\u00ec\7#\2\2\u00ec\u00ed\7$\2\2\u00ed%\3\2\2\2\u00ee\u00ef\7\22\2\2\u00ef"+
		"\u00f0\7#\2\2\u00f0\u00f1\7$\2\2\u00f1\'\3\2\2\2\u00f2\u00f6\5.\30\2\u00f3"+
		"\u00f6\5,\27\2\u00f4\u00f6\n\6\2\2\u00f5\u00f2\3\2\2\2\u00f5\u00f3\3\2"+
		"\2\2\u00f5\u00f4\3\2\2\2\u00f6\u00f9\3\2\2\2\u00f7\u00f5\3\2\2\2\u00f7"+
		"\u00f8\3\2\2\2\u00f8)\3\2\2\2\u00f9\u00f7\3\2\2\2\u00fa\u00fe\5,\27\2"+
		"\u00fb\u00fe\5,\27\2\u00fc\u00fe\n\7\2\2\u00fd\u00fa\3\2\2\2\u00fd\u00fb"+
		"\3\2\2\2\u00fd\u00fc\3\2\2\2\u00fe\u0101\3\2\2\2\u00ff\u00fd\3\2\2\2\u00ff"+
		"\u0100\3\2\2\2\u0100+\3\2\2\2\u0101\u00ff\3\2\2\2\u0102\u0107\7%\2\2\u0103"+
		"\u0106\n\7\2\2\u0104\u0106\5,\27\2\u0105\u0103\3\2\2\2\u0105\u0104\3\2"+
		"\2\2\u0106\u0109\3\2\2\2\u0107\u0105\3\2\2\2\u0107\u0108\3\2\2\2\u0108"+
		"\u010a\3\2\2\2\u0109\u0107\3\2\2\2\u010a\u010b\7&\2\2\u010b-\3\2\2\2\u010c"+
		"\u0111\7#\2\2\u010d\u0110\n\6\2\2\u010e\u0110\5.\30\2\u010f\u010d\3\2"+
		"\2\2\u010f\u010e\3\2\2\2\u0110\u0113\3\2\2\2\u0111\u010f\3\2\2\2\u0111"+
		"\u0112\3\2\2\2\u0112\u0114\3\2\2\2\u0113\u0111\3\2\2\2\u0114\u0115\7$"+
		"\2\2\u0115/\3\2\2\2\u0116\u0117\7\35\2\2\u0117\u0118\7#\2\2\u0118\u011a"+
		"\7$\2\2\u0119\u011b\5\62\32\2\u011a\u0119\3\2\2\2\u011b\u011c\3\2\2\2"+
		"\u011c\u011a\3\2\2\2\u011c\u011d\3\2\2\2\u011d\u011f\3\2\2\2\u011e\u0120"+
		"\7\'\2\2\u011f\u011e\3\2\2\2\u011f\u0120\3\2\2\2\u0120\61\3\2\2\2\u0121"+
		"\u0122\7)\2\2\u0122\u0123\7\23\2\2\u0123\u0124\7#\2\2\u0124\u0125\5\22"+
		"\n\2\u0125\u0126\7$\2\2\u0126\63\3\2\2\2\u0127\u0129\5\22\n\2\u0128\u012a"+
		"\5\66\34\2\u0129\u0128\3\2\2\2\u012a\u012b\3\2\2\2\u012b\u0129\3\2\2\2"+
		"\u012b\u012c\3\2\2\2\u012c\u012e\3\2\2\2\u012d\u012f\7\'\2\2\u012e\u012d"+
		"\3\2\2\2\u012e\u012f\3\2\2\2\u012f\65\3\2\2\2\u0130\u0133\58\35\2\u0131"+
		"\u0133\5:\36\2\u0132\u0130\3\2\2\2\u0132\u0131\3\2\2\2\u0133\67\3\2\2"+
		"\2\u0134\u0135\7)\2\2\u0135\u0136\7\24\2\2\u0136\u0137\7#\2\2\u0137\u0138"+
		"\5T+\2\u0138\u0139\7$\2\2\u0139\u013a\5<\37\2\u013a9\3\2\2\2\u013b\u013c"+
		"\7)\2\2\u013c\u013d\7\25\2\2\u013d\u013e\7#\2\2\u013e\u013f\7$\2\2\u013f"+
		"\u0140\5<\37\2\u0140;\3\2\2\2\u0141\u0145\5> \2\u0142\u0145\5@!\2\u0143"+
		"\u0145\5B\"\2\u0144\u0141\3\2\2\2\u0144\u0142\3\2\2\2\u0144\u0143\3\2"+
		"\2\2\u0145=\3\2\2\2\u0146\u0147\7)\2\2\u0147\u0148\7\36\2\2\u0148\u0149"+
		"\7#\2\2\u0149\u014a\7$\2\2\u014a?\3\2\2\2\u014b\u014c\7)\2\2\u014c\u014d"+
		"\7\26\2\2\u014d\u014e\7#\2\2\u014e\u014f\5\22\n\2\u014f\u0150\7$\2\2\u0150"+
		"A\3\2\2\2\u0151\u0152\7)\2\2\u0152\u0153\7\23\2\2\u0153\u0154\7#\2\2\u0154"+
		"\u0155\5\22\n\2\u0155\u0156\7$\2\2\u0156C\3\2\2\2\u0157\u0158\7\27\2\2"+
		"\u0158\u0159\7#\2\2\u0159\u015d\7$\2\2\u015a\u015c\5F$\2\u015b\u015a\3"+
		"\2\2\2\u015c\u015f\3\2\2\2\u015d\u015b\3\2\2\2\u015d\u015e\3\2\2\2\u015e"+
		"\u0161\3\2\2\2\u015f\u015d\3\2\2\2\u0160\u0162\7\'\2\2\u0161\u0160\3\2"+
		"\2\2\u0161\u0162\3\2\2\2\u0162E\3\2\2\2\u0163\u016a\5H%\2\u0164\u016a"+
		"\5J&\2\u0165\u016a\5L\'\2\u0166\u016a\5N(\2\u0167\u016a\5P)\2\u0168\u016a"+
		"\5R*\2\u0169\u0163\3\2\2\2\u0169\u0164\3\2\2\2\u0169\u0165\3\2\2\2\u0169"+
		"\u0166\3\2\2\2\u0169\u0167\3\2\2\2\u0169\u0168\3\2\2\2\u016aG\3\2\2\2"+
		"\u016b\u016c\7)\2\2\u016c\u016d\7\35\2\2\u016d\u016e\7#\2\2\u016e\u016f"+
		"\7\30\2\2\u016f\u0170\7(\2\2\u0170\u0171\7\30\2\2\u0171\u0172\7$\2\2\u0172"+
		"I\3\2\2\2\u0173\u0174\7)\2\2\u0174\u0175\7\33\2\2\u0175\u0176\7#\2\2\u0176"+
		"\u0177\7/\2\2\u0177\u0178\7(\2\2\u0178\u0179\7\30\2\2\u0179\u017a\7(\2"+
		"\2\u017a\u017b\7\30\2\2\u017b\u017c\7$\2\2\u017cK\3\2\2\2\u017d\u017e"+
		"\7)\2\2\u017e\u017f\7\31\2\2\u017f\u0180\7#\2\2\u0180\u0181\7/\2\2\u0181"+
		"\u0182\7(\2\2\u0182\u0183\7\30\2\2\u0183\u0184\7(\2\2\u0184\u0185\7\30"+
		"\2\2\u0185\u0186\7$\2\2\u0186M\3\2\2\2\u0187\u0188\7)\2\2\u0188\u0189"+
		"\7\37\2\2\u0189\u018a\7#\2\2\u018a\u018b\7/\2\2\u018b\u018c\7(\2\2\u018c"+
		"\u018d\7\30\2\2\u018d\u018e\7(\2\2\u018e\u018f\7\30\2\2\u018f\u0190\7"+
		"$\2\2\u0190O\3\2\2\2\u0191\u0192\7)\2\2\u0192\u0193\7\32\2\2\u0193\u0194"+
		"\7#\2\2\u0194\u0195\7/\2\2\u0195\u0196\7(\2\2\u0196\u0197\7\30\2\2\u0197"+
		"\u0198\7(\2\2\u0198\u0199\7\30\2\2\u0199\u019a\7$\2\2\u019aQ\3\2\2\2\u019b"+
		"\u019c\7)\2\2\u019c\u019d\7\36\2\2\u019d\u019e\7#\2\2\u019e\u019f\7/\2"+
		"\2\u019f\u01a0\7(\2\2\u01a0\u01a1\7\30\2\2\u01a1\u01a2\7(\2\2\u01a2\u01a3"+
		"\7\30\2\2\u01a3\u01a4\7$\2\2\u01a4S\3\2\2\2\u01a5\u01aa\7/\2\2\u01a6\u01a7"+
		"\7)\2\2\u01a7\u01a9\7/\2\2\u01a8\u01a6\3\2\2\2\u01a9\u01ac\3\2\2\2\u01aa"+
		"\u01a8\3\2\2\2\u01aa\u01ab\3\2\2\2\u01abU\3\2\2\2\u01ac\u01aa\3\2\2\2"+
		"\u01ad\u01ae\13\2\2\2\u01aeW\3\2\2\2\u01af\u01b0\t\b\2\2\u01b0Y\3\2\2"+
		"\2%\\^fkq\u0082\u008d\u0098\u009c\u00a1\u00aa\u00b2\u00bc\u00c8\u00d3"+
		"\u00dd\u00e8\u00f5\u00f7\u00fd\u00ff\u0105\u0107\u010f\u0111\u011c\u011f"+
		"\u012b\u012e\u0132\u0144\u015d\u0161\u0169\u01aa";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}
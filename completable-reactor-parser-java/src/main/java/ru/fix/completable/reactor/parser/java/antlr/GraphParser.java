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
		T__17=18, T__18=19, T__19=20, T__20=21, T__21=22, T__22=23, T__23=24, 
		T__24=25, Coordinate=26, SUBGRAPH=27, MERGER=28, HANDLER=29, SUSPEND_HANDLER=30, 
		PAYLOAD=31, COMPLETE=32, ROUTER=33, CLONE=34, VERTEX=35, DecimalNumeral=36, 
		LPAREN=37, RPAREN=38, LBRACE=39, RBRACE=40, SEMI=41, COMMA=42, DOT=43, 
		AT=44, ASSIGN=45, NEW=46, COLONCOLON=47, StringLiteral=48, Identifier=49, 
		WS=50, COMMENT=51, LINE_COMMENT=52, OTHER_SYMBOL=53;
	public static final int
		RULE_sourceFile = 0, RULE_graphBlock = 1, RULE_graphDeclarationBlock = 2, 
		RULE_classModifier = 3, RULE_classAnnotation = 4, RULE_graphClass = 5, 
		RULE_payloadType = 6, RULE_vertexAssignmentBlock = 7, RULE_vertexCloningBlock = 8, 
		RULE_vertexName = 9, RULE_vertexBuilder = 10, RULE_builderSubgraph = 11, 
		RULE_subgraphPayloadClass = 12, RULE_builderRouter = 13, RULE_builderHandler = 14, 
		RULE_handler = 15, RULE_builderMerger = 16, RULE_builderWithMerger = 17, 
		RULE_builderWithoutMerger = 18, RULE_builderWithEmptyMerger = 19, RULE_anythingBeforeRParen = 20, 
		RULE_anythingBeforeRBrace = 21, RULE_ignoreBracesBlock = 22, RULE_ignoreParenthesesBlock = 23, 
		RULE_payloadTransitionBlock = 24, RULE_handleBy = 25, RULE_vertexTransitionBlock = 26, 
		RULE_vertexTransition = 27, RULE_vertexTransitionOn = 28, RULE_vertexTransitionOnAny = 29, 
		RULE_transitionAction = 30, RULE_transitionActionComplete = 31, RULE_transitionActionMergeBy = 32, 
		RULE_transitionActionHandleBy = 33, RULE_coordinatesBlock = 34, RULE_coordinate = 35, 
		RULE_coordinatePayload = 36, RULE_coordinateHandler = 37, RULE_coordinateSubgraph = 38, 
		RULE_coordinateRouter = 39, RULE_coordinateMerger = 40, RULE_coordinateComplete = 41, 
		RULE_transitionCondition = 42, RULE_ignoredToken = 43, RULE_anyGraphKeyword = 44;
	public static final String[] ruleNames = {
		"sourceFile", "graphBlock", "graphDeclarationBlock", "classModifier", 
		"classAnnotation", "graphClass", "payloadType", "vertexAssignmentBlock", 
		"vertexCloningBlock", "vertexName", "vertexBuilder", "builderSubgraph", 
		"subgraphPayloadClass", "builderRouter", "builderHandler", "handler", 
		"builderMerger", "builderWithMerger", "builderWithoutMerger", "builderWithEmptyMerger", 
		"anythingBeforeRParen", "anythingBeforeRBrace", "ignoreBracesBlock", "ignoreParenthesesBlock", 
		"payloadTransitionBlock", "handleBy", "vertexTransitionBlock", "vertexTransition", 
		"vertexTransitionOn", "vertexTransitionOnAny", "transitionAction", "transitionActionComplete", 
		"transitionActionMergeBy", "transitionActionHandleBy", "coordinatesBlock", 
		"coordinate", "coordinatePayload", "coordinateHandler", "coordinateSubgraph", 
		"coordinateRouter", "coordinateMerger", "coordinateComplete", "transitionCondition", 
		"ignoredToken", "anyGraphKeyword"
	};

	private static final String[] _LITERAL_NAMES = {
		null, "'class'", "'constructor'", "'extends'", "':'", "'Graph'", "'<'", 
		"'>'", "'public'", "'protected'", "'private'", "'abstract'", "'static'", 
		"'final'", "'open'", "'sealed'", "'val'", "'var'", "'withMerger'", "'withoutMerger'", 
		"'withEmptyMerger'", "'handleBy'", "'on'", "'onAny'", "'mergeBy'", "'coordinates'", 
		null, "'subgraph'", "'merger'", "'handler'", "'suspendHandler'", "'payload'", 
		"'complete'", "'router'", "'clone'", "'Vertex'", null, "'('", "')'", "'{'", 
		"'}'", "';'", "','", "'.'", "'@'", "'='", "'new'", "'::'"
	};
	private static final String[] _SYMBOLIC_NAMES = {
		null, null, null, null, null, null, null, null, null, null, null, null, 
		null, null, null, null, null, null, null, null, null, null, null, null, 
		null, null, "Coordinate", "SUBGRAPH", "MERGER", "HANDLER", "SUSPEND_HANDLER", 
		"PAYLOAD", "COMPLETE", "ROUTER", "CLONE", "VERTEX", "DecimalNumeral", 
		"LPAREN", "RPAREN", "LBRACE", "RBRACE", "SEMI", "COMMA", "DOT", "AT", 
		"ASSIGN", "NEW", "COLONCOLON", "StringLiteral", "Identifier", "WS", "COMMENT", 
		"LINE_COMMENT", "OTHER_SYMBOL"
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
			setState(92); 
			_errHandler.sync(this);
			_alt = 1+1;
			do {
				switch (_alt) {
				case 1+1:
					{
					setState(92);
					_errHandler.sync(this);
					switch ( getInterpreter().adaptivePredict(_input,0,_ctx) ) {
					case 1:
						{
						setState(90);
						graphBlock();
						}
						break;
					case 2:
						{
						setState(91);
						ignoredToken();
						}
						break;
					}
					}
					break;
				default:
					throw new NoViableAltException(this);
				}
				setState(94); 
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
			setState(102);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,2,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(96);
				payloadTransitionBlock();
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(97);
				vertexTransitionBlock();
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(98);
				vertexAssignmentBlock();
				}
				break;
			case 4:
				enterOuterAlt(_localctx, 4);
				{
				setState(99);
				vertexCloningBlock();
				}
				break;
			case 5:
				enterOuterAlt(_localctx, 5);
				{
				setState(100);
				coordinatesBlock();
				}
				break;
			case 6:
				enterOuterAlt(_localctx, 6);
				{
				setState(101);
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
		public List<ClassModifierContext> classModifier() {
			return getRuleContexts(ClassModifierContext.class);
		}
		public ClassModifierContext classModifier(int i) {
			return getRuleContext(ClassModifierContext.class,i);
		}
		public TerminalNode LPAREN() { return getToken(GraphParser.LPAREN, 0); }
		public AnythingBeforeRParenContext anythingBeforeRParen() {
			return getRuleContext(AnythingBeforeRParenContext.class,0);
		}
		public TerminalNode RPAREN() { return getToken(GraphParser.RPAREN, 0); }
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
			setState(107);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==AT) {
				{
				{
				setState(104);
				classAnnotation();
				}
				}
				setState(109);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(113);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__7) | (1L << T__8) | (1L << T__9) | (1L << T__10) | (1L << T__11) | (1L << T__12) | (1L << T__13) | (1L << T__14))) != 0)) {
				{
				{
				setState(110);
				classModifier();
				}
				}
				setState(115);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(116);
			match(T__0);
			setState(117);
			graphClass();
			setState(122);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__7) | (1L << T__8) | (1L << T__9) | (1L << T__10) | (1L << T__11) | (1L << T__12) | (1L << T__13) | (1L << T__14) | (1L << AT))) != 0)) {
				{
				setState(120);
				_errHandler.sync(this);
				switch (_input.LA(1)) {
				case T__7:
				case T__8:
				case T__9:
				case T__10:
				case T__11:
				case T__12:
				case T__13:
				case T__14:
					{
					setState(118);
					classModifier();
					}
					break;
				case AT:
					{
					setState(119);
					classAnnotation();
					}
					break;
				default:
					throw new NoViableAltException(this);
				}
				}
				setState(124);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(126);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==T__1) {
				{
				setState(125);
				match(T__1);
				}
			}

			setState(132);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==LPAREN) {
				{
				setState(128);
				match(LPAREN);
				setState(129);
				anythingBeforeRParen();
				setState(130);
				match(RPAREN);
				}
			}

			setState(134);
			_la = _input.LA(1);
			if ( !(_la==T__2 || _la==T__3) ) {
			_errHandler.recoverInline(this);
			}
			else {
				if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
				_errHandler.reportMatch(this);
				consume();
			}
			setState(135);
			match(T__4);
			setState(136);
			match(T__5);
			setState(137);
			payloadType();
			setState(138);
			match(T__6);
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

	public static class ClassModifierContext extends ParserRuleContext {
		public ClassModifierContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_classModifier; }
	}

	public final ClassModifierContext classModifier() throws RecognitionException {
		ClassModifierContext _localctx = new ClassModifierContext(_ctx, getState());
		enterRule(_localctx, 6, RULE_classModifier);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(140);
			_la = _input.LA(1);
			if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__7) | (1L << T__8) | (1L << T__9) | (1L << T__10) | (1L << T__11) | (1L << T__12) | (1L << T__13) | (1L << T__14))) != 0)) ) {
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
		enterRule(_localctx, 8, RULE_classAnnotation);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(142);
			match(AT);
			setState(143);
			match(Identifier);
			setState(148);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,9,_ctx) ) {
			case 1:
				{
				setState(144);
				match(LPAREN);
				setState(145);
				anythingBeforeRParen();
				setState(146);
				match(RPAREN);
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

	public static class GraphClassContext extends ParserRuleContext {
		public TerminalNode Identifier() { return getToken(GraphParser.Identifier, 0); }
		public GraphClassContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_graphClass; }
	}

	public final GraphClassContext graphClass() throws RecognitionException {
		GraphClassContext _localctx = new GraphClassContext(_ctx, getState());
		enterRule(_localctx, 10, RULE_graphClass);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(150);
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
		enterRule(_localctx, 12, RULE_payloadType);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(152);
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
		enterRule(_localctx, 14, RULE_vertexAssignmentBlock);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(154);
			_la = _input.LA(1);
			if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__15) | (1L << T__16) | (1L << VERTEX))) != 0)) ) {
			_errHandler.recoverInline(this);
			}
			else {
				if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
				_errHandler.reportMatch(this);
				consume();
			}
			setState(155);
			vertexName();
			setState(156);
			match(ASSIGN);
			setState(157);
			vertexBuilder();
			setState(159);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,10,_ctx) ) {
			case 1:
				{
				setState(158);
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
		enterRule(_localctx, 16, RULE_vertexCloningBlock);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(161);
			_la = _input.LA(1);
			if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__15) | (1L << T__16) | (1L << VERTEX))) != 0)) ) {
			_errHandler.recoverInline(this);
			}
			else {
				if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
				_errHandler.reportMatch(this);
				consume();
			}
			setState(162);
			vertexName();
			setState(163);
			match(ASSIGN);
			setState(164);
			vertexName();
			setState(165);
			match(DOT);
			setState(166);
			match(CLONE);
			setState(167);
			match(LPAREN);
			setState(168);
			match(RPAREN);
			setState(170);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,11,_ctx) ) {
			case 1:
				{
				setState(169);
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
		enterRule(_localctx, 18, RULE_vertexName);
		try {
			setState(174);
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
				setState(172);
				anyGraphKeyword();
				}
				break;
			case Identifier:
				enterOuterAlt(_localctx, 2);
				{
				setState(173);
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
		enterRule(_localctx, 20, RULE_vertexBuilder);
		try {
			setState(179);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case HANDLER:
			case SUSPEND_HANDLER:
				enterOuterAlt(_localctx, 1);
				{
				setState(176);
				builderHandler();
				}
				break;
			case SUBGRAPH:
				enterOuterAlt(_localctx, 2);
				{
				setState(177);
				builderSubgraph();
				}
				break;
			case ROUTER:
				enterOuterAlt(_localctx, 3);
				{
				setState(178);
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
		enterRule(_localctx, 22, RULE_builderSubgraph);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(181);
			match(SUBGRAPH);
			setState(182);
			match(LPAREN);
			setState(183);
			subgraphPayloadClass();
			setState(188);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case DOT:
				{
				{
				setState(184);
				match(DOT);
				setState(185);
				match(T__0);
				}
				}
				break;
			case COLONCOLON:
				{
				{
				setState(186);
				match(COLONCOLON);
				setState(187);
				match(T__0);
				}
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
			setState(190);
			anythingBeforeRParen();
			setState(191);
			match(RPAREN);
			setState(196);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==LBRACE) {
				{
				setState(192);
				match(LBRACE);
				setState(193);
				anythingBeforeRBrace();
				setState(194);
				match(RBRACE);
				}
			}

			setState(198);
			match(DOT);
			setState(199);
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
		enterRule(_localctx, 24, RULE_subgraphPayloadClass);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(201);
			match(Identifier);
			setState(206);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,16,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					{
					{
					setState(202);
					match(DOT);
					setState(203);
					match(Identifier);
					}
					} 
				}
				setState(208);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,16,_ctx);
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
		enterRule(_localctx, 26, RULE_builderRouter);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(209);
			match(ROUTER);
			setState(218);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case LPAREN:
				{
				{
				setState(210);
				match(LPAREN);
				setState(211);
				anythingBeforeRParen();
				setState(212);
				match(RPAREN);
				}
				}
				break;
			case LBRACE:
				{
				{
				setState(214);
				match(LBRACE);
				setState(215);
				anythingBeforeRBrace();
				setState(216);
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
		enterRule(_localctx, 28, RULE_builderHandler);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(220);
			handler();
			setState(229);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case LPAREN:
				{
				{
				setState(221);
				match(LPAREN);
				setState(222);
				anythingBeforeRParen();
				setState(223);
				match(RPAREN);
				}
				}
				break;
			case LBRACE:
				{
				{
				setState(225);
				match(LBRACE);
				setState(226);
				anythingBeforeRBrace();
				setState(227);
				match(RBRACE);
				}
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
			setState(231);
			match(DOT);
			setState(232);
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
		enterRule(_localctx, 30, RULE_handler);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(234);
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
		enterRule(_localctx, 32, RULE_builderMerger);
		try {
			setState(239);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case T__17:
				enterOuterAlt(_localctx, 1);
				{
				setState(236);
				builderWithMerger();
				}
				break;
			case T__18:
				enterOuterAlt(_localctx, 2);
				{
				setState(237);
				builderWithoutMerger();
				}
				break;
			case T__19:
				enterOuterAlt(_localctx, 3);
				{
				setState(238);
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
		enterRule(_localctx, 34, RULE_builderWithMerger);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(241);
			match(T__17);
			setState(250);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case LPAREN:
				{
				{
				setState(242);
				match(LPAREN);
				setState(243);
				anythingBeforeRParen();
				setState(244);
				match(RPAREN);
				}
				}
				break;
			case LBRACE:
				{
				{
				setState(246);
				match(LBRACE);
				setState(247);
				anythingBeforeRBrace();
				setState(248);
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
		enterRule(_localctx, 36, RULE_builderWithoutMerger);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(252);
			match(T__18);
			setState(253);
			match(LPAREN);
			setState(254);
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
		enterRule(_localctx, 38, RULE_builderWithEmptyMerger);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(256);
			match(T__19);
			setState(257);
			match(LPAREN);
			setState(258);
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
		enterRule(_localctx, 40, RULE_anythingBeforeRParen);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(265);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__0) | (1L << T__1) | (1L << T__2) | (1L << T__3) | (1L << T__4) | (1L << T__5) | (1L << T__6) | (1L << T__7) | (1L << T__8) | (1L << T__9) | (1L << T__10) | (1L << T__11) | (1L << T__12) | (1L << T__13) | (1L << T__14) | (1L << T__15) | (1L << T__16) | (1L << T__17) | (1L << T__18) | (1L << T__19) | (1L << T__20) | (1L << T__21) | (1L << T__22) | (1L << T__23) | (1L << T__24) | (1L << Coordinate) | (1L << SUBGRAPH) | (1L << MERGER) | (1L << HANDLER) | (1L << SUSPEND_HANDLER) | (1L << PAYLOAD) | (1L << COMPLETE) | (1L << ROUTER) | (1L << CLONE) | (1L << VERTEX) | (1L << DecimalNumeral) | (1L << LPAREN) | (1L << LBRACE) | (1L << RBRACE) | (1L << SEMI) | (1L << COMMA) | (1L << DOT) | (1L << AT) | (1L << ASSIGN) | (1L << NEW) | (1L << COLONCOLON) | (1L << StringLiteral) | (1L << Identifier) | (1L << WS) | (1L << COMMENT) | (1L << LINE_COMMENT) | (1L << OTHER_SYMBOL))) != 0)) {
				{
				setState(263);
				_errHandler.sync(this);
				switch ( getInterpreter().adaptivePredict(_input,21,_ctx) ) {
				case 1:
					{
					setState(260);
					ignoreParenthesesBlock();
					}
					break;
				case 2:
					{
					setState(261);
					ignoreBracesBlock();
					}
					break;
				case 3:
					{
					setState(262);
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
				setState(267);
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
		enterRule(_localctx, 42, RULE_anythingBeforeRBrace);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(273);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__0) | (1L << T__1) | (1L << T__2) | (1L << T__3) | (1L << T__4) | (1L << T__5) | (1L << T__6) | (1L << T__7) | (1L << T__8) | (1L << T__9) | (1L << T__10) | (1L << T__11) | (1L << T__12) | (1L << T__13) | (1L << T__14) | (1L << T__15) | (1L << T__16) | (1L << T__17) | (1L << T__18) | (1L << T__19) | (1L << T__20) | (1L << T__21) | (1L << T__22) | (1L << T__23) | (1L << T__24) | (1L << Coordinate) | (1L << SUBGRAPH) | (1L << MERGER) | (1L << HANDLER) | (1L << SUSPEND_HANDLER) | (1L << PAYLOAD) | (1L << COMPLETE) | (1L << ROUTER) | (1L << CLONE) | (1L << VERTEX) | (1L << DecimalNumeral) | (1L << LPAREN) | (1L << RPAREN) | (1L << LBRACE) | (1L << SEMI) | (1L << COMMA) | (1L << DOT) | (1L << AT) | (1L << ASSIGN) | (1L << NEW) | (1L << COLONCOLON) | (1L << StringLiteral) | (1L << Identifier) | (1L << WS) | (1L << COMMENT) | (1L << LINE_COMMENT) | (1L << OTHER_SYMBOL))) != 0)) {
				{
				setState(271);
				_errHandler.sync(this);
				switch ( getInterpreter().adaptivePredict(_input,23,_ctx) ) {
				case 1:
					{
					setState(268);
					ignoreBracesBlock();
					}
					break;
				case 2:
					{
					setState(269);
					ignoreBracesBlock();
					}
					break;
				case 3:
					{
					setState(270);
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
				setState(275);
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
		enterRule(_localctx, 44, RULE_ignoreBracesBlock);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(276);
			match(LBRACE);
			setState(281);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__0) | (1L << T__1) | (1L << T__2) | (1L << T__3) | (1L << T__4) | (1L << T__5) | (1L << T__6) | (1L << T__7) | (1L << T__8) | (1L << T__9) | (1L << T__10) | (1L << T__11) | (1L << T__12) | (1L << T__13) | (1L << T__14) | (1L << T__15) | (1L << T__16) | (1L << T__17) | (1L << T__18) | (1L << T__19) | (1L << T__20) | (1L << T__21) | (1L << T__22) | (1L << T__23) | (1L << T__24) | (1L << Coordinate) | (1L << SUBGRAPH) | (1L << MERGER) | (1L << HANDLER) | (1L << SUSPEND_HANDLER) | (1L << PAYLOAD) | (1L << COMPLETE) | (1L << ROUTER) | (1L << CLONE) | (1L << VERTEX) | (1L << DecimalNumeral) | (1L << LPAREN) | (1L << RPAREN) | (1L << LBRACE) | (1L << SEMI) | (1L << COMMA) | (1L << DOT) | (1L << AT) | (1L << ASSIGN) | (1L << NEW) | (1L << COLONCOLON) | (1L << StringLiteral) | (1L << Identifier) | (1L << WS) | (1L << COMMENT) | (1L << LINE_COMMENT) | (1L << OTHER_SYMBOL))) != 0)) {
				{
				setState(279);
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
				case T__21:
				case T__22:
				case T__23:
				case T__24:
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
					setState(277);
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
					setState(278);
					ignoreBracesBlock();
					}
					break;
				default:
					throw new NoViableAltException(this);
				}
				}
				setState(283);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(284);
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
		enterRule(_localctx, 46, RULE_ignoreParenthesesBlock);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(286);
			match(LPAREN);
			setState(291);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__0) | (1L << T__1) | (1L << T__2) | (1L << T__3) | (1L << T__4) | (1L << T__5) | (1L << T__6) | (1L << T__7) | (1L << T__8) | (1L << T__9) | (1L << T__10) | (1L << T__11) | (1L << T__12) | (1L << T__13) | (1L << T__14) | (1L << T__15) | (1L << T__16) | (1L << T__17) | (1L << T__18) | (1L << T__19) | (1L << T__20) | (1L << T__21) | (1L << T__22) | (1L << T__23) | (1L << T__24) | (1L << Coordinate) | (1L << SUBGRAPH) | (1L << MERGER) | (1L << HANDLER) | (1L << SUSPEND_HANDLER) | (1L << PAYLOAD) | (1L << COMPLETE) | (1L << ROUTER) | (1L << CLONE) | (1L << VERTEX) | (1L << DecimalNumeral) | (1L << LPAREN) | (1L << LBRACE) | (1L << RBRACE) | (1L << SEMI) | (1L << COMMA) | (1L << DOT) | (1L << AT) | (1L << ASSIGN) | (1L << NEW) | (1L << COLONCOLON) | (1L << StringLiteral) | (1L << Identifier) | (1L << WS) | (1L << COMMENT) | (1L << LINE_COMMENT) | (1L << OTHER_SYMBOL))) != 0)) {
				{
				setState(289);
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
				case T__21:
				case T__22:
				case T__23:
				case T__24:
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
					setState(287);
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
					setState(288);
					ignoreParenthesesBlock();
					}
					break;
				default:
					throw new NoViableAltException(this);
				}
				}
				setState(293);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(294);
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
		enterRule(_localctx, 48, RULE_payloadTransitionBlock);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(296);
			match(PAYLOAD);
			setState(297);
			match(LPAREN);
			setState(298);
			match(RPAREN);
			setState(300); 
			_errHandler.sync(this);
			_alt = 1;
			do {
				switch (_alt) {
				case 1:
					{
					{
					setState(299);
					handleBy();
					}
					}
					break;
				default:
					throw new NoViableAltException(this);
				}
				setState(302); 
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,29,_ctx);
			} while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER );
			setState(305);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,30,_ctx) ) {
			case 1:
				{
				setState(304);
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
		enterRule(_localctx, 50, RULE_handleBy);
		try {
			enterOuterAlt(_localctx, 1);
			{
			{
			setState(307);
			match(DOT);
			setState(308);
			match(T__20);
			setState(309);
			match(LPAREN);
			setState(310);
			vertexName();
			setState(311);
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
		enterRule(_localctx, 52, RULE_vertexTransitionBlock);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(313);
			vertexName();
			setState(315); 
			_errHandler.sync(this);
			_alt = 1;
			do {
				switch (_alt) {
				case 1:
					{
					{
					setState(314);
					vertexTransition();
					}
					}
					break;
				default:
					throw new NoViableAltException(this);
				}
				setState(317); 
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,31,_ctx);
			} while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER );
			setState(320);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,32,_ctx) ) {
			case 1:
				{
				setState(319);
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
		enterRule(_localctx, 54, RULE_vertexTransition);
		try {
			setState(324);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,33,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(322);
				vertexTransitionOn();
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(323);
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
		enterRule(_localctx, 56, RULE_vertexTransitionOn);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(326);
			match(DOT);
			setState(327);
			match(T__21);
			setState(328);
			match(LPAREN);
			setState(329);
			transitionCondition();
			setState(330);
			match(RPAREN);
			setState(331);
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
		enterRule(_localctx, 58, RULE_vertexTransitionOnAny);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(333);
			match(DOT);
			setState(334);
			match(T__22);
			setState(335);
			match(LPAREN);
			setState(336);
			match(RPAREN);
			setState(337);
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
		enterRule(_localctx, 60, RULE_transitionAction);
		try {
			setState(342);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,34,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(339);
				transitionActionComplete();
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(340);
				transitionActionMergeBy();
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(341);
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
		enterRule(_localctx, 62, RULE_transitionActionComplete);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(344);
			match(DOT);
			setState(345);
			match(COMPLETE);
			setState(346);
			match(LPAREN);
			setState(347);
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
		enterRule(_localctx, 64, RULE_transitionActionMergeBy);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(349);
			match(DOT);
			setState(350);
			match(T__23);
			setState(351);
			match(LPAREN);
			setState(352);
			vertexName();
			setState(353);
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
		enterRule(_localctx, 66, RULE_transitionActionHandleBy);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(355);
			match(DOT);
			setState(356);
			match(T__20);
			setState(357);
			match(LPAREN);
			setState(358);
			vertexName();
			setState(359);
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
		enterRule(_localctx, 68, RULE_coordinatesBlock);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(361);
			match(T__24);
			setState(362);
			match(LPAREN);
			setState(363);
			match(RPAREN);
			setState(367);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,35,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					{
					{
					setState(364);
					coordinate();
					}
					} 
				}
				setState(369);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,35,_ctx);
			}
			setState(371);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,36,_ctx) ) {
			case 1:
				{
				setState(370);
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
		enterRule(_localctx, 70, RULE_coordinate);
		try {
			setState(379);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,37,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(373);
				coordinatePayload();
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(374);
				coordinateHandler();
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(375);
				coordinateSubgraph();
				}
				break;
			case 4:
				enterOuterAlt(_localctx, 4);
				{
				setState(376);
				coordinateRouter();
				}
				break;
			case 5:
				enterOuterAlt(_localctx, 5);
				{
				setState(377);
				coordinateMerger();
				}
				break;
			case 6:
				enterOuterAlt(_localctx, 6);
				{
				setState(378);
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
		enterRule(_localctx, 72, RULE_coordinatePayload);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(381);
			match(DOT);
			setState(382);
			match(PAYLOAD);
			setState(383);
			match(LPAREN);
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
		enterRule(_localctx, 74, RULE_coordinateHandler);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(389);
			match(DOT);
			setState(390);
			match(HANDLER);
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
		enterRule(_localctx, 76, RULE_coordinateSubgraph);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(399);
			match(DOT);
			setState(400);
			match(SUBGRAPH);
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
		enterRule(_localctx, 78, RULE_coordinateRouter);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(409);
			match(DOT);
			setState(410);
			match(ROUTER);
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
		enterRule(_localctx, 80, RULE_coordinateMerger);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(419);
			match(DOT);
			setState(420);
			match(MERGER);
			setState(421);
			match(LPAREN);
			setState(422);
			match(Identifier);
			setState(423);
			match(COMMA);
			setState(424);
			match(Coordinate);
			setState(425);
			match(COMMA);
			setState(426);
			match(Coordinate);
			setState(427);
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
		enterRule(_localctx, 82, RULE_coordinateComplete);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(429);
			match(DOT);
			setState(430);
			match(COMPLETE);
			setState(431);
			match(LPAREN);
			setState(432);
			match(Identifier);
			setState(433);
			match(COMMA);
			setState(434);
			match(Coordinate);
			setState(435);
			match(COMMA);
			setState(436);
			match(Coordinate);
			setState(437);
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
		enterRule(_localctx, 84, RULE_transitionCondition);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(439);
			match(Identifier);
			setState(444);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==DOT) {
				{
				{
				setState(440);
				match(DOT);
				setState(441);
				match(Identifier);
				}
				}
				setState(446);
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
		enterRule(_localctx, 86, RULE_ignoredToken);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(447);
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
		enterRule(_localctx, 88, RULE_anyGraphKeyword);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(449);
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
		"\3\u608b\ua72a\u8133\ub9ed\u417c\u3be7\u7786\u5964\3\67\u01c6\4\2\t\2"+
		"\4\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4\13"+
		"\t\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20\t\20\4\21\t\21\4\22\t\22"+
		"\4\23\t\23\4\24\t\24\4\25\t\25\4\26\t\26\4\27\t\27\4\30\t\30\4\31\t\31"+
		"\4\32\t\32\4\33\t\33\4\34\t\34\4\35\t\35\4\36\t\36\4\37\t\37\4 \t \4!"+
		"\t!\4\"\t\"\4#\t#\4$\t$\4%\t%\4&\t&\4\'\t\'\4(\t(\4)\t)\4*\t*\4+\t+\4"+
		",\t,\4-\t-\4.\t.\3\2\3\2\6\2_\n\2\r\2\16\2`\3\3\3\3\3\3\3\3\3\3\3\3\5"+
		"\3i\n\3\3\4\7\4l\n\4\f\4\16\4o\13\4\3\4\7\4r\n\4\f\4\16\4u\13\4\3\4\3"+
		"\4\3\4\3\4\7\4{\n\4\f\4\16\4~\13\4\3\4\5\4\u0081\n\4\3\4\3\4\3\4\3\4\5"+
		"\4\u0087\n\4\3\4\3\4\3\4\3\4\3\4\3\4\3\5\3\5\3\6\3\6\3\6\3\6\3\6\3\6\5"+
		"\6\u0097\n\6\3\7\3\7\3\b\3\b\3\t\3\t\3\t\3\t\3\t\5\t\u00a2\n\t\3\n\3\n"+
		"\3\n\3\n\3\n\3\n\3\n\3\n\3\n\5\n\u00ad\n\n\3\13\3\13\5\13\u00b1\n\13\3"+
		"\f\3\f\3\f\5\f\u00b6\n\f\3\r\3\r\3\r\3\r\3\r\3\r\3\r\5\r\u00bf\n\r\3\r"+
		"\3\r\3\r\3\r\3\r\3\r\5\r\u00c7\n\r\3\r\3\r\3\r\3\16\3\16\3\16\7\16\u00cf"+
		"\n\16\f\16\16\16\u00d2\13\16\3\17\3\17\3\17\3\17\3\17\3\17\3\17\3\17\3"+
		"\17\5\17\u00dd\n\17\3\20\3\20\3\20\3\20\3\20\3\20\3\20\3\20\3\20\5\20"+
		"\u00e8\n\20\3\20\3\20\3\20\3\21\3\21\3\22\3\22\3\22\5\22\u00f2\n\22\3"+
		"\23\3\23\3\23\3\23\3\23\3\23\3\23\3\23\3\23\5\23\u00fd\n\23\3\24\3\24"+
		"\3\24\3\24\3\25\3\25\3\25\3\25\3\26\3\26\3\26\7\26\u010a\n\26\f\26\16"+
		"\26\u010d\13\26\3\27\3\27\3\27\7\27\u0112\n\27\f\27\16\27\u0115\13\27"+
		"\3\30\3\30\3\30\7\30\u011a\n\30\f\30\16\30\u011d\13\30\3\30\3\30\3\31"+
		"\3\31\3\31\7\31\u0124\n\31\f\31\16\31\u0127\13\31\3\31\3\31\3\32\3\32"+
		"\3\32\3\32\6\32\u012f\n\32\r\32\16\32\u0130\3\32\5\32\u0134\n\32\3\33"+
		"\3\33\3\33\3\33\3\33\3\33\3\34\3\34\6\34\u013e\n\34\r\34\16\34\u013f\3"+
		"\34\5\34\u0143\n\34\3\35\3\35\5\35\u0147\n\35\3\36\3\36\3\36\3\36\3\36"+
		"\3\36\3\36\3\37\3\37\3\37\3\37\3\37\3\37\3 \3 \3 \5 \u0159\n \3!\3!\3"+
		"!\3!\3!\3\"\3\"\3\"\3\"\3\"\3\"\3#\3#\3#\3#\3#\3#\3$\3$\3$\3$\7$\u0170"+
		"\n$\f$\16$\u0173\13$\3$\5$\u0176\n$\3%\3%\3%\3%\3%\3%\5%\u017e\n%\3&\3"+
		"&\3&\3&\3&\3&\3&\3&\3\'\3\'\3\'\3\'\3\'\3\'\3\'\3\'\3\'\3\'\3(\3(\3(\3"+
		"(\3(\3(\3(\3(\3(\3(\3)\3)\3)\3)\3)\3)\3)\3)\3)\3)\3*\3*\3*\3*\3*\3*\3"+
		"*\3*\3*\3*\3+\3+\3+\3+\3+\3+\3+\3+\3+\3+\3,\3,\3,\7,\u01bd\n,\f,\16,\u01c0"+
		"\13,\3-\3-\3.\3.\3.\3`\2/\2\4\6\b\n\f\16\20\22\24\26\30\32\34\36 \"$&"+
		"(*,.\60\62\64\668:<>@BDFHJLNPRTVXZ\2\t\3\2\5\6\3\2\n\21\4\2\22\23%%\3"+
		"\2\37 \3\2\'(\3\2)*\4\2\35\37!$\2\u01cc\2^\3\2\2\2\4h\3\2\2\2\6m\3\2\2"+
		"\2\b\u008e\3\2\2\2\n\u0090\3\2\2\2\f\u0098\3\2\2\2\16\u009a\3\2\2\2\20"+
		"\u009c\3\2\2\2\22\u00a3\3\2\2\2\24\u00b0\3\2\2\2\26\u00b5\3\2\2\2\30\u00b7"+
		"\3\2\2\2\32\u00cb\3\2\2\2\34\u00d3\3\2\2\2\36\u00de\3\2\2\2 \u00ec\3\2"+
		"\2\2\"\u00f1\3\2\2\2$\u00f3\3\2\2\2&\u00fe\3\2\2\2(\u0102\3\2\2\2*\u010b"+
		"\3\2\2\2,\u0113\3\2\2\2.\u0116\3\2\2\2\60\u0120\3\2\2\2\62\u012a\3\2\2"+
		"\2\64\u0135\3\2\2\2\66\u013b\3\2\2\28\u0146\3\2\2\2:\u0148\3\2\2\2<\u014f"+
		"\3\2\2\2>\u0158\3\2\2\2@\u015a\3\2\2\2B\u015f\3\2\2\2D\u0165\3\2\2\2F"+
		"\u016b\3\2\2\2H\u017d\3\2\2\2J\u017f\3\2\2\2L\u0187\3\2\2\2N\u0191\3\2"+
		"\2\2P\u019b\3\2\2\2R\u01a5\3\2\2\2T\u01af\3\2\2\2V\u01b9\3\2\2\2X\u01c1"+
		"\3\2\2\2Z\u01c3\3\2\2\2\\_\5\4\3\2]_\5X-\2^\\\3\2\2\2^]\3\2\2\2_`\3\2"+
		"\2\2`a\3\2\2\2`^\3\2\2\2a\3\3\2\2\2bi\5\62\32\2ci\5\66\34\2di\5\20\t\2"+
		"ei\5\22\n\2fi\5F$\2gi\5\6\4\2hb\3\2\2\2hc\3\2\2\2hd\3\2\2\2he\3\2\2\2"+
		"hf\3\2\2\2hg\3\2\2\2i\5\3\2\2\2jl\5\n\6\2kj\3\2\2\2lo\3\2\2\2mk\3\2\2"+
		"\2mn\3\2\2\2ns\3\2\2\2om\3\2\2\2pr\5\b\5\2qp\3\2\2\2ru\3\2\2\2sq\3\2\2"+
		"\2st\3\2\2\2tv\3\2\2\2us\3\2\2\2vw\7\3\2\2w|\5\f\7\2x{\5\b\5\2y{\5\n\6"+
		"\2zx\3\2\2\2zy\3\2\2\2{~\3\2\2\2|z\3\2\2\2|}\3\2\2\2}\u0080\3\2\2\2~|"+
		"\3\2\2\2\177\u0081\7\4\2\2\u0080\177\3\2\2\2\u0080\u0081\3\2\2\2\u0081"+
		"\u0086\3\2\2\2\u0082\u0083\7\'\2\2\u0083\u0084\5*\26\2\u0084\u0085\7("+
		"\2\2\u0085\u0087\3\2\2\2\u0086\u0082\3\2\2\2\u0086\u0087\3\2\2\2\u0087"+
		"\u0088\3\2\2\2\u0088\u0089\t\2\2\2\u0089\u008a\7\7\2\2\u008a\u008b\7\b"+
		"\2\2\u008b\u008c\5\16\b\2\u008c\u008d\7\t\2\2\u008d\7\3\2\2\2\u008e\u008f"+
		"\t\3\2\2\u008f\t\3\2\2\2\u0090\u0091\7.\2\2\u0091\u0096\7\63\2\2\u0092"+
		"\u0093\7\'\2\2\u0093\u0094\5*\26\2\u0094\u0095\7(\2\2\u0095\u0097\3\2"+
		"\2\2\u0096\u0092\3\2\2\2\u0096\u0097\3\2\2\2\u0097\13\3\2\2\2\u0098\u0099"+
		"\7\63\2\2\u0099\r\3\2\2\2\u009a\u009b\7\63\2\2\u009b\17\3\2\2\2\u009c"+
		"\u009d\t\4\2\2\u009d\u009e\5\24\13\2\u009e\u009f\7/\2\2\u009f\u00a1\5"+
		"\26\f\2\u00a0\u00a2\7+\2\2\u00a1\u00a0\3\2\2\2\u00a1\u00a2\3\2\2\2\u00a2"+
		"\21\3\2\2\2\u00a3\u00a4\t\4\2\2\u00a4\u00a5\5\24\13\2\u00a5\u00a6\7/\2"+
		"\2\u00a6\u00a7\5\24\13\2\u00a7\u00a8\7-\2\2\u00a8\u00a9\7$\2\2\u00a9\u00aa"+
		"\7\'\2\2\u00aa\u00ac\7(\2\2\u00ab\u00ad\7+\2\2\u00ac\u00ab\3\2\2\2\u00ac"+
		"\u00ad\3\2\2\2\u00ad\23\3\2\2\2\u00ae\u00b1\5Z.\2\u00af\u00b1\7\63\2\2"+
		"\u00b0\u00ae\3\2\2\2\u00b0\u00af\3\2\2\2\u00b1\25\3\2\2\2\u00b2\u00b6"+
		"\5\36\20\2\u00b3\u00b6\5\30\r\2\u00b4\u00b6\5\34\17\2\u00b5\u00b2\3\2"+
		"\2\2\u00b5\u00b3\3\2\2\2\u00b5\u00b4\3\2\2\2\u00b6\27\3\2\2\2\u00b7\u00b8"+
		"\7\35\2\2\u00b8\u00b9\7\'\2\2\u00b9\u00be\5\32\16\2\u00ba\u00bb\7-\2\2"+
		"\u00bb\u00bf\7\3\2\2\u00bc\u00bd\7\61\2\2\u00bd\u00bf\7\3\2\2\u00be\u00ba"+
		"\3\2\2\2\u00be\u00bc\3\2\2\2\u00bf\u00c0\3\2\2\2\u00c0\u00c1\5*\26\2\u00c1"+
		"\u00c6\7(\2\2\u00c2\u00c3\7)\2\2\u00c3\u00c4\5,\27\2\u00c4\u00c5\7*\2"+
		"\2\u00c5\u00c7\3\2\2\2\u00c6\u00c2\3\2\2\2\u00c6\u00c7\3\2\2\2\u00c7\u00c8"+
		"\3\2\2\2\u00c8\u00c9\7-\2\2\u00c9\u00ca\5\"\22\2\u00ca\31\3\2\2\2\u00cb"+
		"\u00d0\7\63\2\2\u00cc\u00cd\7-\2\2\u00cd\u00cf\7\63\2\2\u00ce\u00cc\3"+
		"\2\2\2\u00cf\u00d2\3\2\2\2\u00d0\u00ce\3\2\2\2\u00d0\u00d1\3\2\2\2\u00d1"+
		"\33\3\2\2\2\u00d2\u00d0\3\2\2\2\u00d3\u00dc\7#\2\2\u00d4\u00d5\7\'\2\2"+
		"\u00d5\u00d6\5*\26\2\u00d6\u00d7\7(\2\2\u00d7\u00dd\3\2\2\2\u00d8\u00d9"+
		"\7)\2\2\u00d9\u00da\5,\27\2\u00da\u00db\7*\2\2\u00db\u00dd\3\2\2\2\u00dc"+
		"\u00d4\3\2\2\2\u00dc\u00d8\3\2\2\2\u00dd\35\3\2\2\2\u00de\u00e7\5 \21"+
		"\2\u00df\u00e0\7\'\2\2\u00e0\u00e1\5*\26\2\u00e1\u00e2\7(\2\2\u00e2\u00e8"+
		"\3\2\2\2\u00e3\u00e4\7)\2\2\u00e4\u00e5\5,\27\2\u00e5\u00e6\7*\2\2\u00e6"+
		"\u00e8\3\2\2\2\u00e7\u00df\3\2\2\2\u00e7\u00e3\3\2\2\2\u00e8\u00e9\3\2"+
		"\2\2\u00e9\u00ea\7-\2\2\u00ea\u00eb\5\"\22\2\u00eb\37\3\2\2\2\u00ec\u00ed"+
		"\t\5\2\2\u00ed!\3\2\2\2\u00ee\u00f2\5$\23\2\u00ef\u00f2\5&\24\2\u00f0"+
		"\u00f2\5(\25\2\u00f1\u00ee\3\2\2\2\u00f1\u00ef\3\2\2\2\u00f1\u00f0\3\2"+
		"\2\2\u00f2#\3\2\2\2\u00f3\u00fc\7\24\2\2\u00f4\u00f5\7\'\2\2\u00f5\u00f6"+
		"\5*\26\2\u00f6\u00f7\7(\2\2\u00f7\u00fd\3\2\2\2\u00f8\u00f9\7)\2\2\u00f9"+
		"\u00fa\5,\27\2\u00fa\u00fb\7*\2\2\u00fb\u00fd\3\2\2\2\u00fc\u00f4\3\2"+
		"\2\2\u00fc\u00f8\3\2\2\2\u00fd%\3\2\2\2\u00fe\u00ff\7\25\2\2\u00ff\u0100"+
		"\7\'\2\2\u0100\u0101\7(\2\2\u0101\'\3\2\2\2\u0102\u0103\7\26\2\2\u0103"+
		"\u0104\7\'\2\2\u0104\u0105\7(\2\2\u0105)\3\2\2\2\u0106\u010a\5\60\31\2"+
		"\u0107\u010a\5.\30\2\u0108\u010a\n\6\2\2\u0109\u0106\3\2\2\2\u0109\u0107"+
		"\3\2\2\2\u0109\u0108\3\2\2\2\u010a\u010d\3\2\2\2\u010b\u0109\3\2\2\2\u010b"+
		"\u010c\3\2\2\2\u010c+\3\2\2\2\u010d\u010b\3\2\2\2\u010e\u0112\5.\30\2"+
		"\u010f\u0112\5.\30\2\u0110\u0112\n\7\2\2\u0111\u010e\3\2\2\2\u0111\u010f"+
		"\3\2\2\2\u0111\u0110\3\2\2\2\u0112\u0115\3\2\2\2\u0113\u0111\3\2\2\2\u0113"+
		"\u0114\3\2\2\2\u0114-\3\2\2\2\u0115\u0113\3\2\2\2\u0116\u011b\7)\2\2\u0117"+
		"\u011a\n\7\2\2\u0118\u011a\5.\30\2\u0119\u0117\3\2\2\2\u0119\u0118\3\2"+
		"\2\2\u011a\u011d\3\2\2\2\u011b\u0119\3\2\2\2\u011b\u011c\3\2\2\2\u011c"+
		"\u011e\3\2\2\2\u011d\u011b\3\2\2\2\u011e\u011f\7*\2\2\u011f/\3\2\2\2\u0120"+
		"\u0125\7\'\2\2\u0121\u0124\n\6\2\2\u0122\u0124\5\60\31\2\u0123\u0121\3"+
		"\2\2\2\u0123\u0122\3\2\2\2\u0124\u0127\3\2\2\2\u0125\u0123\3\2\2\2\u0125"+
		"\u0126\3\2\2\2\u0126\u0128\3\2\2\2\u0127\u0125\3\2\2\2\u0128\u0129\7("+
		"\2\2\u0129\61\3\2\2\2\u012a\u012b\7!\2\2\u012b\u012c\7\'\2\2\u012c\u012e"+
		"\7(\2\2\u012d\u012f\5\64\33\2\u012e\u012d\3\2\2\2\u012f\u0130\3\2\2\2"+
		"\u0130\u012e\3\2\2\2\u0130\u0131\3\2\2\2\u0131\u0133\3\2\2\2\u0132\u0134"+
		"\7+\2\2\u0133\u0132\3\2\2\2\u0133\u0134\3\2\2\2\u0134\63\3\2\2\2\u0135"+
		"\u0136\7-\2\2\u0136\u0137\7\27\2\2\u0137\u0138\7\'\2\2\u0138\u0139\5\24"+
		"\13\2\u0139\u013a\7(\2\2\u013a\65\3\2\2\2\u013b\u013d\5\24\13\2\u013c"+
		"\u013e\58\35\2\u013d\u013c\3\2\2\2\u013e\u013f\3\2\2\2\u013f\u013d\3\2"+
		"\2\2\u013f\u0140\3\2\2\2\u0140\u0142\3\2\2\2\u0141\u0143\7+\2\2\u0142"+
		"\u0141\3\2\2\2\u0142\u0143\3\2\2\2\u0143\67\3\2\2\2\u0144\u0147\5:\36"+
		"\2\u0145\u0147\5<\37\2\u0146\u0144\3\2\2\2\u0146\u0145\3\2\2\2\u01479"+
		"\3\2\2\2\u0148\u0149\7-\2\2\u0149\u014a\7\30\2\2\u014a\u014b\7\'\2\2\u014b"+
		"\u014c\5V,\2\u014c\u014d\7(\2\2\u014d\u014e\5> \2\u014e;\3\2\2\2\u014f"+
		"\u0150\7-\2\2\u0150\u0151\7\31\2\2\u0151\u0152\7\'\2\2\u0152\u0153\7("+
		"\2\2\u0153\u0154\5> \2\u0154=\3\2\2\2\u0155\u0159\5@!\2\u0156\u0159\5"+
		"B\"\2\u0157\u0159\5D#\2\u0158\u0155\3\2\2\2\u0158\u0156\3\2\2\2\u0158"+
		"\u0157\3\2\2\2\u0159?\3\2\2\2\u015a\u015b\7-\2\2\u015b\u015c\7\"\2\2\u015c"+
		"\u015d\7\'\2\2\u015d\u015e\7(\2\2\u015eA\3\2\2\2\u015f\u0160\7-\2\2\u0160"+
		"\u0161\7\32\2\2\u0161\u0162\7\'\2\2\u0162\u0163\5\24\13\2\u0163\u0164"+
		"\7(\2\2\u0164C\3\2\2\2\u0165\u0166\7-\2\2\u0166\u0167\7\27\2\2\u0167\u0168"+
		"\7\'\2\2\u0168\u0169\5\24\13\2\u0169\u016a\7(\2\2\u016aE\3\2\2\2\u016b"+
		"\u016c\7\33\2\2\u016c\u016d\7\'\2\2\u016d\u0171\7(\2\2\u016e\u0170\5H"+
		"%\2\u016f\u016e\3\2\2\2\u0170\u0173\3\2\2\2\u0171\u016f\3\2\2\2\u0171"+
		"\u0172\3\2\2\2\u0172\u0175\3\2\2\2\u0173\u0171\3\2\2\2\u0174\u0176\7+"+
		"\2\2\u0175\u0174\3\2\2\2\u0175\u0176\3\2\2\2\u0176G\3\2\2\2\u0177\u017e"+
		"\5J&\2\u0178\u017e\5L\'\2\u0179\u017e\5N(\2\u017a\u017e\5P)\2\u017b\u017e"+
		"\5R*\2\u017c\u017e\5T+\2\u017d\u0177\3\2\2\2\u017d\u0178\3\2\2\2\u017d"+
		"\u0179\3\2\2\2\u017d\u017a\3\2\2\2\u017d\u017b\3\2\2\2\u017d\u017c\3\2"+
		"\2\2\u017eI\3\2\2\2\u017f\u0180\7-\2\2\u0180\u0181\7!\2\2\u0181\u0182"+
		"\7\'\2\2\u0182\u0183\7\34\2\2\u0183\u0184\7,\2\2\u0184\u0185\7\34\2\2"+
		"\u0185\u0186\7(\2\2\u0186K\3\2\2\2\u0187\u0188\7-\2\2\u0188\u0189\7\37"+
		"\2\2\u0189\u018a\7\'\2\2\u018a\u018b\7\63\2\2\u018b\u018c\7,\2\2\u018c"+
		"\u018d\7\34\2\2\u018d\u018e\7,\2\2\u018e\u018f\7\34\2\2\u018f\u0190\7"+
		"(\2\2\u0190M\3\2\2\2\u0191\u0192\7-\2\2\u0192\u0193\7\35\2\2\u0193\u0194"+
		"\7\'\2\2\u0194\u0195\7\63\2\2\u0195\u0196\7,\2\2\u0196\u0197\7\34\2\2"+
		"\u0197\u0198\7,\2\2\u0198\u0199\7\34\2\2\u0199\u019a\7(\2\2\u019aO\3\2"+
		"\2\2\u019b\u019c\7-\2\2\u019c\u019d\7#\2\2\u019d\u019e\7\'\2\2\u019e\u019f"+
		"\7\63\2\2\u019f\u01a0\7,\2\2\u01a0\u01a1\7\34\2\2\u01a1\u01a2\7,\2\2\u01a2"+
		"\u01a3\7\34\2\2\u01a3\u01a4\7(\2\2\u01a4Q\3\2\2\2\u01a5\u01a6\7-\2\2\u01a6"+
		"\u01a7\7\36\2\2\u01a7\u01a8\7\'\2\2\u01a8\u01a9\7\63\2\2\u01a9\u01aa\7"+
		",\2\2\u01aa\u01ab\7\34\2\2\u01ab\u01ac\7,\2\2\u01ac\u01ad\7\34\2\2\u01ad"+
		"\u01ae\7(\2\2\u01aeS\3\2\2\2\u01af\u01b0\7-\2\2\u01b0\u01b1\7\"\2\2\u01b1"+
		"\u01b2\7\'\2\2\u01b2\u01b3\7\63\2\2\u01b3\u01b4\7,\2\2\u01b4\u01b5\7\34"+
		"\2\2\u01b5\u01b6\7,\2\2\u01b6\u01b7\7\34\2\2\u01b7\u01b8\7(\2\2\u01b8"+
		"U\3\2\2\2\u01b9\u01be\7\63\2\2\u01ba\u01bb\7-\2\2\u01bb\u01bd\7\63\2\2"+
		"\u01bc\u01ba\3\2\2\2\u01bd\u01c0\3\2\2\2\u01be\u01bc\3\2\2\2\u01be\u01bf"+
		"\3\2\2\2\u01bfW\3\2\2\2\u01c0\u01be\3\2\2\2\u01c1\u01c2\13\2\2\2\u01c2"+
		"Y\3\2\2\2\u01c3\u01c4\t\b\2\2\u01c4[\3\2\2\2)^`hmsz|\u0080\u0086\u0096"+
		"\u00a1\u00ac\u00b0\u00b5\u00be\u00c6\u00d0\u00dc\u00e7\u00f1\u00fc\u0109"+
		"\u010b\u0111\u0113\u0119\u011b\u0123\u0125\u0130\u0133\u013f\u0142\u0146"+
		"\u0158\u0171\u0175\u017d\u01be";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}
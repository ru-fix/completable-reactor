// Generated from KotlinParser.g4 by ANTLR 4.7
package ru.fix.completable.reactor.parser.java.antlr;
import org.antlr.v4.runtime.tree.ParseTreeVisitor;

/**
 * This interface defines a complete generic visitor for a parse tree produced
 * by {@link KotlinParser}.
 *
 * @param <T> The return type of the visit operation. Use {@link Void} for
 * operations with no return type.
 */
public interface KotlinParserVisitor<T> extends ParseTreeVisitor<T> {
	/**
	 * Visit a parse tree produced by {@link KotlinParser#kotlinFile}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitKotlinFile(KotlinParser.KotlinFileContext ctx);
	/**
	 * Visit a parse tree produced by {@link KotlinParser#preamble}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPreamble(KotlinParser.PreambleContext ctx);
	/**
	 * Visit a parse tree produced by {@link KotlinParser#packageHeader}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPackageHeader(KotlinParser.PackageHeaderContext ctx);
	/**
	 * Visit a parse tree produced by {@link KotlinParser#importHeader}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitImportHeader(KotlinParser.ImportHeaderContext ctx);
	/**
	 * Visit a parse tree produced by {@link KotlinParser#toplevelObject}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitToplevelObject(KotlinParser.ToplevelObjectContext ctx);
	/**
	 * Visit a parse tree produced by {@link KotlinParser#memberDeclaration}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMemberDeclaration(KotlinParser.MemberDeclarationContext ctx);
	/**
	 * Visit a parse tree produced by {@link KotlinParser#optionalProjection}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitOptionalProjection(KotlinParser.OptionalProjectionContext ctx);
	/**
	 * Visit a parse tree produced by {@link KotlinParser#typeParameters}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTypeParameters(KotlinParser.TypeParametersContext ctx);
	/**
	 * Visit a parse tree produced by {@link KotlinParser#typeParameter}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTypeParameter(KotlinParser.TypeParameterContext ctx);
	/**
	 * Visit a parse tree produced by {@link KotlinParser#typeArguments}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTypeArguments(KotlinParser.TypeArgumentsContext ctx);
	/**
	 * Visit a parse tree produced by {@link KotlinParser#typeConstraints}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTypeConstraints(KotlinParser.TypeConstraintsContext ctx);
	/**
	 * Visit a parse tree produced by {@link KotlinParser#typeConstraint}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTypeConstraint(KotlinParser.TypeConstraintContext ctx);
	/**
	 * Visit a parse tree produced by {@link KotlinParser#type}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitType(KotlinParser.TypeContext ctx);
	/**
	 * Visit a parse tree produced by {@link KotlinParser#typeDescriptor}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTypeDescriptor(KotlinParser.TypeDescriptorContext ctx);
	/**
	 * Visit a parse tree produced by {@link KotlinParser#userType}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitUserType(KotlinParser.UserTypeContext ctx);
	/**
	 * Visit a parse tree produced by {@link KotlinParser#simpleUserType_typeParam}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSimpleUserType_typeParam(KotlinParser.SimpleUserType_typeParamContext ctx);
	/**
	 * Visit a parse tree produced by {@link KotlinParser#simpleUserType}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSimpleUserType(KotlinParser.SimpleUserTypeContext ctx);
	/**
	 * Visit a parse tree produced by {@link KotlinParser#variableDeclarationEntry}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitVariableDeclarationEntry(KotlinParser.VariableDeclarationEntryContext ctx);
	/**
	 * Visit a parse tree produced by {@link KotlinParser#functionType_paramOrType}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFunctionType_paramOrType(KotlinParser.FunctionType_paramOrTypeContext ctx);
	/**
	 * Visit a parse tree produced by {@link KotlinParser#extensionFunctionTypeNoReceiver}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExtensionFunctionTypeNoReceiver(KotlinParser.ExtensionFunctionTypeNoReceiverContext ctx);
	/**
	 * Visit a parse tree produced by {@link KotlinParser#functionTypeNoReceiver}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFunctionTypeNoReceiver(KotlinParser.FunctionTypeNoReceiverContext ctx);
	/**
	 * Visit a parse tree produced by {@link KotlinParser#multipleVariableDeclarations}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMultipleVariableDeclarations(KotlinParser.MultipleVariableDeclarationsContext ctx);
	/**
	 * Visit a parse tree produced by {@link KotlinParser#propertyDeclaration}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPropertyDeclaration(KotlinParser.PropertyDeclarationContext ctx);
	/**
	 * Visit a parse tree produced by {@link KotlinParser#getter}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitGetter(KotlinParser.GetterContext ctx);
	/**
	 * Visit a parse tree produced by {@link KotlinParser#setter}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSetter(KotlinParser.SetterContext ctx);
	/**
	 * Visit a parse tree produced by {@link KotlinParser#modifiers}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitModifiers(KotlinParser.ModifiersContext ctx);
	/**
	 * Visit a parse tree produced by {@link KotlinParser#modifier}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitModifier(KotlinParser.ModifierContext ctx);
	/**
	 * Visit a parse tree produced by {@link KotlinParser#modifierKeyword}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitModifierKeyword(KotlinParser.ModifierKeywordContext ctx);
	/**
	 * Visit a parse tree produced by {@link KotlinParser#hierarchyModifier}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitHierarchyModifier(KotlinParser.HierarchyModifierContext ctx);
	/**
	 * Visit a parse tree produced by {@link KotlinParser#classModifier}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitClassModifier(KotlinParser.ClassModifierContext ctx);
	/**
	 * Visit a parse tree produced by {@link KotlinParser#accessModifier}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAccessModifier(KotlinParser.AccessModifierContext ctx);
	/**
	 * Visit a parse tree produced by {@link KotlinParser#varianceAnnotation}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitVarianceAnnotation(KotlinParser.VarianceAnnotationContext ctx);
	/**
	 * Visit a parse tree produced by {@link KotlinParser#annotations}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAnnotations(KotlinParser.AnnotationsContext ctx);
	/**
	 * Visit a parse tree produced by {@link KotlinParser#annotation}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAnnotation(KotlinParser.AnnotationContext ctx);
	/**
	 * Visit a parse tree produced by {@link KotlinParser#annotationUseSiteTarget}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAnnotationUseSiteTarget(KotlinParser.AnnotationUseSiteTargetContext ctx);
	/**
	 * Visit a parse tree produced by {@link KotlinParser#valueArgument}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitValueArgument(KotlinParser.ValueArgumentContext ctx);
	/**
	 * Visit a parse tree produced by {@link KotlinParser#valueArguments}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitValueArguments(KotlinParser.ValueArgumentsContext ctx);
	/**
	 * Visit a parse tree produced by {@link KotlinParser#unescapedAnnotation}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitUnescapedAnnotation(KotlinParser.UnescapedAnnotationContext ctx);
	/**
	 * Visit a parse tree produced by {@link KotlinParser#jump}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitJump(KotlinParser.JumpContext ctx);
	/**
	 * Visit a parse tree produced by {@link KotlinParser#labelReference}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLabelReference(KotlinParser.LabelReferenceContext ctx);
	/**
	 * Visit a parse tree produced by {@link KotlinParser#labelDefinition}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLabelDefinition(KotlinParser.LabelDefinitionContext ctx);
	/**
	 * Visit a parse tree produced by {@link KotlinParser#parameter}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitParameter(KotlinParser.ParameterContext ctx);
	/**
	 * Visit a parse tree produced by {@link KotlinParser#functionParameter}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFunctionParameter(KotlinParser.FunctionParameterContext ctx);
	/**
	 * Visit a parse tree produced by {@link KotlinParser#primaryConstructor}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPrimaryConstructor(KotlinParser.PrimaryConstructorContext ctx);
	/**
	 * Visit a parse tree produced by {@link KotlinParser#secondaryConstructor}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSecondaryConstructor(KotlinParser.SecondaryConstructorContext ctx);
	/**
	 * Visit a parse tree produced by {@link KotlinParser#constructorDelegationCall}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitConstructorDelegationCall(KotlinParser.ConstructorDelegationCallContext ctx);
	/**
	 * Visit a parse tree produced by {@link KotlinParser#callSuffix}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCallSuffix(KotlinParser.CallSuffixContext ctx);
	/**
	 * Visit a parse tree produced by {@link KotlinParser#constructorInvocation}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitConstructorInvocation(KotlinParser.ConstructorInvocationContext ctx);
	/**
	 * Visit a parse tree produced by {@link KotlinParser#explicitDelegation}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExplicitDelegation(KotlinParser.ExplicitDelegationContext ctx);
	/**
	 * Visit a parse tree produced by {@link KotlinParser#delegationSpecifier}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDelegationSpecifier(KotlinParser.DelegationSpecifierContext ctx);
	/**
	 * Visit a parse tree produced by {@link KotlinParser#classDeclaration}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitClassDeclaration(KotlinParser.ClassDeclarationContext ctx);
	/**
	 * Visit a parse tree produced by {@link KotlinParser#supertypesSpecifiers}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSupertypesSpecifiers(KotlinParser.SupertypesSpecifiersContext ctx);
	/**
	 * Visit a parse tree produced by {@link KotlinParser#objectDeclaration}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitObjectDeclaration(KotlinParser.ObjectDeclarationContext ctx);
	/**
	 * Visit a parse tree produced by {@link KotlinParser#companionObject}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCompanionObject(KotlinParser.CompanionObjectContext ctx);
	/**
	 * Visit a parse tree produced by {@link KotlinParser#classBody}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitClassBody(KotlinParser.ClassBodyContext ctx);
	/**
	 * Visit a parse tree produced by {@link KotlinParser#members}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMembers(KotlinParser.MembersContext ctx);
	/**
	 * Visit a parse tree produced by {@link KotlinParser#valueParameters}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitValueParameters(KotlinParser.ValueParametersContext ctx);
	/**
	 * Visit a parse tree produced by {@link KotlinParser#functionDeclaration}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFunctionDeclaration(KotlinParser.FunctionDeclarationContext ctx);
	/**
	 * Visit a parse tree produced by {@link KotlinParser#statements}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitStatements(KotlinParser.StatementsContext ctx);
	/**
	 * Visit a parse tree produced by {@link KotlinParser#functionBody}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFunctionBody(KotlinParser.FunctionBodyContext ctx);
	/**
	 * Visit a parse tree produced by {@link KotlinParser#block}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBlock(KotlinParser.BlockContext ctx);
	/**
	 * Visit a parse tree produced by {@link KotlinParser#anonymousInitializer}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAnonymousInitializer(KotlinParser.AnonymousInitializerContext ctx);
	/**
	 * Visit a parse tree produced by {@link KotlinParser#enumClassBody}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitEnumClassBody(KotlinParser.EnumClassBodyContext ctx);
	/**
	 * Visit a parse tree produced by {@link KotlinParser#enumEntries}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitEnumEntries(KotlinParser.EnumEntriesContext ctx);
	/**
	 * Visit a parse tree produced by {@link KotlinParser#enumEntry}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitEnumEntry(KotlinParser.EnumEntryContext ctx);
	/**
	 * Visit a parse tree produced by {@link KotlinParser#ifExpression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIfExpression(KotlinParser.IfExpressionContext ctx);
	/**
	 * Visit a parse tree produced by {@link KotlinParser#tryExpression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTryExpression(KotlinParser.TryExpressionContext ctx);
	/**
	 * Visit a parse tree produced by {@link KotlinParser#catchBlock}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCatchBlock(KotlinParser.CatchBlockContext ctx);
	/**
	 * Visit a parse tree produced by {@link KotlinParser#finallyBlock}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFinallyBlock(KotlinParser.FinallyBlockContext ctx);
	/**
	 * Visit a parse tree produced by {@link KotlinParser#loop}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLoop(KotlinParser.LoopContext ctx);
	/**
	 * Visit a parse tree produced by {@link KotlinParser#forLoop}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitForLoop(KotlinParser.ForLoopContext ctx);
	/**
	 * Visit a parse tree produced by {@link KotlinParser#whileLoop}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitWhileLoop(KotlinParser.WhileLoopContext ctx);
	/**
	 * Visit a parse tree produced by {@link KotlinParser#doWhileLoop}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDoWhileLoop(KotlinParser.DoWhileLoopContext ctx);
	/**
	 * Visit a parse tree produced by {@link KotlinParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExpression(KotlinParser.ExpressionContext ctx);
	/**
	 * Visit a parse tree produced by {@link KotlinParser#disjunction}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDisjunction(KotlinParser.DisjunctionContext ctx);
	/**
	 * Visit a parse tree produced by {@link KotlinParser#conjunction}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitConjunction(KotlinParser.ConjunctionContext ctx);
	/**
	 * Visit a parse tree produced by {@link KotlinParser#equalityComparison}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitEqualityComparison(KotlinParser.EqualityComparisonContext ctx);
	/**
	 * Visit a parse tree produced by {@link KotlinParser#comparison}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitComparison(KotlinParser.ComparisonContext ctx);
	/**
	 * Visit a parse tree produced by {@link KotlinParser#namedInfix}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitNamedInfix(KotlinParser.NamedInfixContext ctx);
	/**
	 * Visit a parse tree produced by {@link KotlinParser#elvisExpression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitElvisExpression(KotlinParser.ElvisExpressionContext ctx);
	/**
	 * Visit a parse tree produced by {@link KotlinParser#infixFunctionCall}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitInfixFunctionCall(KotlinParser.InfixFunctionCallContext ctx);
	/**
	 * Visit a parse tree produced by {@link KotlinParser#rangeExpression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitRangeExpression(KotlinParser.RangeExpressionContext ctx);
	/**
	 * Visit a parse tree produced by {@link KotlinParser#additiveExpression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAdditiveExpression(KotlinParser.AdditiveExpressionContext ctx);
	/**
	 * Visit a parse tree produced by {@link KotlinParser#multiplicativeExpression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMultiplicativeExpression(KotlinParser.MultiplicativeExpressionContext ctx);
	/**
	 * Visit a parse tree produced by {@link KotlinParser#typeRHS}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTypeRHS(KotlinParser.TypeRHSContext ctx);
	/**
	 * Visit a parse tree produced by {@link KotlinParser#prefixUnaryExpression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPrefixUnaryExpression(KotlinParser.PrefixUnaryExpressionContext ctx);
	/**
	 * Visit a parse tree produced by {@link KotlinParser#postfixUnaryExpression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPostfixUnaryExpression(KotlinParser.PostfixUnaryExpressionContext ctx);
	/**
	 * Visit a parse tree produced by {@link KotlinParser#callableReference}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCallableReference(KotlinParser.CallableReferenceContext ctx);
	/**
	 * Visit a parse tree produced by {@link KotlinParser#identifier}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIdentifier(KotlinParser.IdentifierContext ctx);
	/**
	 * Visit a parse tree produced by {@link KotlinParser#stringLiteral}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitStringLiteral(KotlinParser.StringLiteralContext ctx);
	/**
	 * Visit a parse tree produced by {@link KotlinParser#atomicExpression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAtomicExpression(KotlinParser.AtomicExpressionContext ctx);
	/**
	 * Visit a parse tree produced by {@link KotlinParser#literalConstant}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLiteralConstant(KotlinParser.LiteralConstantContext ctx);
	/**
	 * Visit a parse tree produced by {@link KotlinParser#declaration}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDeclaration(KotlinParser.DeclarationContext ctx);
	/**
	 * Visit a parse tree produced by {@link KotlinParser#statement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitStatement(KotlinParser.StatementContext ctx);
	/**
	 * Visit a parse tree produced by {@link KotlinParser#multiplicativeOperation}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMultiplicativeOperation(KotlinParser.MultiplicativeOperationContext ctx);
	/**
	 * Visit a parse tree produced by {@link KotlinParser#additiveOperation}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAdditiveOperation(KotlinParser.AdditiveOperationContext ctx);
	/**
	 * Visit a parse tree produced by {@link KotlinParser#inOperation}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitInOperation(KotlinParser.InOperationContext ctx);
	/**
	 * Visit a parse tree produced by {@link KotlinParser#typeOperation}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTypeOperation(KotlinParser.TypeOperationContext ctx);
	/**
	 * Visit a parse tree produced by {@link KotlinParser#isOperation}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIsOperation(KotlinParser.IsOperationContext ctx);
	/**
	 * Visit a parse tree produced by {@link KotlinParser#comparisonOperation}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitComparisonOperation(KotlinParser.ComparisonOperationContext ctx);
	/**
	 * Visit a parse tree produced by {@link KotlinParser#equalityOperation}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitEqualityOperation(KotlinParser.EqualityOperationContext ctx);
	/**
	 * Visit a parse tree produced by {@link KotlinParser#assignmentOperator}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAssignmentOperator(KotlinParser.AssignmentOperatorContext ctx);
	/**
	 * Visit a parse tree produced by {@link KotlinParser#prefixUnaryOperation}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPrefixUnaryOperation(KotlinParser.PrefixUnaryOperationContext ctx);
	/**
	 * Visit a parse tree produced by {@link KotlinParser#postfixUnaryOperation}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPostfixUnaryOperation(KotlinParser.PostfixUnaryOperationContext ctx);
	/**
	 * Visit a parse tree produced by {@link KotlinParser#annotatedLambda}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAnnotatedLambda(KotlinParser.AnnotatedLambdaContext ctx);
	/**
	 * Visit a parse tree produced by {@link KotlinParser#memberAccessOperation}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMemberAccessOperation(KotlinParser.MemberAccessOperationContext ctx);
	/**
	 * Visit a parse tree produced by {@link KotlinParser#functionLiteral}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFunctionLiteral(KotlinParser.FunctionLiteralContext ctx);
	/**
	 * Visit a parse tree produced by {@link KotlinParser#arrayAccess}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitArrayAccess(KotlinParser.ArrayAccessContext ctx);
	/**
	 * Visit a parse tree produced by {@link KotlinParser#objectLiteral}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitObjectLiteral(KotlinParser.ObjectLiteralContext ctx);
	/**
	 * Visit a parse tree produced by {@link KotlinParser#when}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitWhen(KotlinParser.WhenContext ctx);
	/**
	 * Visit a parse tree produced by {@link KotlinParser#whenEntry}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitWhenEntry(KotlinParser.WhenEntryContext ctx);
	/**
	 * Visit a parse tree produced by {@link KotlinParser#whenCondition}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitWhenCondition(KotlinParser.WhenConditionContext ctx);
}
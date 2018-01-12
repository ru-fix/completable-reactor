// Generated from GraphConfigJava9.g4 by ANTLR 4.7.1
package ru.fix.completable.reactor.parser.java.antlr;
import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link GraphConfigJava9Parser}.
 */
public interface GraphConfigJava9Listener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by {@link GraphConfigJava9Parser#sourceFile}.
	 * @param ctx the parse tree
	 */
	void enterSourceFile(GraphConfigJava9Parser.SourceFileContext ctx);
	/**
	 * Exit a parse tree produced by {@link GraphConfigJava9Parser#sourceFile}.
	 * @param ctx the parse tree
	 */
	void exitSourceFile(GraphConfigJava9Parser.SourceFileContext ctx);
	/**
	 * Enter a parse tree produced by {@link GraphConfigJava9Parser#graphBlock}.
	 * @param ctx the parse tree
	 */
	void enterGraphBlock(GraphConfigJava9Parser.GraphBlockContext ctx);
	/**
	 * Exit a parse tree produced by {@link GraphConfigJava9Parser#graphBlock}.
	 * @param ctx the parse tree
	 */
	void exitGraphBlock(GraphConfigJava9Parser.GraphBlockContext ctx);
	/**
	 * Enter a parse tree produced by {@link GraphConfigJava9Parser#vertexInitializationBlock}.
	 * @param ctx the parse tree
	 */
	void enterVertexInitializationBlock(GraphConfigJava9Parser.VertexInitializationBlockContext ctx);
	/**
	 * Exit a parse tree produced by {@link GraphConfigJava9Parser#vertexInitializationBlock}.
	 * @param ctx the parse tree
	 */
	void exitVertexInitializationBlock(GraphConfigJava9Parser.VertexInitializationBlockContext ctx);
	/**
	 * Enter a parse tree produced by {@link GraphConfigJava9Parser#ignoreBracesBlock}.
	 * @param ctx the parse tree
	 */
	void enterIgnoreBracesBlock(GraphConfigJava9Parser.IgnoreBracesBlockContext ctx);
	/**
	 * Exit a parse tree produced by {@link GraphConfigJava9Parser#ignoreBracesBlock}.
	 * @param ctx the parse tree
	 */
	void exitIgnoreBracesBlock(GraphConfigJava9Parser.IgnoreBracesBlockContext ctx);
	/**
	 * Enter a parse tree produced by {@link GraphConfigJava9Parser#payloadTransitionBlock}.
	 * @param ctx the parse tree
	 */
	void enterPayloadTransitionBlock(GraphConfigJava9Parser.PayloadTransitionBlockContext ctx);
	/**
	 * Exit a parse tree produced by {@link GraphConfigJava9Parser#payloadTransitionBlock}.
	 * @param ctx the parse tree
	 */
	void exitPayloadTransitionBlock(GraphConfigJava9Parser.PayloadTransitionBlockContext ctx);
	/**
	 * Enter a parse tree produced by {@link GraphConfigJava9Parser#handleBy}.
	 * @param ctx the parse tree
	 */
	void enterHandleBy(GraphConfigJava9Parser.HandleByContext ctx);
	/**
	 * Exit a parse tree produced by {@link GraphConfigJava9Parser#handleBy}.
	 * @param ctx the parse tree
	 */
	void exitHandleBy(GraphConfigJava9Parser.HandleByContext ctx);
	/**
	 * Enter a parse tree produced by {@link GraphConfigJava9Parser#handleByVertex}.
	 * @param ctx the parse tree
	 */
	void enterHandleByVertex(GraphConfigJava9Parser.HandleByVertexContext ctx);
	/**
	 * Exit a parse tree produced by {@link GraphConfigJava9Parser#handleByVertex}.
	 * @param ctx the parse tree
	 */
	void exitHandleByVertex(GraphConfigJava9Parser.HandleByVertexContext ctx);
	/**
	 * Enter a parse tree produced by {@link GraphConfigJava9Parser#vertexTransitionBlock}.
	 * @param ctx the parse tree
	 */
	void enterVertexTransitionBlock(GraphConfigJava9Parser.VertexTransitionBlockContext ctx);
	/**
	 * Exit a parse tree produced by {@link GraphConfigJava9Parser#vertexTransitionBlock}.
	 * @param ctx the parse tree
	 */
	void exitVertexTransitionBlock(GraphConfigJava9Parser.VertexTransitionBlockContext ctx);
	/**
	 * Enter a parse tree produced by {@link GraphConfigJava9Parser#vertexTransition}.
	 * @param ctx the parse tree
	 */
	void enterVertexTransition(GraphConfigJava9Parser.VertexTransitionContext ctx);
	/**
	 * Exit a parse tree produced by {@link GraphConfigJava9Parser#vertexTransition}.
	 * @param ctx the parse tree
	 */
	void exitVertexTransition(GraphConfigJava9Parser.VertexTransitionContext ctx);
	/**
	 * Enter a parse tree produced by {@link GraphConfigJava9Parser#transitionAction}.
	 * @param ctx the parse tree
	 */
	void enterTransitionAction(GraphConfigJava9Parser.TransitionActionContext ctx);
	/**
	 * Exit a parse tree produced by {@link GraphConfigJava9Parser#transitionAction}.
	 * @param ctx the parse tree
	 */
	void exitTransitionAction(GraphConfigJava9Parser.TransitionActionContext ctx);
	/**
	 * Enter a parse tree produced by {@link GraphConfigJava9Parser#transitionCondition}.
	 * @param ctx the parse tree
	 */
	void enterTransitionCondition(GraphConfigJava9Parser.TransitionConditionContext ctx);
	/**
	 * Exit a parse tree produced by {@link GraphConfigJava9Parser#transitionCondition}.
	 * @param ctx the parse tree
	 */
	void exitTransitionCondition(GraphConfigJava9Parser.TransitionConditionContext ctx);
	/**
	 * Enter a parse tree produced by {@link GraphConfigJava9Parser#ignoredCharBloc}.
	 * @param ctx the parse tree
	 */
	void enterIgnoredCharBloc(GraphConfigJava9Parser.IgnoredCharBlocContext ctx);
	/**
	 * Exit a parse tree produced by {@link GraphConfigJava9Parser#ignoredCharBloc}.
	 * @param ctx the parse tree
	 */
	void exitIgnoredCharBloc(GraphConfigJava9Parser.IgnoredCharBlocContext ctx);
	/**
	 * Enter a parse tree produced by {@link GraphConfigJava9Parser#ignoredChar}.
	 * @param ctx the parse tree
	 */
	void enterIgnoredChar(GraphConfigJava9Parser.IgnoredCharContext ctx);
	/**
	 * Exit a parse tree produced by {@link GraphConfigJava9Parser#ignoredChar}.
	 * @param ctx the parse tree
	 */
	void exitIgnoredChar(GraphConfigJava9Parser.IgnoredCharContext ctx);
	/**
	 * Enter a parse tree produced by {@link GraphConfigJava9Parser#literal}.
	 * @param ctx the parse tree
	 */
	void enterLiteral(GraphConfigJava9Parser.LiteralContext ctx);
	/**
	 * Exit a parse tree produced by {@link GraphConfigJava9Parser#literal}.
	 * @param ctx the parse tree
	 */
	void exitLiteral(GraphConfigJava9Parser.LiteralContext ctx);
	/**
	 * Enter a parse tree produced by {@link GraphConfigJava9Parser#type}.
	 * @param ctx the parse tree
	 */
	void enterType(GraphConfigJava9Parser.TypeContext ctx);
	/**
	 * Exit a parse tree produced by {@link GraphConfigJava9Parser#type}.
	 * @param ctx the parse tree
	 */
	void exitType(GraphConfigJava9Parser.TypeContext ctx);
	/**
	 * Enter a parse tree produced by {@link GraphConfigJava9Parser#primitiveType}.
	 * @param ctx the parse tree
	 */
	void enterPrimitiveType(GraphConfigJava9Parser.PrimitiveTypeContext ctx);
	/**
	 * Exit a parse tree produced by {@link GraphConfigJava9Parser#primitiveType}.
	 * @param ctx the parse tree
	 */
	void exitPrimitiveType(GraphConfigJava9Parser.PrimitiveTypeContext ctx);
	/**
	 * Enter a parse tree produced by {@link GraphConfigJava9Parser#numericType}.
	 * @param ctx the parse tree
	 */
	void enterNumericType(GraphConfigJava9Parser.NumericTypeContext ctx);
	/**
	 * Exit a parse tree produced by {@link GraphConfigJava9Parser#numericType}.
	 * @param ctx the parse tree
	 */
	void exitNumericType(GraphConfigJava9Parser.NumericTypeContext ctx);
	/**
	 * Enter a parse tree produced by {@link GraphConfigJava9Parser#integralType}.
	 * @param ctx the parse tree
	 */
	void enterIntegralType(GraphConfigJava9Parser.IntegralTypeContext ctx);
	/**
	 * Exit a parse tree produced by {@link GraphConfigJava9Parser#integralType}.
	 * @param ctx the parse tree
	 */
	void exitIntegralType(GraphConfigJava9Parser.IntegralTypeContext ctx);
	/**
	 * Enter a parse tree produced by {@link GraphConfigJava9Parser#floatingPointType}.
	 * @param ctx the parse tree
	 */
	void enterFloatingPointType(GraphConfigJava9Parser.FloatingPointTypeContext ctx);
	/**
	 * Exit a parse tree produced by {@link GraphConfigJava9Parser#floatingPointType}.
	 * @param ctx the parse tree
	 */
	void exitFloatingPointType(GraphConfigJava9Parser.FloatingPointTypeContext ctx);
	/**
	 * Enter a parse tree produced by {@link GraphConfigJava9Parser#referenceType}.
	 * @param ctx the parse tree
	 */
	void enterReferenceType(GraphConfigJava9Parser.ReferenceTypeContext ctx);
	/**
	 * Exit a parse tree produced by {@link GraphConfigJava9Parser#referenceType}.
	 * @param ctx the parse tree
	 */
	void exitReferenceType(GraphConfigJava9Parser.ReferenceTypeContext ctx);
	/**
	 * Enter a parse tree produced by {@link GraphConfigJava9Parser#classOrInterfaceType}.
	 * @param ctx the parse tree
	 */
	void enterClassOrInterfaceType(GraphConfigJava9Parser.ClassOrInterfaceTypeContext ctx);
	/**
	 * Exit a parse tree produced by {@link GraphConfigJava9Parser#classOrInterfaceType}.
	 * @param ctx the parse tree
	 */
	void exitClassOrInterfaceType(GraphConfigJava9Parser.ClassOrInterfaceTypeContext ctx);
	/**
	 * Enter a parse tree produced by {@link GraphConfigJava9Parser#classType}.
	 * @param ctx the parse tree
	 */
	void enterClassType(GraphConfigJava9Parser.ClassTypeContext ctx);
	/**
	 * Exit a parse tree produced by {@link GraphConfigJava9Parser#classType}.
	 * @param ctx the parse tree
	 */
	void exitClassType(GraphConfigJava9Parser.ClassTypeContext ctx);
	/**
	 * Enter a parse tree produced by {@link GraphConfigJava9Parser#classType_lf_classOrInterfaceType}.
	 * @param ctx the parse tree
	 */
	void enterClassType_lf_classOrInterfaceType(GraphConfigJava9Parser.ClassType_lf_classOrInterfaceTypeContext ctx);
	/**
	 * Exit a parse tree produced by {@link GraphConfigJava9Parser#classType_lf_classOrInterfaceType}.
	 * @param ctx the parse tree
	 */
	void exitClassType_lf_classOrInterfaceType(GraphConfigJava9Parser.ClassType_lf_classOrInterfaceTypeContext ctx);
	/**
	 * Enter a parse tree produced by {@link GraphConfigJava9Parser#classType_lfno_classOrInterfaceType}.
	 * @param ctx the parse tree
	 */
	void enterClassType_lfno_classOrInterfaceType(GraphConfigJava9Parser.ClassType_lfno_classOrInterfaceTypeContext ctx);
	/**
	 * Exit a parse tree produced by {@link GraphConfigJava9Parser#classType_lfno_classOrInterfaceType}.
	 * @param ctx the parse tree
	 */
	void exitClassType_lfno_classOrInterfaceType(GraphConfigJava9Parser.ClassType_lfno_classOrInterfaceTypeContext ctx);
	/**
	 * Enter a parse tree produced by {@link GraphConfigJava9Parser#interfaceType}.
	 * @param ctx the parse tree
	 */
	void enterInterfaceType(GraphConfigJava9Parser.InterfaceTypeContext ctx);
	/**
	 * Exit a parse tree produced by {@link GraphConfigJava9Parser#interfaceType}.
	 * @param ctx the parse tree
	 */
	void exitInterfaceType(GraphConfigJava9Parser.InterfaceTypeContext ctx);
	/**
	 * Enter a parse tree produced by {@link GraphConfigJava9Parser#interfaceType_lf_classOrInterfaceType}.
	 * @param ctx the parse tree
	 */
	void enterInterfaceType_lf_classOrInterfaceType(GraphConfigJava9Parser.InterfaceType_lf_classOrInterfaceTypeContext ctx);
	/**
	 * Exit a parse tree produced by {@link GraphConfigJava9Parser#interfaceType_lf_classOrInterfaceType}.
	 * @param ctx the parse tree
	 */
	void exitInterfaceType_lf_classOrInterfaceType(GraphConfigJava9Parser.InterfaceType_lf_classOrInterfaceTypeContext ctx);
	/**
	 * Enter a parse tree produced by {@link GraphConfigJava9Parser#interfaceType_lfno_classOrInterfaceType}.
	 * @param ctx the parse tree
	 */
	void enterInterfaceType_lfno_classOrInterfaceType(GraphConfigJava9Parser.InterfaceType_lfno_classOrInterfaceTypeContext ctx);
	/**
	 * Exit a parse tree produced by {@link GraphConfigJava9Parser#interfaceType_lfno_classOrInterfaceType}.
	 * @param ctx the parse tree
	 */
	void exitInterfaceType_lfno_classOrInterfaceType(GraphConfigJava9Parser.InterfaceType_lfno_classOrInterfaceTypeContext ctx);
	/**
	 * Enter a parse tree produced by {@link GraphConfigJava9Parser#typeVariable}.
	 * @param ctx the parse tree
	 */
	void enterTypeVariable(GraphConfigJava9Parser.TypeVariableContext ctx);
	/**
	 * Exit a parse tree produced by {@link GraphConfigJava9Parser#typeVariable}.
	 * @param ctx the parse tree
	 */
	void exitTypeVariable(GraphConfigJava9Parser.TypeVariableContext ctx);
	/**
	 * Enter a parse tree produced by {@link GraphConfigJava9Parser#arrayType}.
	 * @param ctx the parse tree
	 */
	void enterArrayType(GraphConfigJava9Parser.ArrayTypeContext ctx);
	/**
	 * Exit a parse tree produced by {@link GraphConfigJava9Parser#arrayType}.
	 * @param ctx the parse tree
	 */
	void exitArrayType(GraphConfigJava9Parser.ArrayTypeContext ctx);
	/**
	 * Enter a parse tree produced by {@link GraphConfigJava9Parser#dims}.
	 * @param ctx the parse tree
	 */
	void enterDims(GraphConfigJava9Parser.DimsContext ctx);
	/**
	 * Exit a parse tree produced by {@link GraphConfigJava9Parser#dims}.
	 * @param ctx the parse tree
	 */
	void exitDims(GraphConfigJava9Parser.DimsContext ctx);
	/**
	 * Enter a parse tree produced by {@link GraphConfigJava9Parser#typeParameter}.
	 * @param ctx the parse tree
	 */
	void enterTypeParameter(GraphConfigJava9Parser.TypeParameterContext ctx);
	/**
	 * Exit a parse tree produced by {@link GraphConfigJava9Parser#typeParameter}.
	 * @param ctx the parse tree
	 */
	void exitTypeParameter(GraphConfigJava9Parser.TypeParameterContext ctx);
	/**
	 * Enter a parse tree produced by {@link GraphConfigJava9Parser#typeParameterModifier}.
	 * @param ctx the parse tree
	 */
	void enterTypeParameterModifier(GraphConfigJava9Parser.TypeParameterModifierContext ctx);
	/**
	 * Exit a parse tree produced by {@link GraphConfigJava9Parser#typeParameterModifier}.
	 * @param ctx the parse tree
	 */
	void exitTypeParameterModifier(GraphConfigJava9Parser.TypeParameterModifierContext ctx);
	/**
	 * Enter a parse tree produced by {@link GraphConfigJava9Parser#typeBound}.
	 * @param ctx the parse tree
	 */
	void enterTypeBound(GraphConfigJava9Parser.TypeBoundContext ctx);
	/**
	 * Exit a parse tree produced by {@link GraphConfigJava9Parser#typeBound}.
	 * @param ctx the parse tree
	 */
	void exitTypeBound(GraphConfigJava9Parser.TypeBoundContext ctx);
	/**
	 * Enter a parse tree produced by {@link GraphConfigJava9Parser#additionalBound}.
	 * @param ctx the parse tree
	 */
	void enterAdditionalBound(GraphConfigJava9Parser.AdditionalBoundContext ctx);
	/**
	 * Exit a parse tree produced by {@link GraphConfigJava9Parser#additionalBound}.
	 * @param ctx the parse tree
	 */
	void exitAdditionalBound(GraphConfigJava9Parser.AdditionalBoundContext ctx);
	/**
	 * Enter a parse tree produced by {@link GraphConfigJava9Parser#typeArguments}.
	 * @param ctx the parse tree
	 */
	void enterTypeArguments(GraphConfigJava9Parser.TypeArgumentsContext ctx);
	/**
	 * Exit a parse tree produced by {@link GraphConfigJava9Parser#typeArguments}.
	 * @param ctx the parse tree
	 */
	void exitTypeArguments(GraphConfigJava9Parser.TypeArgumentsContext ctx);
	/**
	 * Enter a parse tree produced by {@link GraphConfigJava9Parser#typeArgumentList}.
	 * @param ctx the parse tree
	 */
	void enterTypeArgumentList(GraphConfigJava9Parser.TypeArgumentListContext ctx);
	/**
	 * Exit a parse tree produced by {@link GraphConfigJava9Parser#typeArgumentList}.
	 * @param ctx the parse tree
	 */
	void exitTypeArgumentList(GraphConfigJava9Parser.TypeArgumentListContext ctx);
	/**
	 * Enter a parse tree produced by {@link GraphConfigJava9Parser#typeArgument}.
	 * @param ctx the parse tree
	 */
	void enterTypeArgument(GraphConfigJava9Parser.TypeArgumentContext ctx);
	/**
	 * Exit a parse tree produced by {@link GraphConfigJava9Parser#typeArgument}.
	 * @param ctx the parse tree
	 */
	void exitTypeArgument(GraphConfigJava9Parser.TypeArgumentContext ctx);
	/**
	 * Enter a parse tree produced by {@link GraphConfigJava9Parser#wildcard}.
	 * @param ctx the parse tree
	 */
	void enterWildcard(GraphConfigJava9Parser.WildcardContext ctx);
	/**
	 * Exit a parse tree produced by {@link GraphConfigJava9Parser#wildcard}.
	 * @param ctx the parse tree
	 */
	void exitWildcard(GraphConfigJava9Parser.WildcardContext ctx);
	/**
	 * Enter a parse tree produced by {@link GraphConfigJava9Parser#wildcardBounds}.
	 * @param ctx the parse tree
	 */
	void enterWildcardBounds(GraphConfigJava9Parser.WildcardBoundsContext ctx);
	/**
	 * Exit a parse tree produced by {@link GraphConfigJava9Parser#wildcardBounds}.
	 * @param ctx the parse tree
	 */
	void exitWildcardBounds(GraphConfigJava9Parser.WildcardBoundsContext ctx);
	/**
	 * Enter a parse tree produced by {@link GraphConfigJava9Parser#moduleName}.
	 * @param ctx the parse tree
	 */
	void enterModuleName(GraphConfigJava9Parser.ModuleNameContext ctx);
	/**
	 * Exit a parse tree produced by {@link GraphConfigJava9Parser#moduleName}.
	 * @param ctx the parse tree
	 */
	void exitModuleName(GraphConfigJava9Parser.ModuleNameContext ctx);
	/**
	 * Enter a parse tree produced by {@link GraphConfigJava9Parser#packageName}.
	 * @param ctx the parse tree
	 */
	void enterPackageName(GraphConfigJava9Parser.PackageNameContext ctx);
	/**
	 * Exit a parse tree produced by {@link GraphConfigJava9Parser#packageName}.
	 * @param ctx the parse tree
	 */
	void exitPackageName(GraphConfigJava9Parser.PackageNameContext ctx);
	/**
	 * Enter a parse tree produced by {@link GraphConfigJava9Parser#typeName}.
	 * @param ctx the parse tree
	 */
	void enterTypeName(GraphConfigJava9Parser.TypeNameContext ctx);
	/**
	 * Exit a parse tree produced by {@link GraphConfigJava9Parser#typeName}.
	 * @param ctx the parse tree
	 */
	void exitTypeName(GraphConfigJava9Parser.TypeNameContext ctx);
	/**
	 * Enter a parse tree produced by {@link GraphConfigJava9Parser#packageOrTypeName}.
	 * @param ctx the parse tree
	 */
	void enterPackageOrTypeName(GraphConfigJava9Parser.PackageOrTypeNameContext ctx);
	/**
	 * Exit a parse tree produced by {@link GraphConfigJava9Parser#packageOrTypeName}.
	 * @param ctx the parse tree
	 */
	void exitPackageOrTypeName(GraphConfigJava9Parser.PackageOrTypeNameContext ctx);
	/**
	 * Enter a parse tree produced by {@link GraphConfigJava9Parser#expressionName}.
	 * @param ctx the parse tree
	 */
	void enterExpressionName(GraphConfigJava9Parser.ExpressionNameContext ctx);
	/**
	 * Exit a parse tree produced by {@link GraphConfigJava9Parser#expressionName}.
	 * @param ctx the parse tree
	 */
	void exitExpressionName(GraphConfigJava9Parser.ExpressionNameContext ctx);
	/**
	 * Enter a parse tree produced by {@link GraphConfigJava9Parser#methodName}.
	 * @param ctx the parse tree
	 */
	void enterMethodName(GraphConfigJava9Parser.MethodNameContext ctx);
	/**
	 * Exit a parse tree produced by {@link GraphConfigJava9Parser#methodName}.
	 * @param ctx the parse tree
	 */
	void exitMethodName(GraphConfigJava9Parser.MethodNameContext ctx);
	/**
	 * Enter a parse tree produced by {@link GraphConfigJava9Parser#ambiguousName}.
	 * @param ctx the parse tree
	 */
	void enterAmbiguousName(GraphConfigJava9Parser.AmbiguousNameContext ctx);
	/**
	 * Exit a parse tree produced by {@link GraphConfigJava9Parser#ambiguousName}.
	 * @param ctx the parse tree
	 */
	void exitAmbiguousName(GraphConfigJava9Parser.AmbiguousNameContext ctx);
	/**
	 * Enter a parse tree produced by {@link GraphConfigJava9Parser#compilationUnit}.
	 * @param ctx the parse tree
	 */
	void enterCompilationUnit(GraphConfigJava9Parser.CompilationUnitContext ctx);
	/**
	 * Exit a parse tree produced by {@link GraphConfigJava9Parser#compilationUnit}.
	 * @param ctx the parse tree
	 */
	void exitCompilationUnit(GraphConfigJava9Parser.CompilationUnitContext ctx);
	/**
	 * Enter a parse tree produced by {@link GraphConfigJava9Parser#ordinaryCompilation}.
	 * @param ctx the parse tree
	 */
	void enterOrdinaryCompilation(GraphConfigJava9Parser.OrdinaryCompilationContext ctx);
	/**
	 * Exit a parse tree produced by {@link GraphConfigJava9Parser#ordinaryCompilation}.
	 * @param ctx the parse tree
	 */
	void exitOrdinaryCompilation(GraphConfigJava9Parser.OrdinaryCompilationContext ctx);
	/**
	 * Enter a parse tree produced by {@link GraphConfigJava9Parser#modularCompilation}.
	 * @param ctx the parse tree
	 */
	void enterModularCompilation(GraphConfigJava9Parser.ModularCompilationContext ctx);
	/**
	 * Exit a parse tree produced by {@link GraphConfigJava9Parser#modularCompilation}.
	 * @param ctx the parse tree
	 */
	void exitModularCompilation(GraphConfigJava9Parser.ModularCompilationContext ctx);
	/**
	 * Enter a parse tree produced by {@link GraphConfigJava9Parser#packageDeclaration}.
	 * @param ctx the parse tree
	 */
	void enterPackageDeclaration(GraphConfigJava9Parser.PackageDeclarationContext ctx);
	/**
	 * Exit a parse tree produced by {@link GraphConfigJava9Parser#packageDeclaration}.
	 * @param ctx the parse tree
	 */
	void exitPackageDeclaration(GraphConfigJava9Parser.PackageDeclarationContext ctx);
	/**
	 * Enter a parse tree produced by {@link GraphConfigJava9Parser#packageModifier}.
	 * @param ctx the parse tree
	 */
	void enterPackageModifier(GraphConfigJava9Parser.PackageModifierContext ctx);
	/**
	 * Exit a parse tree produced by {@link GraphConfigJava9Parser#packageModifier}.
	 * @param ctx the parse tree
	 */
	void exitPackageModifier(GraphConfigJava9Parser.PackageModifierContext ctx);
	/**
	 * Enter a parse tree produced by {@link GraphConfigJava9Parser#importDeclaration}.
	 * @param ctx the parse tree
	 */
	void enterImportDeclaration(GraphConfigJava9Parser.ImportDeclarationContext ctx);
	/**
	 * Exit a parse tree produced by {@link GraphConfigJava9Parser#importDeclaration}.
	 * @param ctx the parse tree
	 */
	void exitImportDeclaration(GraphConfigJava9Parser.ImportDeclarationContext ctx);
	/**
	 * Enter a parse tree produced by {@link GraphConfigJava9Parser#singleTypeImportDeclaration}.
	 * @param ctx the parse tree
	 */
	void enterSingleTypeImportDeclaration(GraphConfigJava9Parser.SingleTypeImportDeclarationContext ctx);
	/**
	 * Exit a parse tree produced by {@link GraphConfigJava9Parser#singleTypeImportDeclaration}.
	 * @param ctx the parse tree
	 */
	void exitSingleTypeImportDeclaration(GraphConfigJava9Parser.SingleTypeImportDeclarationContext ctx);
	/**
	 * Enter a parse tree produced by {@link GraphConfigJava9Parser#typeImportOnDemandDeclaration}.
	 * @param ctx the parse tree
	 */
	void enterTypeImportOnDemandDeclaration(GraphConfigJava9Parser.TypeImportOnDemandDeclarationContext ctx);
	/**
	 * Exit a parse tree produced by {@link GraphConfigJava9Parser#typeImportOnDemandDeclaration}.
	 * @param ctx the parse tree
	 */
	void exitTypeImportOnDemandDeclaration(GraphConfigJava9Parser.TypeImportOnDemandDeclarationContext ctx);
	/**
	 * Enter a parse tree produced by {@link GraphConfigJava9Parser#singleStaticImportDeclaration}.
	 * @param ctx the parse tree
	 */
	void enterSingleStaticImportDeclaration(GraphConfigJava9Parser.SingleStaticImportDeclarationContext ctx);
	/**
	 * Exit a parse tree produced by {@link GraphConfigJava9Parser#singleStaticImportDeclaration}.
	 * @param ctx the parse tree
	 */
	void exitSingleStaticImportDeclaration(GraphConfigJava9Parser.SingleStaticImportDeclarationContext ctx);
	/**
	 * Enter a parse tree produced by {@link GraphConfigJava9Parser#staticImportOnDemandDeclaration}.
	 * @param ctx the parse tree
	 */
	void enterStaticImportOnDemandDeclaration(GraphConfigJava9Parser.StaticImportOnDemandDeclarationContext ctx);
	/**
	 * Exit a parse tree produced by {@link GraphConfigJava9Parser#staticImportOnDemandDeclaration}.
	 * @param ctx the parse tree
	 */
	void exitStaticImportOnDemandDeclaration(GraphConfigJava9Parser.StaticImportOnDemandDeclarationContext ctx);
	/**
	 * Enter a parse tree produced by {@link GraphConfigJava9Parser#typeDeclaration}.
	 * @param ctx the parse tree
	 */
	void enterTypeDeclaration(GraphConfigJava9Parser.TypeDeclarationContext ctx);
	/**
	 * Exit a parse tree produced by {@link GraphConfigJava9Parser#typeDeclaration}.
	 * @param ctx the parse tree
	 */
	void exitTypeDeclaration(GraphConfigJava9Parser.TypeDeclarationContext ctx);
	/**
	 * Enter a parse tree produced by {@link GraphConfigJava9Parser#moduleDeclaration}.
	 * @param ctx the parse tree
	 */
	void enterModuleDeclaration(GraphConfigJava9Parser.ModuleDeclarationContext ctx);
	/**
	 * Exit a parse tree produced by {@link GraphConfigJava9Parser#moduleDeclaration}.
	 * @param ctx the parse tree
	 */
	void exitModuleDeclaration(GraphConfigJava9Parser.ModuleDeclarationContext ctx);
	/**
	 * Enter a parse tree produced by {@link GraphConfigJava9Parser#moduleDirective}.
	 * @param ctx the parse tree
	 */
	void enterModuleDirective(GraphConfigJava9Parser.ModuleDirectiveContext ctx);
	/**
	 * Exit a parse tree produced by {@link GraphConfigJava9Parser#moduleDirective}.
	 * @param ctx the parse tree
	 */
	void exitModuleDirective(GraphConfigJava9Parser.ModuleDirectiveContext ctx);
	/**
	 * Enter a parse tree produced by {@link GraphConfigJava9Parser#requiresModifier}.
	 * @param ctx the parse tree
	 */
	void enterRequiresModifier(GraphConfigJava9Parser.RequiresModifierContext ctx);
	/**
	 * Exit a parse tree produced by {@link GraphConfigJava9Parser#requiresModifier}.
	 * @param ctx the parse tree
	 */
	void exitRequiresModifier(GraphConfigJava9Parser.RequiresModifierContext ctx);
	/**
	 * Enter a parse tree produced by {@link GraphConfigJava9Parser#classDeclaration}.
	 * @param ctx the parse tree
	 */
	void enterClassDeclaration(GraphConfigJava9Parser.ClassDeclarationContext ctx);
	/**
	 * Exit a parse tree produced by {@link GraphConfigJava9Parser#classDeclaration}.
	 * @param ctx the parse tree
	 */
	void exitClassDeclaration(GraphConfigJava9Parser.ClassDeclarationContext ctx);
	/**
	 * Enter a parse tree produced by {@link GraphConfigJava9Parser#normalClassDeclaration}.
	 * @param ctx the parse tree
	 */
	void enterNormalClassDeclaration(GraphConfigJava9Parser.NormalClassDeclarationContext ctx);
	/**
	 * Exit a parse tree produced by {@link GraphConfigJava9Parser#normalClassDeclaration}.
	 * @param ctx the parse tree
	 */
	void exitNormalClassDeclaration(GraphConfigJava9Parser.NormalClassDeclarationContext ctx);
	/**
	 * Enter a parse tree produced by {@link GraphConfigJava9Parser#classModifier}.
	 * @param ctx the parse tree
	 */
	void enterClassModifier(GraphConfigJava9Parser.ClassModifierContext ctx);
	/**
	 * Exit a parse tree produced by {@link GraphConfigJava9Parser#classModifier}.
	 * @param ctx the parse tree
	 */
	void exitClassModifier(GraphConfigJava9Parser.ClassModifierContext ctx);
	/**
	 * Enter a parse tree produced by {@link GraphConfigJava9Parser#typeParameters}.
	 * @param ctx the parse tree
	 */
	void enterTypeParameters(GraphConfigJava9Parser.TypeParametersContext ctx);
	/**
	 * Exit a parse tree produced by {@link GraphConfigJava9Parser#typeParameters}.
	 * @param ctx the parse tree
	 */
	void exitTypeParameters(GraphConfigJava9Parser.TypeParametersContext ctx);
	/**
	 * Enter a parse tree produced by {@link GraphConfigJava9Parser#typeParameterList}.
	 * @param ctx the parse tree
	 */
	void enterTypeParameterList(GraphConfigJava9Parser.TypeParameterListContext ctx);
	/**
	 * Exit a parse tree produced by {@link GraphConfigJava9Parser#typeParameterList}.
	 * @param ctx the parse tree
	 */
	void exitTypeParameterList(GraphConfigJava9Parser.TypeParameterListContext ctx);
	/**
	 * Enter a parse tree produced by {@link GraphConfigJava9Parser#superclass}.
	 * @param ctx the parse tree
	 */
	void enterSuperclass(GraphConfigJava9Parser.SuperclassContext ctx);
	/**
	 * Exit a parse tree produced by {@link GraphConfigJava9Parser#superclass}.
	 * @param ctx the parse tree
	 */
	void exitSuperclass(GraphConfigJava9Parser.SuperclassContext ctx);
	/**
	 * Enter a parse tree produced by {@link GraphConfigJava9Parser#superinterfaces}.
	 * @param ctx the parse tree
	 */
	void enterSuperinterfaces(GraphConfigJava9Parser.SuperinterfacesContext ctx);
	/**
	 * Exit a parse tree produced by {@link GraphConfigJava9Parser#superinterfaces}.
	 * @param ctx the parse tree
	 */
	void exitSuperinterfaces(GraphConfigJava9Parser.SuperinterfacesContext ctx);
	/**
	 * Enter a parse tree produced by {@link GraphConfigJava9Parser#interfaceTypeList}.
	 * @param ctx the parse tree
	 */
	void enterInterfaceTypeList(GraphConfigJava9Parser.InterfaceTypeListContext ctx);
	/**
	 * Exit a parse tree produced by {@link GraphConfigJava9Parser#interfaceTypeList}.
	 * @param ctx the parse tree
	 */
	void exitInterfaceTypeList(GraphConfigJava9Parser.InterfaceTypeListContext ctx);
	/**
	 * Enter a parse tree produced by {@link GraphConfigJava9Parser#classBody}.
	 * @param ctx the parse tree
	 */
	void enterClassBody(GraphConfigJava9Parser.ClassBodyContext ctx);
	/**
	 * Exit a parse tree produced by {@link GraphConfigJava9Parser#classBody}.
	 * @param ctx the parse tree
	 */
	void exitClassBody(GraphConfigJava9Parser.ClassBodyContext ctx);
	/**
	 * Enter a parse tree produced by {@link GraphConfigJava9Parser#classBodyDeclaration}.
	 * @param ctx the parse tree
	 */
	void enterClassBodyDeclaration(GraphConfigJava9Parser.ClassBodyDeclarationContext ctx);
	/**
	 * Exit a parse tree produced by {@link GraphConfigJava9Parser#classBodyDeclaration}.
	 * @param ctx the parse tree
	 */
	void exitClassBodyDeclaration(GraphConfigJava9Parser.ClassBodyDeclarationContext ctx);
	/**
	 * Enter a parse tree produced by {@link GraphConfigJava9Parser#classMemberDeclaration}.
	 * @param ctx the parse tree
	 */
	void enterClassMemberDeclaration(GraphConfigJava9Parser.ClassMemberDeclarationContext ctx);
	/**
	 * Exit a parse tree produced by {@link GraphConfigJava9Parser#classMemberDeclaration}.
	 * @param ctx the parse tree
	 */
	void exitClassMemberDeclaration(GraphConfigJava9Parser.ClassMemberDeclarationContext ctx);
	/**
	 * Enter a parse tree produced by {@link GraphConfigJava9Parser#fieldDeclaration}.
	 * @param ctx the parse tree
	 */
	void enterFieldDeclaration(GraphConfigJava9Parser.FieldDeclarationContext ctx);
	/**
	 * Exit a parse tree produced by {@link GraphConfigJava9Parser#fieldDeclaration}.
	 * @param ctx the parse tree
	 */
	void exitFieldDeclaration(GraphConfigJava9Parser.FieldDeclarationContext ctx);
	/**
	 * Enter a parse tree produced by {@link GraphConfigJava9Parser#fieldModifier}.
	 * @param ctx the parse tree
	 */
	void enterFieldModifier(GraphConfigJava9Parser.FieldModifierContext ctx);
	/**
	 * Exit a parse tree produced by {@link GraphConfigJava9Parser#fieldModifier}.
	 * @param ctx the parse tree
	 */
	void exitFieldModifier(GraphConfigJava9Parser.FieldModifierContext ctx);
	/**
	 * Enter a parse tree produced by {@link GraphConfigJava9Parser#variableDeclaratorList}.
	 * @param ctx the parse tree
	 */
	void enterVariableDeclaratorList(GraphConfigJava9Parser.VariableDeclaratorListContext ctx);
	/**
	 * Exit a parse tree produced by {@link GraphConfigJava9Parser#variableDeclaratorList}.
	 * @param ctx the parse tree
	 */
	void exitVariableDeclaratorList(GraphConfigJava9Parser.VariableDeclaratorListContext ctx);
	/**
	 * Enter a parse tree produced by {@link GraphConfigJava9Parser#variableDeclarator}.
	 * @param ctx the parse tree
	 */
	void enterVariableDeclarator(GraphConfigJava9Parser.VariableDeclaratorContext ctx);
	/**
	 * Exit a parse tree produced by {@link GraphConfigJava9Parser#variableDeclarator}.
	 * @param ctx the parse tree
	 */
	void exitVariableDeclarator(GraphConfigJava9Parser.VariableDeclaratorContext ctx);
	/**
	 * Enter a parse tree produced by {@link GraphConfigJava9Parser#variableDeclaratorId}.
	 * @param ctx the parse tree
	 */
	void enterVariableDeclaratorId(GraphConfigJava9Parser.VariableDeclaratorIdContext ctx);
	/**
	 * Exit a parse tree produced by {@link GraphConfigJava9Parser#variableDeclaratorId}.
	 * @param ctx the parse tree
	 */
	void exitVariableDeclaratorId(GraphConfigJava9Parser.VariableDeclaratorIdContext ctx);
	/**
	 * Enter a parse tree produced by {@link GraphConfigJava9Parser#variableInitializer}.
	 * @param ctx the parse tree
	 */
	void enterVariableInitializer(GraphConfigJava9Parser.VariableInitializerContext ctx);
	/**
	 * Exit a parse tree produced by {@link GraphConfigJava9Parser#variableInitializer}.
	 * @param ctx the parse tree
	 */
	void exitVariableInitializer(GraphConfigJava9Parser.VariableInitializerContext ctx);
	/**
	 * Enter a parse tree produced by {@link GraphConfigJava9Parser#unannType}.
	 * @param ctx the parse tree
	 */
	void enterUnannType(GraphConfigJava9Parser.UnannTypeContext ctx);
	/**
	 * Exit a parse tree produced by {@link GraphConfigJava9Parser#unannType}.
	 * @param ctx the parse tree
	 */
	void exitUnannType(GraphConfigJava9Parser.UnannTypeContext ctx);
	/**
	 * Enter a parse tree produced by {@link GraphConfigJava9Parser#unannPrimitiveType}.
	 * @param ctx the parse tree
	 */
	void enterUnannPrimitiveType(GraphConfigJava9Parser.UnannPrimitiveTypeContext ctx);
	/**
	 * Exit a parse tree produced by {@link GraphConfigJava9Parser#unannPrimitiveType}.
	 * @param ctx the parse tree
	 */
	void exitUnannPrimitiveType(GraphConfigJava9Parser.UnannPrimitiveTypeContext ctx);
	/**
	 * Enter a parse tree produced by {@link GraphConfigJava9Parser#unannReferenceType}.
	 * @param ctx the parse tree
	 */
	void enterUnannReferenceType(GraphConfigJava9Parser.UnannReferenceTypeContext ctx);
	/**
	 * Exit a parse tree produced by {@link GraphConfigJava9Parser#unannReferenceType}.
	 * @param ctx the parse tree
	 */
	void exitUnannReferenceType(GraphConfigJava9Parser.UnannReferenceTypeContext ctx);
	/**
	 * Enter a parse tree produced by {@link GraphConfigJava9Parser#unannClassOrInterfaceType}.
	 * @param ctx the parse tree
	 */
	void enterUnannClassOrInterfaceType(GraphConfigJava9Parser.UnannClassOrInterfaceTypeContext ctx);
	/**
	 * Exit a parse tree produced by {@link GraphConfigJava9Parser#unannClassOrInterfaceType}.
	 * @param ctx the parse tree
	 */
	void exitUnannClassOrInterfaceType(GraphConfigJava9Parser.UnannClassOrInterfaceTypeContext ctx);
	/**
	 * Enter a parse tree produced by {@link GraphConfigJava9Parser#unannClassType}.
	 * @param ctx the parse tree
	 */
	void enterUnannClassType(GraphConfigJava9Parser.UnannClassTypeContext ctx);
	/**
	 * Exit a parse tree produced by {@link GraphConfigJava9Parser#unannClassType}.
	 * @param ctx the parse tree
	 */
	void exitUnannClassType(GraphConfigJava9Parser.UnannClassTypeContext ctx);
	/**
	 * Enter a parse tree produced by {@link GraphConfigJava9Parser#unannClassType_lf_unannClassOrInterfaceType}.
	 * @param ctx the parse tree
	 */
	void enterUnannClassType_lf_unannClassOrInterfaceType(GraphConfigJava9Parser.UnannClassType_lf_unannClassOrInterfaceTypeContext ctx);
	/**
	 * Exit a parse tree produced by {@link GraphConfigJava9Parser#unannClassType_lf_unannClassOrInterfaceType}.
	 * @param ctx the parse tree
	 */
	void exitUnannClassType_lf_unannClassOrInterfaceType(GraphConfigJava9Parser.UnannClassType_lf_unannClassOrInterfaceTypeContext ctx);
	/**
	 * Enter a parse tree produced by {@link GraphConfigJava9Parser#unannClassType_lfno_unannClassOrInterfaceType}.
	 * @param ctx the parse tree
	 */
	void enterUnannClassType_lfno_unannClassOrInterfaceType(GraphConfigJava9Parser.UnannClassType_lfno_unannClassOrInterfaceTypeContext ctx);
	/**
	 * Exit a parse tree produced by {@link GraphConfigJava9Parser#unannClassType_lfno_unannClassOrInterfaceType}.
	 * @param ctx the parse tree
	 */
	void exitUnannClassType_lfno_unannClassOrInterfaceType(GraphConfigJava9Parser.UnannClassType_lfno_unannClassOrInterfaceTypeContext ctx);
	/**
	 * Enter a parse tree produced by {@link GraphConfigJava9Parser#unannInterfaceType}.
	 * @param ctx the parse tree
	 */
	void enterUnannInterfaceType(GraphConfigJava9Parser.UnannInterfaceTypeContext ctx);
	/**
	 * Exit a parse tree produced by {@link GraphConfigJava9Parser#unannInterfaceType}.
	 * @param ctx the parse tree
	 */
	void exitUnannInterfaceType(GraphConfigJava9Parser.UnannInterfaceTypeContext ctx);
	/**
	 * Enter a parse tree produced by {@link GraphConfigJava9Parser#unannInterfaceType_lf_unannClassOrInterfaceType}.
	 * @param ctx the parse tree
	 */
	void enterUnannInterfaceType_lf_unannClassOrInterfaceType(GraphConfigJava9Parser.UnannInterfaceType_lf_unannClassOrInterfaceTypeContext ctx);
	/**
	 * Exit a parse tree produced by {@link GraphConfigJava9Parser#unannInterfaceType_lf_unannClassOrInterfaceType}.
	 * @param ctx the parse tree
	 */
	void exitUnannInterfaceType_lf_unannClassOrInterfaceType(GraphConfigJava9Parser.UnannInterfaceType_lf_unannClassOrInterfaceTypeContext ctx);
	/**
	 * Enter a parse tree produced by {@link GraphConfigJava9Parser#unannInterfaceType_lfno_unannClassOrInterfaceType}.
	 * @param ctx the parse tree
	 */
	void enterUnannInterfaceType_lfno_unannClassOrInterfaceType(GraphConfigJava9Parser.UnannInterfaceType_lfno_unannClassOrInterfaceTypeContext ctx);
	/**
	 * Exit a parse tree produced by {@link GraphConfigJava9Parser#unannInterfaceType_lfno_unannClassOrInterfaceType}.
	 * @param ctx the parse tree
	 */
	void exitUnannInterfaceType_lfno_unannClassOrInterfaceType(GraphConfigJava9Parser.UnannInterfaceType_lfno_unannClassOrInterfaceTypeContext ctx);
	/**
	 * Enter a parse tree produced by {@link GraphConfigJava9Parser#unannTypeVariable}.
	 * @param ctx the parse tree
	 */
	void enterUnannTypeVariable(GraphConfigJava9Parser.UnannTypeVariableContext ctx);
	/**
	 * Exit a parse tree produced by {@link GraphConfigJava9Parser#unannTypeVariable}.
	 * @param ctx the parse tree
	 */
	void exitUnannTypeVariable(GraphConfigJava9Parser.UnannTypeVariableContext ctx);
	/**
	 * Enter a parse tree produced by {@link GraphConfigJava9Parser#unannArrayType}.
	 * @param ctx the parse tree
	 */
	void enterUnannArrayType(GraphConfigJava9Parser.UnannArrayTypeContext ctx);
	/**
	 * Exit a parse tree produced by {@link GraphConfigJava9Parser#unannArrayType}.
	 * @param ctx the parse tree
	 */
	void exitUnannArrayType(GraphConfigJava9Parser.UnannArrayTypeContext ctx);
	/**
	 * Enter a parse tree produced by {@link GraphConfigJava9Parser#methodDeclaration}.
	 * @param ctx the parse tree
	 */
	void enterMethodDeclaration(GraphConfigJava9Parser.MethodDeclarationContext ctx);
	/**
	 * Exit a parse tree produced by {@link GraphConfigJava9Parser#methodDeclaration}.
	 * @param ctx the parse tree
	 */
	void exitMethodDeclaration(GraphConfigJava9Parser.MethodDeclarationContext ctx);
	/**
	 * Enter a parse tree produced by {@link GraphConfigJava9Parser#methodModifier}.
	 * @param ctx the parse tree
	 */
	void enterMethodModifier(GraphConfigJava9Parser.MethodModifierContext ctx);
	/**
	 * Exit a parse tree produced by {@link GraphConfigJava9Parser#methodModifier}.
	 * @param ctx the parse tree
	 */
	void exitMethodModifier(GraphConfigJava9Parser.MethodModifierContext ctx);
	/**
	 * Enter a parse tree produced by {@link GraphConfigJava9Parser#methodHeader}.
	 * @param ctx the parse tree
	 */
	void enterMethodHeader(GraphConfigJava9Parser.MethodHeaderContext ctx);
	/**
	 * Exit a parse tree produced by {@link GraphConfigJava9Parser#methodHeader}.
	 * @param ctx the parse tree
	 */
	void exitMethodHeader(GraphConfigJava9Parser.MethodHeaderContext ctx);
	/**
	 * Enter a parse tree produced by {@link GraphConfigJava9Parser#result}.
	 * @param ctx the parse tree
	 */
	void enterResult(GraphConfigJava9Parser.ResultContext ctx);
	/**
	 * Exit a parse tree produced by {@link GraphConfigJava9Parser#result}.
	 * @param ctx the parse tree
	 */
	void exitResult(GraphConfigJava9Parser.ResultContext ctx);
	/**
	 * Enter a parse tree produced by {@link GraphConfigJava9Parser#methodDeclarator}.
	 * @param ctx the parse tree
	 */
	void enterMethodDeclarator(GraphConfigJava9Parser.MethodDeclaratorContext ctx);
	/**
	 * Exit a parse tree produced by {@link GraphConfigJava9Parser#methodDeclarator}.
	 * @param ctx the parse tree
	 */
	void exitMethodDeclarator(GraphConfigJava9Parser.MethodDeclaratorContext ctx);
	/**
	 * Enter a parse tree produced by {@link GraphConfigJava9Parser#formalParameterList}.
	 * @param ctx the parse tree
	 */
	void enterFormalParameterList(GraphConfigJava9Parser.FormalParameterListContext ctx);
	/**
	 * Exit a parse tree produced by {@link GraphConfigJava9Parser#formalParameterList}.
	 * @param ctx the parse tree
	 */
	void exitFormalParameterList(GraphConfigJava9Parser.FormalParameterListContext ctx);
	/**
	 * Enter a parse tree produced by {@link GraphConfigJava9Parser#formalParameters}.
	 * @param ctx the parse tree
	 */
	void enterFormalParameters(GraphConfigJava9Parser.FormalParametersContext ctx);
	/**
	 * Exit a parse tree produced by {@link GraphConfigJava9Parser#formalParameters}.
	 * @param ctx the parse tree
	 */
	void exitFormalParameters(GraphConfigJava9Parser.FormalParametersContext ctx);
	/**
	 * Enter a parse tree produced by {@link GraphConfigJava9Parser#formalParameter}.
	 * @param ctx the parse tree
	 */
	void enterFormalParameter(GraphConfigJava9Parser.FormalParameterContext ctx);
	/**
	 * Exit a parse tree produced by {@link GraphConfigJava9Parser#formalParameter}.
	 * @param ctx the parse tree
	 */
	void exitFormalParameter(GraphConfigJava9Parser.FormalParameterContext ctx);
	/**
	 * Enter a parse tree produced by {@link GraphConfigJava9Parser#variableModifier}.
	 * @param ctx the parse tree
	 */
	void enterVariableModifier(GraphConfigJava9Parser.VariableModifierContext ctx);
	/**
	 * Exit a parse tree produced by {@link GraphConfigJava9Parser#variableModifier}.
	 * @param ctx the parse tree
	 */
	void exitVariableModifier(GraphConfigJava9Parser.VariableModifierContext ctx);
	/**
	 * Enter a parse tree produced by {@link GraphConfigJava9Parser#lastFormalParameter}.
	 * @param ctx the parse tree
	 */
	void enterLastFormalParameter(GraphConfigJava9Parser.LastFormalParameterContext ctx);
	/**
	 * Exit a parse tree produced by {@link GraphConfigJava9Parser#lastFormalParameter}.
	 * @param ctx the parse tree
	 */
	void exitLastFormalParameter(GraphConfigJava9Parser.LastFormalParameterContext ctx);
	/**
	 * Enter a parse tree produced by {@link GraphConfigJava9Parser#receiverParameter}.
	 * @param ctx the parse tree
	 */
	void enterReceiverParameter(GraphConfigJava9Parser.ReceiverParameterContext ctx);
	/**
	 * Exit a parse tree produced by {@link GraphConfigJava9Parser#receiverParameter}.
	 * @param ctx the parse tree
	 */
	void exitReceiverParameter(GraphConfigJava9Parser.ReceiverParameterContext ctx);
	/**
	 * Enter a parse tree produced by {@link GraphConfigJava9Parser#throws_}.
	 * @param ctx the parse tree
	 */
	void enterThrows_(GraphConfigJava9Parser.Throws_Context ctx);
	/**
	 * Exit a parse tree produced by {@link GraphConfigJava9Parser#throws_}.
	 * @param ctx the parse tree
	 */
	void exitThrows_(GraphConfigJava9Parser.Throws_Context ctx);
	/**
	 * Enter a parse tree produced by {@link GraphConfigJava9Parser#exceptionTypeList}.
	 * @param ctx the parse tree
	 */
	void enterExceptionTypeList(GraphConfigJava9Parser.ExceptionTypeListContext ctx);
	/**
	 * Exit a parse tree produced by {@link GraphConfigJava9Parser#exceptionTypeList}.
	 * @param ctx the parse tree
	 */
	void exitExceptionTypeList(GraphConfigJava9Parser.ExceptionTypeListContext ctx);
	/**
	 * Enter a parse tree produced by {@link GraphConfigJava9Parser#exceptionType}.
	 * @param ctx the parse tree
	 */
	void enterExceptionType(GraphConfigJava9Parser.ExceptionTypeContext ctx);
	/**
	 * Exit a parse tree produced by {@link GraphConfigJava9Parser#exceptionType}.
	 * @param ctx the parse tree
	 */
	void exitExceptionType(GraphConfigJava9Parser.ExceptionTypeContext ctx);
	/**
	 * Enter a parse tree produced by {@link GraphConfigJava9Parser#methodBody}.
	 * @param ctx the parse tree
	 */
	void enterMethodBody(GraphConfigJava9Parser.MethodBodyContext ctx);
	/**
	 * Exit a parse tree produced by {@link GraphConfigJava9Parser#methodBody}.
	 * @param ctx the parse tree
	 */
	void exitMethodBody(GraphConfigJava9Parser.MethodBodyContext ctx);
	/**
	 * Enter a parse tree produced by {@link GraphConfigJava9Parser#instanceInitializer}.
	 * @param ctx the parse tree
	 */
	void enterInstanceInitializer(GraphConfigJava9Parser.InstanceInitializerContext ctx);
	/**
	 * Exit a parse tree produced by {@link GraphConfigJava9Parser#instanceInitializer}.
	 * @param ctx the parse tree
	 */
	void exitInstanceInitializer(GraphConfigJava9Parser.InstanceInitializerContext ctx);
	/**
	 * Enter a parse tree produced by {@link GraphConfigJava9Parser#staticInitializer}.
	 * @param ctx the parse tree
	 */
	void enterStaticInitializer(GraphConfigJava9Parser.StaticInitializerContext ctx);
	/**
	 * Exit a parse tree produced by {@link GraphConfigJava9Parser#staticInitializer}.
	 * @param ctx the parse tree
	 */
	void exitStaticInitializer(GraphConfigJava9Parser.StaticInitializerContext ctx);
	/**
	 * Enter a parse tree produced by {@link GraphConfigJava9Parser#constructorDeclaration}.
	 * @param ctx the parse tree
	 */
	void enterConstructorDeclaration(GraphConfigJava9Parser.ConstructorDeclarationContext ctx);
	/**
	 * Exit a parse tree produced by {@link GraphConfigJava9Parser#constructorDeclaration}.
	 * @param ctx the parse tree
	 */
	void exitConstructorDeclaration(GraphConfigJava9Parser.ConstructorDeclarationContext ctx);
	/**
	 * Enter a parse tree produced by {@link GraphConfigJava9Parser#constructorModifier}.
	 * @param ctx the parse tree
	 */
	void enterConstructorModifier(GraphConfigJava9Parser.ConstructorModifierContext ctx);
	/**
	 * Exit a parse tree produced by {@link GraphConfigJava9Parser#constructorModifier}.
	 * @param ctx the parse tree
	 */
	void exitConstructorModifier(GraphConfigJava9Parser.ConstructorModifierContext ctx);
	/**
	 * Enter a parse tree produced by {@link GraphConfigJava9Parser#constructorDeclarator}.
	 * @param ctx the parse tree
	 */
	void enterConstructorDeclarator(GraphConfigJava9Parser.ConstructorDeclaratorContext ctx);
	/**
	 * Exit a parse tree produced by {@link GraphConfigJava9Parser#constructorDeclarator}.
	 * @param ctx the parse tree
	 */
	void exitConstructorDeclarator(GraphConfigJava9Parser.ConstructorDeclaratorContext ctx);
	/**
	 * Enter a parse tree produced by {@link GraphConfigJava9Parser#simpleTypeName}.
	 * @param ctx the parse tree
	 */
	void enterSimpleTypeName(GraphConfigJava9Parser.SimpleTypeNameContext ctx);
	/**
	 * Exit a parse tree produced by {@link GraphConfigJava9Parser#simpleTypeName}.
	 * @param ctx the parse tree
	 */
	void exitSimpleTypeName(GraphConfigJava9Parser.SimpleTypeNameContext ctx);
	/**
	 * Enter a parse tree produced by {@link GraphConfigJava9Parser#constructorBody}.
	 * @param ctx the parse tree
	 */
	void enterConstructorBody(GraphConfigJava9Parser.ConstructorBodyContext ctx);
	/**
	 * Exit a parse tree produced by {@link GraphConfigJava9Parser#constructorBody}.
	 * @param ctx the parse tree
	 */
	void exitConstructorBody(GraphConfigJava9Parser.ConstructorBodyContext ctx);
	/**
	 * Enter a parse tree produced by {@link GraphConfigJava9Parser#explicitConstructorInvocation}.
	 * @param ctx the parse tree
	 */
	void enterExplicitConstructorInvocation(GraphConfigJava9Parser.ExplicitConstructorInvocationContext ctx);
	/**
	 * Exit a parse tree produced by {@link GraphConfigJava9Parser#explicitConstructorInvocation}.
	 * @param ctx the parse tree
	 */
	void exitExplicitConstructorInvocation(GraphConfigJava9Parser.ExplicitConstructorInvocationContext ctx);
	/**
	 * Enter a parse tree produced by {@link GraphConfigJava9Parser#enumDeclaration}.
	 * @param ctx the parse tree
	 */
	void enterEnumDeclaration(GraphConfigJava9Parser.EnumDeclarationContext ctx);
	/**
	 * Exit a parse tree produced by {@link GraphConfigJava9Parser#enumDeclaration}.
	 * @param ctx the parse tree
	 */
	void exitEnumDeclaration(GraphConfigJava9Parser.EnumDeclarationContext ctx);
	/**
	 * Enter a parse tree produced by {@link GraphConfigJava9Parser#enumBody}.
	 * @param ctx the parse tree
	 */
	void enterEnumBody(GraphConfigJava9Parser.EnumBodyContext ctx);
	/**
	 * Exit a parse tree produced by {@link GraphConfigJava9Parser#enumBody}.
	 * @param ctx the parse tree
	 */
	void exitEnumBody(GraphConfigJava9Parser.EnumBodyContext ctx);
	/**
	 * Enter a parse tree produced by {@link GraphConfigJava9Parser#enumConstantList}.
	 * @param ctx the parse tree
	 */
	void enterEnumConstantList(GraphConfigJava9Parser.EnumConstantListContext ctx);
	/**
	 * Exit a parse tree produced by {@link GraphConfigJava9Parser#enumConstantList}.
	 * @param ctx the parse tree
	 */
	void exitEnumConstantList(GraphConfigJava9Parser.EnumConstantListContext ctx);
	/**
	 * Enter a parse tree produced by {@link GraphConfigJava9Parser#enumConstant}.
	 * @param ctx the parse tree
	 */
	void enterEnumConstant(GraphConfigJava9Parser.EnumConstantContext ctx);
	/**
	 * Exit a parse tree produced by {@link GraphConfigJava9Parser#enumConstant}.
	 * @param ctx the parse tree
	 */
	void exitEnumConstant(GraphConfigJava9Parser.EnumConstantContext ctx);
	/**
	 * Enter a parse tree produced by {@link GraphConfigJava9Parser#enumConstantModifier}.
	 * @param ctx the parse tree
	 */
	void enterEnumConstantModifier(GraphConfigJava9Parser.EnumConstantModifierContext ctx);
	/**
	 * Exit a parse tree produced by {@link GraphConfigJava9Parser#enumConstantModifier}.
	 * @param ctx the parse tree
	 */
	void exitEnumConstantModifier(GraphConfigJava9Parser.EnumConstantModifierContext ctx);
	/**
	 * Enter a parse tree produced by {@link GraphConfigJava9Parser#enumBodyDeclarations}.
	 * @param ctx the parse tree
	 */
	void enterEnumBodyDeclarations(GraphConfigJava9Parser.EnumBodyDeclarationsContext ctx);
	/**
	 * Exit a parse tree produced by {@link GraphConfigJava9Parser#enumBodyDeclarations}.
	 * @param ctx the parse tree
	 */
	void exitEnumBodyDeclarations(GraphConfigJava9Parser.EnumBodyDeclarationsContext ctx);
	/**
	 * Enter a parse tree produced by {@link GraphConfigJava9Parser#interfaceDeclaration}.
	 * @param ctx the parse tree
	 */
	void enterInterfaceDeclaration(GraphConfigJava9Parser.InterfaceDeclarationContext ctx);
	/**
	 * Exit a parse tree produced by {@link GraphConfigJava9Parser#interfaceDeclaration}.
	 * @param ctx the parse tree
	 */
	void exitInterfaceDeclaration(GraphConfigJava9Parser.InterfaceDeclarationContext ctx);
	/**
	 * Enter a parse tree produced by {@link GraphConfigJava9Parser#normalInterfaceDeclaration}.
	 * @param ctx the parse tree
	 */
	void enterNormalInterfaceDeclaration(GraphConfigJava9Parser.NormalInterfaceDeclarationContext ctx);
	/**
	 * Exit a parse tree produced by {@link GraphConfigJava9Parser#normalInterfaceDeclaration}.
	 * @param ctx the parse tree
	 */
	void exitNormalInterfaceDeclaration(GraphConfigJava9Parser.NormalInterfaceDeclarationContext ctx);
	/**
	 * Enter a parse tree produced by {@link GraphConfigJava9Parser#interfaceModifier}.
	 * @param ctx the parse tree
	 */
	void enterInterfaceModifier(GraphConfigJava9Parser.InterfaceModifierContext ctx);
	/**
	 * Exit a parse tree produced by {@link GraphConfigJava9Parser#interfaceModifier}.
	 * @param ctx the parse tree
	 */
	void exitInterfaceModifier(GraphConfigJava9Parser.InterfaceModifierContext ctx);
	/**
	 * Enter a parse tree produced by {@link GraphConfigJava9Parser#extendsInterfaces}.
	 * @param ctx the parse tree
	 */
	void enterExtendsInterfaces(GraphConfigJava9Parser.ExtendsInterfacesContext ctx);
	/**
	 * Exit a parse tree produced by {@link GraphConfigJava9Parser#extendsInterfaces}.
	 * @param ctx the parse tree
	 */
	void exitExtendsInterfaces(GraphConfigJava9Parser.ExtendsInterfacesContext ctx);
	/**
	 * Enter a parse tree produced by {@link GraphConfigJava9Parser#interfaceBody}.
	 * @param ctx the parse tree
	 */
	void enterInterfaceBody(GraphConfigJava9Parser.InterfaceBodyContext ctx);
	/**
	 * Exit a parse tree produced by {@link GraphConfigJava9Parser#interfaceBody}.
	 * @param ctx the parse tree
	 */
	void exitInterfaceBody(GraphConfigJava9Parser.InterfaceBodyContext ctx);
	/**
	 * Enter a parse tree produced by {@link GraphConfigJava9Parser#interfaceMemberDeclaration}.
	 * @param ctx the parse tree
	 */
	void enterInterfaceMemberDeclaration(GraphConfigJava9Parser.InterfaceMemberDeclarationContext ctx);
	/**
	 * Exit a parse tree produced by {@link GraphConfigJava9Parser#interfaceMemberDeclaration}.
	 * @param ctx the parse tree
	 */
	void exitInterfaceMemberDeclaration(GraphConfigJava9Parser.InterfaceMemberDeclarationContext ctx);
	/**
	 * Enter a parse tree produced by {@link GraphConfigJava9Parser#constantDeclaration}.
	 * @param ctx the parse tree
	 */
	void enterConstantDeclaration(GraphConfigJava9Parser.ConstantDeclarationContext ctx);
	/**
	 * Exit a parse tree produced by {@link GraphConfigJava9Parser#constantDeclaration}.
	 * @param ctx the parse tree
	 */
	void exitConstantDeclaration(GraphConfigJava9Parser.ConstantDeclarationContext ctx);
	/**
	 * Enter a parse tree produced by {@link GraphConfigJava9Parser#constantModifier}.
	 * @param ctx the parse tree
	 */
	void enterConstantModifier(GraphConfigJava9Parser.ConstantModifierContext ctx);
	/**
	 * Exit a parse tree produced by {@link GraphConfigJava9Parser#constantModifier}.
	 * @param ctx the parse tree
	 */
	void exitConstantModifier(GraphConfigJava9Parser.ConstantModifierContext ctx);
	/**
	 * Enter a parse tree produced by {@link GraphConfigJava9Parser#interfaceMethodDeclaration}.
	 * @param ctx the parse tree
	 */
	void enterInterfaceMethodDeclaration(GraphConfigJava9Parser.InterfaceMethodDeclarationContext ctx);
	/**
	 * Exit a parse tree produced by {@link GraphConfigJava9Parser#interfaceMethodDeclaration}.
	 * @param ctx the parse tree
	 */
	void exitInterfaceMethodDeclaration(GraphConfigJava9Parser.InterfaceMethodDeclarationContext ctx);
	/**
	 * Enter a parse tree produced by {@link GraphConfigJava9Parser#interfaceMethodModifier}.
	 * @param ctx the parse tree
	 */
	void enterInterfaceMethodModifier(GraphConfigJava9Parser.InterfaceMethodModifierContext ctx);
	/**
	 * Exit a parse tree produced by {@link GraphConfigJava9Parser#interfaceMethodModifier}.
	 * @param ctx the parse tree
	 */
	void exitInterfaceMethodModifier(GraphConfigJava9Parser.InterfaceMethodModifierContext ctx);
	/**
	 * Enter a parse tree produced by {@link GraphConfigJava9Parser#annotationTypeDeclaration}.
	 * @param ctx the parse tree
	 */
	void enterAnnotationTypeDeclaration(GraphConfigJava9Parser.AnnotationTypeDeclarationContext ctx);
	/**
	 * Exit a parse tree produced by {@link GraphConfigJava9Parser#annotationTypeDeclaration}.
	 * @param ctx the parse tree
	 */
	void exitAnnotationTypeDeclaration(GraphConfigJava9Parser.AnnotationTypeDeclarationContext ctx);
	/**
	 * Enter a parse tree produced by {@link GraphConfigJava9Parser#annotationTypeBody}.
	 * @param ctx the parse tree
	 */
	void enterAnnotationTypeBody(GraphConfigJava9Parser.AnnotationTypeBodyContext ctx);
	/**
	 * Exit a parse tree produced by {@link GraphConfigJava9Parser#annotationTypeBody}.
	 * @param ctx the parse tree
	 */
	void exitAnnotationTypeBody(GraphConfigJava9Parser.AnnotationTypeBodyContext ctx);
	/**
	 * Enter a parse tree produced by {@link GraphConfigJava9Parser#annotationTypeMemberDeclaration}.
	 * @param ctx the parse tree
	 */
	void enterAnnotationTypeMemberDeclaration(GraphConfigJava9Parser.AnnotationTypeMemberDeclarationContext ctx);
	/**
	 * Exit a parse tree produced by {@link GraphConfigJava9Parser#annotationTypeMemberDeclaration}.
	 * @param ctx the parse tree
	 */
	void exitAnnotationTypeMemberDeclaration(GraphConfigJava9Parser.AnnotationTypeMemberDeclarationContext ctx);
	/**
	 * Enter a parse tree produced by {@link GraphConfigJava9Parser#annotationTypeElementDeclaration}.
	 * @param ctx the parse tree
	 */
	void enterAnnotationTypeElementDeclaration(GraphConfigJava9Parser.AnnotationTypeElementDeclarationContext ctx);
	/**
	 * Exit a parse tree produced by {@link GraphConfigJava9Parser#annotationTypeElementDeclaration}.
	 * @param ctx the parse tree
	 */
	void exitAnnotationTypeElementDeclaration(GraphConfigJava9Parser.AnnotationTypeElementDeclarationContext ctx);
	/**
	 * Enter a parse tree produced by {@link GraphConfigJava9Parser#annotationTypeElementModifier}.
	 * @param ctx the parse tree
	 */
	void enterAnnotationTypeElementModifier(GraphConfigJava9Parser.AnnotationTypeElementModifierContext ctx);
	/**
	 * Exit a parse tree produced by {@link GraphConfigJava9Parser#annotationTypeElementModifier}.
	 * @param ctx the parse tree
	 */
	void exitAnnotationTypeElementModifier(GraphConfigJava9Parser.AnnotationTypeElementModifierContext ctx);
	/**
	 * Enter a parse tree produced by {@link GraphConfigJava9Parser#defaultValue}.
	 * @param ctx the parse tree
	 */
	void enterDefaultValue(GraphConfigJava9Parser.DefaultValueContext ctx);
	/**
	 * Exit a parse tree produced by {@link GraphConfigJava9Parser#defaultValue}.
	 * @param ctx the parse tree
	 */
	void exitDefaultValue(GraphConfigJava9Parser.DefaultValueContext ctx);
	/**
	 * Enter a parse tree produced by {@link GraphConfigJava9Parser#annotation}.
	 * @param ctx the parse tree
	 */
	void enterAnnotation(GraphConfigJava9Parser.AnnotationContext ctx);
	/**
	 * Exit a parse tree produced by {@link GraphConfigJava9Parser#annotation}.
	 * @param ctx the parse tree
	 */
	void exitAnnotation(GraphConfigJava9Parser.AnnotationContext ctx);
	/**
	 * Enter a parse tree produced by {@link GraphConfigJava9Parser#normalAnnotation}.
	 * @param ctx the parse tree
	 */
	void enterNormalAnnotation(GraphConfigJava9Parser.NormalAnnotationContext ctx);
	/**
	 * Exit a parse tree produced by {@link GraphConfigJava9Parser#normalAnnotation}.
	 * @param ctx the parse tree
	 */
	void exitNormalAnnotation(GraphConfigJava9Parser.NormalAnnotationContext ctx);
	/**
	 * Enter a parse tree produced by {@link GraphConfigJava9Parser#elementValuePairList}.
	 * @param ctx the parse tree
	 */
	void enterElementValuePairList(GraphConfigJava9Parser.ElementValuePairListContext ctx);
	/**
	 * Exit a parse tree produced by {@link GraphConfigJava9Parser#elementValuePairList}.
	 * @param ctx the parse tree
	 */
	void exitElementValuePairList(GraphConfigJava9Parser.ElementValuePairListContext ctx);
	/**
	 * Enter a parse tree produced by {@link GraphConfigJava9Parser#elementValuePair}.
	 * @param ctx the parse tree
	 */
	void enterElementValuePair(GraphConfigJava9Parser.ElementValuePairContext ctx);
	/**
	 * Exit a parse tree produced by {@link GraphConfigJava9Parser#elementValuePair}.
	 * @param ctx the parse tree
	 */
	void exitElementValuePair(GraphConfigJava9Parser.ElementValuePairContext ctx);
	/**
	 * Enter a parse tree produced by {@link GraphConfigJava9Parser#elementValue}.
	 * @param ctx the parse tree
	 */
	void enterElementValue(GraphConfigJava9Parser.ElementValueContext ctx);
	/**
	 * Exit a parse tree produced by {@link GraphConfigJava9Parser#elementValue}.
	 * @param ctx the parse tree
	 */
	void exitElementValue(GraphConfigJava9Parser.ElementValueContext ctx);
	/**
	 * Enter a parse tree produced by {@link GraphConfigJava9Parser#elementValueArrayInitializer}.
	 * @param ctx the parse tree
	 */
	void enterElementValueArrayInitializer(GraphConfigJava9Parser.ElementValueArrayInitializerContext ctx);
	/**
	 * Exit a parse tree produced by {@link GraphConfigJava9Parser#elementValueArrayInitializer}.
	 * @param ctx the parse tree
	 */
	void exitElementValueArrayInitializer(GraphConfigJava9Parser.ElementValueArrayInitializerContext ctx);
	/**
	 * Enter a parse tree produced by {@link GraphConfigJava9Parser#elementValueList}.
	 * @param ctx the parse tree
	 */
	void enterElementValueList(GraphConfigJava9Parser.ElementValueListContext ctx);
	/**
	 * Exit a parse tree produced by {@link GraphConfigJava9Parser#elementValueList}.
	 * @param ctx the parse tree
	 */
	void exitElementValueList(GraphConfigJava9Parser.ElementValueListContext ctx);
	/**
	 * Enter a parse tree produced by {@link GraphConfigJava9Parser#markerAnnotation}.
	 * @param ctx the parse tree
	 */
	void enterMarkerAnnotation(GraphConfigJava9Parser.MarkerAnnotationContext ctx);
	/**
	 * Exit a parse tree produced by {@link GraphConfigJava9Parser#markerAnnotation}.
	 * @param ctx the parse tree
	 */
	void exitMarkerAnnotation(GraphConfigJava9Parser.MarkerAnnotationContext ctx);
	/**
	 * Enter a parse tree produced by {@link GraphConfigJava9Parser#singleElementAnnotation}.
	 * @param ctx the parse tree
	 */
	void enterSingleElementAnnotation(GraphConfigJava9Parser.SingleElementAnnotationContext ctx);
	/**
	 * Exit a parse tree produced by {@link GraphConfigJava9Parser#singleElementAnnotation}.
	 * @param ctx the parse tree
	 */
	void exitSingleElementAnnotation(GraphConfigJava9Parser.SingleElementAnnotationContext ctx);
	/**
	 * Enter a parse tree produced by {@link GraphConfigJava9Parser#arrayInitializer}.
	 * @param ctx the parse tree
	 */
	void enterArrayInitializer(GraphConfigJava9Parser.ArrayInitializerContext ctx);
	/**
	 * Exit a parse tree produced by {@link GraphConfigJava9Parser#arrayInitializer}.
	 * @param ctx the parse tree
	 */
	void exitArrayInitializer(GraphConfigJava9Parser.ArrayInitializerContext ctx);
	/**
	 * Enter a parse tree produced by {@link GraphConfigJava9Parser#variableInitializerList}.
	 * @param ctx the parse tree
	 */
	void enterVariableInitializerList(GraphConfigJava9Parser.VariableInitializerListContext ctx);
	/**
	 * Exit a parse tree produced by {@link GraphConfigJava9Parser#variableInitializerList}.
	 * @param ctx the parse tree
	 */
	void exitVariableInitializerList(GraphConfigJava9Parser.VariableInitializerListContext ctx);
	/**
	 * Enter a parse tree produced by {@link GraphConfigJava9Parser#block}.
	 * @param ctx the parse tree
	 */
	void enterBlock(GraphConfigJava9Parser.BlockContext ctx);
	/**
	 * Exit a parse tree produced by {@link GraphConfigJava9Parser#block}.
	 * @param ctx the parse tree
	 */
	void exitBlock(GraphConfigJava9Parser.BlockContext ctx);
	/**
	 * Enter a parse tree produced by {@link GraphConfigJava9Parser#blockStatements}.
	 * @param ctx the parse tree
	 */
	void enterBlockStatements(GraphConfigJava9Parser.BlockStatementsContext ctx);
	/**
	 * Exit a parse tree produced by {@link GraphConfigJava9Parser#blockStatements}.
	 * @param ctx the parse tree
	 */
	void exitBlockStatements(GraphConfigJava9Parser.BlockStatementsContext ctx);
	/**
	 * Enter a parse tree produced by {@link GraphConfigJava9Parser#blockStatement}.
	 * @param ctx the parse tree
	 */
	void enterBlockStatement(GraphConfigJava9Parser.BlockStatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link GraphConfigJava9Parser#blockStatement}.
	 * @param ctx the parse tree
	 */
	void exitBlockStatement(GraphConfigJava9Parser.BlockStatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link GraphConfigJava9Parser#localVariableDeclarationStatement}.
	 * @param ctx the parse tree
	 */
	void enterLocalVariableDeclarationStatement(GraphConfigJava9Parser.LocalVariableDeclarationStatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link GraphConfigJava9Parser#localVariableDeclarationStatement}.
	 * @param ctx the parse tree
	 */
	void exitLocalVariableDeclarationStatement(GraphConfigJava9Parser.LocalVariableDeclarationStatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link GraphConfigJava9Parser#localVariableDeclaration}.
	 * @param ctx the parse tree
	 */
	void enterLocalVariableDeclaration(GraphConfigJava9Parser.LocalVariableDeclarationContext ctx);
	/**
	 * Exit a parse tree produced by {@link GraphConfigJava9Parser#localVariableDeclaration}.
	 * @param ctx the parse tree
	 */
	void exitLocalVariableDeclaration(GraphConfigJava9Parser.LocalVariableDeclarationContext ctx);
	/**
	 * Enter a parse tree produced by {@link GraphConfigJava9Parser#statement}.
	 * @param ctx the parse tree
	 */
	void enterStatement(GraphConfigJava9Parser.StatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link GraphConfigJava9Parser#statement}.
	 * @param ctx the parse tree
	 */
	void exitStatement(GraphConfigJava9Parser.StatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link GraphConfigJava9Parser#statementNoShortIf}.
	 * @param ctx the parse tree
	 */
	void enterStatementNoShortIf(GraphConfigJava9Parser.StatementNoShortIfContext ctx);
	/**
	 * Exit a parse tree produced by {@link GraphConfigJava9Parser#statementNoShortIf}.
	 * @param ctx the parse tree
	 */
	void exitStatementNoShortIf(GraphConfigJava9Parser.StatementNoShortIfContext ctx);
	/**
	 * Enter a parse tree produced by {@link GraphConfigJava9Parser#statementWithoutTrailingSubstatement}.
	 * @param ctx the parse tree
	 */
	void enterStatementWithoutTrailingSubstatement(GraphConfigJava9Parser.StatementWithoutTrailingSubstatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link GraphConfigJava9Parser#statementWithoutTrailingSubstatement}.
	 * @param ctx the parse tree
	 */
	void exitStatementWithoutTrailingSubstatement(GraphConfigJava9Parser.StatementWithoutTrailingSubstatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link GraphConfigJava9Parser#emptyStatement}.
	 * @param ctx the parse tree
	 */
	void enterEmptyStatement(GraphConfigJava9Parser.EmptyStatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link GraphConfigJava9Parser#emptyStatement}.
	 * @param ctx the parse tree
	 */
	void exitEmptyStatement(GraphConfigJava9Parser.EmptyStatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link GraphConfigJava9Parser#labeledStatement}.
	 * @param ctx the parse tree
	 */
	void enterLabeledStatement(GraphConfigJava9Parser.LabeledStatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link GraphConfigJava9Parser#labeledStatement}.
	 * @param ctx the parse tree
	 */
	void exitLabeledStatement(GraphConfigJava9Parser.LabeledStatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link GraphConfigJava9Parser#labeledStatementNoShortIf}.
	 * @param ctx the parse tree
	 */
	void enterLabeledStatementNoShortIf(GraphConfigJava9Parser.LabeledStatementNoShortIfContext ctx);
	/**
	 * Exit a parse tree produced by {@link GraphConfigJava9Parser#labeledStatementNoShortIf}.
	 * @param ctx the parse tree
	 */
	void exitLabeledStatementNoShortIf(GraphConfigJava9Parser.LabeledStatementNoShortIfContext ctx);
	/**
	 * Enter a parse tree produced by {@link GraphConfigJava9Parser#expressionStatement}.
	 * @param ctx the parse tree
	 */
	void enterExpressionStatement(GraphConfigJava9Parser.ExpressionStatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link GraphConfigJava9Parser#expressionStatement}.
	 * @param ctx the parse tree
	 */
	void exitExpressionStatement(GraphConfigJava9Parser.ExpressionStatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link GraphConfigJava9Parser#statementExpression}.
	 * @param ctx the parse tree
	 */
	void enterStatementExpression(GraphConfigJava9Parser.StatementExpressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link GraphConfigJava9Parser#statementExpression}.
	 * @param ctx the parse tree
	 */
	void exitStatementExpression(GraphConfigJava9Parser.StatementExpressionContext ctx);
	/**
	 * Enter a parse tree produced by {@link GraphConfigJava9Parser#ifThenStatement}.
	 * @param ctx the parse tree
	 */
	void enterIfThenStatement(GraphConfigJava9Parser.IfThenStatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link GraphConfigJava9Parser#ifThenStatement}.
	 * @param ctx the parse tree
	 */
	void exitIfThenStatement(GraphConfigJava9Parser.IfThenStatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link GraphConfigJava9Parser#ifThenElseStatement}.
	 * @param ctx the parse tree
	 */
	void enterIfThenElseStatement(GraphConfigJava9Parser.IfThenElseStatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link GraphConfigJava9Parser#ifThenElseStatement}.
	 * @param ctx the parse tree
	 */
	void exitIfThenElseStatement(GraphConfigJava9Parser.IfThenElseStatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link GraphConfigJava9Parser#ifThenElseStatementNoShortIf}.
	 * @param ctx the parse tree
	 */
	void enterIfThenElseStatementNoShortIf(GraphConfigJava9Parser.IfThenElseStatementNoShortIfContext ctx);
	/**
	 * Exit a parse tree produced by {@link GraphConfigJava9Parser#ifThenElseStatementNoShortIf}.
	 * @param ctx the parse tree
	 */
	void exitIfThenElseStatementNoShortIf(GraphConfigJava9Parser.IfThenElseStatementNoShortIfContext ctx);
	/**
	 * Enter a parse tree produced by {@link GraphConfigJava9Parser#assertStatement}.
	 * @param ctx the parse tree
	 */
	void enterAssertStatement(GraphConfigJava9Parser.AssertStatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link GraphConfigJava9Parser#assertStatement}.
	 * @param ctx the parse tree
	 */
	void exitAssertStatement(GraphConfigJava9Parser.AssertStatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link GraphConfigJava9Parser#switchStatement}.
	 * @param ctx the parse tree
	 */
	void enterSwitchStatement(GraphConfigJava9Parser.SwitchStatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link GraphConfigJava9Parser#switchStatement}.
	 * @param ctx the parse tree
	 */
	void exitSwitchStatement(GraphConfigJava9Parser.SwitchStatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link GraphConfigJava9Parser#switchBlock}.
	 * @param ctx the parse tree
	 */
	void enterSwitchBlock(GraphConfigJava9Parser.SwitchBlockContext ctx);
	/**
	 * Exit a parse tree produced by {@link GraphConfigJava9Parser#switchBlock}.
	 * @param ctx the parse tree
	 */
	void exitSwitchBlock(GraphConfigJava9Parser.SwitchBlockContext ctx);
	/**
	 * Enter a parse tree produced by {@link GraphConfigJava9Parser#switchBlockStatementGroup}.
	 * @param ctx the parse tree
	 */
	void enterSwitchBlockStatementGroup(GraphConfigJava9Parser.SwitchBlockStatementGroupContext ctx);
	/**
	 * Exit a parse tree produced by {@link GraphConfigJava9Parser#switchBlockStatementGroup}.
	 * @param ctx the parse tree
	 */
	void exitSwitchBlockStatementGroup(GraphConfigJava9Parser.SwitchBlockStatementGroupContext ctx);
	/**
	 * Enter a parse tree produced by {@link GraphConfigJava9Parser#switchLabels}.
	 * @param ctx the parse tree
	 */
	void enterSwitchLabels(GraphConfigJava9Parser.SwitchLabelsContext ctx);
	/**
	 * Exit a parse tree produced by {@link GraphConfigJava9Parser#switchLabels}.
	 * @param ctx the parse tree
	 */
	void exitSwitchLabels(GraphConfigJava9Parser.SwitchLabelsContext ctx);
	/**
	 * Enter a parse tree produced by {@link GraphConfigJava9Parser#switchLabel}.
	 * @param ctx the parse tree
	 */
	void enterSwitchLabel(GraphConfigJava9Parser.SwitchLabelContext ctx);
	/**
	 * Exit a parse tree produced by {@link GraphConfigJava9Parser#switchLabel}.
	 * @param ctx the parse tree
	 */
	void exitSwitchLabel(GraphConfigJava9Parser.SwitchLabelContext ctx);
	/**
	 * Enter a parse tree produced by {@link GraphConfigJava9Parser#enumConstantName}.
	 * @param ctx the parse tree
	 */
	void enterEnumConstantName(GraphConfigJava9Parser.EnumConstantNameContext ctx);
	/**
	 * Exit a parse tree produced by {@link GraphConfigJava9Parser#enumConstantName}.
	 * @param ctx the parse tree
	 */
	void exitEnumConstantName(GraphConfigJava9Parser.EnumConstantNameContext ctx);
	/**
	 * Enter a parse tree produced by {@link GraphConfigJava9Parser#whileStatement}.
	 * @param ctx the parse tree
	 */
	void enterWhileStatement(GraphConfigJava9Parser.WhileStatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link GraphConfigJava9Parser#whileStatement}.
	 * @param ctx the parse tree
	 */
	void exitWhileStatement(GraphConfigJava9Parser.WhileStatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link GraphConfigJava9Parser#whileStatementNoShortIf}.
	 * @param ctx the parse tree
	 */
	void enterWhileStatementNoShortIf(GraphConfigJava9Parser.WhileStatementNoShortIfContext ctx);
	/**
	 * Exit a parse tree produced by {@link GraphConfigJava9Parser#whileStatementNoShortIf}.
	 * @param ctx the parse tree
	 */
	void exitWhileStatementNoShortIf(GraphConfigJava9Parser.WhileStatementNoShortIfContext ctx);
	/**
	 * Enter a parse tree produced by {@link GraphConfigJava9Parser#doStatement}.
	 * @param ctx the parse tree
	 */
	void enterDoStatement(GraphConfigJava9Parser.DoStatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link GraphConfigJava9Parser#doStatement}.
	 * @param ctx the parse tree
	 */
	void exitDoStatement(GraphConfigJava9Parser.DoStatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link GraphConfigJava9Parser#forStatement}.
	 * @param ctx the parse tree
	 */
	void enterForStatement(GraphConfigJava9Parser.ForStatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link GraphConfigJava9Parser#forStatement}.
	 * @param ctx the parse tree
	 */
	void exitForStatement(GraphConfigJava9Parser.ForStatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link GraphConfigJava9Parser#forStatementNoShortIf}.
	 * @param ctx the parse tree
	 */
	void enterForStatementNoShortIf(GraphConfigJava9Parser.ForStatementNoShortIfContext ctx);
	/**
	 * Exit a parse tree produced by {@link GraphConfigJava9Parser#forStatementNoShortIf}.
	 * @param ctx the parse tree
	 */
	void exitForStatementNoShortIf(GraphConfigJava9Parser.ForStatementNoShortIfContext ctx);
	/**
	 * Enter a parse tree produced by {@link GraphConfigJava9Parser#basicForStatement}.
	 * @param ctx the parse tree
	 */
	void enterBasicForStatement(GraphConfigJava9Parser.BasicForStatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link GraphConfigJava9Parser#basicForStatement}.
	 * @param ctx the parse tree
	 */
	void exitBasicForStatement(GraphConfigJava9Parser.BasicForStatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link GraphConfigJava9Parser#basicForStatementNoShortIf}.
	 * @param ctx the parse tree
	 */
	void enterBasicForStatementNoShortIf(GraphConfigJava9Parser.BasicForStatementNoShortIfContext ctx);
	/**
	 * Exit a parse tree produced by {@link GraphConfigJava9Parser#basicForStatementNoShortIf}.
	 * @param ctx the parse tree
	 */
	void exitBasicForStatementNoShortIf(GraphConfigJava9Parser.BasicForStatementNoShortIfContext ctx);
	/**
	 * Enter a parse tree produced by {@link GraphConfigJava9Parser#forInit}.
	 * @param ctx the parse tree
	 */
	void enterForInit(GraphConfigJava9Parser.ForInitContext ctx);
	/**
	 * Exit a parse tree produced by {@link GraphConfigJava9Parser#forInit}.
	 * @param ctx the parse tree
	 */
	void exitForInit(GraphConfigJava9Parser.ForInitContext ctx);
	/**
	 * Enter a parse tree produced by {@link GraphConfigJava9Parser#forUpdate}.
	 * @param ctx the parse tree
	 */
	void enterForUpdate(GraphConfigJava9Parser.ForUpdateContext ctx);
	/**
	 * Exit a parse tree produced by {@link GraphConfigJava9Parser#forUpdate}.
	 * @param ctx the parse tree
	 */
	void exitForUpdate(GraphConfigJava9Parser.ForUpdateContext ctx);
	/**
	 * Enter a parse tree produced by {@link GraphConfigJava9Parser#statementExpressionList}.
	 * @param ctx the parse tree
	 */
	void enterStatementExpressionList(GraphConfigJava9Parser.StatementExpressionListContext ctx);
	/**
	 * Exit a parse tree produced by {@link GraphConfigJava9Parser#statementExpressionList}.
	 * @param ctx the parse tree
	 */
	void exitStatementExpressionList(GraphConfigJava9Parser.StatementExpressionListContext ctx);
	/**
	 * Enter a parse tree produced by {@link GraphConfigJava9Parser#enhancedForStatement}.
	 * @param ctx the parse tree
	 */
	void enterEnhancedForStatement(GraphConfigJava9Parser.EnhancedForStatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link GraphConfigJava9Parser#enhancedForStatement}.
	 * @param ctx the parse tree
	 */
	void exitEnhancedForStatement(GraphConfigJava9Parser.EnhancedForStatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link GraphConfigJava9Parser#enhancedForStatementNoShortIf}.
	 * @param ctx the parse tree
	 */
	void enterEnhancedForStatementNoShortIf(GraphConfigJava9Parser.EnhancedForStatementNoShortIfContext ctx);
	/**
	 * Exit a parse tree produced by {@link GraphConfigJava9Parser#enhancedForStatementNoShortIf}.
	 * @param ctx the parse tree
	 */
	void exitEnhancedForStatementNoShortIf(GraphConfigJava9Parser.EnhancedForStatementNoShortIfContext ctx);
	/**
	 * Enter a parse tree produced by {@link GraphConfigJava9Parser#breakStatement}.
	 * @param ctx the parse tree
	 */
	void enterBreakStatement(GraphConfigJava9Parser.BreakStatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link GraphConfigJava9Parser#breakStatement}.
	 * @param ctx the parse tree
	 */
	void exitBreakStatement(GraphConfigJava9Parser.BreakStatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link GraphConfigJava9Parser#continueStatement}.
	 * @param ctx the parse tree
	 */
	void enterContinueStatement(GraphConfigJava9Parser.ContinueStatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link GraphConfigJava9Parser#continueStatement}.
	 * @param ctx the parse tree
	 */
	void exitContinueStatement(GraphConfigJava9Parser.ContinueStatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link GraphConfigJava9Parser#returnStatement}.
	 * @param ctx the parse tree
	 */
	void enterReturnStatement(GraphConfigJava9Parser.ReturnStatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link GraphConfigJava9Parser#returnStatement}.
	 * @param ctx the parse tree
	 */
	void exitReturnStatement(GraphConfigJava9Parser.ReturnStatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link GraphConfigJava9Parser#throwStatement}.
	 * @param ctx the parse tree
	 */
	void enterThrowStatement(GraphConfigJava9Parser.ThrowStatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link GraphConfigJava9Parser#throwStatement}.
	 * @param ctx the parse tree
	 */
	void exitThrowStatement(GraphConfigJava9Parser.ThrowStatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link GraphConfigJava9Parser#synchronizedStatement}.
	 * @param ctx the parse tree
	 */
	void enterSynchronizedStatement(GraphConfigJava9Parser.SynchronizedStatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link GraphConfigJava9Parser#synchronizedStatement}.
	 * @param ctx the parse tree
	 */
	void exitSynchronizedStatement(GraphConfigJava9Parser.SynchronizedStatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link GraphConfigJava9Parser#tryStatement}.
	 * @param ctx the parse tree
	 */
	void enterTryStatement(GraphConfigJava9Parser.TryStatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link GraphConfigJava9Parser#tryStatement}.
	 * @param ctx the parse tree
	 */
	void exitTryStatement(GraphConfigJava9Parser.TryStatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link GraphConfigJava9Parser#catches}.
	 * @param ctx the parse tree
	 */
	void enterCatches(GraphConfigJava9Parser.CatchesContext ctx);
	/**
	 * Exit a parse tree produced by {@link GraphConfigJava9Parser#catches}.
	 * @param ctx the parse tree
	 */
	void exitCatches(GraphConfigJava9Parser.CatchesContext ctx);
	/**
	 * Enter a parse tree produced by {@link GraphConfigJava9Parser#catchClause}.
	 * @param ctx the parse tree
	 */
	void enterCatchClause(GraphConfigJava9Parser.CatchClauseContext ctx);
	/**
	 * Exit a parse tree produced by {@link GraphConfigJava9Parser#catchClause}.
	 * @param ctx the parse tree
	 */
	void exitCatchClause(GraphConfigJava9Parser.CatchClauseContext ctx);
	/**
	 * Enter a parse tree produced by {@link GraphConfigJava9Parser#catchFormalParameter}.
	 * @param ctx the parse tree
	 */
	void enterCatchFormalParameter(GraphConfigJava9Parser.CatchFormalParameterContext ctx);
	/**
	 * Exit a parse tree produced by {@link GraphConfigJava9Parser#catchFormalParameter}.
	 * @param ctx the parse tree
	 */
	void exitCatchFormalParameter(GraphConfigJava9Parser.CatchFormalParameterContext ctx);
	/**
	 * Enter a parse tree produced by {@link GraphConfigJava9Parser#catchType}.
	 * @param ctx the parse tree
	 */
	void enterCatchType(GraphConfigJava9Parser.CatchTypeContext ctx);
	/**
	 * Exit a parse tree produced by {@link GraphConfigJava9Parser#catchType}.
	 * @param ctx the parse tree
	 */
	void exitCatchType(GraphConfigJava9Parser.CatchTypeContext ctx);
	/**
	 * Enter a parse tree produced by {@link GraphConfigJava9Parser#finally_}.
	 * @param ctx the parse tree
	 */
	void enterFinally_(GraphConfigJava9Parser.Finally_Context ctx);
	/**
	 * Exit a parse tree produced by {@link GraphConfigJava9Parser#finally_}.
	 * @param ctx the parse tree
	 */
	void exitFinally_(GraphConfigJava9Parser.Finally_Context ctx);
	/**
	 * Enter a parse tree produced by {@link GraphConfigJava9Parser#tryWithResourcesStatement}.
	 * @param ctx the parse tree
	 */
	void enterTryWithResourcesStatement(GraphConfigJava9Parser.TryWithResourcesStatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link GraphConfigJava9Parser#tryWithResourcesStatement}.
	 * @param ctx the parse tree
	 */
	void exitTryWithResourcesStatement(GraphConfigJava9Parser.TryWithResourcesStatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link GraphConfigJava9Parser#resourceSpecification}.
	 * @param ctx the parse tree
	 */
	void enterResourceSpecification(GraphConfigJava9Parser.ResourceSpecificationContext ctx);
	/**
	 * Exit a parse tree produced by {@link GraphConfigJava9Parser#resourceSpecification}.
	 * @param ctx the parse tree
	 */
	void exitResourceSpecification(GraphConfigJava9Parser.ResourceSpecificationContext ctx);
	/**
	 * Enter a parse tree produced by {@link GraphConfigJava9Parser#resourceList}.
	 * @param ctx the parse tree
	 */
	void enterResourceList(GraphConfigJava9Parser.ResourceListContext ctx);
	/**
	 * Exit a parse tree produced by {@link GraphConfigJava9Parser#resourceList}.
	 * @param ctx the parse tree
	 */
	void exitResourceList(GraphConfigJava9Parser.ResourceListContext ctx);
	/**
	 * Enter a parse tree produced by {@link GraphConfigJava9Parser#resource}.
	 * @param ctx the parse tree
	 */
	void enterResource(GraphConfigJava9Parser.ResourceContext ctx);
	/**
	 * Exit a parse tree produced by {@link GraphConfigJava9Parser#resource}.
	 * @param ctx the parse tree
	 */
	void exitResource(GraphConfigJava9Parser.ResourceContext ctx);
	/**
	 * Enter a parse tree produced by {@link GraphConfigJava9Parser#variableAccess}.
	 * @param ctx the parse tree
	 */
	void enterVariableAccess(GraphConfigJava9Parser.VariableAccessContext ctx);
	/**
	 * Exit a parse tree produced by {@link GraphConfigJava9Parser#variableAccess}.
	 * @param ctx the parse tree
	 */
	void exitVariableAccess(GraphConfigJava9Parser.VariableAccessContext ctx);
	/**
	 * Enter a parse tree produced by {@link GraphConfigJava9Parser#primary}.
	 * @param ctx the parse tree
	 */
	void enterPrimary(GraphConfigJava9Parser.PrimaryContext ctx);
	/**
	 * Exit a parse tree produced by {@link GraphConfigJava9Parser#primary}.
	 * @param ctx the parse tree
	 */
	void exitPrimary(GraphConfigJava9Parser.PrimaryContext ctx);
	/**
	 * Enter a parse tree produced by {@link GraphConfigJava9Parser#primaryNoNewArray}.
	 * @param ctx the parse tree
	 */
	void enterPrimaryNoNewArray(GraphConfigJava9Parser.PrimaryNoNewArrayContext ctx);
	/**
	 * Exit a parse tree produced by {@link GraphConfigJava9Parser#primaryNoNewArray}.
	 * @param ctx the parse tree
	 */
	void exitPrimaryNoNewArray(GraphConfigJava9Parser.PrimaryNoNewArrayContext ctx);
	/**
	 * Enter a parse tree produced by {@link GraphConfigJava9Parser#primaryNoNewArray_lf_arrayAccess}.
	 * @param ctx the parse tree
	 */
	void enterPrimaryNoNewArray_lf_arrayAccess(GraphConfigJava9Parser.PrimaryNoNewArray_lf_arrayAccessContext ctx);
	/**
	 * Exit a parse tree produced by {@link GraphConfigJava9Parser#primaryNoNewArray_lf_arrayAccess}.
	 * @param ctx the parse tree
	 */
	void exitPrimaryNoNewArray_lf_arrayAccess(GraphConfigJava9Parser.PrimaryNoNewArray_lf_arrayAccessContext ctx);
	/**
	 * Enter a parse tree produced by {@link GraphConfigJava9Parser#primaryNoNewArray_lfno_arrayAccess}.
	 * @param ctx the parse tree
	 */
	void enterPrimaryNoNewArray_lfno_arrayAccess(GraphConfigJava9Parser.PrimaryNoNewArray_lfno_arrayAccessContext ctx);
	/**
	 * Exit a parse tree produced by {@link GraphConfigJava9Parser#primaryNoNewArray_lfno_arrayAccess}.
	 * @param ctx the parse tree
	 */
	void exitPrimaryNoNewArray_lfno_arrayAccess(GraphConfigJava9Parser.PrimaryNoNewArray_lfno_arrayAccessContext ctx);
	/**
	 * Enter a parse tree produced by {@link GraphConfigJava9Parser#primaryNoNewArray_lf_primary}.
	 * @param ctx the parse tree
	 */
	void enterPrimaryNoNewArray_lf_primary(GraphConfigJava9Parser.PrimaryNoNewArray_lf_primaryContext ctx);
	/**
	 * Exit a parse tree produced by {@link GraphConfigJava9Parser#primaryNoNewArray_lf_primary}.
	 * @param ctx the parse tree
	 */
	void exitPrimaryNoNewArray_lf_primary(GraphConfigJava9Parser.PrimaryNoNewArray_lf_primaryContext ctx);
	/**
	 * Enter a parse tree produced by {@link GraphConfigJava9Parser#primaryNoNewArray_lf_primary_lf_arrayAccess_lf_primary}.
	 * @param ctx the parse tree
	 */
	void enterPrimaryNoNewArray_lf_primary_lf_arrayAccess_lf_primary(GraphConfigJava9Parser.PrimaryNoNewArray_lf_primary_lf_arrayAccess_lf_primaryContext ctx);
	/**
	 * Exit a parse tree produced by {@link GraphConfigJava9Parser#primaryNoNewArray_lf_primary_lf_arrayAccess_lf_primary}.
	 * @param ctx the parse tree
	 */
	void exitPrimaryNoNewArray_lf_primary_lf_arrayAccess_lf_primary(GraphConfigJava9Parser.PrimaryNoNewArray_lf_primary_lf_arrayAccess_lf_primaryContext ctx);
	/**
	 * Enter a parse tree produced by {@link GraphConfigJava9Parser#primaryNoNewArray_lf_primary_lfno_arrayAccess_lf_primary}.
	 * @param ctx the parse tree
	 */
	void enterPrimaryNoNewArray_lf_primary_lfno_arrayAccess_lf_primary(GraphConfigJava9Parser.PrimaryNoNewArray_lf_primary_lfno_arrayAccess_lf_primaryContext ctx);
	/**
	 * Exit a parse tree produced by {@link GraphConfigJava9Parser#primaryNoNewArray_lf_primary_lfno_arrayAccess_lf_primary}.
	 * @param ctx the parse tree
	 */
	void exitPrimaryNoNewArray_lf_primary_lfno_arrayAccess_lf_primary(GraphConfigJava9Parser.PrimaryNoNewArray_lf_primary_lfno_arrayAccess_lf_primaryContext ctx);
	/**
	 * Enter a parse tree produced by {@link GraphConfigJava9Parser#primaryNoNewArray_lfno_primary}.
	 * @param ctx the parse tree
	 */
	void enterPrimaryNoNewArray_lfno_primary(GraphConfigJava9Parser.PrimaryNoNewArray_lfno_primaryContext ctx);
	/**
	 * Exit a parse tree produced by {@link GraphConfigJava9Parser#primaryNoNewArray_lfno_primary}.
	 * @param ctx the parse tree
	 */
	void exitPrimaryNoNewArray_lfno_primary(GraphConfigJava9Parser.PrimaryNoNewArray_lfno_primaryContext ctx);
	/**
	 * Enter a parse tree produced by {@link GraphConfigJava9Parser#primaryNoNewArray_lfno_primary_lf_arrayAccess_lfno_primary}.
	 * @param ctx the parse tree
	 */
	void enterPrimaryNoNewArray_lfno_primary_lf_arrayAccess_lfno_primary(GraphConfigJava9Parser.PrimaryNoNewArray_lfno_primary_lf_arrayAccess_lfno_primaryContext ctx);
	/**
	 * Exit a parse tree produced by {@link GraphConfigJava9Parser#primaryNoNewArray_lfno_primary_lf_arrayAccess_lfno_primary}.
	 * @param ctx the parse tree
	 */
	void exitPrimaryNoNewArray_lfno_primary_lf_arrayAccess_lfno_primary(GraphConfigJava9Parser.PrimaryNoNewArray_lfno_primary_lf_arrayAccess_lfno_primaryContext ctx);
	/**
	 * Enter a parse tree produced by {@link GraphConfigJava9Parser#primaryNoNewArray_lfno_primary_lfno_arrayAccess_lfno_primary}.
	 * @param ctx the parse tree
	 */
	void enterPrimaryNoNewArray_lfno_primary_lfno_arrayAccess_lfno_primary(GraphConfigJava9Parser.PrimaryNoNewArray_lfno_primary_lfno_arrayAccess_lfno_primaryContext ctx);
	/**
	 * Exit a parse tree produced by {@link GraphConfigJava9Parser#primaryNoNewArray_lfno_primary_lfno_arrayAccess_lfno_primary}.
	 * @param ctx the parse tree
	 */
	void exitPrimaryNoNewArray_lfno_primary_lfno_arrayAccess_lfno_primary(GraphConfigJava9Parser.PrimaryNoNewArray_lfno_primary_lfno_arrayAccess_lfno_primaryContext ctx);
	/**
	 * Enter a parse tree produced by {@link GraphConfigJava9Parser#classLiteral}.
	 * @param ctx the parse tree
	 */
	void enterClassLiteral(GraphConfigJava9Parser.ClassLiteralContext ctx);
	/**
	 * Exit a parse tree produced by {@link GraphConfigJava9Parser#classLiteral}.
	 * @param ctx the parse tree
	 */
	void exitClassLiteral(GraphConfigJava9Parser.ClassLiteralContext ctx);
	/**
	 * Enter a parse tree produced by {@link GraphConfigJava9Parser#classInstanceCreationExpression}.
	 * @param ctx the parse tree
	 */
	void enterClassInstanceCreationExpression(GraphConfigJava9Parser.ClassInstanceCreationExpressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link GraphConfigJava9Parser#classInstanceCreationExpression}.
	 * @param ctx the parse tree
	 */
	void exitClassInstanceCreationExpression(GraphConfigJava9Parser.ClassInstanceCreationExpressionContext ctx);
	/**
	 * Enter a parse tree produced by {@link GraphConfigJava9Parser#classInstanceCreationExpression_lf_primary}.
	 * @param ctx the parse tree
	 */
	void enterClassInstanceCreationExpression_lf_primary(GraphConfigJava9Parser.ClassInstanceCreationExpression_lf_primaryContext ctx);
	/**
	 * Exit a parse tree produced by {@link GraphConfigJava9Parser#classInstanceCreationExpression_lf_primary}.
	 * @param ctx the parse tree
	 */
	void exitClassInstanceCreationExpression_lf_primary(GraphConfigJava9Parser.ClassInstanceCreationExpression_lf_primaryContext ctx);
	/**
	 * Enter a parse tree produced by {@link GraphConfigJava9Parser#classInstanceCreationExpression_lfno_primary}.
	 * @param ctx the parse tree
	 */
	void enterClassInstanceCreationExpression_lfno_primary(GraphConfigJava9Parser.ClassInstanceCreationExpression_lfno_primaryContext ctx);
	/**
	 * Exit a parse tree produced by {@link GraphConfigJava9Parser#classInstanceCreationExpression_lfno_primary}.
	 * @param ctx the parse tree
	 */
	void exitClassInstanceCreationExpression_lfno_primary(GraphConfigJava9Parser.ClassInstanceCreationExpression_lfno_primaryContext ctx);
	/**
	 * Enter a parse tree produced by {@link GraphConfigJava9Parser#typeArgumentsOrDiamond}.
	 * @param ctx the parse tree
	 */
	void enterTypeArgumentsOrDiamond(GraphConfigJava9Parser.TypeArgumentsOrDiamondContext ctx);
	/**
	 * Exit a parse tree produced by {@link GraphConfigJava9Parser#typeArgumentsOrDiamond}.
	 * @param ctx the parse tree
	 */
	void exitTypeArgumentsOrDiamond(GraphConfigJava9Parser.TypeArgumentsOrDiamondContext ctx);
	/**
	 * Enter a parse tree produced by {@link GraphConfigJava9Parser#fieldAccess}.
	 * @param ctx the parse tree
	 */
	void enterFieldAccess(GraphConfigJava9Parser.FieldAccessContext ctx);
	/**
	 * Exit a parse tree produced by {@link GraphConfigJava9Parser#fieldAccess}.
	 * @param ctx the parse tree
	 */
	void exitFieldAccess(GraphConfigJava9Parser.FieldAccessContext ctx);
	/**
	 * Enter a parse tree produced by {@link GraphConfigJava9Parser#fieldAccess_lf_primary}.
	 * @param ctx the parse tree
	 */
	void enterFieldAccess_lf_primary(GraphConfigJava9Parser.FieldAccess_lf_primaryContext ctx);
	/**
	 * Exit a parse tree produced by {@link GraphConfigJava9Parser#fieldAccess_lf_primary}.
	 * @param ctx the parse tree
	 */
	void exitFieldAccess_lf_primary(GraphConfigJava9Parser.FieldAccess_lf_primaryContext ctx);
	/**
	 * Enter a parse tree produced by {@link GraphConfigJava9Parser#fieldAccess_lfno_primary}.
	 * @param ctx the parse tree
	 */
	void enterFieldAccess_lfno_primary(GraphConfigJava9Parser.FieldAccess_lfno_primaryContext ctx);
	/**
	 * Exit a parse tree produced by {@link GraphConfigJava9Parser#fieldAccess_lfno_primary}.
	 * @param ctx the parse tree
	 */
	void exitFieldAccess_lfno_primary(GraphConfigJava9Parser.FieldAccess_lfno_primaryContext ctx);
	/**
	 * Enter a parse tree produced by {@link GraphConfigJava9Parser#arrayAccess}.
	 * @param ctx the parse tree
	 */
	void enterArrayAccess(GraphConfigJava9Parser.ArrayAccessContext ctx);
	/**
	 * Exit a parse tree produced by {@link GraphConfigJava9Parser#arrayAccess}.
	 * @param ctx the parse tree
	 */
	void exitArrayAccess(GraphConfigJava9Parser.ArrayAccessContext ctx);
	/**
	 * Enter a parse tree produced by {@link GraphConfigJava9Parser#arrayAccess_lf_primary}.
	 * @param ctx the parse tree
	 */
	void enterArrayAccess_lf_primary(GraphConfigJava9Parser.ArrayAccess_lf_primaryContext ctx);
	/**
	 * Exit a parse tree produced by {@link GraphConfigJava9Parser#arrayAccess_lf_primary}.
	 * @param ctx the parse tree
	 */
	void exitArrayAccess_lf_primary(GraphConfigJava9Parser.ArrayAccess_lf_primaryContext ctx);
	/**
	 * Enter a parse tree produced by {@link GraphConfigJava9Parser#arrayAccess_lfno_primary}.
	 * @param ctx the parse tree
	 */
	void enterArrayAccess_lfno_primary(GraphConfigJava9Parser.ArrayAccess_lfno_primaryContext ctx);
	/**
	 * Exit a parse tree produced by {@link GraphConfigJava9Parser#arrayAccess_lfno_primary}.
	 * @param ctx the parse tree
	 */
	void exitArrayAccess_lfno_primary(GraphConfigJava9Parser.ArrayAccess_lfno_primaryContext ctx);
	/**
	 * Enter a parse tree produced by {@link GraphConfigJava9Parser#methodInvocation}.
	 * @param ctx the parse tree
	 */
	void enterMethodInvocation(GraphConfigJava9Parser.MethodInvocationContext ctx);
	/**
	 * Exit a parse tree produced by {@link GraphConfigJava9Parser#methodInvocation}.
	 * @param ctx the parse tree
	 */
	void exitMethodInvocation(GraphConfigJava9Parser.MethodInvocationContext ctx);
	/**
	 * Enter a parse tree produced by {@link GraphConfigJava9Parser#methodInvocation_lf_primary}.
	 * @param ctx the parse tree
	 */
	void enterMethodInvocation_lf_primary(GraphConfigJava9Parser.MethodInvocation_lf_primaryContext ctx);
	/**
	 * Exit a parse tree produced by {@link GraphConfigJava9Parser#methodInvocation_lf_primary}.
	 * @param ctx the parse tree
	 */
	void exitMethodInvocation_lf_primary(GraphConfigJava9Parser.MethodInvocation_lf_primaryContext ctx);
	/**
	 * Enter a parse tree produced by {@link GraphConfigJava9Parser#methodInvocation_lfno_primary}.
	 * @param ctx the parse tree
	 */
	void enterMethodInvocation_lfno_primary(GraphConfigJava9Parser.MethodInvocation_lfno_primaryContext ctx);
	/**
	 * Exit a parse tree produced by {@link GraphConfigJava9Parser#methodInvocation_lfno_primary}.
	 * @param ctx the parse tree
	 */
	void exitMethodInvocation_lfno_primary(GraphConfigJava9Parser.MethodInvocation_lfno_primaryContext ctx);
	/**
	 * Enter a parse tree produced by {@link GraphConfigJava9Parser#argumentList}.
	 * @param ctx the parse tree
	 */
	void enterArgumentList(GraphConfigJava9Parser.ArgumentListContext ctx);
	/**
	 * Exit a parse tree produced by {@link GraphConfigJava9Parser#argumentList}.
	 * @param ctx the parse tree
	 */
	void exitArgumentList(GraphConfigJava9Parser.ArgumentListContext ctx);
	/**
	 * Enter a parse tree produced by {@link GraphConfigJava9Parser#methodReference}.
	 * @param ctx the parse tree
	 */
	void enterMethodReference(GraphConfigJava9Parser.MethodReferenceContext ctx);
	/**
	 * Exit a parse tree produced by {@link GraphConfigJava9Parser#methodReference}.
	 * @param ctx the parse tree
	 */
	void exitMethodReference(GraphConfigJava9Parser.MethodReferenceContext ctx);
	/**
	 * Enter a parse tree produced by {@link GraphConfigJava9Parser#methodReference_lf_primary}.
	 * @param ctx the parse tree
	 */
	void enterMethodReference_lf_primary(GraphConfigJava9Parser.MethodReference_lf_primaryContext ctx);
	/**
	 * Exit a parse tree produced by {@link GraphConfigJava9Parser#methodReference_lf_primary}.
	 * @param ctx the parse tree
	 */
	void exitMethodReference_lf_primary(GraphConfigJava9Parser.MethodReference_lf_primaryContext ctx);
	/**
	 * Enter a parse tree produced by {@link GraphConfigJava9Parser#methodReference_lfno_primary}.
	 * @param ctx the parse tree
	 */
	void enterMethodReference_lfno_primary(GraphConfigJava9Parser.MethodReference_lfno_primaryContext ctx);
	/**
	 * Exit a parse tree produced by {@link GraphConfigJava9Parser#methodReference_lfno_primary}.
	 * @param ctx the parse tree
	 */
	void exitMethodReference_lfno_primary(GraphConfigJava9Parser.MethodReference_lfno_primaryContext ctx);
	/**
	 * Enter a parse tree produced by {@link GraphConfigJava9Parser#arrayCreationExpression}.
	 * @param ctx the parse tree
	 */
	void enterArrayCreationExpression(GraphConfigJava9Parser.ArrayCreationExpressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link GraphConfigJava9Parser#arrayCreationExpression}.
	 * @param ctx the parse tree
	 */
	void exitArrayCreationExpression(GraphConfigJava9Parser.ArrayCreationExpressionContext ctx);
	/**
	 * Enter a parse tree produced by {@link GraphConfigJava9Parser#dimExprs}.
	 * @param ctx the parse tree
	 */
	void enterDimExprs(GraphConfigJava9Parser.DimExprsContext ctx);
	/**
	 * Exit a parse tree produced by {@link GraphConfigJava9Parser#dimExprs}.
	 * @param ctx the parse tree
	 */
	void exitDimExprs(GraphConfigJava9Parser.DimExprsContext ctx);
	/**
	 * Enter a parse tree produced by {@link GraphConfigJava9Parser#dimExpr}.
	 * @param ctx the parse tree
	 */
	void enterDimExpr(GraphConfigJava9Parser.DimExprContext ctx);
	/**
	 * Exit a parse tree produced by {@link GraphConfigJava9Parser#dimExpr}.
	 * @param ctx the parse tree
	 */
	void exitDimExpr(GraphConfigJava9Parser.DimExprContext ctx);
	/**
	 * Enter a parse tree produced by {@link GraphConfigJava9Parser#constantExpression}.
	 * @param ctx the parse tree
	 */
	void enterConstantExpression(GraphConfigJava9Parser.ConstantExpressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link GraphConfigJava9Parser#constantExpression}.
	 * @param ctx the parse tree
	 */
	void exitConstantExpression(GraphConfigJava9Parser.ConstantExpressionContext ctx);
	/**
	 * Enter a parse tree produced by {@link GraphConfigJava9Parser#expression}.
	 * @param ctx the parse tree
	 */
	void enterExpression(GraphConfigJava9Parser.ExpressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link GraphConfigJava9Parser#expression}.
	 * @param ctx the parse tree
	 */
	void exitExpression(GraphConfigJava9Parser.ExpressionContext ctx);
	/**
	 * Enter a parse tree produced by {@link GraphConfigJava9Parser#lambdaExpression}.
	 * @param ctx the parse tree
	 */
	void enterLambdaExpression(GraphConfigJava9Parser.LambdaExpressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link GraphConfigJava9Parser#lambdaExpression}.
	 * @param ctx the parse tree
	 */
	void exitLambdaExpression(GraphConfigJava9Parser.LambdaExpressionContext ctx);
	/**
	 * Enter a parse tree produced by {@link GraphConfigJava9Parser#lambdaParameters}.
	 * @param ctx the parse tree
	 */
	void enterLambdaParameters(GraphConfigJava9Parser.LambdaParametersContext ctx);
	/**
	 * Exit a parse tree produced by {@link GraphConfigJava9Parser#lambdaParameters}.
	 * @param ctx the parse tree
	 */
	void exitLambdaParameters(GraphConfigJava9Parser.LambdaParametersContext ctx);
	/**
	 * Enter a parse tree produced by {@link GraphConfigJava9Parser#inferredFormalParameterList}.
	 * @param ctx the parse tree
	 */
	void enterInferredFormalParameterList(GraphConfigJava9Parser.InferredFormalParameterListContext ctx);
	/**
	 * Exit a parse tree produced by {@link GraphConfigJava9Parser#inferredFormalParameterList}.
	 * @param ctx the parse tree
	 */
	void exitInferredFormalParameterList(GraphConfigJava9Parser.InferredFormalParameterListContext ctx);
	/**
	 * Enter a parse tree produced by {@link GraphConfigJava9Parser#lambdaBody}.
	 * @param ctx the parse tree
	 */
	void enterLambdaBody(GraphConfigJava9Parser.LambdaBodyContext ctx);
	/**
	 * Exit a parse tree produced by {@link GraphConfigJava9Parser#lambdaBody}.
	 * @param ctx the parse tree
	 */
	void exitLambdaBody(GraphConfigJava9Parser.LambdaBodyContext ctx);
	/**
	 * Enter a parse tree produced by {@link GraphConfigJava9Parser#assignmentExpression}.
	 * @param ctx the parse tree
	 */
	void enterAssignmentExpression(GraphConfigJava9Parser.AssignmentExpressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link GraphConfigJava9Parser#assignmentExpression}.
	 * @param ctx the parse tree
	 */
	void exitAssignmentExpression(GraphConfigJava9Parser.AssignmentExpressionContext ctx);
	/**
	 * Enter a parse tree produced by {@link GraphConfigJava9Parser#assignment}.
	 * @param ctx the parse tree
	 */
	void enterAssignment(GraphConfigJava9Parser.AssignmentContext ctx);
	/**
	 * Exit a parse tree produced by {@link GraphConfigJava9Parser#assignment}.
	 * @param ctx the parse tree
	 */
	void exitAssignment(GraphConfigJava9Parser.AssignmentContext ctx);
	/**
	 * Enter a parse tree produced by {@link GraphConfigJava9Parser#leftHandSide}.
	 * @param ctx the parse tree
	 */
	void enterLeftHandSide(GraphConfigJava9Parser.LeftHandSideContext ctx);
	/**
	 * Exit a parse tree produced by {@link GraphConfigJava9Parser#leftHandSide}.
	 * @param ctx the parse tree
	 */
	void exitLeftHandSide(GraphConfigJava9Parser.LeftHandSideContext ctx);
	/**
	 * Enter a parse tree produced by {@link GraphConfigJava9Parser#assignmentOperator}.
	 * @param ctx the parse tree
	 */
	void enterAssignmentOperator(GraphConfigJava9Parser.AssignmentOperatorContext ctx);
	/**
	 * Exit a parse tree produced by {@link GraphConfigJava9Parser#assignmentOperator}.
	 * @param ctx the parse tree
	 */
	void exitAssignmentOperator(GraphConfigJava9Parser.AssignmentOperatorContext ctx);
	/**
	 * Enter a parse tree produced by {@link GraphConfigJava9Parser#conditionalExpression}.
	 * @param ctx the parse tree
	 */
	void enterConditionalExpression(GraphConfigJava9Parser.ConditionalExpressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link GraphConfigJava9Parser#conditionalExpression}.
	 * @param ctx the parse tree
	 */
	void exitConditionalExpression(GraphConfigJava9Parser.ConditionalExpressionContext ctx);
	/**
	 * Enter a parse tree produced by {@link GraphConfigJava9Parser#conditionalOrExpression}.
	 * @param ctx the parse tree
	 */
	void enterConditionalOrExpression(GraphConfigJava9Parser.ConditionalOrExpressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link GraphConfigJava9Parser#conditionalOrExpression}.
	 * @param ctx the parse tree
	 */
	void exitConditionalOrExpression(GraphConfigJava9Parser.ConditionalOrExpressionContext ctx);
	/**
	 * Enter a parse tree produced by {@link GraphConfigJava9Parser#conditionalAndExpression}.
	 * @param ctx the parse tree
	 */
	void enterConditionalAndExpression(GraphConfigJava9Parser.ConditionalAndExpressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link GraphConfigJava9Parser#conditionalAndExpression}.
	 * @param ctx the parse tree
	 */
	void exitConditionalAndExpression(GraphConfigJava9Parser.ConditionalAndExpressionContext ctx);
	/**
	 * Enter a parse tree produced by {@link GraphConfigJava9Parser#inclusiveOrExpression}.
	 * @param ctx the parse tree
	 */
	void enterInclusiveOrExpression(GraphConfigJava9Parser.InclusiveOrExpressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link GraphConfigJava9Parser#inclusiveOrExpression}.
	 * @param ctx the parse tree
	 */
	void exitInclusiveOrExpression(GraphConfigJava9Parser.InclusiveOrExpressionContext ctx);
	/**
	 * Enter a parse tree produced by {@link GraphConfigJava9Parser#exclusiveOrExpression}.
	 * @param ctx the parse tree
	 */
	void enterExclusiveOrExpression(GraphConfigJava9Parser.ExclusiveOrExpressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link GraphConfigJava9Parser#exclusiveOrExpression}.
	 * @param ctx the parse tree
	 */
	void exitExclusiveOrExpression(GraphConfigJava9Parser.ExclusiveOrExpressionContext ctx);
	/**
	 * Enter a parse tree produced by {@link GraphConfigJava9Parser#andExpression}.
	 * @param ctx the parse tree
	 */
	void enterAndExpression(GraphConfigJava9Parser.AndExpressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link GraphConfigJava9Parser#andExpression}.
	 * @param ctx the parse tree
	 */
	void exitAndExpression(GraphConfigJava9Parser.AndExpressionContext ctx);
	/**
	 * Enter a parse tree produced by {@link GraphConfigJava9Parser#equalityExpression}.
	 * @param ctx the parse tree
	 */
	void enterEqualityExpression(GraphConfigJava9Parser.EqualityExpressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link GraphConfigJava9Parser#equalityExpression}.
	 * @param ctx the parse tree
	 */
	void exitEqualityExpression(GraphConfigJava9Parser.EqualityExpressionContext ctx);
	/**
	 * Enter a parse tree produced by {@link GraphConfigJava9Parser#relationalExpression}.
	 * @param ctx the parse tree
	 */
	void enterRelationalExpression(GraphConfigJava9Parser.RelationalExpressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link GraphConfigJava9Parser#relationalExpression}.
	 * @param ctx the parse tree
	 */
	void exitRelationalExpression(GraphConfigJava9Parser.RelationalExpressionContext ctx);
	/**
	 * Enter a parse tree produced by {@link GraphConfigJava9Parser#shiftExpression}.
	 * @param ctx the parse tree
	 */
	void enterShiftExpression(GraphConfigJava9Parser.ShiftExpressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link GraphConfigJava9Parser#shiftExpression}.
	 * @param ctx the parse tree
	 */
	void exitShiftExpression(GraphConfigJava9Parser.ShiftExpressionContext ctx);
	/**
	 * Enter a parse tree produced by {@link GraphConfigJava9Parser#additiveExpression}.
	 * @param ctx the parse tree
	 */
	void enterAdditiveExpression(GraphConfigJava9Parser.AdditiveExpressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link GraphConfigJava9Parser#additiveExpression}.
	 * @param ctx the parse tree
	 */
	void exitAdditiveExpression(GraphConfigJava9Parser.AdditiveExpressionContext ctx);
	/**
	 * Enter a parse tree produced by {@link GraphConfigJava9Parser#multiplicativeExpression}.
	 * @param ctx the parse tree
	 */
	void enterMultiplicativeExpression(GraphConfigJava9Parser.MultiplicativeExpressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link GraphConfigJava9Parser#multiplicativeExpression}.
	 * @param ctx the parse tree
	 */
	void exitMultiplicativeExpression(GraphConfigJava9Parser.MultiplicativeExpressionContext ctx);
	/**
	 * Enter a parse tree produced by {@link GraphConfigJava9Parser#unaryExpression}.
	 * @param ctx the parse tree
	 */
	void enterUnaryExpression(GraphConfigJava9Parser.UnaryExpressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link GraphConfigJava9Parser#unaryExpression}.
	 * @param ctx the parse tree
	 */
	void exitUnaryExpression(GraphConfigJava9Parser.UnaryExpressionContext ctx);
	/**
	 * Enter a parse tree produced by {@link GraphConfigJava9Parser#preIncrementExpression}.
	 * @param ctx the parse tree
	 */
	void enterPreIncrementExpression(GraphConfigJava9Parser.PreIncrementExpressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link GraphConfigJava9Parser#preIncrementExpression}.
	 * @param ctx the parse tree
	 */
	void exitPreIncrementExpression(GraphConfigJava9Parser.PreIncrementExpressionContext ctx);
	/**
	 * Enter a parse tree produced by {@link GraphConfigJava9Parser#preDecrementExpression}.
	 * @param ctx the parse tree
	 */
	void enterPreDecrementExpression(GraphConfigJava9Parser.PreDecrementExpressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link GraphConfigJava9Parser#preDecrementExpression}.
	 * @param ctx the parse tree
	 */
	void exitPreDecrementExpression(GraphConfigJava9Parser.PreDecrementExpressionContext ctx);
	/**
	 * Enter a parse tree produced by {@link GraphConfigJava9Parser#unaryExpressionNotPlusMinus}.
	 * @param ctx the parse tree
	 */
	void enterUnaryExpressionNotPlusMinus(GraphConfigJava9Parser.UnaryExpressionNotPlusMinusContext ctx);
	/**
	 * Exit a parse tree produced by {@link GraphConfigJava9Parser#unaryExpressionNotPlusMinus}.
	 * @param ctx the parse tree
	 */
	void exitUnaryExpressionNotPlusMinus(GraphConfigJava9Parser.UnaryExpressionNotPlusMinusContext ctx);
	/**
	 * Enter a parse tree produced by {@link GraphConfigJava9Parser#postfixExpression}.
	 * @param ctx the parse tree
	 */
	void enterPostfixExpression(GraphConfigJava9Parser.PostfixExpressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link GraphConfigJava9Parser#postfixExpression}.
	 * @param ctx the parse tree
	 */
	void exitPostfixExpression(GraphConfigJava9Parser.PostfixExpressionContext ctx);
	/**
	 * Enter a parse tree produced by {@link GraphConfigJava9Parser#postIncrementExpression}.
	 * @param ctx the parse tree
	 */
	void enterPostIncrementExpression(GraphConfigJava9Parser.PostIncrementExpressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link GraphConfigJava9Parser#postIncrementExpression}.
	 * @param ctx the parse tree
	 */
	void exitPostIncrementExpression(GraphConfigJava9Parser.PostIncrementExpressionContext ctx);
	/**
	 * Enter a parse tree produced by {@link GraphConfigJava9Parser#postIncrementExpression_lf_postfixExpression}.
	 * @param ctx the parse tree
	 */
	void enterPostIncrementExpression_lf_postfixExpression(GraphConfigJava9Parser.PostIncrementExpression_lf_postfixExpressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link GraphConfigJava9Parser#postIncrementExpression_lf_postfixExpression}.
	 * @param ctx the parse tree
	 */
	void exitPostIncrementExpression_lf_postfixExpression(GraphConfigJava9Parser.PostIncrementExpression_lf_postfixExpressionContext ctx);
	/**
	 * Enter a parse tree produced by {@link GraphConfigJava9Parser#postDecrementExpression}.
	 * @param ctx the parse tree
	 */
	void enterPostDecrementExpression(GraphConfigJava9Parser.PostDecrementExpressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link GraphConfigJava9Parser#postDecrementExpression}.
	 * @param ctx the parse tree
	 */
	void exitPostDecrementExpression(GraphConfigJava9Parser.PostDecrementExpressionContext ctx);
	/**
	 * Enter a parse tree produced by {@link GraphConfigJava9Parser#postDecrementExpression_lf_postfixExpression}.
	 * @param ctx the parse tree
	 */
	void enterPostDecrementExpression_lf_postfixExpression(GraphConfigJava9Parser.PostDecrementExpression_lf_postfixExpressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link GraphConfigJava9Parser#postDecrementExpression_lf_postfixExpression}.
	 * @param ctx the parse tree
	 */
	void exitPostDecrementExpression_lf_postfixExpression(GraphConfigJava9Parser.PostDecrementExpression_lf_postfixExpressionContext ctx);
	/**
	 * Enter a parse tree produced by {@link GraphConfigJava9Parser#castExpression}.
	 * @param ctx the parse tree
	 */
	void enterCastExpression(GraphConfigJava9Parser.CastExpressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link GraphConfigJava9Parser#castExpression}.
	 * @param ctx the parse tree
	 */
	void exitCastExpression(GraphConfigJava9Parser.CastExpressionContext ctx);
}
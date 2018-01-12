// Generated from GraphConfigJava9.g4 by ANTLR 4.7.1
package ru.fix.completable.reactor.parser.java.antlr;
import org.antlr.v4.runtime.tree.ParseTreeVisitor;

/**
 * This interface defines a complete generic visitor for a parse tree produced
 * by {@link GraphConfigJava9Parser}.
 *
 * @param <T> The return type of the visit operation. Use {@link Void} for
 * operations with no return type.
 */
public interface GraphConfigJava9Visitor<T> extends ParseTreeVisitor<T> {
	/**
	 * Visit a parse tree produced by {@link GraphConfigJava9Parser#sourceFile}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSourceFile(GraphConfigJava9Parser.SourceFileContext ctx);
	/**
	 * Visit a parse tree produced by {@link GraphConfigJava9Parser#graphBlock}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitGraphBlock(GraphConfigJava9Parser.GraphBlockContext ctx);
	/**
	 * Visit a parse tree produced by {@link GraphConfigJava9Parser#vertexInitializationBlock}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitVertexInitializationBlock(GraphConfigJava9Parser.VertexInitializationBlockContext ctx);
	/**
	 * Visit a parse tree produced by {@link GraphConfigJava9Parser#ignoreBracesBlock}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIgnoreBracesBlock(GraphConfigJava9Parser.IgnoreBracesBlockContext ctx);
	/**
	 * Visit a parse tree produced by {@link GraphConfigJava9Parser#payloadTransitionBlock}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPayloadTransitionBlock(GraphConfigJava9Parser.PayloadTransitionBlockContext ctx);
	/**
	 * Visit a parse tree produced by {@link GraphConfigJava9Parser#handleBy}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitHandleBy(GraphConfigJava9Parser.HandleByContext ctx);
	/**
	 * Visit a parse tree produced by {@link GraphConfigJava9Parser#handleByVertex}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitHandleByVertex(GraphConfigJava9Parser.HandleByVertexContext ctx);
	/**
	 * Visit a parse tree produced by {@link GraphConfigJava9Parser#vertexTransitionBlock}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitVertexTransitionBlock(GraphConfigJava9Parser.VertexTransitionBlockContext ctx);
	/**
	 * Visit a parse tree produced by {@link GraphConfigJava9Parser#vertexTransition}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitVertexTransition(GraphConfigJava9Parser.VertexTransitionContext ctx);
	/**
	 * Visit a parse tree produced by {@link GraphConfigJava9Parser#transitionAction}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTransitionAction(GraphConfigJava9Parser.TransitionActionContext ctx);
	/**
	 * Visit a parse tree produced by {@link GraphConfigJava9Parser#transitionCondition}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTransitionCondition(GraphConfigJava9Parser.TransitionConditionContext ctx);
	/**
	 * Visit a parse tree produced by {@link GraphConfigJava9Parser#ignoredCharBloc}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIgnoredCharBloc(GraphConfigJava9Parser.IgnoredCharBlocContext ctx);
	/**
	 * Visit a parse tree produced by {@link GraphConfigJava9Parser#ignoredChar}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIgnoredChar(GraphConfigJava9Parser.IgnoredCharContext ctx);
	/**
	 * Visit a parse tree produced by {@link GraphConfigJava9Parser#literal}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLiteral(GraphConfigJava9Parser.LiteralContext ctx);
	/**
	 * Visit a parse tree produced by {@link GraphConfigJava9Parser#type}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitType(GraphConfigJava9Parser.TypeContext ctx);
	/**
	 * Visit a parse tree produced by {@link GraphConfigJava9Parser#primitiveType}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPrimitiveType(GraphConfigJava9Parser.PrimitiveTypeContext ctx);
	/**
	 * Visit a parse tree produced by {@link GraphConfigJava9Parser#numericType}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitNumericType(GraphConfigJava9Parser.NumericTypeContext ctx);
	/**
	 * Visit a parse tree produced by {@link GraphConfigJava9Parser#integralType}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIntegralType(GraphConfigJava9Parser.IntegralTypeContext ctx);
	/**
	 * Visit a parse tree produced by {@link GraphConfigJava9Parser#floatingPointType}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFloatingPointType(GraphConfigJava9Parser.FloatingPointTypeContext ctx);
	/**
	 * Visit a parse tree produced by {@link GraphConfigJava9Parser#referenceType}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitReferenceType(GraphConfigJava9Parser.ReferenceTypeContext ctx);
	/**
	 * Visit a parse tree produced by {@link GraphConfigJava9Parser#classOrInterfaceType}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitClassOrInterfaceType(GraphConfigJava9Parser.ClassOrInterfaceTypeContext ctx);
	/**
	 * Visit a parse tree produced by {@link GraphConfigJava9Parser#classType}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitClassType(GraphConfigJava9Parser.ClassTypeContext ctx);
	/**
	 * Visit a parse tree produced by {@link GraphConfigJava9Parser#classType_lf_classOrInterfaceType}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitClassType_lf_classOrInterfaceType(GraphConfigJava9Parser.ClassType_lf_classOrInterfaceTypeContext ctx);
	/**
	 * Visit a parse tree produced by {@link GraphConfigJava9Parser#classType_lfno_classOrInterfaceType}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitClassType_lfno_classOrInterfaceType(GraphConfigJava9Parser.ClassType_lfno_classOrInterfaceTypeContext ctx);
	/**
	 * Visit a parse tree produced by {@link GraphConfigJava9Parser#interfaceType}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitInterfaceType(GraphConfigJava9Parser.InterfaceTypeContext ctx);
	/**
	 * Visit a parse tree produced by {@link GraphConfigJava9Parser#interfaceType_lf_classOrInterfaceType}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitInterfaceType_lf_classOrInterfaceType(GraphConfigJava9Parser.InterfaceType_lf_classOrInterfaceTypeContext ctx);
	/**
	 * Visit a parse tree produced by {@link GraphConfigJava9Parser#interfaceType_lfno_classOrInterfaceType}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitInterfaceType_lfno_classOrInterfaceType(GraphConfigJava9Parser.InterfaceType_lfno_classOrInterfaceTypeContext ctx);
	/**
	 * Visit a parse tree produced by {@link GraphConfigJava9Parser#typeVariable}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTypeVariable(GraphConfigJava9Parser.TypeVariableContext ctx);
	/**
	 * Visit a parse tree produced by {@link GraphConfigJava9Parser#arrayType}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitArrayType(GraphConfigJava9Parser.ArrayTypeContext ctx);
	/**
	 * Visit a parse tree produced by {@link GraphConfigJava9Parser#dims}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDims(GraphConfigJava9Parser.DimsContext ctx);
	/**
	 * Visit a parse tree produced by {@link GraphConfigJava9Parser#typeParameter}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTypeParameter(GraphConfigJava9Parser.TypeParameterContext ctx);
	/**
	 * Visit a parse tree produced by {@link GraphConfigJava9Parser#typeParameterModifier}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTypeParameterModifier(GraphConfigJava9Parser.TypeParameterModifierContext ctx);
	/**
	 * Visit a parse tree produced by {@link GraphConfigJava9Parser#typeBound}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTypeBound(GraphConfigJava9Parser.TypeBoundContext ctx);
	/**
	 * Visit a parse tree produced by {@link GraphConfigJava9Parser#additionalBound}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAdditionalBound(GraphConfigJava9Parser.AdditionalBoundContext ctx);
	/**
	 * Visit a parse tree produced by {@link GraphConfigJava9Parser#typeArguments}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTypeArguments(GraphConfigJava9Parser.TypeArgumentsContext ctx);
	/**
	 * Visit a parse tree produced by {@link GraphConfigJava9Parser#typeArgumentList}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTypeArgumentList(GraphConfigJava9Parser.TypeArgumentListContext ctx);
	/**
	 * Visit a parse tree produced by {@link GraphConfigJava9Parser#typeArgument}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTypeArgument(GraphConfigJava9Parser.TypeArgumentContext ctx);
	/**
	 * Visit a parse tree produced by {@link GraphConfigJava9Parser#wildcard}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitWildcard(GraphConfigJava9Parser.WildcardContext ctx);
	/**
	 * Visit a parse tree produced by {@link GraphConfigJava9Parser#wildcardBounds}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitWildcardBounds(GraphConfigJava9Parser.WildcardBoundsContext ctx);
	/**
	 * Visit a parse tree produced by {@link GraphConfigJava9Parser#moduleName}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitModuleName(GraphConfigJava9Parser.ModuleNameContext ctx);
	/**
	 * Visit a parse tree produced by {@link GraphConfigJava9Parser#packageName}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPackageName(GraphConfigJava9Parser.PackageNameContext ctx);
	/**
	 * Visit a parse tree produced by {@link GraphConfigJava9Parser#typeName}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTypeName(GraphConfigJava9Parser.TypeNameContext ctx);
	/**
	 * Visit a parse tree produced by {@link GraphConfigJava9Parser#packageOrTypeName}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPackageOrTypeName(GraphConfigJava9Parser.PackageOrTypeNameContext ctx);
	/**
	 * Visit a parse tree produced by {@link GraphConfigJava9Parser#expressionName}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExpressionName(GraphConfigJava9Parser.ExpressionNameContext ctx);
	/**
	 * Visit a parse tree produced by {@link GraphConfigJava9Parser#methodName}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMethodName(GraphConfigJava9Parser.MethodNameContext ctx);
	/**
	 * Visit a parse tree produced by {@link GraphConfigJava9Parser#ambiguousName}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAmbiguousName(GraphConfigJava9Parser.AmbiguousNameContext ctx);
	/**
	 * Visit a parse tree produced by {@link GraphConfigJava9Parser#compilationUnit}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCompilationUnit(GraphConfigJava9Parser.CompilationUnitContext ctx);
	/**
	 * Visit a parse tree produced by {@link GraphConfigJava9Parser#ordinaryCompilation}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitOrdinaryCompilation(GraphConfigJava9Parser.OrdinaryCompilationContext ctx);
	/**
	 * Visit a parse tree produced by {@link GraphConfigJava9Parser#modularCompilation}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitModularCompilation(GraphConfigJava9Parser.ModularCompilationContext ctx);
	/**
	 * Visit a parse tree produced by {@link GraphConfigJava9Parser#packageDeclaration}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPackageDeclaration(GraphConfigJava9Parser.PackageDeclarationContext ctx);
	/**
	 * Visit a parse tree produced by {@link GraphConfigJava9Parser#packageModifier}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPackageModifier(GraphConfigJava9Parser.PackageModifierContext ctx);
	/**
	 * Visit a parse tree produced by {@link GraphConfigJava9Parser#importDeclaration}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitImportDeclaration(GraphConfigJava9Parser.ImportDeclarationContext ctx);
	/**
	 * Visit a parse tree produced by {@link GraphConfigJava9Parser#singleTypeImportDeclaration}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSingleTypeImportDeclaration(GraphConfigJava9Parser.SingleTypeImportDeclarationContext ctx);
	/**
	 * Visit a parse tree produced by {@link GraphConfigJava9Parser#typeImportOnDemandDeclaration}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTypeImportOnDemandDeclaration(GraphConfigJava9Parser.TypeImportOnDemandDeclarationContext ctx);
	/**
	 * Visit a parse tree produced by {@link GraphConfigJava9Parser#singleStaticImportDeclaration}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSingleStaticImportDeclaration(GraphConfigJava9Parser.SingleStaticImportDeclarationContext ctx);
	/**
	 * Visit a parse tree produced by {@link GraphConfigJava9Parser#staticImportOnDemandDeclaration}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitStaticImportOnDemandDeclaration(GraphConfigJava9Parser.StaticImportOnDemandDeclarationContext ctx);
	/**
	 * Visit a parse tree produced by {@link GraphConfigJava9Parser#typeDeclaration}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTypeDeclaration(GraphConfigJava9Parser.TypeDeclarationContext ctx);
	/**
	 * Visit a parse tree produced by {@link GraphConfigJava9Parser#moduleDeclaration}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitModuleDeclaration(GraphConfigJava9Parser.ModuleDeclarationContext ctx);
	/**
	 * Visit a parse tree produced by {@link GraphConfigJava9Parser#moduleDirective}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitModuleDirective(GraphConfigJava9Parser.ModuleDirectiveContext ctx);
	/**
	 * Visit a parse tree produced by {@link GraphConfigJava9Parser#requiresModifier}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitRequiresModifier(GraphConfigJava9Parser.RequiresModifierContext ctx);
	/**
	 * Visit a parse tree produced by {@link GraphConfigJava9Parser#classDeclaration}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitClassDeclaration(GraphConfigJava9Parser.ClassDeclarationContext ctx);
	/**
	 * Visit a parse tree produced by {@link GraphConfigJava9Parser#normalClassDeclaration}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitNormalClassDeclaration(GraphConfigJava9Parser.NormalClassDeclarationContext ctx);
	/**
	 * Visit a parse tree produced by {@link GraphConfigJava9Parser#classModifier}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitClassModifier(GraphConfigJava9Parser.ClassModifierContext ctx);
	/**
	 * Visit a parse tree produced by {@link GraphConfigJava9Parser#typeParameters}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTypeParameters(GraphConfigJava9Parser.TypeParametersContext ctx);
	/**
	 * Visit a parse tree produced by {@link GraphConfigJava9Parser#typeParameterList}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTypeParameterList(GraphConfigJava9Parser.TypeParameterListContext ctx);
	/**
	 * Visit a parse tree produced by {@link GraphConfigJava9Parser#superclass}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSuperclass(GraphConfigJava9Parser.SuperclassContext ctx);
	/**
	 * Visit a parse tree produced by {@link GraphConfigJava9Parser#superinterfaces}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSuperinterfaces(GraphConfigJava9Parser.SuperinterfacesContext ctx);
	/**
	 * Visit a parse tree produced by {@link GraphConfigJava9Parser#interfaceTypeList}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitInterfaceTypeList(GraphConfigJava9Parser.InterfaceTypeListContext ctx);
	/**
	 * Visit a parse tree produced by {@link GraphConfigJava9Parser#classBody}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitClassBody(GraphConfigJava9Parser.ClassBodyContext ctx);
	/**
	 * Visit a parse tree produced by {@link GraphConfigJava9Parser#classBodyDeclaration}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitClassBodyDeclaration(GraphConfigJava9Parser.ClassBodyDeclarationContext ctx);
	/**
	 * Visit a parse tree produced by {@link GraphConfigJava9Parser#classMemberDeclaration}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitClassMemberDeclaration(GraphConfigJava9Parser.ClassMemberDeclarationContext ctx);
	/**
	 * Visit a parse tree produced by {@link GraphConfigJava9Parser#fieldDeclaration}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFieldDeclaration(GraphConfigJava9Parser.FieldDeclarationContext ctx);
	/**
	 * Visit a parse tree produced by {@link GraphConfigJava9Parser#fieldModifier}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFieldModifier(GraphConfigJava9Parser.FieldModifierContext ctx);
	/**
	 * Visit a parse tree produced by {@link GraphConfigJava9Parser#variableDeclaratorList}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitVariableDeclaratorList(GraphConfigJava9Parser.VariableDeclaratorListContext ctx);
	/**
	 * Visit a parse tree produced by {@link GraphConfigJava9Parser#variableDeclarator}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitVariableDeclarator(GraphConfigJava9Parser.VariableDeclaratorContext ctx);
	/**
	 * Visit a parse tree produced by {@link GraphConfigJava9Parser#variableDeclaratorId}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitVariableDeclaratorId(GraphConfigJava9Parser.VariableDeclaratorIdContext ctx);
	/**
	 * Visit a parse tree produced by {@link GraphConfigJava9Parser#variableInitializer}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitVariableInitializer(GraphConfigJava9Parser.VariableInitializerContext ctx);
	/**
	 * Visit a parse tree produced by {@link GraphConfigJava9Parser#unannType}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitUnannType(GraphConfigJava9Parser.UnannTypeContext ctx);
	/**
	 * Visit a parse tree produced by {@link GraphConfigJava9Parser#unannPrimitiveType}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitUnannPrimitiveType(GraphConfigJava9Parser.UnannPrimitiveTypeContext ctx);
	/**
	 * Visit a parse tree produced by {@link GraphConfigJava9Parser#unannReferenceType}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitUnannReferenceType(GraphConfigJava9Parser.UnannReferenceTypeContext ctx);
	/**
	 * Visit a parse tree produced by {@link GraphConfigJava9Parser#unannClassOrInterfaceType}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitUnannClassOrInterfaceType(GraphConfigJava9Parser.UnannClassOrInterfaceTypeContext ctx);
	/**
	 * Visit a parse tree produced by {@link GraphConfigJava9Parser#unannClassType}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitUnannClassType(GraphConfigJava9Parser.UnannClassTypeContext ctx);
	/**
	 * Visit a parse tree produced by {@link GraphConfigJava9Parser#unannClassType_lf_unannClassOrInterfaceType}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitUnannClassType_lf_unannClassOrInterfaceType(GraphConfigJava9Parser.UnannClassType_lf_unannClassOrInterfaceTypeContext ctx);
	/**
	 * Visit a parse tree produced by {@link GraphConfigJava9Parser#unannClassType_lfno_unannClassOrInterfaceType}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitUnannClassType_lfno_unannClassOrInterfaceType(GraphConfigJava9Parser.UnannClassType_lfno_unannClassOrInterfaceTypeContext ctx);
	/**
	 * Visit a parse tree produced by {@link GraphConfigJava9Parser#unannInterfaceType}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitUnannInterfaceType(GraphConfigJava9Parser.UnannInterfaceTypeContext ctx);
	/**
	 * Visit a parse tree produced by {@link GraphConfigJava9Parser#unannInterfaceType_lf_unannClassOrInterfaceType}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitUnannInterfaceType_lf_unannClassOrInterfaceType(GraphConfigJava9Parser.UnannInterfaceType_lf_unannClassOrInterfaceTypeContext ctx);
	/**
	 * Visit a parse tree produced by {@link GraphConfigJava9Parser#unannInterfaceType_lfno_unannClassOrInterfaceType}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitUnannInterfaceType_lfno_unannClassOrInterfaceType(GraphConfigJava9Parser.UnannInterfaceType_lfno_unannClassOrInterfaceTypeContext ctx);
	/**
	 * Visit a parse tree produced by {@link GraphConfigJava9Parser#unannTypeVariable}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitUnannTypeVariable(GraphConfigJava9Parser.UnannTypeVariableContext ctx);
	/**
	 * Visit a parse tree produced by {@link GraphConfigJava9Parser#unannArrayType}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitUnannArrayType(GraphConfigJava9Parser.UnannArrayTypeContext ctx);
	/**
	 * Visit a parse tree produced by {@link GraphConfigJava9Parser#methodDeclaration}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMethodDeclaration(GraphConfigJava9Parser.MethodDeclarationContext ctx);
	/**
	 * Visit a parse tree produced by {@link GraphConfigJava9Parser#methodModifier}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMethodModifier(GraphConfigJava9Parser.MethodModifierContext ctx);
	/**
	 * Visit a parse tree produced by {@link GraphConfigJava9Parser#methodHeader}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMethodHeader(GraphConfigJava9Parser.MethodHeaderContext ctx);
	/**
	 * Visit a parse tree produced by {@link GraphConfigJava9Parser#result}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitResult(GraphConfigJava9Parser.ResultContext ctx);
	/**
	 * Visit a parse tree produced by {@link GraphConfigJava9Parser#methodDeclarator}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMethodDeclarator(GraphConfigJava9Parser.MethodDeclaratorContext ctx);
	/**
	 * Visit a parse tree produced by {@link GraphConfigJava9Parser#formalParameterList}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFormalParameterList(GraphConfigJava9Parser.FormalParameterListContext ctx);
	/**
	 * Visit a parse tree produced by {@link GraphConfigJava9Parser#formalParameters}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFormalParameters(GraphConfigJava9Parser.FormalParametersContext ctx);
	/**
	 * Visit a parse tree produced by {@link GraphConfigJava9Parser#formalParameter}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFormalParameter(GraphConfigJava9Parser.FormalParameterContext ctx);
	/**
	 * Visit a parse tree produced by {@link GraphConfigJava9Parser#variableModifier}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitVariableModifier(GraphConfigJava9Parser.VariableModifierContext ctx);
	/**
	 * Visit a parse tree produced by {@link GraphConfigJava9Parser#lastFormalParameter}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLastFormalParameter(GraphConfigJava9Parser.LastFormalParameterContext ctx);
	/**
	 * Visit a parse tree produced by {@link GraphConfigJava9Parser#receiverParameter}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitReceiverParameter(GraphConfigJava9Parser.ReceiverParameterContext ctx);
	/**
	 * Visit a parse tree produced by {@link GraphConfigJava9Parser#throws_}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitThrows_(GraphConfigJava9Parser.Throws_Context ctx);
	/**
	 * Visit a parse tree produced by {@link GraphConfigJava9Parser#exceptionTypeList}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExceptionTypeList(GraphConfigJava9Parser.ExceptionTypeListContext ctx);
	/**
	 * Visit a parse tree produced by {@link GraphConfigJava9Parser#exceptionType}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExceptionType(GraphConfigJava9Parser.ExceptionTypeContext ctx);
	/**
	 * Visit a parse tree produced by {@link GraphConfigJava9Parser#methodBody}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMethodBody(GraphConfigJava9Parser.MethodBodyContext ctx);
	/**
	 * Visit a parse tree produced by {@link GraphConfigJava9Parser#instanceInitializer}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitInstanceInitializer(GraphConfigJava9Parser.InstanceInitializerContext ctx);
	/**
	 * Visit a parse tree produced by {@link GraphConfigJava9Parser#staticInitializer}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitStaticInitializer(GraphConfigJava9Parser.StaticInitializerContext ctx);
	/**
	 * Visit a parse tree produced by {@link GraphConfigJava9Parser#constructorDeclaration}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitConstructorDeclaration(GraphConfigJava9Parser.ConstructorDeclarationContext ctx);
	/**
	 * Visit a parse tree produced by {@link GraphConfigJava9Parser#constructorModifier}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitConstructorModifier(GraphConfigJava9Parser.ConstructorModifierContext ctx);
	/**
	 * Visit a parse tree produced by {@link GraphConfigJava9Parser#constructorDeclarator}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitConstructorDeclarator(GraphConfigJava9Parser.ConstructorDeclaratorContext ctx);
	/**
	 * Visit a parse tree produced by {@link GraphConfigJava9Parser#simpleTypeName}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSimpleTypeName(GraphConfigJava9Parser.SimpleTypeNameContext ctx);
	/**
	 * Visit a parse tree produced by {@link GraphConfigJava9Parser#constructorBody}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitConstructorBody(GraphConfigJava9Parser.ConstructorBodyContext ctx);
	/**
	 * Visit a parse tree produced by {@link GraphConfigJava9Parser#explicitConstructorInvocation}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExplicitConstructorInvocation(GraphConfigJava9Parser.ExplicitConstructorInvocationContext ctx);
	/**
	 * Visit a parse tree produced by {@link GraphConfigJava9Parser#enumDeclaration}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitEnumDeclaration(GraphConfigJava9Parser.EnumDeclarationContext ctx);
	/**
	 * Visit a parse tree produced by {@link GraphConfigJava9Parser#enumBody}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitEnumBody(GraphConfigJava9Parser.EnumBodyContext ctx);
	/**
	 * Visit a parse tree produced by {@link GraphConfigJava9Parser#enumConstantList}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitEnumConstantList(GraphConfigJava9Parser.EnumConstantListContext ctx);
	/**
	 * Visit a parse tree produced by {@link GraphConfigJava9Parser#enumConstant}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitEnumConstant(GraphConfigJava9Parser.EnumConstantContext ctx);
	/**
	 * Visit a parse tree produced by {@link GraphConfigJava9Parser#enumConstantModifier}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitEnumConstantModifier(GraphConfigJava9Parser.EnumConstantModifierContext ctx);
	/**
	 * Visit a parse tree produced by {@link GraphConfigJava9Parser#enumBodyDeclarations}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitEnumBodyDeclarations(GraphConfigJava9Parser.EnumBodyDeclarationsContext ctx);
	/**
	 * Visit a parse tree produced by {@link GraphConfigJava9Parser#interfaceDeclaration}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitInterfaceDeclaration(GraphConfigJava9Parser.InterfaceDeclarationContext ctx);
	/**
	 * Visit a parse tree produced by {@link GraphConfigJava9Parser#normalInterfaceDeclaration}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitNormalInterfaceDeclaration(GraphConfigJava9Parser.NormalInterfaceDeclarationContext ctx);
	/**
	 * Visit a parse tree produced by {@link GraphConfigJava9Parser#interfaceModifier}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitInterfaceModifier(GraphConfigJava9Parser.InterfaceModifierContext ctx);
	/**
	 * Visit a parse tree produced by {@link GraphConfigJava9Parser#extendsInterfaces}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExtendsInterfaces(GraphConfigJava9Parser.ExtendsInterfacesContext ctx);
	/**
	 * Visit a parse tree produced by {@link GraphConfigJava9Parser#interfaceBody}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitInterfaceBody(GraphConfigJava9Parser.InterfaceBodyContext ctx);
	/**
	 * Visit a parse tree produced by {@link GraphConfigJava9Parser#interfaceMemberDeclaration}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitInterfaceMemberDeclaration(GraphConfigJava9Parser.InterfaceMemberDeclarationContext ctx);
	/**
	 * Visit a parse tree produced by {@link GraphConfigJava9Parser#constantDeclaration}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitConstantDeclaration(GraphConfigJava9Parser.ConstantDeclarationContext ctx);
	/**
	 * Visit a parse tree produced by {@link GraphConfigJava9Parser#constantModifier}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitConstantModifier(GraphConfigJava9Parser.ConstantModifierContext ctx);
	/**
	 * Visit a parse tree produced by {@link GraphConfigJava9Parser#interfaceMethodDeclaration}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitInterfaceMethodDeclaration(GraphConfigJava9Parser.InterfaceMethodDeclarationContext ctx);
	/**
	 * Visit a parse tree produced by {@link GraphConfigJava9Parser#interfaceMethodModifier}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitInterfaceMethodModifier(GraphConfigJava9Parser.InterfaceMethodModifierContext ctx);
	/**
	 * Visit a parse tree produced by {@link GraphConfigJava9Parser#annotationTypeDeclaration}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAnnotationTypeDeclaration(GraphConfigJava9Parser.AnnotationTypeDeclarationContext ctx);
	/**
	 * Visit a parse tree produced by {@link GraphConfigJava9Parser#annotationTypeBody}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAnnotationTypeBody(GraphConfigJava9Parser.AnnotationTypeBodyContext ctx);
	/**
	 * Visit a parse tree produced by {@link GraphConfigJava9Parser#annotationTypeMemberDeclaration}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAnnotationTypeMemberDeclaration(GraphConfigJava9Parser.AnnotationTypeMemberDeclarationContext ctx);
	/**
	 * Visit a parse tree produced by {@link GraphConfigJava9Parser#annotationTypeElementDeclaration}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAnnotationTypeElementDeclaration(GraphConfigJava9Parser.AnnotationTypeElementDeclarationContext ctx);
	/**
	 * Visit a parse tree produced by {@link GraphConfigJava9Parser#annotationTypeElementModifier}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAnnotationTypeElementModifier(GraphConfigJava9Parser.AnnotationTypeElementModifierContext ctx);
	/**
	 * Visit a parse tree produced by {@link GraphConfigJava9Parser#defaultValue}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDefaultValue(GraphConfigJava9Parser.DefaultValueContext ctx);
	/**
	 * Visit a parse tree produced by {@link GraphConfigJava9Parser#annotation}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAnnotation(GraphConfigJava9Parser.AnnotationContext ctx);
	/**
	 * Visit a parse tree produced by {@link GraphConfigJava9Parser#normalAnnotation}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitNormalAnnotation(GraphConfigJava9Parser.NormalAnnotationContext ctx);
	/**
	 * Visit a parse tree produced by {@link GraphConfigJava9Parser#elementValuePairList}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitElementValuePairList(GraphConfigJava9Parser.ElementValuePairListContext ctx);
	/**
	 * Visit a parse tree produced by {@link GraphConfigJava9Parser#elementValuePair}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitElementValuePair(GraphConfigJava9Parser.ElementValuePairContext ctx);
	/**
	 * Visit a parse tree produced by {@link GraphConfigJava9Parser#elementValue}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitElementValue(GraphConfigJava9Parser.ElementValueContext ctx);
	/**
	 * Visit a parse tree produced by {@link GraphConfigJava9Parser#elementValueArrayInitializer}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitElementValueArrayInitializer(GraphConfigJava9Parser.ElementValueArrayInitializerContext ctx);
	/**
	 * Visit a parse tree produced by {@link GraphConfigJava9Parser#elementValueList}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitElementValueList(GraphConfigJava9Parser.ElementValueListContext ctx);
	/**
	 * Visit a parse tree produced by {@link GraphConfigJava9Parser#markerAnnotation}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMarkerAnnotation(GraphConfigJava9Parser.MarkerAnnotationContext ctx);
	/**
	 * Visit a parse tree produced by {@link GraphConfigJava9Parser#singleElementAnnotation}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSingleElementAnnotation(GraphConfigJava9Parser.SingleElementAnnotationContext ctx);
	/**
	 * Visit a parse tree produced by {@link GraphConfigJava9Parser#arrayInitializer}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitArrayInitializer(GraphConfigJava9Parser.ArrayInitializerContext ctx);
	/**
	 * Visit a parse tree produced by {@link GraphConfigJava9Parser#variableInitializerList}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitVariableInitializerList(GraphConfigJava9Parser.VariableInitializerListContext ctx);
	/**
	 * Visit a parse tree produced by {@link GraphConfigJava9Parser#block}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBlock(GraphConfigJava9Parser.BlockContext ctx);
	/**
	 * Visit a parse tree produced by {@link GraphConfigJava9Parser#blockStatements}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBlockStatements(GraphConfigJava9Parser.BlockStatementsContext ctx);
	/**
	 * Visit a parse tree produced by {@link GraphConfigJava9Parser#blockStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBlockStatement(GraphConfigJava9Parser.BlockStatementContext ctx);
	/**
	 * Visit a parse tree produced by {@link GraphConfigJava9Parser#localVariableDeclarationStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLocalVariableDeclarationStatement(GraphConfigJava9Parser.LocalVariableDeclarationStatementContext ctx);
	/**
	 * Visit a parse tree produced by {@link GraphConfigJava9Parser#localVariableDeclaration}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLocalVariableDeclaration(GraphConfigJava9Parser.LocalVariableDeclarationContext ctx);
	/**
	 * Visit a parse tree produced by {@link GraphConfigJava9Parser#statement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitStatement(GraphConfigJava9Parser.StatementContext ctx);
	/**
	 * Visit a parse tree produced by {@link GraphConfigJava9Parser#statementNoShortIf}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitStatementNoShortIf(GraphConfigJava9Parser.StatementNoShortIfContext ctx);
	/**
	 * Visit a parse tree produced by {@link GraphConfigJava9Parser#statementWithoutTrailingSubstatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitStatementWithoutTrailingSubstatement(GraphConfigJava9Parser.StatementWithoutTrailingSubstatementContext ctx);
	/**
	 * Visit a parse tree produced by {@link GraphConfigJava9Parser#emptyStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitEmptyStatement(GraphConfigJava9Parser.EmptyStatementContext ctx);
	/**
	 * Visit a parse tree produced by {@link GraphConfigJava9Parser#labeledStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLabeledStatement(GraphConfigJava9Parser.LabeledStatementContext ctx);
	/**
	 * Visit a parse tree produced by {@link GraphConfigJava9Parser#labeledStatementNoShortIf}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLabeledStatementNoShortIf(GraphConfigJava9Parser.LabeledStatementNoShortIfContext ctx);
	/**
	 * Visit a parse tree produced by {@link GraphConfigJava9Parser#expressionStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExpressionStatement(GraphConfigJava9Parser.ExpressionStatementContext ctx);
	/**
	 * Visit a parse tree produced by {@link GraphConfigJava9Parser#statementExpression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitStatementExpression(GraphConfigJava9Parser.StatementExpressionContext ctx);
	/**
	 * Visit a parse tree produced by {@link GraphConfigJava9Parser#ifThenStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIfThenStatement(GraphConfigJava9Parser.IfThenStatementContext ctx);
	/**
	 * Visit a parse tree produced by {@link GraphConfigJava9Parser#ifThenElseStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIfThenElseStatement(GraphConfigJava9Parser.IfThenElseStatementContext ctx);
	/**
	 * Visit a parse tree produced by {@link GraphConfigJava9Parser#ifThenElseStatementNoShortIf}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIfThenElseStatementNoShortIf(GraphConfigJava9Parser.IfThenElseStatementNoShortIfContext ctx);
	/**
	 * Visit a parse tree produced by {@link GraphConfigJava9Parser#assertStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAssertStatement(GraphConfigJava9Parser.AssertStatementContext ctx);
	/**
	 * Visit a parse tree produced by {@link GraphConfigJava9Parser#switchStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSwitchStatement(GraphConfigJava9Parser.SwitchStatementContext ctx);
	/**
	 * Visit a parse tree produced by {@link GraphConfigJava9Parser#switchBlock}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSwitchBlock(GraphConfigJava9Parser.SwitchBlockContext ctx);
	/**
	 * Visit a parse tree produced by {@link GraphConfigJava9Parser#switchBlockStatementGroup}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSwitchBlockStatementGroup(GraphConfigJava9Parser.SwitchBlockStatementGroupContext ctx);
	/**
	 * Visit a parse tree produced by {@link GraphConfigJava9Parser#switchLabels}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSwitchLabels(GraphConfigJava9Parser.SwitchLabelsContext ctx);
	/**
	 * Visit a parse tree produced by {@link GraphConfigJava9Parser#switchLabel}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSwitchLabel(GraphConfigJava9Parser.SwitchLabelContext ctx);
	/**
	 * Visit a parse tree produced by {@link GraphConfigJava9Parser#enumConstantName}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitEnumConstantName(GraphConfigJava9Parser.EnumConstantNameContext ctx);
	/**
	 * Visit a parse tree produced by {@link GraphConfigJava9Parser#whileStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitWhileStatement(GraphConfigJava9Parser.WhileStatementContext ctx);
	/**
	 * Visit a parse tree produced by {@link GraphConfigJava9Parser#whileStatementNoShortIf}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitWhileStatementNoShortIf(GraphConfigJava9Parser.WhileStatementNoShortIfContext ctx);
	/**
	 * Visit a parse tree produced by {@link GraphConfigJava9Parser#doStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDoStatement(GraphConfigJava9Parser.DoStatementContext ctx);
	/**
	 * Visit a parse tree produced by {@link GraphConfigJava9Parser#forStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitForStatement(GraphConfigJava9Parser.ForStatementContext ctx);
	/**
	 * Visit a parse tree produced by {@link GraphConfigJava9Parser#forStatementNoShortIf}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitForStatementNoShortIf(GraphConfigJava9Parser.ForStatementNoShortIfContext ctx);
	/**
	 * Visit a parse tree produced by {@link GraphConfigJava9Parser#basicForStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBasicForStatement(GraphConfigJava9Parser.BasicForStatementContext ctx);
	/**
	 * Visit a parse tree produced by {@link GraphConfigJava9Parser#basicForStatementNoShortIf}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBasicForStatementNoShortIf(GraphConfigJava9Parser.BasicForStatementNoShortIfContext ctx);
	/**
	 * Visit a parse tree produced by {@link GraphConfigJava9Parser#forInit}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitForInit(GraphConfigJava9Parser.ForInitContext ctx);
	/**
	 * Visit a parse tree produced by {@link GraphConfigJava9Parser#forUpdate}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitForUpdate(GraphConfigJava9Parser.ForUpdateContext ctx);
	/**
	 * Visit a parse tree produced by {@link GraphConfigJava9Parser#statementExpressionList}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitStatementExpressionList(GraphConfigJava9Parser.StatementExpressionListContext ctx);
	/**
	 * Visit a parse tree produced by {@link GraphConfigJava9Parser#enhancedForStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitEnhancedForStatement(GraphConfigJava9Parser.EnhancedForStatementContext ctx);
	/**
	 * Visit a parse tree produced by {@link GraphConfigJava9Parser#enhancedForStatementNoShortIf}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitEnhancedForStatementNoShortIf(GraphConfigJava9Parser.EnhancedForStatementNoShortIfContext ctx);
	/**
	 * Visit a parse tree produced by {@link GraphConfigJava9Parser#breakStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBreakStatement(GraphConfigJava9Parser.BreakStatementContext ctx);
	/**
	 * Visit a parse tree produced by {@link GraphConfigJava9Parser#continueStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitContinueStatement(GraphConfigJava9Parser.ContinueStatementContext ctx);
	/**
	 * Visit a parse tree produced by {@link GraphConfigJava9Parser#returnStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitReturnStatement(GraphConfigJava9Parser.ReturnStatementContext ctx);
	/**
	 * Visit a parse tree produced by {@link GraphConfigJava9Parser#throwStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitThrowStatement(GraphConfigJava9Parser.ThrowStatementContext ctx);
	/**
	 * Visit a parse tree produced by {@link GraphConfigJava9Parser#synchronizedStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSynchronizedStatement(GraphConfigJava9Parser.SynchronizedStatementContext ctx);
	/**
	 * Visit a parse tree produced by {@link GraphConfigJava9Parser#tryStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTryStatement(GraphConfigJava9Parser.TryStatementContext ctx);
	/**
	 * Visit a parse tree produced by {@link GraphConfigJava9Parser#catches}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCatches(GraphConfigJava9Parser.CatchesContext ctx);
	/**
	 * Visit a parse tree produced by {@link GraphConfigJava9Parser#catchClause}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCatchClause(GraphConfigJava9Parser.CatchClauseContext ctx);
	/**
	 * Visit a parse tree produced by {@link GraphConfigJava9Parser#catchFormalParameter}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCatchFormalParameter(GraphConfigJava9Parser.CatchFormalParameterContext ctx);
	/**
	 * Visit a parse tree produced by {@link GraphConfigJava9Parser#catchType}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCatchType(GraphConfigJava9Parser.CatchTypeContext ctx);
	/**
	 * Visit a parse tree produced by {@link GraphConfigJava9Parser#finally_}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFinally_(GraphConfigJava9Parser.Finally_Context ctx);
	/**
	 * Visit a parse tree produced by {@link GraphConfigJava9Parser#tryWithResourcesStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTryWithResourcesStatement(GraphConfigJava9Parser.TryWithResourcesStatementContext ctx);
	/**
	 * Visit a parse tree produced by {@link GraphConfigJava9Parser#resourceSpecification}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitResourceSpecification(GraphConfigJava9Parser.ResourceSpecificationContext ctx);
	/**
	 * Visit a parse tree produced by {@link GraphConfigJava9Parser#resourceList}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitResourceList(GraphConfigJava9Parser.ResourceListContext ctx);
	/**
	 * Visit a parse tree produced by {@link GraphConfigJava9Parser#resource}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitResource(GraphConfigJava9Parser.ResourceContext ctx);
	/**
	 * Visit a parse tree produced by {@link GraphConfigJava9Parser#variableAccess}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitVariableAccess(GraphConfigJava9Parser.VariableAccessContext ctx);
	/**
	 * Visit a parse tree produced by {@link GraphConfigJava9Parser#primary}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPrimary(GraphConfigJava9Parser.PrimaryContext ctx);
	/**
	 * Visit a parse tree produced by {@link GraphConfigJava9Parser#primaryNoNewArray}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPrimaryNoNewArray(GraphConfigJava9Parser.PrimaryNoNewArrayContext ctx);
	/**
	 * Visit a parse tree produced by {@link GraphConfigJava9Parser#primaryNoNewArray_lf_arrayAccess}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPrimaryNoNewArray_lf_arrayAccess(GraphConfigJava9Parser.PrimaryNoNewArray_lf_arrayAccessContext ctx);
	/**
	 * Visit a parse tree produced by {@link GraphConfigJava9Parser#primaryNoNewArray_lfno_arrayAccess}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPrimaryNoNewArray_lfno_arrayAccess(GraphConfigJava9Parser.PrimaryNoNewArray_lfno_arrayAccessContext ctx);
	/**
	 * Visit a parse tree produced by {@link GraphConfigJava9Parser#primaryNoNewArray_lf_primary}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPrimaryNoNewArray_lf_primary(GraphConfigJava9Parser.PrimaryNoNewArray_lf_primaryContext ctx);
	/**
	 * Visit a parse tree produced by {@link GraphConfigJava9Parser#primaryNoNewArray_lf_primary_lf_arrayAccess_lf_primary}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPrimaryNoNewArray_lf_primary_lf_arrayAccess_lf_primary(GraphConfigJava9Parser.PrimaryNoNewArray_lf_primary_lf_arrayAccess_lf_primaryContext ctx);
	/**
	 * Visit a parse tree produced by {@link GraphConfigJava9Parser#primaryNoNewArray_lf_primary_lfno_arrayAccess_lf_primary}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPrimaryNoNewArray_lf_primary_lfno_arrayAccess_lf_primary(GraphConfigJava9Parser.PrimaryNoNewArray_lf_primary_lfno_arrayAccess_lf_primaryContext ctx);
	/**
	 * Visit a parse tree produced by {@link GraphConfigJava9Parser#primaryNoNewArray_lfno_primary}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPrimaryNoNewArray_lfno_primary(GraphConfigJava9Parser.PrimaryNoNewArray_lfno_primaryContext ctx);
	/**
	 * Visit a parse tree produced by {@link GraphConfigJava9Parser#primaryNoNewArray_lfno_primary_lf_arrayAccess_lfno_primary}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPrimaryNoNewArray_lfno_primary_lf_arrayAccess_lfno_primary(GraphConfigJava9Parser.PrimaryNoNewArray_lfno_primary_lf_arrayAccess_lfno_primaryContext ctx);
	/**
	 * Visit a parse tree produced by {@link GraphConfigJava9Parser#primaryNoNewArray_lfno_primary_lfno_arrayAccess_lfno_primary}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPrimaryNoNewArray_lfno_primary_lfno_arrayAccess_lfno_primary(GraphConfigJava9Parser.PrimaryNoNewArray_lfno_primary_lfno_arrayAccess_lfno_primaryContext ctx);
	/**
	 * Visit a parse tree produced by {@link GraphConfigJava9Parser#classLiteral}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitClassLiteral(GraphConfigJava9Parser.ClassLiteralContext ctx);
	/**
	 * Visit a parse tree produced by {@link GraphConfigJava9Parser#classInstanceCreationExpression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitClassInstanceCreationExpression(GraphConfigJava9Parser.ClassInstanceCreationExpressionContext ctx);
	/**
	 * Visit a parse tree produced by {@link GraphConfigJava9Parser#classInstanceCreationExpression_lf_primary}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitClassInstanceCreationExpression_lf_primary(GraphConfigJava9Parser.ClassInstanceCreationExpression_lf_primaryContext ctx);
	/**
	 * Visit a parse tree produced by {@link GraphConfigJava9Parser#classInstanceCreationExpression_lfno_primary}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitClassInstanceCreationExpression_lfno_primary(GraphConfigJava9Parser.ClassInstanceCreationExpression_lfno_primaryContext ctx);
	/**
	 * Visit a parse tree produced by {@link GraphConfigJava9Parser#typeArgumentsOrDiamond}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTypeArgumentsOrDiamond(GraphConfigJava9Parser.TypeArgumentsOrDiamondContext ctx);
	/**
	 * Visit a parse tree produced by {@link GraphConfigJava9Parser#fieldAccess}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFieldAccess(GraphConfigJava9Parser.FieldAccessContext ctx);
	/**
	 * Visit a parse tree produced by {@link GraphConfigJava9Parser#fieldAccess_lf_primary}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFieldAccess_lf_primary(GraphConfigJava9Parser.FieldAccess_lf_primaryContext ctx);
	/**
	 * Visit a parse tree produced by {@link GraphConfigJava9Parser#fieldAccess_lfno_primary}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFieldAccess_lfno_primary(GraphConfigJava9Parser.FieldAccess_lfno_primaryContext ctx);
	/**
	 * Visit a parse tree produced by {@link GraphConfigJava9Parser#arrayAccess}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitArrayAccess(GraphConfigJava9Parser.ArrayAccessContext ctx);
	/**
	 * Visit a parse tree produced by {@link GraphConfigJava9Parser#arrayAccess_lf_primary}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitArrayAccess_lf_primary(GraphConfigJava9Parser.ArrayAccess_lf_primaryContext ctx);
	/**
	 * Visit a parse tree produced by {@link GraphConfigJava9Parser#arrayAccess_lfno_primary}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitArrayAccess_lfno_primary(GraphConfigJava9Parser.ArrayAccess_lfno_primaryContext ctx);
	/**
	 * Visit a parse tree produced by {@link GraphConfigJava9Parser#methodInvocation}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMethodInvocation(GraphConfigJava9Parser.MethodInvocationContext ctx);
	/**
	 * Visit a parse tree produced by {@link GraphConfigJava9Parser#methodInvocation_lf_primary}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMethodInvocation_lf_primary(GraphConfigJava9Parser.MethodInvocation_lf_primaryContext ctx);
	/**
	 * Visit a parse tree produced by {@link GraphConfigJava9Parser#methodInvocation_lfno_primary}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMethodInvocation_lfno_primary(GraphConfigJava9Parser.MethodInvocation_lfno_primaryContext ctx);
	/**
	 * Visit a parse tree produced by {@link GraphConfigJava9Parser#argumentList}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitArgumentList(GraphConfigJava9Parser.ArgumentListContext ctx);
	/**
	 * Visit a parse tree produced by {@link GraphConfigJava9Parser#methodReference}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMethodReference(GraphConfigJava9Parser.MethodReferenceContext ctx);
	/**
	 * Visit a parse tree produced by {@link GraphConfigJava9Parser#methodReference_lf_primary}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMethodReference_lf_primary(GraphConfigJava9Parser.MethodReference_lf_primaryContext ctx);
	/**
	 * Visit a parse tree produced by {@link GraphConfigJava9Parser#methodReference_lfno_primary}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMethodReference_lfno_primary(GraphConfigJava9Parser.MethodReference_lfno_primaryContext ctx);
	/**
	 * Visit a parse tree produced by {@link GraphConfigJava9Parser#arrayCreationExpression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitArrayCreationExpression(GraphConfigJava9Parser.ArrayCreationExpressionContext ctx);
	/**
	 * Visit a parse tree produced by {@link GraphConfigJava9Parser#dimExprs}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDimExprs(GraphConfigJava9Parser.DimExprsContext ctx);
	/**
	 * Visit a parse tree produced by {@link GraphConfigJava9Parser#dimExpr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDimExpr(GraphConfigJava9Parser.DimExprContext ctx);
	/**
	 * Visit a parse tree produced by {@link GraphConfigJava9Parser#constantExpression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitConstantExpression(GraphConfigJava9Parser.ConstantExpressionContext ctx);
	/**
	 * Visit a parse tree produced by {@link GraphConfigJava9Parser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExpression(GraphConfigJava9Parser.ExpressionContext ctx);
	/**
	 * Visit a parse tree produced by {@link GraphConfigJava9Parser#lambdaExpression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLambdaExpression(GraphConfigJava9Parser.LambdaExpressionContext ctx);
	/**
	 * Visit a parse tree produced by {@link GraphConfigJava9Parser#lambdaParameters}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLambdaParameters(GraphConfigJava9Parser.LambdaParametersContext ctx);
	/**
	 * Visit a parse tree produced by {@link GraphConfigJava9Parser#inferredFormalParameterList}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitInferredFormalParameterList(GraphConfigJava9Parser.InferredFormalParameterListContext ctx);
	/**
	 * Visit a parse tree produced by {@link GraphConfigJava9Parser#lambdaBody}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLambdaBody(GraphConfigJava9Parser.LambdaBodyContext ctx);
	/**
	 * Visit a parse tree produced by {@link GraphConfigJava9Parser#assignmentExpression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAssignmentExpression(GraphConfigJava9Parser.AssignmentExpressionContext ctx);
	/**
	 * Visit a parse tree produced by {@link GraphConfigJava9Parser#assignment}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAssignment(GraphConfigJava9Parser.AssignmentContext ctx);
	/**
	 * Visit a parse tree produced by {@link GraphConfigJava9Parser#leftHandSide}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLeftHandSide(GraphConfigJava9Parser.LeftHandSideContext ctx);
	/**
	 * Visit a parse tree produced by {@link GraphConfigJava9Parser#assignmentOperator}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAssignmentOperator(GraphConfigJava9Parser.AssignmentOperatorContext ctx);
	/**
	 * Visit a parse tree produced by {@link GraphConfigJava9Parser#conditionalExpression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitConditionalExpression(GraphConfigJava9Parser.ConditionalExpressionContext ctx);
	/**
	 * Visit a parse tree produced by {@link GraphConfigJava9Parser#conditionalOrExpression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitConditionalOrExpression(GraphConfigJava9Parser.ConditionalOrExpressionContext ctx);
	/**
	 * Visit a parse tree produced by {@link GraphConfigJava9Parser#conditionalAndExpression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitConditionalAndExpression(GraphConfigJava9Parser.ConditionalAndExpressionContext ctx);
	/**
	 * Visit a parse tree produced by {@link GraphConfigJava9Parser#inclusiveOrExpression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitInclusiveOrExpression(GraphConfigJava9Parser.InclusiveOrExpressionContext ctx);
	/**
	 * Visit a parse tree produced by {@link GraphConfigJava9Parser#exclusiveOrExpression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExclusiveOrExpression(GraphConfigJava9Parser.ExclusiveOrExpressionContext ctx);
	/**
	 * Visit a parse tree produced by {@link GraphConfigJava9Parser#andExpression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAndExpression(GraphConfigJava9Parser.AndExpressionContext ctx);
	/**
	 * Visit a parse tree produced by {@link GraphConfigJava9Parser#equalityExpression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitEqualityExpression(GraphConfigJava9Parser.EqualityExpressionContext ctx);
	/**
	 * Visit a parse tree produced by {@link GraphConfigJava9Parser#relationalExpression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitRelationalExpression(GraphConfigJava9Parser.RelationalExpressionContext ctx);
	/**
	 * Visit a parse tree produced by {@link GraphConfigJava9Parser#shiftExpression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitShiftExpression(GraphConfigJava9Parser.ShiftExpressionContext ctx);
	/**
	 * Visit a parse tree produced by {@link GraphConfigJava9Parser#additiveExpression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAdditiveExpression(GraphConfigJava9Parser.AdditiveExpressionContext ctx);
	/**
	 * Visit a parse tree produced by {@link GraphConfigJava9Parser#multiplicativeExpression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMultiplicativeExpression(GraphConfigJava9Parser.MultiplicativeExpressionContext ctx);
	/**
	 * Visit a parse tree produced by {@link GraphConfigJava9Parser#unaryExpression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitUnaryExpression(GraphConfigJava9Parser.UnaryExpressionContext ctx);
	/**
	 * Visit a parse tree produced by {@link GraphConfigJava9Parser#preIncrementExpression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPreIncrementExpression(GraphConfigJava9Parser.PreIncrementExpressionContext ctx);
	/**
	 * Visit a parse tree produced by {@link GraphConfigJava9Parser#preDecrementExpression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPreDecrementExpression(GraphConfigJava9Parser.PreDecrementExpressionContext ctx);
	/**
	 * Visit a parse tree produced by {@link GraphConfigJava9Parser#unaryExpressionNotPlusMinus}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitUnaryExpressionNotPlusMinus(GraphConfigJava9Parser.UnaryExpressionNotPlusMinusContext ctx);
	/**
	 * Visit a parse tree produced by {@link GraphConfigJava9Parser#postfixExpression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPostfixExpression(GraphConfigJava9Parser.PostfixExpressionContext ctx);
	/**
	 * Visit a parse tree produced by {@link GraphConfigJava9Parser#postIncrementExpression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPostIncrementExpression(GraphConfigJava9Parser.PostIncrementExpressionContext ctx);
	/**
	 * Visit a parse tree produced by {@link GraphConfigJava9Parser#postIncrementExpression_lf_postfixExpression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPostIncrementExpression_lf_postfixExpression(GraphConfigJava9Parser.PostIncrementExpression_lf_postfixExpressionContext ctx);
	/**
	 * Visit a parse tree produced by {@link GraphConfigJava9Parser#postDecrementExpression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPostDecrementExpression(GraphConfigJava9Parser.PostDecrementExpressionContext ctx);
	/**
	 * Visit a parse tree produced by {@link GraphConfigJava9Parser#postDecrementExpression_lf_postfixExpression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPostDecrementExpression_lf_postfixExpression(GraphConfigJava9Parser.PostDecrementExpression_lf_postfixExpressionContext ctx);
	/**
	 * Visit a parse tree produced by {@link GraphConfigJava9Parser#castExpression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCastExpression(GraphConfigJava9Parser.CastExpressionContext ctx);
}
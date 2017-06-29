// Generated from KotlinParser.g4 by ANTLR 4.7
package ru.fix.completable.reactor.parser.java.antlr;
import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link KotlinParser}.
 */
public interface KotlinParserListener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by {@link KotlinParser#kotlinFile}.
	 * @param ctx the parse tree
	 */
	void enterKotlinFile(KotlinParser.KotlinFileContext ctx);
	/**
	 * Exit a parse tree produced by {@link KotlinParser#kotlinFile}.
	 * @param ctx the parse tree
	 */
	void exitKotlinFile(KotlinParser.KotlinFileContext ctx);
	/**
	 * Enter a parse tree produced by {@link KotlinParser#preamble}.
	 * @param ctx the parse tree
	 */
	void enterPreamble(KotlinParser.PreambleContext ctx);
	/**
	 * Exit a parse tree produced by {@link KotlinParser#preamble}.
	 * @param ctx the parse tree
	 */
	void exitPreamble(KotlinParser.PreambleContext ctx);
	/**
	 * Enter a parse tree produced by {@link KotlinParser#packageHeader}.
	 * @param ctx the parse tree
	 */
	void enterPackageHeader(KotlinParser.PackageHeaderContext ctx);
	/**
	 * Exit a parse tree produced by {@link KotlinParser#packageHeader}.
	 * @param ctx the parse tree
	 */
	void exitPackageHeader(KotlinParser.PackageHeaderContext ctx);
	/**
	 * Enter a parse tree produced by {@link KotlinParser#importHeader}.
	 * @param ctx the parse tree
	 */
	void enterImportHeader(KotlinParser.ImportHeaderContext ctx);
	/**
	 * Exit a parse tree produced by {@link KotlinParser#importHeader}.
	 * @param ctx the parse tree
	 */
	void exitImportHeader(KotlinParser.ImportHeaderContext ctx);
	/**
	 * Enter a parse tree produced by {@link KotlinParser#toplevelObject}.
	 * @param ctx the parse tree
	 */
	void enterToplevelObject(KotlinParser.ToplevelObjectContext ctx);
	/**
	 * Exit a parse tree produced by {@link KotlinParser#toplevelObject}.
	 * @param ctx the parse tree
	 */
	void exitToplevelObject(KotlinParser.ToplevelObjectContext ctx);
	/**
	 * Enter a parse tree produced by {@link KotlinParser#memberDeclaration}.
	 * @param ctx the parse tree
	 */
	void enterMemberDeclaration(KotlinParser.MemberDeclarationContext ctx);
	/**
	 * Exit a parse tree produced by {@link KotlinParser#memberDeclaration}.
	 * @param ctx the parse tree
	 */
	void exitMemberDeclaration(KotlinParser.MemberDeclarationContext ctx);
	/**
	 * Enter a parse tree produced by {@link KotlinParser#optionalProjection}.
	 * @param ctx the parse tree
	 */
	void enterOptionalProjection(KotlinParser.OptionalProjectionContext ctx);
	/**
	 * Exit a parse tree produced by {@link KotlinParser#optionalProjection}.
	 * @param ctx the parse tree
	 */
	void exitOptionalProjection(KotlinParser.OptionalProjectionContext ctx);
	/**
	 * Enter a parse tree produced by {@link KotlinParser#typeParameters}.
	 * @param ctx the parse tree
	 */
	void enterTypeParameters(KotlinParser.TypeParametersContext ctx);
	/**
	 * Exit a parse tree produced by {@link KotlinParser#typeParameters}.
	 * @param ctx the parse tree
	 */
	void exitTypeParameters(KotlinParser.TypeParametersContext ctx);
	/**
	 * Enter a parse tree produced by {@link KotlinParser#typeParameter}.
	 * @param ctx the parse tree
	 */
	void enterTypeParameter(KotlinParser.TypeParameterContext ctx);
	/**
	 * Exit a parse tree produced by {@link KotlinParser#typeParameter}.
	 * @param ctx the parse tree
	 */
	void exitTypeParameter(KotlinParser.TypeParameterContext ctx);
	/**
	 * Enter a parse tree produced by {@link KotlinParser#typeArguments}.
	 * @param ctx the parse tree
	 */
	void enterTypeArguments(KotlinParser.TypeArgumentsContext ctx);
	/**
	 * Exit a parse tree produced by {@link KotlinParser#typeArguments}.
	 * @param ctx the parse tree
	 */
	void exitTypeArguments(KotlinParser.TypeArgumentsContext ctx);
	/**
	 * Enter a parse tree produced by {@link KotlinParser#typeConstraints}.
	 * @param ctx the parse tree
	 */
	void enterTypeConstraints(KotlinParser.TypeConstraintsContext ctx);
	/**
	 * Exit a parse tree produced by {@link KotlinParser#typeConstraints}.
	 * @param ctx the parse tree
	 */
	void exitTypeConstraints(KotlinParser.TypeConstraintsContext ctx);
	/**
	 * Enter a parse tree produced by {@link KotlinParser#typeConstraint}.
	 * @param ctx the parse tree
	 */
	void enterTypeConstraint(KotlinParser.TypeConstraintContext ctx);
	/**
	 * Exit a parse tree produced by {@link KotlinParser#typeConstraint}.
	 * @param ctx the parse tree
	 */
	void exitTypeConstraint(KotlinParser.TypeConstraintContext ctx);
	/**
	 * Enter a parse tree produced by {@link KotlinParser#type}.
	 * @param ctx the parse tree
	 */
	void enterType(KotlinParser.TypeContext ctx);
	/**
	 * Exit a parse tree produced by {@link KotlinParser#type}.
	 * @param ctx the parse tree
	 */
	void exitType(KotlinParser.TypeContext ctx);
	/**
	 * Enter a parse tree produced by {@link KotlinParser#typeDescriptor}.
	 * @param ctx the parse tree
	 */
	void enterTypeDescriptor(KotlinParser.TypeDescriptorContext ctx);
	/**
	 * Exit a parse tree produced by {@link KotlinParser#typeDescriptor}.
	 * @param ctx the parse tree
	 */
	void exitTypeDescriptor(KotlinParser.TypeDescriptorContext ctx);
	/**
	 * Enter a parse tree produced by {@link KotlinParser#userType}.
	 * @param ctx the parse tree
	 */
	void enterUserType(KotlinParser.UserTypeContext ctx);
	/**
	 * Exit a parse tree produced by {@link KotlinParser#userType}.
	 * @param ctx the parse tree
	 */
	void exitUserType(KotlinParser.UserTypeContext ctx);
	/**
	 * Enter a parse tree produced by {@link KotlinParser#simpleUserType_typeParam}.
	 * @param ctx the parse tree
	 */
	void enterSimpleUserType_typeParam(KotlinParser.SimpleUserType_typeParamContext ctx);
	/**
	 * Exit a parse tree produced by {@link KotlinParser#simpleUserType_typeParam}.
	 * @param ctx the parse tree
	 */
	void exitSimpleUserType_typeParam(KotlinParser.SimpleUserType_typeParamContext ctx);
	/**
	 * Enter a parse tree produced by {@link KotlinParser#simpleUserType}.
	 * @param ctx the parse tree
	 */
	void enterSimpleUserType(KotlinParser.SimpleUserTypeContext ctx);
	/**
	 * Exit a parse tree produced by {@link KotlinParser#simpleUserType}.
	 * @param ctx the parse tree
	 */
	void exitSimpleUserType(KotlinParser.SimpleUserTypeContext ctx);
	/**
	 * Enter a parse tree produced by {@link KotlinParser#variableDeclarationEntry}.
	 * @param ctx the parse tree
	 */
	void enterVariableDeclarationEntry(KotlinParser.VariableDeclarationEntryContext ctx);
	/**
	 * Exit a parse tree produced by {@link KotlinParser#variableDeclarationEntry}.
	 * @param ctx the parse tree
	 */
	void exitVariableDeclarationEntry(KotlinParser.VariableDeclarationEntryContext ctx);
	/**
	 * Enter a parse tree produced by {@link KotlinParser#functionType_paramOrType}.
	 * @param ctx the parse tree
	 */
	void enterFunctionType_paramOrType(KotlinParser.FunctionType_paramOrTypeContext ctx);
	/**
	 * Exit a parse tree produced by {@link KotlinParser#functionType_paramOrType}.
	 * @param ctx the parse tree
	 */
	void exitFunctionType_paramOrType(KotlinParser.FunctionType_paramOrTypeContext ctx);
	/**
	 * Enter a parse tree produced by {@link KotlinParser#extensionFunctionTypeNoReceiver}.
	 * @param ctx the parse tree
	 */
	void enterExtensionFunctionTypeNoReceiver(KotlinParser.ExtensionFunctionTypeNoReceiverContext ctx);
	/**
	 * Exit a parse tree produced by {@link KotlinParser#extensionFunctionTypeNoReceiver}.
	 * @param ctx the parse tree
	 */
	void exitExtensionFunctionTypeNoReceiver(KotlinParser.ExtensionFunctionTypeNoReceiverContext ctx);
	/**
	 * Enter a parse tree produced by {@link KotlinParser#functionTypeNoReceiver}.
	 * @param ctx the parse tree
	 */
	void enterFunctionTypeNoReceiver(KotlinParser.FunctionTypeNoReceiverContext ctx);
	/**
	 * Exit a parse tree produced by {@link KotlinParser#functionTypeNoReceiver}.
	 * @param ctx the parse tree
	 */
	void exitFunctionTypeNoReceiver(KotlinParser.FunctionTypeNoReceiverContext ctx);
	/**
	 * Enter a parse tree produced by {@link KotlinParser#multipleVariableDeclarations}.
	 * @param ctx the parse tree
	 */
	void enterMultipleVariableDeclarations(KotlinParser.MultipleVariableDeclarationsContext ctx);
	/**
	 * Exit a parse tree produced by {@link KotlinParser#multipleVariableDeclarations}.
	 * @param ctx the parse tree
	 */
	void exitMultipleVariableDeclarations(KotlinParser.MultipleVariableDeclarationsContext ctx);
	/**
	 * Enter a parse tree produced by {@link KotlinParser#propertyDeclaration}.
	 * @param ctx the parse tree
	 */
	void enterPropertyDeclaration(KotlinParser.PropertyDeclarationContext ctx);
	/**
	 * Exit a parse tree produced by {@link KotlinParser#propertyDeclaration}.
	 * @param ctx the parse tree
	 */
	void exitPropertyDeclaration(KotlinParser.PropertyDeclarationContext ctx);
	/**
	 * Enter a parse tree produced by {@link KotlinParser#getter}.
	 * @param ctx the parse tree
	 */
	void enterGetter(KotlinParser.GetterContext ctx);
	/**
	 * Exit a parse tree produced by {@link KotlinParser#getter}.
	 * @param ctx the parse tree
	 */
	void exitGetter(KotlinParser.GetterContext ctx);
	/**
	 * Enter a parse tree produced by {@link KotlinParser#setter}.
	 * @param ctx the parse tree
	 */
	void enterSetter(KotlinParser.SetterContext ctx);
	/**
	 * Exit a parse tree produced by {@link KotlinParser#setter}.
	 * @param ctx the parse tree
	 */
	void exitSetter(KotlinParser.SetterContext ctx);
	/**
	 * Enter a parse tree produced by {@link KotlinParser#modifiers}.
	 * @param ctx the parse tree
	 */
	void enterModifiers(KotlinParser.ModifiersContext ctx);
	/**
	 * Exit a parse tree produced by {@link KotlinParser#modifiers}.
	 * @param ctx the parse tree
	 */
	void exitModifiers(KotlinParser.ModifiersContext ctx);
	/**
	 * Enter a parse tree produced by {@link KotlinParser#modifier}.
	 * @param ctx the parse tree
	 */
	void enterModifier(KotlinParser.ModifierContext ctx);
	/**
	 * Exit a parse tree produced by {@link KotlinParser#modifier}.
	 * @param ctx the parse tree
	 */
	void exitModifier(KotlinParser.ModifierContext ctx);
	/**
	 * Enter a parse tree produced by {@link KotlinParser#modifierKeyword}.
	 * @param ctx the parse tree
	 */
	void enterModifierKeyword(KotlinParser.ModifierKeywordContext ctx);
	/**
	 * Exit a parse tree produced by {@link KotlinParser#modifierKeyword}.
	 * @param ctx the parse tree
	 */
	void exitModifierKeyword(KotlinParser.ModifierKeywordContext ctx);
	/**
	 * Enter a parse tree produced by {@link KotlinParser#hierarchyModifier}.
	 * @param ctx the parse tree
	 */
	void enterHierarchyModifier(KotlinParser.HierarchyModifierContext ctx);
	/**
	 * Exit a parse tree produced by {@link KotlinParser#hierarchyModifier}.
	 * @param ctx the parse tree
	 */
	void exitHierarchyModifier(KotlinParser.HierarchyModifierContext ctx);
	/**
	 * Enter a parse tree produced by {@link KotlinParser#classModifier}.
	 * @param ctx the parse tree
	 */
	void enterClassModifier(KotlinParser.ClassModifierContext ctx);
	/**
	 * Exit a parse tree produced by {@link KotlinParser#classModifier}.
	 * @param ctx the parse tree
	 */
	void exitClassModifier(KotlinParser.ClassModifierContext ctx);
	/**
	 * Enter a parse tree produced by {@link KotlinParser#accessModifier}.
	 * @param ctx the parse tree
	 */
	void enterAccessModifier(KotlinParser.AccessModifierContext ctx);
	/**
	 * Exit a parse tree produced by {@link KotlinParser#accessModifier}.
	 * @param ctx the parse tree
	 */
	void exitAccessModifier(KotlinParser.AccessModifierContext ctx);
	/**
	 * Enter a parse tree produced by {@link KotlinParser#varianceAnnotation}.
	 * @param ctx the parse tree
	 */
	void enterVarianceAnnotation(KotlinParser.VarianceAnnotationContext ctx);
	/**
	 * Exit a parse tree produced by {@link KotlinParser#varianceAnnotation}.
	 * @param ctx the parse tree
	 */
	void exitVarianceAnnotation(KotlinParser.VarianceAnnotationContext ctx);
	/**
	 * Enter a parse tree produced by {@link KotlinParser#annotations}.
	 * @param ctx the parse tree
	 */
	void enterAnnotations(KotlinParser.AnnotationsContext ctx);
	/**
	 * Exit a parse tree produced by {@link KotlinParser#annotations}.
	 * @param ctx the parse tree
	 */
	void exitAnnotations(KotlinParser.AnnotationsContext ctx);
	/**
	 * Enter a parse tree produced by {@link KotlinParser#annotation}.
	 * @param ctx the parse tree
	 */
	void enterAnnotation(KotlinParser.AnnotationContext ctx);
	/**
	 * Exit a parse tree produced by {@link KotlinParser#annotation}.
	 * @param ctx the parse tree
	 */
	void exitAnnotation(KotlinParser.AnnotationContext ctx);
	/**
	 * Enter a parse tree produced by {@link KotlinParser#annotationUseSiteTarget}.
	 * @param ctx the parse tree
	 */
	void enterAnnotationUseSiteTarget(KotlinParser.AnnotationUseSiteTargetContext ctx);
	/**
	 * Exit a parse tree produced by {@link KotlinParser#annotationUseSiteTarget}.
	 * @param ctx the parse tree
	 */
	void exitAnnotationUseSiteTarget(KotlinParser.AnnotationUseSiteTargetContext ctx);
	/**
	 * Enter a parse tree produced by {@link KotlinParser#valueArgument}.
	 * @param ctx the parse tree
	 */
	void enterValueArgument(KotlinParser.ValueArgumentContext ctx);
	/**
	 * Exit a parse tree produced by {@link KotlinParser#valueArgument}.
	 * @param ctx the parse tree
	 */
	void exitValueArgument(KotlinParser.ValueArgumentContext ctx);
	/**
	 * Enter a parse tree produced by {@link KotlinParser#valueArguments}.
	 * @param ctx the parse tree
	 */
	void enterValueArguments(KotlinParser.ValueArgumentsContext ctx);
	/**
	 * Exit a parse tree produced by {@link KotlinParser#valueArguments}.
	 * @param ctx the parse tree
	 */
	void exitValueArguments(KotlinParser.ValueArgumentsContext ctx);
	/**
	 * Enter a parse tree produced by {@link KotlinParser#unescapedAnnotation}.
	 * @param ctx the parse tree
	 */
	void enterUnescapedAnnotation(KotlinParser.UnescapedAnnotationContext ctx);
	/**
	 * Exit a parse tree produced by {@link KotlinParser#unescapedAnnotation}.
	 * @param ctx the parse tree
	 */
	void exitUnescapedAnnotation(KotlinParser.UnescapedAnnotationContext ctx);
	/**
	 * Enter a parse tree produced by {@link KotlinParser#jump}.
	 * @param ctx the parse tree
	 */
	void enterJump(KotlinParser.JumpContext ctx);
	/**
	 * Exit a parse tree produced by {@link KotlinParser#jump}.
	 * @param ctx the parse tree
	 */
	void exitJump(KotlinParser.JumpContext ctx);
	/**
	 * Enter a parse tree produced by {@link KotlinParser#labelReference}.
	 * @param ctx the parse tree
	 */
	void enterLabelReference(KotlinParser.LabelReferenceContext ctx);
	/**
	 * Exit a parse tree produced by {@link KotlinParser#labelReference}.
	 * @param ctx the parse tree
	 */
	void exitLabelReference(KotlinParser.LabelReferenceContext ctx);
	/**
	 * Enter a parse tree produced by {@link KotlinParser#labelDefinition}.
	 * @param ctx the parse tree
	 */
	void enterLabelDefinition(KotlinParser.LabelDefinitionContext ctx);
	/**
	 * Exit a parse tree produced by {@link KotlinParser#labelDefinition}.
	 * @param ctx the parse tree
	 */
	void exitLabelDefinition(KotlinParser.LabelDefinitionContext ctx);
	/**
	 * Enter a parse tree produced by {@link KotlinParser#parameter}.
	 * @param ctx the parse tree
	 */
	void enterParameter(KotlinParser.ParameterContext ctx);
	/**
	 * Exit a parse tree produced by {@link KotlinParser#parameter}.
	 * @param ctx the parse tree
	 */
	void exitParameter(KotlinParser.ParameterContext ctx);
	/**
	 * Enter a parse tree produced by {@link KotlinParser#functionParameter}.
	 * @param ctx the parse tree
	 */
	void enterFunctionParameter(KotlinParser.FunctionParameterContext ctx);
	/**
	 * Exit a parse tree produced by {@link KotlinParser#functionParameter}.
	 * @param ctx the parse tree
	 */
	void exitFunctionParameter(KotlinParser.FunctionParameterContext ctx);
	/**
	 * Enter a parse tree produced by {@link KotlinParser#primaryConstructor}.
	 * @param ctx the parse tree
	 */
	void enterPrimaryConstructor(KotlinParser.PrimaryConstructorContext ctx);
	/**
	 * Exit a parse tree produced by {@link KotlinParser#primaryConstructor}.
	 * @param ctx the parse tree
	 */
	void exitPrimaryConstructor(KotlinParser.PrimaryConstructorContext ctx);
	/**
	 * Enter a parse tree produced by {@link KotlinParser#secondaryConstructor}.
	 * @param ctx the parse tree
	 */
	void enterSecondaryConstructor(KotlinParser.SecondaryConstructorContext ctx);
	/**
	 * Exit a parse tree produced by {@link KotlinParser#secondaryConstructor}.
	 * @param ctx the parse tree
	 */
	void exitSecondaryConstructor(KotlinParser.SecondaryConstructorContext ctx);
	/**
	 * Enter a parse tree produced by {@link KotlinParser#constructorDelegationCall}.
	 * @param ctx the parse tree
	 */
	void enterConstructorDelegationCall(KotlinParser.ConstructorDelegationCallContext ctx);
	/**
	 * Exit a parse tree produced by {@link KotlinParser#constructorDelegationCall}.
	 * @param ctx the parse tree
	 */
	void exitConstructorDelegationCall(KotlinParser.ConstructorDelegationCallContext ctx);
	/**
	 * Enter a parse tree produced by {@link KotlinParser#callSuffix}.
	 * @param ctx the parse tree
	 */
	void enterCallSuffix(KotlinParser.CallSuffixContext ctx);
	/**
	 * Exit a parse tree produced by {@link KotlinParser#callSuffix}.
	 * @param ctx the parse tree
	 */
	void exitCallSuffix(KotlinParser.CallSuffixContext ctx);
	/**
	 * Enter a parse tree produced by {@link KotlinParser#constructorInvocation}.
	 * @param ctx the parse tree
	 */
	void enterConstructorInvocation(KotlinParser.ConstructorInvocationContext ctx);
	/**
	 * Exit a parse tree produced by {@link KotlinParser#constructorInvocation}.
	 * @param ctx the parse tree
	 */
	void exitConstructorInvocation(KotlinParser.ConstructorInvocationContext ctx);
	/**
	 * Enter a parse tree produced by {@link KotlinParser#explicitDelegation}.
	 * @param ctx the parse tree
	 */
	void enterExplicitDelegation(KotlinParser.ExplicitDelegationContext ctx);
	/**
	 * Exit a parse tree produced by {@link KotlinParser#explicitDelegation}.
	 * @param ctx the parse tree
	 */
	void exitExplicitDelegation(KotlinParser.ExplicitDelegationContext ctx);
	/**
	 * Enter a parse tree produced by {@link KotlinParser#delegationSpecifier}.
	 * @param ctx the parse tree
	 */
	void enterDelegationSpecifier(KotlinParser.DelegationSpecifierContext ctx);
	/**
	 * Exit a parse tree produced by {@link KotlinParser#delegationSpecifier}.
	 * @param ctx the parse tree
	 */
	void exitDelegationSpecifier(KotlinParser.DelegationSpecifierContext ctx);
	/**
	 * Enter a parse tree produced by {@link KotlinParser#classDeclaration}.
	 * @param ctx the parse tree
	 */
	void enterClassDeclaration(KotlinParser.ClassDeclarationContext ctx);
	/**
	 * Exit a parse tree produced by {@link KotlinParser#classDeclaration}.
	 * @param ctx the parse tree
	 */
	void exitClassDeclaration(KotlinParser.ClassDeclarationContext ctx);
	/**
	 * Enter a parse tree produced by {@link KotlinParser#supertypesSpecifiers}.
	 * @param ctx the parse tree
	 */
	void enterSupertypesSpecifiers(KotlinParser.SupertypesSpecifiersContext ctx);
	/**
	 * Exit a parse tree produced by {@link KotlinParser#supertypesSpecifiers}.
	 * @param ctx the parse tree
	 */
	void exitSupertypesSpecifiers(KotlinParser.SupertypesSpecifiersContext ctx);
	/**
	 * Enter a parse tree produced by {@link KotlinParser#objectDeclaration}.
	 * @param ctx the parse tree
	 */
	void enterObjectDeclaration(KotlinParser.ObjectDeclarationContext ctx);
	/**
	 * Exit a parse tree produced by {@link KotlinParser#objectDeclaration}.
	 * @param ctx the parse tree
	 */
	void exitObjectDeclaration(KotlinParser.ObjectDeclarationContext ctx);
	/**
	 * Enter a parse tree produced by {@link KotlinParser#companionObject}.
	 * @param ctx the parse tree
	 */
	void enterCompanionObject(KotlinParser.CompanionObjectContext ctx);
	/**
	 * Exit a parse tree produced by {@link KotlinParser#companionObject}.
	 * @param ctx the parse tree
	 */
	void exitCompanionObject(KotlinParser.CompanionObjectContext ctx);
	/**
	 * Enter a parse tree produced by {@link KotlinParser#classBody}.
	 * @param ctx the parse tree
	 */
	void enterClassBody(KotlinParser.ClassBodyContext ctx);
	/**
	 * Exit a parse tree produced by {@link KotlinParser#classBody}.
	 * @param ctx the parse tree
	 */
	void exitClassBody(KotlinParser.ClassBodyContext ctx);
	/**
	 * Enter a parse tree produced by {@link KotlinParser#members}.
	 * @param ctx the parse tree
	 */
	void enterMembers(KotlinParser.MembersContext ctx);
	/**
	 * Exit a parse tree produced by {@link KotlinParser#members}.
	 * @param ctx the parse tree
	 */
	void exitMembers(KotlinParser.MembersContext ctx);
	/**
	 * Enter a parse tree produced by {@link KotlinParser#valueParameters}.
	 * @param ctx the parse tree
	 */
	void enterValueParameters(KotlinParser.ValueParametersContext ctx);
	/**
	 * Exit a parse tree produced by {@link KotlinParser#valueParameters}.
	 * @param ctx the parse tree
	 */
	void exitValueParameters(KotlinParser.ValueParametersContext ctx);
	/**
	 * Enter a parse tree produced by {@link KotlinParser#functionDeclaration}.
	 * @param ctx the parse tree
	 */
	void enterFunctionDeclaration(KotlinParser.FunctionDeclarationContext ctx);
	/**
	 * Exit a parse tree produced by {@link KotlinParser#functionDeclaration}.
	 * @param ctx the parse tree
	 */
	void exitFunctionDeclaration(KotlinParser.FunctionDeclarationContext ctx);
	/**
	 * Enter a parse tree produced by {@link KotlinParser#statements}.
	 * @param ctx the parse tree
	 */
	void enterStatements(KotlinParser.StatementsContext ctx);
	/**
	 * Exit a parse tree produced by {@link KotlinParser#statements}.
	 * @param ctx the parse tree
	 */
	void exitStatements(KotlinParser.StatementsContext ctx);
	/**
	 * Enter a parse tree produced by {@link KotlinParser#functionBody}.
	 * @param ctx the parse tree
	 */
	void enterFunctionBody(KotlinParser.FunctionBodyContext ctx);
	/**
	 * Exit a parse tree produced by {@link KotlinParser#functionBody}.
	 * @param ctx the parse tree
	 */
	void exitFunctionBody(KotlinParser.FunctionBodyContext ctx);
	/**
	 * Enter a parse tree produced by {@link KotlinParser#block}.
	 * @param ctx the parse tree
	 */
	void enterBlock(KotlinParser.BlockContext ctx);
	/**
	 * Exit a parse tree produced by {@link KotlinParser#block}.
	 * @param ctx the parse tree
	 */
	void exitBlock(KotlinParser.BlockContext ctx);
	/**
	 * Enter a parse tree produced by {@link KotlinParser#anonymousInitializer}.
	 * @param ctx the parse tree
	 */
	void enterAnonymousInitializer(KotlinParser.AnonymousInitializerContext ctx);
	/**
	 * Exit a parse tree produced by {@link KotlinParser#anonymousInitializer}.
	 * @param ctx the parse tree
	 */
	void exitAnonymousInitializer(KotlinParser.AnonymousInitializerContext ctx);
	/**
	 * Enter a parse tree produced by {@link KotlinParser#enumClassBody}.
	 * @param ctx the parse tree
	 */
	void enterEnumClassBody(KotlinParser.EnumClassBodyContext ctx);
	/**
	 * Exit a parse tree produced by {@link KotlinParser#enumClassBody}.
	 * @param ctx the parse tree
	 */
	void exitEnumClassBody(KotlinParser.EnumClassBodyContext ctx);
	/**
	 * Enter a parse tree produced by {@link KotlinParser#enumEntries}.
	 * @param ctx the parse tree
	 */
	void enterEnumEntries(KotlinParser.EnumEntriesContext ctx);
	/**
	 * Exit a parse tree produced by {@link KotlinParser#enumEntries}.
	 * @param ctx the parse tree
	 */
	void exitEnumEntries(KotlinParser.EnumEntriesContext ctx);
	/**
	 * Enter a parse tree produced by {@link KotlinParser#enumEntry}.
	 * @param ctx the parse tree
	 */
	void enterEnumEntry(KotlinParser.EnumEntryContext ctx);
	/**
	 * Exit a parse tree produced by {@link KotlinParser#enumEntry}.
	 * @param ctx the parse tree
	 */
	void exitEnumEntry(KotlinParser.EnumEntryContext ctx);
	/**
	 * Enter a parse tree produced by {@link KotlinParser#ifExpression}.
	 * @param ctx the parse tree
	 */
	void enterIfExpression(KotlinParser.IfExpressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link KotlinParser#ifExpression}.
	 * @param ctx the parse tree
	 */
	void exitIfExpression(KotlinParser.IfExpressionContext ctx);
	/**
	 * Enter a parse tree produced by {@link KotlinParser#tryExpression}.
	 * @param ctx the parse tree
	 */
	void enterTryExpression(KotlinParser.TryExpressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link KotlinParser#tryExpression}.
	 * @param ctx the parse tree
	 */
	void exitTryExpression(KotlinParser.TryExpressionContext ctx);
	/**
	 * Enter a parse tree produced by {@link KotlinParser#catchBlock}.
	 * @param ctx the parse tree
	 */
	void enterCatchBlock(KotlinParser.CatchBlockContext ctx);
	/**
	 * Exit a parse tree produced by {@link KotlinParser#catchBlock}.
	 * @param ctx the parse tree
	 */
	void exitCatchBlock(KotlinParser.CatchBlockContext ctx);
	/**
	 * Enter a parse tree produced by {@link KotlinParser#finallyBlock}.
	 * @param ctx the parse tree
	 */
	void enterFinallyBlock(KotlinParser.FinallyBlockContext ctx);
	/**
	 * Exit a parse tree produced by {@link KotlinParser#finallyBlock}.
	 * @param ctx the parse tree
	 */
	void exitFinallyBlock(KotlinParser.FinallyBlockContext ctx);
	/**
	 * Enter a parse tree produced by {@link KotlinParser#loop}.
	 * @param ctx the parse tree
	 */
	void enterLoop(KotlinParser.LoopContext ctx);
	/**
	 * Exit a parse tree produced by {@link KotlinParser#loop}.
	 * @param ctx the parse tree
	 */
	void exitLoop(KotlinParser.LoopContext ctx);
	/**
	 * Enter a parse tree produced by {@link KotlinParser#forLoop}.
	 * @param ctx the parse tree
	 */
	void enterForLoop(KotlinParser.ForLoopContext ctx);
	/**
	 * Exit a parse tree produced by {@link KotlinParser#forLoop}.
	 * @param ctx the parse tree
	 */
	void exitForLoop(KotlinParser.ForLoopContext ctx);
	/**
	 * Enter a parse tree produced by {@link KotlinParser#whileLoop}.
	 * @param ctx the parse tree
	 */
	void enterWhileLoop(KotlinParser.WhileLoopContext ctx);
	/**
	 * Exit a parse tree produced by {@link KotlinParser#whileLoop}.
	 * @param ctx the parse tree
	 */
	void exitWhileLoop(KotlinParser.WhileLoopContext ctx);
	/**
	 * Enter a parse tree produced by {@link KotlinParser#doWhileLoop}.
	 * @param ctx the parse tree
	 */
	void enterDoWhileLoop(KotlinParser.DoWhileLoopContext ctx);
	/**
	 * Exit a parse tree produced by {@link KotlinParser#doWhileLoop}.
	 * @param ctx the parse tree
	 */
	void exitDoWhileLoop(KotlinParser.DoWhileLoopContext ctx);
	/**
	 * Enter a parse tree produced by {@link KotlinParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterExpression(KotlinParser.ExpressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link KotlinParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitExpression(KotlinParser.ExpressionContext ctx);
	/**
	 * Enter a parse tree produced by {@link KotlinParser#disjunction}.
	 * @param ctx the parse tree
	 */
	void enterDisjunction(KotlinParser.DisjunctionContext ctx);
	/**
	 * Exit a parse tree produced by {@link KotlinParser#disjunction}.
	 * @param ctx the parse tree
	 */
	void exitDisjunction(KotlinParser.DisjunctionContext ctx);
	/**
	 * Enter a parse tree produced by {@link KotlinParser#conjunction}.
	 * @param ctx the parse tree
	 */
	void enterConjunction(KotlinParser.ConjunctionContext ctx);
	/**
	 * Exit a parse tree produced by {@link KotlinParser#conjunction}.
	 * @param ctx the parse tree
	 */
	void exitConjunction(KotlinParser.ConjunctionContext ctx);
	/**
	 * Enter a parse tree produced by {@link KotlinParser#equalityComparison}.
	 * @param ctx the parse tree
	 */
	void enterEqualityComparison(KotlinParser.EqualityComparisonContext ctx);
	/**
	 * Exit a parse tree produced by {@link KotlinParser#equalityComparison}.
	 * @param ctx the parse tree
	 */
	void exitEqualityComparison(KotlinParser.EqualityComparisonContext ctx);
	/**
	 * Enter a parse tree produced by {@link KotlinParser#comparison}.
	 * @param ctx the parse tree
	 */
	void enterComparison(KotlinParser.ComparisonContext ctx);
	/**
	 * Exit a parse tree produced by {@link KotlinParser#comparison}.
	 * @param ctx the parse tree
	 */
	void exitComparison(KotlinParser.ComparisonContext ctx);
	/**
	 * Enter a parse tree produced by {@link KotlinParser#namedInfix}.
	 * @param ctx the parse tree
	 */
	void enterNamedInfix(KotlinParser.NamedInfixContext ctx);
	/**
	 * Exit a parse tree produced by {@link KotlinParser#namedInfix}.
	 * @param ctx the parse tree
	 */
	void exitNamedInfix(KotlinParser.NamedInfixContext ctx);
	/**
	 * Enter a parse tree produced by {@link KotlinParser#elvisExpression}.
	 * @param ctx the parse tree
	 */
	void enterElvisExpression(KotlinParser.ElvisExpressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link KotlinParser#elvisExpression}.
	 * @param ctx the parse tree
	 */
	void exitElvisExpression(KotlinParser.ElvisExpressionContext ctx);
	/**
	 * Enter a parse tree produced by {@link KotlinParser#infixFunctionCall}.
	 * @param ctx the parse tree
	 */
	void enterInfixFunctionCall(KotlinParser.InfixFunctionCallContext ctx);
	/**
	 * Exit a parse tree produced by {@link KotlinParser#infixFunctionCall}.
	 * @param ctx the parse tree
	 */
	void exitInfixFunctionCall(KotlinParser.InfixFunctionCallContext ctx);
	/**
	 * Enter a parse tree produced by {@link KotlinParser#rangeExpression}.
	 * @param ctx the parse tree
	 */
	void enterRangeExpression(KotlinParser.RangeExpressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link KotlinParser#rangeExpression}.
	 * @param ctx the parse tree
	 */
	void exitRangeExpression(KotlinParser.RangeExpressionContext ctx);
	/**
	 * Enter a parse tree produced by {@link KotlinParser#additiveExpression}.
	 * @param ctx the parse tree
	 */
	void enterAdditiveExpression(KotlinParser.AdditiveExpressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link KotlinParser#additiveExpression}.
	 * @param ctx the parse tree
	 */
	void exitAdditiveExpression(KotlinParser.AdditiveExpressionContext ctx);
	/**
	 * Enter a parse tree produced by {@link KotlinParser#multiplicativeExpression}.
	 * @param ctx the parse tree
	 */
	void enterMultiplicativeExpression(KotlinParser.MultiplicativeExpressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link KotlinParser#multiplicativeExpression}.
	 * @param ctx the parse tree
	 */
	void exitMultiplicativeExpression(KotlinParser.MultiplicativeExpressionContext ctx);
	/**
	 * Enter a parse tree produced by {@link KotlinParser#typeRHS}.
	 * @param ctx the parse tree
	 */
	void enterTypeRHS(KotlinParser.TypeRHSContext ctx);
	/**
	 * Exit a parse tree produced by {@link KotlinParser#typeRHS}.
	 * @param ctx the parse tree
	 */
	void exitTypeRHS(KotlinParser.TypeRHSContext ctx);
	/**
	 * Enter a parse tree produced by {@link KotlinParser#prefixUnaryExpression}.
	 * @param ctx the parse tree
	 */
	void enterPrefixUnaryExpression(KotlinParser.PrefixUnaryExpressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link KotlinParser#prefixUnaryExpression}.
	 * @param ctx the parse tree
	 */
	void exitPrefixUnaryExpression(KotlinParser.PrefixUnaryExpressionContext ctx);
	/**
	 * Enter a parse tree produced by {@link KotlinParser#postfixUnaryExpression}.
	 * @param ctx the parse tree
	 */
	void enterPostfixUnaryExpression(KotlinParser.PostfixUnaryExpressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link KotlinParser#postfixUnaryExpression}.
	 * @param ctx the parse tree
	 */
	void exitPostfixUnaryExpression(KotlinParser.PostfixUnaryExpressionContext ctx);
	/**
	 * Enter a parse tree produced by {@link KotlinParser#callableReference}.
	 * @param ctx the parse tree
	 */
	void enterCallableReference(KotlinParser.CallableReferenceContext ctx);
	/**
	 * Exit a parse tree produced by {@link KotlinParser#callableReference}.
	 * @param ctx the parse tree
	 */
	void exitCallableReference(KotlinParser.CallableReferenceContext ctx);
	/**
	 * Enter a parse tree produced by {@link KotlinParser#identifier}.
	 * @param ctx the parse tree
	 */
	void enterIdentifier(KotlinParser.IdentifierContext ctx);
	/**
	 * Exit a parse tree produced by {@link KotlinParser#identifier}.
	 * @param ctx the parse tree
	 */
	void exitIdentifier(KotlinParser.IdentifierContext ctx);
	/**
	 * Enter a parse tree produced by {@link KotlinParser#stringLiteral}.
	 * @param ctx the parse tree
	 */
	void enterStringLiteral(KotlinParser.StringLiteralContext ctx);
	/**
	 * Exit a parse tree produced by {@link KotlinParser#stringLiteral}.
	 * @param ctx the parse tree
	 */
	void exitStringLiteral(KotlinParser.StringLiteralContext ctx);
	/**
	 * Enter a parse tree produced by {@link KotlinParser#atomicExpression}.
	 * @param ctx the parse tree
	 */
	void enterAtomicExpression(KotlinParser.AtomicExpressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link KotlinParser#atomicExpression}.
	 * @param ctx the parse tree
	 */
	void exitAtomicExpression(KotlinParser.AtomicExpressionContext ctx);
	/**
	 * Enter a parse tree produced by {@link KotlinParser#literalConstant}.
	 * @param ctx the parse tree
	 */
	void enterLiteralConstant(KotlinParser.LiteralConstantContext ctx);
	/**
	 * Exit a parse tree produced by {@link KotlinParser#literalConstant}.
	 * @param ctx the parse tree
	 */
	void exitLiteralConstant(KotlinParser.LiteralConstantContext ctx);
	/**
	 * Enter a parse tree produced by {@link KotlinParser#declaration}.
	 * @param ctx the parse tree
	 */
	void enterDeclaration(KotlinParser.DeclarationContext ctx);
	/**
	 * Exit a parse tree produced by {@link KotlinParser#declaration}.
	 * @param ctx the parse tree
	 */
	void exitDeclaration(KotlinParser.DeclarationContext ctx);
	/**
	 * Enter a parse tree produced by {@link KotlinParser#statement}.
	 * @param ctx the parse tree
	 */
	void enterStatement(KotlinParser.StatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link KotlinParser#statement}.
	 * @param ctx the parse tree
	 */
	void exitStatement(KotlinParser.StatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link KotlinParser#multiplicativeOperation}.
	 * @param ctx the parse tree
	 */
	void enterMultiplicativeOperation(KotlinParser.MultiplicativeOperationContext ctx);
	/**
	 * Exit a parse tree produced by {@link KotlinParser#multiplicativeOperation}.
	 * @param ctx the parse tree
	 */
	void exitMultiplicativeOperation(KotlinParser.MultiplicativeOperationContext ctx);
	/**
	 * Enter a parse tree produced by {@link KotlinParser#additiveOperation}.
	 * @param ctx the parse tree
	 */
	void enterAdditiveOperation(KotlinParser.AdditiveOperationContext ctx);
	/**
	 * Exit a parse tree produced by {@link KotlinParser#additiveOperation}.
	 * @param ctx the parse tree
	 */
	void exitAdditiveOperation(KotlinParser.AdditiveOperationContext ctx);
	/**
	 * Enter a parse tree produced by {@link KotlinParser#inOperation}.
	 * @param ctx the parse tree
	 */
	void enterInOperation(KotlinParser.InOperationContext ctx);
	/**
	 * Exit a parse tree produced by {@link KotlinParser#inOperation}.
	 * @param ctx the parse tree
	 */
	void exitInOperation(KotlinParser.InOperationContext ctx);
	/**
	 * Enter a parse tree produced by {@link KotlinParser#typeOperation}.
	 * @param ctx the parse tree
	 */
	void enterTypeOperation(KotlinParser.TypeOperationContext ctx);
	/**
	 * Exit a parse tree produced by {@link KotlinParser#typeOperation}.
	 * @param ctx the parse tree
	 */
	void exitTypeOperation(KotlinParser.TypeOperationContext ctx);
	/**
	 * Enter a parse tree produced by {@link KotlinParser#isOperation}.
	 * @param ctx the parse tree
	 */
	void enterIsOperation(KotlinParser.IsOperationContext ctx);
	/**
	 * Exit a parse tree produced by {@link KotlinParser#isOperation}.
	 * @param ctx the parse tree
	 */
	void exitIsOperation(KotlinParser.IsOperationContext ctx);
	/**
	 * Enter a parse tree produced by {@link KotlinParser#comparisonOperation}.
	 * @param ctx the parse tree
	 */
	void enterComparisonOperation(KotlinParser.ComparisonOperationContext ctx);
	/**
	 * Exit a parse tree produced by {@link KotlinParser#comparisonOperation}.
	 * @param ctx the parse tree
	 */
	void exitComparisonOperation(KotlinParser.ComparisonOperationContext ctx);
	/**
	 * Enter a parse tree produced by {@link KotlinParser#equalityOperation}.
	 * @param ctx the parse tree
	 */
	void enterEqualityOperation(KotlinParser.EqualityOperationContext ctx);
	/**
	 * Exit a parse tree produced by {@link KotlinParser#equalityOperation}.
	 * @param ctx the parse tree
	 */
	void exitEqualityOperation(KotlinParser.EqualityOperationContext ctx);
	/**
	 * Enter a parse tree produced by {@link KotlinParser#assignmentOperator}.
	 * @param ctx the parse tree
	 */
	void enterAssignmentOperator(KotlinParser.AssignmentOperatorContext ctx);
	/**
	 * Exit a parse tree produced by {@link KotlinParser#assignmentOperator}.
	 * @param ctx the parse tree
	 */
	void exitAssignmentOperator(KotlinParser.AssignmentOperatorContext ctx);
	/**
	 * Enter a parse tree produced by {@link KotlinParser#prefixUnaryOperation}.
	 * @param ctx the parse tree
	 */
	void enterPrefixUnaryOperation(KotlinParser.PrefixUnaryOperationContext ctx);
	/**
	 * Exit a parse tree produced by {@link KotlinParser#prefixUnaryOperation}.
	 * @param ctx the parse tree
	 */
	void exitPrefixUnaryOperation(KotlinParser.PrefixUnaryOperationContext ctx);
	/**
	 * Enter a parse tree produced by {@link KotlinParser#postfixUnaryOperation}.
	 * @param ctx the parse tree
	 */
	void enterPostfixUnaryOperation(KotlinParser.PostfixUnaryOperationContext ctx);
	/**
	 * Exit a parse tree produced by {@link KotlinParser#postfixUnaryOperation}.
	 * @param ctx the parse tree
	 */
	void exitPostfixUnaryOperation(KotlinParser.PostfixUnaryOperationContext ctx);
	/**
	 * Enter a parse tree produced by {@link KotlinParser#annotatedLambda}.
	 * @param ctx the parse tree
	 */
	void enterAnnotatedLambda(KotlinParser.AnnotatedLambdaContext ctx);
	/**
	 * Exit a parse tree produced by {@link KotlinParser#annotatedLambda}.
	 * @param ctx the parse tree
	 */
	void exitAnnotatedLambda(KotlinParser.AnnotatedLambdaContext ctx);
	/**
	 * Enter a parse tree produced by {@link KotlinParser#memberAccessOperation}.
	 * @param ctx the parse tree
	 */
	void enterMemberAccessOperation(KotlinParser.MemberAccessOperationContext ctx);
	/**
	 * Exit a parse tree produced by {@link KotlinParser#memberAccessOperation}.
	 * @param ctx the parse tree
	 */
	void exitMemberAccessOperation(KotlinParser.MemberAccessOperationContext ctx);
	/**
	 * Enter a parse tree produced by {@link KotlinParser#functionLiteral}.
	 * @param ctx the parse tree
	 */
	void enterFunctionLiteral(KotlinParser.FunctionLiteralContext ctx);
	/**
	 * Exit a parse tree produced by {@link KotlinParser#functionLiteral}.
	 * @param ctx the parse tree
	 */
	void exitFunctionLiteral(KotlinParser.FunctionLiteralContext ctx);
	/**
	 * Enter a parse tree produced by {@link KotlinParser#arrayAccess}.
	 * @param ctx the parse tree
	 */
	void enterArrayAccess(KotlinParser.ArrayAccessContext ctx);
	/**
	 * Exit a parse tree produced by {@link KotlinParser#arrayAccess}.
	 * @param ctx the parse tree
	 */
	void exitArrayAccess(KotlinParser.ArrayAccessContext ctx);
	/**
	 * Enter a parse tree produced by {@link KotlinParser#objectLiteral}.
	 * @param ctx the parse tree
	 */
	void enterObjectLiteral(KotlinParser.ObjectLiteralContext ctx);
	/**
	 * Exit a parse tree produced by {@link KotlinParser#objectLiteral}.
	 * @param ctx the parse tree
	 */
	void exitObjectLiteral(KotlinParser.ObjectLiteralContext ctx);
	/**
	 * Enter a parse tree produced by {@link KotlinParser#when}.
	 * @param ctx the parse tree
	 */
	void enterWhen(KotlinParser.WhenContext ctx);
	/**
	 * Exit a parse tree produced by {@link KotlinParser#when}.
	 * @param ctx the parse tree
	 */
	void exitWhen(KotlinParser.WhenContext ctx);
	/**
	 * Enter a parse tree produced by {@link KotlinParser#whenEntry}.
	 * @param ctx the parse tree
	 */
	void enterWhenEntry(KotlinParser.WhenEntryContext ctx);
	/**
	 * Exit a parse tree produced by {@link KotlinParser#whenEntry}.
	 * @param ctx the parse tree
	 */
	void exitWhenEntry(KotlinParser.WhenEntryContext ctx);
	/**
	 * Enter a parse tree produced by {@link KotlinParser#whenCondition}.
	 * @param ctx the parse tree
	 */
	void enterWhenCondition(KotlinParser.WhenConditionContext ctx);
	/**
	 * Exit a parse tree produced by {@link KotlinParser#whenCondition}.
	 * @param ctx the parse tree
	 */
	void exitWhenCondition(KotlinParser.WhenConditionContext ctx);
}
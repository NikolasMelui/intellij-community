/*
 * Copyright 2003-2007 Dave Griffith, Bas Leijdekkers
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.siyeh.ig.bugs;

import com.intellij.psi.*;
import com.siyeh.InspectionGadgetsBundle;
import com.siyeh.ig.BaseInspection;
import com.siyeh.ig.BaseInspectionVisitor;
import com.siyeh.ig.psiutils.InheritanceUtil;
import org.jetbrains.annotations.NotNull;

public class InstanceofIncompatibleInterfaceInspection
        extends BaseInspection {

    @NotNull
    public String getDisplayName(){
        return InspectionGadgetsBundle.message(
                "instanceof.with.incompatible.interface.display.name");
    }

    @NotNull
    public String buildErrorString(Object... infos){
        return InspectionGadgetsBundle.message(
                "instanceof.with.incompatible.interface.problem.descriptor");
    }

    public BaseInspectionVisitor buildVisitor(){
        return new InstanceofIncompatibleInterfaceVisitor();
    }

    private static class InstanceofIncompatibleInterfaceVisitor
            extends BaseInspectionVisitor{

        public void visitInstanceOfExpression(
                @NotNull PsiInstanceOfExpression expression){
            super.visitInstanceOfExpression(expression);
            final PsiTypeElement castTypeElement = expression.getCheckType();
            if(castTypeElement == null){
                return;
            }
            final PsiType castType = castTypeElement.getType();
            if(!(castType instanceof PsiClassType)){
                return;
            }
            final PsiClassType castClassType = (PsiClassType)castType;
            final PsiExpression operand = expression.getOperand();
            final PsiType operandType = operand.getType();
            if(!(operandType instanceof PsiClassType)){
                return;
            }
            final PsiClassType operandClassType = (PsiClassType)operandType;
            final PsiClass castClass = castClassType.resolve();
            if(castClass == null){
                return;
            }
            if(!castClass.isInterface()){
                return;
            }
            final PsiClass operandClass = operandClassType.resolve();
            if(operandClass == null){
                return;
            }
            if(operandClass.isInterface()){
                return;
            }
            if(InheritanceUtil.existsMutualSubclass(operandClass, castClass)){
                return;
            }
            registerError(castTypeElement);
        }
    }
}
/*
 * iReport - Visual Designer for JasperReports.
 * Copyright (C) 2002 - 2009 Jaspersoft Corporation. All rights reserved.
 * http://www.jaspersoft.com
 *
 * Unless you have purchased a commercial license agreement from Jaspersoft,
 * the following license terms apply:
 *
 * This program is part of iReport.
 *
 * iReport is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * iReport is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with iReport. If not, see <http://www.gnu.org/licenses/>.
 */
package com.jaspersoft.ireport.designer.compiler;

import com.jaspersoft.ireport.designer.compiler.xml.SourceLocation;
import com.jaspersoft.ireport.designer.compiler.xml.SourceTraceDigester;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import net.sf.jasperreports.engine.JRExpression;
import net.sf.jasperreports.engine.design.JRCompilationSourceCode;
import net.sf.jasperreports.engine.design.JRCompilationUnit;
import net.sf.jasperreports.engine.design.JRJdtCompiler;
import org.eclipse.jdt.core.compiler.IProblem;
import org.eclipse.jdt.internal.compiler.CompilationResult;
import org.eclipse.jdt.internal.compiler.ICompilerRequestor;

/**
 *
 * @author gtoffoli
 */
public class ExtendedJRJdtCompiler extends JRJdtCompiler {
    
    private JasperReportErrorHandler errorHandler = null;
    private SourceTraceDigester digester = null;
    private ICompilerRequestor superCompilerRequestor = null;
    
    @Override
    protected ICompilerRequestor getCompilerRequestor(JRCompilationUnit[] units, StringBuffer problemBuffer)
    {
            return new CompilerRequestor(super.getCompilerRequestor(units, problemBuffer), units);
    }
    
    
    protected class CompilerRequestor implements ICompilerRequestor
	{
                private ICompilerRequestor superCompilerRequestor = null;
		private final Map expressionErrors = new HashMap();
		private final JRCompilationUnit[] units;

		protected CompilerRequestor(ICompilerRequestor superCompilerRequestor, JRCompilationUnit[] units)
		{
                        this.superCompilerRequestor = superCompilerRequestor;
			this.units = units;
		}

		public void acceptResult(CompilationResult result)
		{
                        if (superCompilerRequestor != null) superCompilerRequestor.acceptResult(result);
			if (result.hasErrors())
			{
				String className = String.valueOf(result.getCompilationUnit().getMainTypeName());
				
				JRCompilationUnit unit = null;
				for (int classIdx = 0; classIdx < units.length; ++classIdx)
				{
					if (className.equals(units[classIdx].getName()))
					{
						unit = units[classIdx];
						break;
					}
				}
				
				IProblem[] errors = result.getErrors();
				for (int i = 0; i < errors.length; i++)
				{
					IProblem problem = errors[i];
					int line = problem.getSourceLineNumber();
					JRCompilationSourceCode sourceCode = unit.getCompilationSource();
					JRExpression expression = sourceCode.getExpressionAtLine(line);
					if (expression == null)
					{
						getErrorHandler().addMarker( problem, null);
					}
					else if (addExpressionError(expression, problem))
					{
						SourceLocation location = getDigester().getLocation(expression);
						getErrorHandler().addMarker(problem, expression, location);
					}
				}
			}
		}
		
        @SuppressWarnings("unchecked")
		protected boolean addExpressionError(JRExpression expression, IProblem problem)
		{
			Set errors = (Set) expressionErrors.get(expression);
			if (errors == null)
			{
				errors = new HashSet();
				expressionErrors.put(expression, errors);
			}
			return errors.add(new ExpressionErrorKey(problem));
		}
	}
	
	protected static class ExpressionErrorKey
	{
		private final IProblem problem;
		private final int hash;
		
		public ExpressionErrorKey(IProblem problem)
		{
			this.problem = problem;
			this.hash = computeHash();
		}

		private int computeHash()
		{
			int h = problem.getMessage().hashCode();
			return h;
		}
		
		public int hashCode()
		{
			return hash;
		}
		
		public boolean equals(Object o)
		{
			if (o == null || !(o instanceof ExpressionErrorKey) || this.hash != o.hashCode())
			{
				return false;
			}
			
			if (this == o)
			{
				return true;
			}
			
			ExpressionErrorKey k = (ExpressionErrorKey) o;
			return problem.getMessage().equals(k.problem.getMessage());
		}
	}

    public JasperReportErrorHandler getErrorHandler() {
        return errorHandler;
    }

    public void setErrorHandler(JasperReportErrorHandler errorHandler) {
        this.errorHandler = errorHandler;
    }

    public SourceTraceDigester getDigester() {
        return digester;
    }

    public void setDigester(SourceTraceDigester digester) {
        this.digester = digester;
    }
}

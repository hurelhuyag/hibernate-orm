/*
 * Hibernate, Relational Persistence for Idiomatic Java
 *
 * License: GNU Lesser General Public License (LGPL), version 2.1 or later
 * See the lgpl.txt file in the root directory or http://www.gnu.org/licenses/lgpl-2.1.html
 */
package org.hibernate.dialect.function.json;

import org.hibernate.query.ReturnableType;
import org.hibernate.sql.ast.SqlAstTranslator;
import org.hibernate.sql.ast.spi.SqlAppender;
import org.hibernate.sql.ast.tree.expression.JsonPathPassingClause;
import org.hibernate.sql.ast.tree.expression.JsonValueEmptyBehavior;
import org.hibernate.sql.ast.tree.expression.JsonValueErrorBehavior;
import org.hibernate.type.spi.TypeConfiguration;

/**
 * MySQL json_value function.
 */
public class MySQLJsonValueFunction extends JsonValueFunction {

	public MySQLJsonValueFunction(TypeConfiguration typeConfiguration) {
		super( typeConfiguration, true, false );
	}

	@Override
	protected void render(
			SqlAppender sqlAppender,
			JsonValueArguments arguments,
			ReturnableType<?> returnType,
			SqlAstTranslator<?> walker) {
		// json_extract errors by default
		if ( arguments.errorBehavior() != null && arguments.errorBehavior() != JsonValueErrorBehavior.ERROR
				|| arguments.emptyBehavior() == JsonValueEmptyBehavior.ERROR
				// Can't emulate DEFAULT ON EMPTY since we can't differentiate between a NULL value and EMPTY
				|| arguments.emptyBehavior() != null && arguments.emptyBehavior() != JsonValueEmptyBehavior.NULL ) {
			super.render( sqlAppender, arguments, returnType, walker );
		}
		else {
			if ( arguments.returningType() != null ) {
				sqlAppender.append( "cast(" );
			}
			sqlAppender.appendSql( "json_unquote(nullif(json_extract(" );
			arguments.jsonDocument().accept( walker );
			sqlAppender.appendSql( "," );
			final JsonPathPassingClause passingClause = arguments.passingClause();
			if ( passingClause == null ) {
				arguments.jsonPath().accept( walker );
			}
			else {
				JsonPathHelper.appendJsonPathConcatPassingClause(
						sqlAppender,
						arguments.jsonPath(),
						passingClause, walker
				);
			}
			sqlAppender.appendSql( "),cast('null' as json)))" );
			if ( arguments.returningType() != null ) {
				sqlAppender.appendSql( " as " );
				arguments.returningType().accept( walker );
				sqlAppender.appendSql( ')' );
			}
		}
	}
}

/*
 * Hibernate, Relational Persistence for Idiomatic Java
 *
 * License: GNU Lesser General Public License (LGPL), version 2.1 or later.
 * See the lgpl.txt file in the root directory or http://www.gnu.org/licenses/lgpl-2.1.html.
 */
package org.hibernate.boot.models.annotations.internal;

import java.lang.annotation.Annotation;
import java.util.Map;

import org.hibernate.models.spi.SourceModelBuildingContext;

import jakarta.persistence.Inheritance;

@SuppressWarnings({ "ClassExplicitlyAnnotation", "unused" })
@jakarta.annotation.Generated("org.hibernate.orm.build.annotations.ClassGeneratorProcessor")
public class InheritanceJpaAnnotation implements Inheritance {
	private jakarta.persistence.InheritanceType strategy;

	/**
	 * Used in creating dynamic annotation instances (e.g. from XML)
	 */
	public InheritanceJpaAnnotation(SourceModelBuildingContext modelContext) {
		this.strategy = jakarta.persistence.InheritanceType.SINGLE_TABLE;
	}

	/**
	 * Used in creating annotation instances from JDK variant
	 */
	public InheritanceJpaAnnotation(Inheritance annotation, SourceModelBuildingContext modelContext) {
		this.strategy = annotation.strategy();
	}

	/**
	 * Used in creating annotation instances from Jandex variant
	 */
	public InheritanceJpaAnnotation(Map<String, Object> attributeValues, SourceModelBuildingContext modelContext) {
		this.strategy = (jakarta.persistence.InheritanceType) attributeValues.get( "strategy" );
	}

	@Override
	public Class<? extends Annotation> annotationType() {
		return Inheritance.class;
	}

	@Override
	public jakarta.persistence.InheritanceType strategy() {
		return strategy;
	}

	public void strategy(jakarta.persistence.InheritanceType value) {
		this.strategy = value;
	}


}

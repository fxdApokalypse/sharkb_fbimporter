/**
 * <p>
 * Contains the already implemented Facebook importers.
 * All new importer should go into this package in order found by the underlying framework.
 * </p>
 *
 * <p>
*  User provided importers could go into another package if the importer developer provides
 * a Spring Bean configuration at the classpath <code>"classpath*:/META-INF/spring/fb-importer-plugin.xml"</code>
 * in which a developer configure the desired package name of it's importers.
 *
 * Such a file looks like this:
 * </p>
 *
 * <pre>
 *     {@code
 *     	<?xml version="1.0" encoding="UTF-8"?>
 *		<beans xmlns="http://www.springframework.org/schema/beans"
 *		xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
 *		xmlns:context="http://www.springframework.org/schema/context"
 *		xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd
 *			<context:component-scan base-package="YOUR.PACKAGE.NAME"/>
 *		</beans>
 *
 *     }
 * </pre>
 *
 */
package net.sharkfw.apps.fb.importers;
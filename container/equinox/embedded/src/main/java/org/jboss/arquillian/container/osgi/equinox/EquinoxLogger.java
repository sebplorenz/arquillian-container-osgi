/*
 * JBoss, Home of Professional Open Source
 * Copyright 2005, JBoss Inc., and individual contributors as indicated
 * by the @authors tag. See the copyright.txt in the distribution for a
 * full listing of individual contributors.
 *
 * This is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation; either version 2.1 of
 * the License, or (at your option) any later version.
 *
 * This software is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this software; if not, write to the Free
 * Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
 * 02110-1301 USA, or see the FSF site: http://www.fsf.org.
 */
package org.jboss.arquillian.container.osgi.equinox;

import org.eclipse.equinox.log.internal.ExtendedLogServiceImpl;
import org.eclipse.equinox.log.internal.LoggerImpl;
import org.osgi.framework.ServiceReference;
import org.osgi.service.log.LogService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * An integration with the Equinox Logger.
 *
 * This Logger gets registered with the Equinox framework and delegates
 * framework log messages to slf4j.
 *
 */
public class EquinoxLogger extends LoggerImpl {

    // Provide logging
    private final Logger log = LoggerFactory.getLogger(EquinoxLogger.class
            .getPackage().getName());

    public EquinoxLogger(ExtendedLogServiceImpl logServiceImpl, String name) {
        super(logServiceImpl, name);
    }

    @SuppressWarnings("rawtypes")
    @Override
    public void log(ServiceReference sref, int level, String msg,
            Throwable throwable) {
        if (sref != null)
            msg = sref + ": " + msg;

        // An unresolved bundle causes a WARNING that comes with an exception
        // Currently we log WARNING exceptions at DEBUG level

        if (level == LogService.LOG_DEBUG) {
            log.debug(msg, throwable);
        } else if (level == LogService.LOG_INFO) {
            log.info(msg, throwable);
        } else if (level == LogService.LOG_WARNING) {
            log.warn(msg);
            if (throwable != null)
                log.debug(msg, throwable);
        } else if (level == LogService.LOG_ERROR) {
            log.error(msg, throwable);
        }
    }

    @Override
    public void log(Object context, int level, String msg, Throwable throwable) {
        if (context != null)
            msg = context + ": " + msg;

        // An unresolved bundle causes a WARNING that comes with an exception
        // Currently we log WARNING exceptions at DEBUG level

        if (level == LogService.LOG_DEBUG) {
            log.debug(msg, throwable);
        } else if (level == LogService.LOG_INFO) {
            log.info(msg, throwable);
        } else if (level == LogService.LOG_WARNING) {
            log.warn(msg);
            if (throwable != null)
                log.debug(msg, throwable);
        } else if (level == LogService.LOG_ERROR) {
            log.error(msg, throwable);
        }
    }

}
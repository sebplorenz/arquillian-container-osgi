/*
 * JBoss, Home of Professional Open Source
 * Copyright 2009, Red Hat Middleware LLC, and individual contributors
 * by the @authors tag. See the copyright.txt in the distribution for a
 * full listing of individual contributors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.jboss.arquillian.container.osgi.equinox;

import java.util.HashMap;
import java.util.Map;

import org.jboss.arquillian.container.osgi.EmbeddedDeployableContainer;
import org.jboss.arquillian.container.osgi.OSGiContainerConfiguration;
import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.BundleException;
import org.osgi.framework.launch.Framework;
import org.osgi.framework.launch.FrameworkFactory;
import org.osgi.service.log.LogService;

/**
 * EquinoxDeployableContainer
 *
 */
public class EquinoxEmbeddedDeployableContainer extends
        EmbeddedDeployableContainer<OSGiContainerConfiguration> {

    private EquinoxLogger logger;

    @Override
    public Class<OSGiContainerConfiguration> getConfigurationClass() {
        return OSGiContainerConfiguration.class;
    }

    @Override
    @SuppressWarnings({ "rawtypes", "unchecked" })
    protected Framework createFramework(OSGiContainerConfiguration conf) {

        // Add the logger if not given
        Map config = new HashMap(conf.getFrameworkConfiguration());
        // if (config.get(FelixConstants.LOG_LOGGER_PROP) == null) {
        // config.put(FelixConstants.LOG_LOGGER_PROP, logger);
        // }

        FrameworkFactory factory = conf.getFrameworkFactory();
        return factory.newFramework(config);
    }

    @Override
    protected BundleContext startFramework() throws BundleException {
        BundleContext bundleContext = super.startFramework();

        // Process the auto install settings
        Map<String, String> config = getContainerConfiguration()
                .getFrameworkConfiguration();
        if (config.containsKey("osgi.bundles")) {
            String[] bundleNames = config.get("osgi.bundles").split(",");
            for (String bundleName : bundleNames) {
                Bundle bundle = bundleContext.installBundle(bundleName);
                bundle.start();
            }
        }

        // logger = new EquinoxLogger(new ExtendedLogServiceImpl(new
        // ExtendedLogServiceFactory(new ExtendedLogReaderServiceFactory()), ),
        // EquinoxEmbeddedDeployableContainer.class.getName());

        return bundleContext;
    }

//    @Override
//    protected ContainerLogger getLogger() {
//        return new AbstractContainerLogger() {
//            @Override
//            public void log(Level level, String message, Throwable th) {
//                switch (level) {
//                case DEBUG:
////                    logger.log(LogService.LOG_DEBUG, message, th);
//                    break;
//                case INFO:
////                    logger.log(LogService.LOG_INFO, message, th);
//                    break;
//                case WARN:
////                    logger.log(LogService.LOG_WARNING, message, th);
//                    break;
//                case ERROR:
////                    logger.log(LogService.LOG_ERROR, message, th);
//                    break;
//                }
//            }
//        };
//    }
}

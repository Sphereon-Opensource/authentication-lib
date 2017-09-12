package com.sphereon.libs.authentication.impl.osgi;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

public class Activator implements BundleActivator {

    /**
     * Implements BundleActivator.start().
     *
     * @param bundleContext - the framework context for the bundle.
     **/

    public void start(BundleContext bundleContext) {
        System.out.println("Sphereon TokenAPI activated");
    }


    /**
     * Implements BundleActivator.stop()
     *
     * @param bundleContext - the framework context for the bundle
     **/
    public void stop(BundleContext bundleContext) {
        System.out.println("Sphereon TokenAPI deactivated");
    }
}